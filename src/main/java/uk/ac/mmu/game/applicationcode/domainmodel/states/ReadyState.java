package uk.ac.mmu.game.applicationcode.domainmodel.states;

import uk.ac.mmu.game.applicationcode.domainmodel.Game;

public class ReadyState implements GameState {

    @Override
    public void takeTurn(Game game, int diceRoll) {
        game.transitionToState(new InPlayState());
        game.takeTurn(diceRoll);
    }

    @Override
    public String getStateName() {
        return "Ready";
    }
}