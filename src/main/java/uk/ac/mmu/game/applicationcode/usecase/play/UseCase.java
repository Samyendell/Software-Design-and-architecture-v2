package uk.ac.mmu.game.applicationcode.usecase.play;

import uk.ac.mmu.game.applicationcode.domainmodel.*;
import uk.ac.mmu.game.applicationcode.domainmodel.board.BasicBoard;
import uk.ac.mmu.game.applicationcode.domainmodel.board.Board;
import uk.ac.mmu.game.applicationcode.domainmodel.board.LargeBoard;
import uk.ac.mmu.game.applicationcode.domainmodel.dice.RecordingDiceRoller;
import uk.ac.mmu.game.applicationcode.domainmodel.events.GameObserver;
import uk.ac.mmu.game.applicationcode.domainmodel.player.Player;
import uk.ac.mmu.game.applicationcode.domainmodel.player.PlayerColour;
import uk.ac.mmu.game.applicationcode.domainmodel.rules.*;
import java.util.ArrayList;
import java.util.List;

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
      // Wrap dice in recording decorator
      RecordingDiceRoller recordingDice = 
          new RecordingDiceRoller(request.getDiceRoller());
      
      // Create game
      Game game = createGame(request.getConfiguration());
      
      // Register observer
      GameObserver observer = observerFactory.createObserver();
      game.addObserver(observer);
      
      // Play until winner
      while (!game.isGameOver()) {
        int roll = recordingDice.roll();
        game.takeTurn(roll);
      }
      
      // Create record with dice sequence
      GameRecord record = new GameRecord(
          game.getId(),
          game.getConfiguration(),
          recordingDice.getRecordedRolls(),  // Store dice sequence
          game.getWinner().getColour(),
          game.getWinner().getTurnCount(),
          game.getTotalTurns()
      );
      
      // Save game record
      repository.save(record);
      
      return PlayResponse.success(
          game.getId(),
          game.getWinner().getColour(),
          game.getTotalTurns()
      );
      
    } catch (Exception e) {
      return PlayResponse.failure("Failed to play game: " + e.getMessage());
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