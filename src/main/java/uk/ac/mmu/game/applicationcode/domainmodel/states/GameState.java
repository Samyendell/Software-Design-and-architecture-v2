package uk.ac.mmu.game.applicationcode.domainmodel.states;

import uk.ac.mmu.game.applicationcode.domainmodel.Game;

public interface GameState {
    void takeTurn(Game game, int diceRoll);

    String getStateName();
}