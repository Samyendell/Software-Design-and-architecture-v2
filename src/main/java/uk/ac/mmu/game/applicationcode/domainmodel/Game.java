package uk.ac.mmu.game.applicationcode.domainmodel;

import uk.ac.mmu.game.applicationcode.domainmodel.board.Board;
import uk.ac.mmu.game.applicationcode.domainmodel.board.Position;
import uk.ac.mmu.game.applicationcode.domainmodel.events.*;
import uk.ac.mmu.game.applicationcode.domainmodel.player.Player;
import uk.ac.mmu.game.applicationcode.domainmodel.rules.AllowHitRule;
import uk.ac.mmu.game.applicationcode.domainmodel.rules.EndRule;
import uk.ac.mmu.game.applicationcode.domainmodel.rules.HitRule;
import uk.ac.mmu.game.applicationcode.domainmodel.states.GameOverState;
import uk.ac.mmu.game.applicationcode.domainmodel.states.GameState;
import uk.ac.mmu.game.applicationcode.domainmodel.states.ReadyState;
import java.util.ArrayList;
import java.util.List;

/**
 * Game - Aggregate Root.
 * 
 * Orchestrates all game components using multiple design patterns:
 * - State Pattern: Manages game lifecycle
 * - Observer Pattern: Notifies observers of events
 * - Strategy Pattern: Delegates to pluggable rules
 * 
 * Responsibilities:
 * - Maintains game state and player turn order
 * - Enforces game rules through strategy objects
 * - Publishes events to observers
 * - Ensures invariants are maintained
 */
public class Game {
  private final GameId id;
  private final GameConfiguration configuration;
  private final Board board;
  private final List<Player> players;
  private final EndRule endRule;
  private final HitRule hitRule;
  private final List<GameObserver> observers;
  
  private GameState currentState;
  private int currentPlayerIndex;
  private int totalTurns;
  private Player winner;

  public Game(GameId id, GameConfiguration configuration, Board board,
              List<Player> players, EndRule endRule, HitRule hitRule) {
    this.id = id;
    this.configuration = configuration;
    this.board = board;
    this.players = new ArrayList<>(players);
    this.endRule = endRule;
    this.hitRule = hitRule;
    this.observers = new ArrayList<>();
    this.currentState = ReadyState.getInstance();
    this.currentPlayerIndex = 0;
    this.totalTurns = 0;
    this.winner = null;

    // Initialize board occupancy
    board.updateOccupancy(players);
  }

  // ============================================
  // PUBLIC API
  // ============================================

  /**
   * Process a dice roll for the current player.
   * Delegates to current state.
   */
  public void takeTurn(int diceRoll) {
    currentState.takeTurn(this, diceRoll);
  }

  /**
   * Register an observer to receive game events.
   */
  public void addObserver(GameObserver observer) {
    observers.add(observer);
  }

  /**
   * Remove an observer.
   */
  public void removeObserver(GameObserver observer) {
    observers.remove(observer);
  }

  // ============================================
  // STATE TRANSITIONS
  // ============================================

  /**
   * Transition to a new state.
   * Notifies observers of the transition.
   */
  public void transitionToState(GameState newState) {
    String oldStateName = currentState.getStateName();
    this.currentState = newState;
    notifyObservers(new StateTransitionEvent(oldStateName, 
                                             newState.getStateName()));
  }

  // ============================================
  // TURN PROCESSING (called by InPlayState)
  // ============================================

  /**
   * Process a turn in the InPlay state.
   * This is the main game logic.
   */
  public void processTurn(int diceRoll) {
    Player currentPlayer = getCurrentPlayer();
    Position positionBefore = currentPlayer.getCurrentPosition();
    
    // Notify turn start
    notifyObservers(new TurnStartEvent(
        currentPlayer.getName(),
        diceRoll,
        positionBefore,
        currentPlayer.getTurnCount() + 1
    ));
    
    // Calculate target position
    Position targetPosition = currentPlayer.getPositionAfterMove(diceRoll);
    
    // Check for hit
    boolean hitAllowed = hitRule.processHit(currentPlayer, targetPosition, board);
    
    if (!hitAllowed) {
      // Hit caused forfeit
      notifyObservers(new ForfeitEvent(
          currentPlayer.getName(),
          ForfeitEvent.ForfeitReason.HIT,
          targetPosition,
          currentPlayer.getCurrentPosition()
      ));
      incrementTotalTurns();
      moveToNextPlayer();
      return;
    }
    
    // Notify about hit if it occurred but was allowed
    if (hitRule instanceof AllowHitRule) {
      AllowHitRule allowHitRule = (AllowHitRule) hitRule;
      if (allowHitRule.isHit(targetPosition, board, currentPlayer)) {
        notifyObservers(new HitEvent(targetPosition));
      }
    }
    
    // Process move according to end rule
    boolean moved = endRule.processMove(currentPlayer, diceRoll);
    
    if (!moved) {
      // Overshoot caused forfeit
      notifyObservers(new ForfeitEvent(
          currentPlayer.getName(),
          ForfeitEvent.ForfeitReason.OVERSHOOT,
          targetPosition,
          currentPlayer.getCurrentPosition()
      ));
    } else {
      // Successful move
      notifyObservers(new MoveEvent(
          currentPlayer.getName(),
          positionBefore,
          currentPlayer.getCurrentPosition()
      ));
    }
    
    // Update board occupancy
    board.updateOccupancy(players);
    
    // Notify turn end
    notifyObservers(new TurnEndEvent(
        currentPlayer.getName(),
        currentPlayer.getTurnCount()
    ));
    
    // Check for win
    if (currentPlayer.isAtEnd()) {
      winner = currentPlayer;
      notifyObservers(new WinEvent(
          winner.getName(),
          winner.getTurnCount(),
          totalTurns + 1
      ));
      transitionToState(GameOverState.getInstance());
      return;
    }
    
    incrementTotalTurns();
    moveToNextPlayer();
  }

  /**
   * Notify observers that game is over.
   * Called by GameOverState.
   */
  public void notifyGameOver() {
    notifyObservers(new GameOverMessageEvent());
  }

  // ============================================
  // HELPER METHODS
  // ============================================

  private void notifyObservers(GameEvent event) {
    for (GameObserver observer : observers) {
      observer.onGameEvent(event);
    }
  }

  private void incrementTotalTurns() {
    totalTurns++;
  }

  private void moveToNextPlayer() {
    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
  }

  // ============================================
  // GETTERS
  // ============================================

  public GameId getId() { return id; }
  public GameConfiguration getConfiguration() { return configuration; }
  public Board getBoard() { return board; }
  public List<Player> getPlayers() { return List.copyOf(players); }
  public Player getCurrentPlayer() { return players.get(currentPlayerIndex); }
  public int getTotalTurns() { return totalTurns; }
  public Player getWinner() { return winner; }
  public boolean isGameOver() { return winner != null; }
  public GameState getCurrentState() { return currentState; }

  /**
   * Create a record of this game for persistence.
   */
  public GameRecord toRecord() {
    if (winner == null) {
      throw new IllegalStateException("Cannot create record of unfinished game");
    }
    return new GameRecord(
        id,
        configuration,
        winner.getColour(),
        winner.getTurnCount(),
        totalTurns
    );
  }
}