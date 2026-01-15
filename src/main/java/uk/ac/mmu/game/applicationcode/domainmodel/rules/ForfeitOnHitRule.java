package uk.ac.mmu.game.applicationcode.domainmodel.rules;

import uk.ac.mmu.game.applicationcode.domainmodel.board.Board;
import uk.ac.mmu.game.applicationcode.domainmodel.board.Position;
import uk.ac.mmu.game.applicationcode.domainmodel.player.Player;

/**
 * Variation: Landing on another player forfeits the turn.
 */
public class ForfeitOnHitRule implements HitRule {
  @Override
  public boolean processHit(Player player, Position targetPosition, Board board) {
    if (board.isPositionOccupied(targetPosition, player)) {
      player.incrementTurnCountWithoutMoving();
      return false; // Turn forfeit
    }
    return true;
  }
}