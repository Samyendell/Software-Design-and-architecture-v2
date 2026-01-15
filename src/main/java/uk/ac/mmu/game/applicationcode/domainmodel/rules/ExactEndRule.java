package uk.ac.mmu.game.applicationcode.domainmodel.rules;

import uk.ac.mmu.game.applicationcode.domainmodel.player.Player;

/**
 * Variation: Player must land exactly on end.
 * Overshoot forfeits the turn.
 */
public class ExactEndRule implements EndRule {
  @Override
  public boolean canWin(Player player, int diceRoll) {
    return player.getDistanceToEnd() == diceRoll;
  }

  @Override
  public boolean processMove(Player player, int diceRoll) {
    if (player.wouldOvershootEnd(diceRoll)) {
      player.incrementTurnCountWithoutMoving();
      return false; // Turn forfeit
    }
    player.move(diceRoll);
    return true;
  }
}