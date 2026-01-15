package uk.ac.mmu.game.infrastructure.driving;

import uk.ac.mmu.game.applicationcode.domainmodel.GameConfiguration;
import uk.ac.mmu.game.applicationcode.domainmodel.dice.*;
import uk.ac.mmu.game.applicationcode.domainmodel.player.PlayerColour;
import uk.ac.mmu.game.applicationcode.usecase.play.*;
import uk.ac.mmu.game.infrastructure.driven.output.ConsoleGameObserver;
import java.util.List;

/**
 * Driving adapter: Runs game scenarios from command line.
 */
public class PlayRunner {
  private final Provided playUseCase;
  private final ConsoleGameObserver observer;

  public PlayRunner(Provided playUseCase, ConsoleGameObserver observer) {
    this.playUseCase = playUseCase;
    this.observer = observer;
  }

  public void runBasicGame() {
    System.out.println("=".repeat(60));
    System.out.println("BASIC GAME - SCENARIO 1");
    System.out.println("=".repeat(60));

    GameConfiguration config = GameConfiguration.basicGame();
    observer.setConfiguration(config);

    DiceRoller dice = new FixedSequenceDiceRoller(12, 12, 7, 8);
    playUseCase.execute(new PlayRequest(config, dice));
    System.out.println();
  }

  public void runSingleDieVariation() {
    System.out.println("=".repeat(60));
    System.out.println("VARIATION: SINGLE DIE");
    System.out.println("=".repeat(60));

    GameConfiguration config = new GameConfiguration(
        List.of(PlayerColour.RED, PlayerColour.BLUE),
        true, false, false, false
    );
    observer.setConfiguration(config);

    DiceRoller dice = new FixedSequenceDiceRoller(6, 6, 6, 6, 3, 4, 3, 4);
    playUseCase.execute(new PlayRequest(config, dice));
    System.out.println();
  }

  public void runExactLandingVariation() {
    System.out.println("=".repeat(60));
    System.out.println("VARIATION: EXACT LANDING + FORFEIT ON HIT");
    System.out.println("=".repeat(60));

    GameConfiguration config = new GameConfiguration(
        List.of(PlayerColour.RED, PlayerColour.BLUE),
        false, true, true, false
    );
    observer.setConfiguration(config);

    DiceRoller dice = new FixedSequenceDiceRoller(12, 12, 12, 9, 8);
    playUseCase.execute(new PlayRequest(config, dice));
    System.out.println();
  }

  public void runForfeitOnHitVariation() {
    System.out.println("=".repeat(60));
    System.out.println("VARIATION: FORFEIT ON HIT");
    System.out.println("=".repeat(60));

    GameConfiguration config = new GameConfiguration(
        List.of(PlayerColour.RED, PlayerColour.BLUE),
        false, true, true, false
    );
    observer.setConfiguration(config);

    DiceRoller dice = new FixedSequenceDiceRoller(8, 2, 3, 12, 9, 6);
    playUseCase.execute(new PlayRequest(config, dice));
    System.out.println();
  }

  public void runLargeBoardFourPlayers() {
    System.out.println("=".repeat(60));
    System.out.println("ADVANCED: LARGE BOARD - 4 PLAYERS");
    System.out.println("=".repeat(60));

    GameConfiguration config = GameConfiguration.largeBoardGame();
    observer.setConfiguration(config);

    DiceRoller dice = new FixedSequenceDiceRoller(
        7, 3, 8, 5, 7, 6, 8, 7, 6, 8, 2, 4,
        4, 8, 5, 7, 8, 3, 9, 9, 7, 5, 7, 9
    );
    playUseCase.execute(new PlayRequest(config, dice));
    System.out.println();
  }

  public void runAllScenarios() {
    runBasicGame();
    runSingleDieVariation();
    runExactLandingVariation();
    runForfeitOnHitVariation();
    runLargeBoardFourPlayers();
  }
}