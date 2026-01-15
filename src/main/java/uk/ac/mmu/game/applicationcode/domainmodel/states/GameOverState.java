package uk.ac.mmu.game.applicationcode.domainmodel.states;

import uk.ac.mmu.game.applicationcode.domainmodel.Game;

/**
 * GameOver State: Game finished, only outputs "Game over".
 */
public class GameOverState implements GameState {
  private static GameOverState instance;

  private GameOverState() {}

  public static synchronized GameOverState getInstance() {
    if (instance == null) {
      instance = new GameOverState();
    }
    return instance;
  }

  @Override
  public void takeTurn(Game game, int diceRoll) {
    game.notifyGameOver();
  }

  @Override
  public String getStateName() {
    return "GameOver";
  }
}
