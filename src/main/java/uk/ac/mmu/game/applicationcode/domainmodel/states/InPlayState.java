package uk.ac.mmu.game.applicationcode.domainmodel.states;

import uk.ac.mmu.game.applicationcode.domainmodel.Game;

/**
 * InPlay State: Game actively being played.
 */
public class InPlayState implements GameState {
  private static InPlayState instance;

  private InPlayState() {}

  public static synchronized InPlayState getInstance() {
    if (instance == null) {
      instance = new InPlayState();
    }
    return instance;
  }

  @Override
  public void takeTurn(Game game, int diceRoll) {
    game.processTurn(diceRoll);
  }

  @Override
  public String getStateName() {
    return "InPlay";
  }
}