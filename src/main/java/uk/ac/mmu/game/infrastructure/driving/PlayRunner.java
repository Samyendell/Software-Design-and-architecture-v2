package uk.ac.mmu.game.infrastructure.driving;

import uk.ac.mmu.game.applicationcode.domainmodel.GameConfiguration;
import uk.ac.mmu.game.applicationcode.domainmodel.dice.*;
import uk.ac.mmu.game.applicationcode.usecase.play.*;
import uk.ac.mmu.game.infrastructure.driven.output.ConfigurationPrinter;

public class PlayRunner {
    private final Provided playUseCase;

    public PlayRunner(Provided playUseCase) {
        this.playUseCase = playUseCase;
    }

    public void runSingleDieWithRandomDice() {
        System.out.println("=".repeat(60));
        System.out.println("SINGLE DICE (Random Dice)");
        System.out.println("=".repeat(60));

        GameConfiguration config = GameConfiguration.singleDieGame();
        ConfigurationPrinter.print(config);

        DiceRoller dice = new RandomDiceRoller();
        playUseCase.execute(new PlayRequest(config, dice));
        System.out.println();
    }

    public void runTwoDiceWithRandomDice() {
        System.out.println("=".repeat(60));
        System.out.println("TWO DICE (Random Dice)");
        System.out.println("=".repeat(60));

        GameConfiguration config = GameConfiguration.basicGame();
        ConfigurationPrinter.print(config);

        DiceRoller dice = new TwoDiceDecorator(new RandomDiceRoller());
        playUseCase.execute(new PlayRequest(config, dice));
        System.out.println();
    }

    public void runBasicGame() {
        System.out.println("=".repeat(60));
        System.out.println("BASIC GAME (Fixed Sequence Dice)");
        System.out.println("=".repeat(60));

        GameConfiguration config = GameConfiguration.basicGame();
        ConfigurationPrinter.print(config);

        DiceRoller dice = new FixedSequenceDiceRoller(12, 12, 7, 8);
        playUseCase.execute(new PlayRequest(config, dice));
        System.out.println();
    }

    public void runExactLandingVariation() {
        System.out.println("=".repeat(60));
        System.out.println("EXACT LANDING (Fixed Sequence Dice)");
        System.out.println("=".repeat(60));

        GameConfiguration config = GameConfiguration.exactLandingGame();
        ConfigurationPrinter.print(config);

        DiceRoller dice = new FixedSequenceDiceRoller(12, 12, 12, 9, 8);
        playUseCase.execute(new PlayRequest(config, dice));
        System.out.println();
    }

    public void runForfeitOnHitVariation() {
        System.out.println("=".repeat(60));
        System.out.println("FORFEIT ON HIT (Fixed Sequence Dice)");
        System.out.println("=".repeat(60));

        GameConfiguration config = GameConfiguration.forfeitOnHitGame();
        ConfigurationPrinter.print(config);

        DiceRoller dice = new FixedSequenceDiceRoller(8, 2, 3, 12, 9, 6);
        playUseCase.execute(new PlayRequest(config, dice));
        System.out.println();
    }

    public void runLargeBoardFourPlayers() {
        System.out.println("=".repeat(60));
        System.out.println("LARGE BOARD - 4 PLAYERS (Fixed Sequence Dice)");
        System.out.println("=".repeat(60));

        GameConfiguration config = GameConfiguration.largeBoardGame();
        ConfigurationPrinter.print(config);

        DiceRoller dice = new FixedSequenceDiceRoller(7, 3, 8, 5, 7, 6, 8, 7, 6, 8, 2, 4, 4, 8, 5, 7, 8, 3,
                9, 9, 7, 5, 7, 9);
        playUseCase.execute(new PlayRequest(config, dice));
        System.out.println();
    }

    public void runAllScenarios() {
        runSingleDieWithRandomDice();
        runTwoDiceWithRandomDice();
        runBasicGame();
        runExactLandingVariation();
        runForfeitOnHitVariation();
        runLargeBoardFourPlayers();
    }
}