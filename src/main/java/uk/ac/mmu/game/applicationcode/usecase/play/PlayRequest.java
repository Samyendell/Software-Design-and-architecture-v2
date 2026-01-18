package uk.ac.mmu.game.applicationcode.usecase.play;

import uk.ac.mmu.game.applicationcode.domainmodel.GameConfiguration;
import uk.ac.mmu.game.applicationcode.domainmodel.dice.DiceRoller;


public class PlayRequest {
    private final GameConfiguration configuration;
    private final DiceRoller diceRoller;

    public PlayRequest(GameConfiguration configuration, DiceRoller diceRoller) {
        this.configuration = configuration;
        this.diceRoller = diceRoller;
    }

    public GameConfiguration getConfiguration() {
        return configuration;
    }

    public DiceRoller getDiceRoller() {
        return diceRoller;
    }
}