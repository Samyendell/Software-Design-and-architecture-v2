package uk.ac.mmu.game.applicationcode.usecase.replay;

import uk.ac.mmu.game.applicationcode.domainmodel.*;
import uk.ac.mmu.game.applicationcode.domainmodel.board.BasicBoard;
import uk.ac.mmu.game.applicationcode.domainmodel.board.Board;
import uk.ac.mmu.game.applicationcode.domainmodel.board.LargeBoard;
import uk.ac.mmu.game.applicationcode.domainmodel.dice.FixedSequenceDiceRoller;
import uk.ac.mmu.game.applicationcode.domainmodel.events.GameObserver;
import uk.ac.mmu.game.applicationcode.domainmodel.player.Player;
import uk.ac.mmu.game.applicationcode.domainmodel.player.PlayerColour;
import uk.ac.mmu.game.applicationcode.domainmodel.rules.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Enhanced Replay Use Case.
 * Replays the game using stored configuration and dice sequence.
 * 
 * Design Decision: We chose to store dice sequence and replay the game
 * rather than storing output, because:
 * 1. Smaller storage footprint (just numbers vs full text)
 * 2. Can change output format without re-recording games
 * 3. Verifies game logic is deterministic
 * 4. Allows analysis of game state at any point
 */
public class UseCase implements Provided {
  private final Required.GameRepository repository;
  private final Required.ObserverFactory observerFactory;

  public UseCase(Required.GameRepository repository,
                Required.ObserverFactory observerFactory) {
    this.repository = repository;
    this.observerFactory = observerFactory;
  }

  @Override
  public ReplayResponse execute(ReplayRequest request) {
    try {
      // Find the saved game
      GameRecord record = repository.findById(request.getGameId())
          .orElseThrow(() -> new IllegalArgumentException(
              "Game not found: " + request.getGameId()));
      
      System.out.println();
      System.out.println("=".repeat(60));
      System.out.println("REPLAYING GAME: " + record.getGameId());
      System.out.println("Original game completed at: " + 
          new java.util.Date(record.getTimestamp()));
      System.out.println("=".repeat(60));
      System.out.println();
      
      // Recreate the game with stored dice sequence
      FixedSequenceDiceRoller replayDice = new FixedSequenceDiceRoller(
          record.getDiceSequence().toArray(new Integer[0])
      );
      
      Game game = createGame(record.getConfiguration());
      
      // Register observer for output
      GameObserver observer = observerFactory.createObserver();
      game.addObserver(observer);
      
      // Replay the game
      while (!game.isGameOver()) {
        int roll = replayDice.roll();
        game.takeTurn(roll);
      }
      
      System.out.println();
      System.out.println("=".repeat(60));
      System.out.println("REPLAY COMPLETE");
      System.out.println("=".repeat(60));
      
      return ReplayResponse.success(record);
      
    } catch (Exception e) {
      return ReplayResponse.failure(
          "Failed to replay game: " + e.getMessage());
    }
  }

  private Game createGame(GameConfiguration config) {
    GameId id = GameId.generate();
    
    Board board = config.isLargeBoard() 
        ? new LargeBoard() 
        : new BasicBoard();
    
    List<Player> players = new ArrayList<>();
    for (PlayerColour colour : config.getPlayers()) {
      players.add(new Player(
          colour.name(),
          colour,
          board.createPathForPlayer(colour)
      ));
    }
    
    EndRule endRule = config.isExactLandingRequired()
        ? new ExactEndRule()
        : new OvershootEndRule();
    
    HitRule hitRule = config.isForfeitOnHit()
        ? new ForfeitOnHitRule()
        : new AllowHitRule();
    
    return new Game(id, config, board, players, endRule, hitRule);
  }
}