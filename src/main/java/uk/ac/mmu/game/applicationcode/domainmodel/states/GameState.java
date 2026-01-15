package uk.ac.mmu.game.applicationcode.domainmodel.states;

import uk.ac.mmu.game.applicationcode.domainmodel.Game;

/**
 * State Pattern: Interface for game states.
 * Each state handles takeTurn() differently.
 */
public interface GameState {
  void takeTurn(Game game, int diceRoll);
  String getStateName();
}