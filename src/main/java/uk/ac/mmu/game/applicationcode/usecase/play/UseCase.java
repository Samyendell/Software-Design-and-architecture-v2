package uk.ac.mmu.game.applicationcode.usecase.play;

import uk.ac.mmu.game.applicationcode.domainmodel.*;
import uk.ac.mmu.game.applicationcode.domainmodel.board.BasicBoard;
import uk.ac.mmu.game.applicationcode.domainmodel.board.Board;
import uk.ac.mmu.game.applicationcode.domainmodel.board.LargeBoard;
import uk.ac.mmu.game.applicationcode.domainmodel.events.GameObserver;
import uk.ac.mmu.game.applicationcode.domainmodel.player.Player;
import uk.ac.mmu.game.applicationcode.domainmodel.player.PlayerColour;
import uk.ac.mmu.game.applicationcode.domainmodel.rules.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Play Game Use Case.
 * 
 * Orchestrates playing a complete game:
 * 1. Creates game from configuration
 * 2. Registers observer for output
 * 3. Plays until winner
 * 4. Persists game record
 * 
 * Single Responsibility: Coordinating game play workflow.
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
  public PlayResponse execute(PlayRequest request) {
    try {
      // Create game
      Game game = createGame(request.getConfiguration());
      
      // Register observer
      GameObserver observer = observerFactory.createObserver();
      game.addObserver(observer);
      
      // Play until winner
      while (!game.isGameOver()) {
        int roll = request.getDiceRoller().roll();
        game.takeTurn(roll);
      }
      
      // Save game record
      GameRecord record = game.toRecord();
      repository.save(record);
      
      // Return success response
      return PlayResponse.success(
          game.getId(),
          game.getWinner().getColour(),
          game.getTotalTurns()
      );
      
    } catch (Exception e) {
      return PlayResponse.failure("Failed to play game: " + e.getMessage());
    }
  }

  /**
   * Factory method: Creates game from configuration.
   */
  private Game createGame(GameConfiguration config) {
    GameId id = GameId.generate();
    
    // Create board
    Board board = config.isLargeBoard() 
        ? new LargeBoard() 
        : new BasicBoard();
    
    // Create players
    List<Player> players = new ArrayList<>();
    for (PlayerColour colour : config.getPlayers()) {
      players.add(new Player(
          colour.name(),
          colour,
          board.createPathForPlayer(colour)
      ));
    }
    
    // Create rules
    EndRule endRule = config.isExactLandingRequired()
        ? new ExactEndRule()
        : new OvershootEndRule();
    
    HitRule hitRule = config.isForfeitOnHit()
        ? new ForfeitOnHitRule()
        : new AllowHitRule();
    
    return new Game(id, config, board, players, endRule, hitRule);
  }
}