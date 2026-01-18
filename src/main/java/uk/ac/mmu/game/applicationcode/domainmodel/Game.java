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

    public Game(GameId id, GameConfiguration configuration, Board board, List<Player> players, EndRule endRule,
                HitRule hitRule) {
        this.id = id;
        this.configuration = configuration;
        this.board = board;
        this.players = new ArrayList<>(players);
        this.endRule = endRule;
        this.hitRule = hitRule;
        this.observers = new ArrayList<>();
        this.currentState = new ReadyState();
        this.currentPlayerIndex = 0;
        this.totalTurns = 0;
        this.winner = null;

        board.updateOccupancy(players);
    }

    public void takeTurn(int diceRoll) {
        currentState.takeTurn(this, diceRoll);
    }

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void transitionToState(GameState newState) {
        String oldStateName = currentState.getStateName();
        this.currentState = newState;
        notifyObservers(new StateTransitionEvent(oldStateName, newState.getStateName()));
    }

    public void processTurn(int diceRoll) {
        Player currentPlayer = getCurrentPlayer();
        Position positionBefore = currentPlayer.getCurrentPosition();

        notifyObservers(new TurnStartEvent(currentPlayer.getName(), diceRoll,
                currentPlayer.getTurnCount() + 1));

        Position targetPosition = currentPlayer.getPositionAfterMove(diceRoll);

        boolean hitAllowed = hitRule.processHit(currentPlayer, targetPosition, board);

        if (!hitAllowed) {
            notifyObservers(new ForfeitEvent(currentPlayer.getName(), ForfeitEvent.ForfeitReason.HIT, targetPosition,
                    currentPlayer.getCurrentPosition()));
            incrementTotalTurns();
            moveToNextPlayer();
            return;
        }

        if (hitRule instanceof AllowHitRule) {
            AllowHitRule allowHitRule = (AllowHitRule) hitRule;
            if (allowHitRule.isHit(targetPosition, board, currentPlayer)) {
                notifyObservers(new HitEvent(targetPosition));
            }
        }

        boolean moved = endRule.processMove(currentPlayer, diceRoll);

        if (!moved) {
            notifyObservers(new ForfeitEvent(currentPlayer.getName(), ForfeitEvent.ForfeitReason.OVERSHOOT,
                    targetPosition, currentPlayer.getCurrentPosition()));
        } else {
            notifyObservers(new MoveEvent(currentPlayer.getName(), positionBefore, currentPlayer.getCurrentPosition()));
        }

        board.updateOccupancy(players);
        incrementTotalTurns();

        if (currentPlayer.isAtEnd()) {
            winner = currentPlayer;
            notifyObservers(new WinEvent(winner.getName(), winner.getTurnCount(), totalTurns));
            transitionToState(new GameOverState());
            return;
        }

        moveToNextPlayer();
    }

    public void notifyGameOver() {
        notifyObservers(new GameOverMessageEvent());
    }

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

    public GameId getId() {
        return id;
    }

    public GameConfiguration getConfiguration() {
        return configuration;
    }

    public Board getBoard() {
        return board;
    }

    public List<Player> getPlayers() {
        return List.copyOf(players);
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public int getTotalTurns() {
        return totalTurns;
    }

    public Player getWinner() {
        return winner;
    }

    public boolean isGameOver() {
        return winner != null;
    }

    public GameState getCurrentState() {
        return currentState;
    }
}