package uk.ac.mmu.game.applicationcode.domainmodel.states;

import uk.ac.mmu.game.applicationcode.domainmodel.Game;

public class InPlayState implements GameState {

    @Override
    public void takeTurn(Game game, int diceRoll) {
        game.processTurn(diceRoll);
    }

    @Override
    public String getStateName() {
        return "InPlay";
    }
}