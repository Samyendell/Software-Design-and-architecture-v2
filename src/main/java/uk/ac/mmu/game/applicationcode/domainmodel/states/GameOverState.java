package uk.ac.mmu.game.applicationcode.domainmodel.states;

import uk.ac.mmu.game.applicationcode.domainmodel.Game;

public class GameOverState implements GameState {

    @Override
    public void takeTurn(Game game, int diceRoll) {
        game.notifyGameOver();
    }

    @Override
    public String getStateName() {
        return "GameOver";
    }
}