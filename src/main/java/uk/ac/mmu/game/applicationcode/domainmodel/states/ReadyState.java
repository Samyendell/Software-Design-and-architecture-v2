package uk.ac.mmu.game.applicationcode.domainmodel.states;

import uk.ac.mmu.game.applicationcode.domainmodel.Game;

/**
 * Ready State: Game configured but not started.
 * Singleton Pattern: Only one instance needed.
 */
public class ReadyState implements GameState {
  private static ReadyState instance;

  private ReadyState() {}

  public static synchronized ReadyState getInstance() {
    if (instance == null) {
      instance = new ReadyState();
    }
    return instance;
  }

  @Override
  public void takeTurn(Game game, int diceRoll) {
    // Auto-transition to InPlay on first turn
    game.transitionToState(InPlayState.getInstance());
    game.takeTurn(diceRoll);
  }

  @Override
  public String getStateName() {
    return "Ready";
  }
}