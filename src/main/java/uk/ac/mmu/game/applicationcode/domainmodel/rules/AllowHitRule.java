package uk.ac.mmu.game.applicationcode.domainmodel.rules;

import uk.ac.mmu.game.applicationcode.domainmodel.board.Board;
import uk.ac.mmu.game.applicationcode.domainmodel.board.Position;
import uk.ac.mmu.game.applicationcode.domainmodel.player.Player;

/**
 * Basic rule: Hits are allowed, multiple players can occupy same position.
 */
public class AllowHitRule implements HitRule {
  @Override
  public boolean processHit(Player player, Position targetPosition, Board board) {
    return true; // Always allow the move
  }

  public boolean isHit(Position targetPosition, Board board, Player movingPlayer) {
    return board.isPositionOccupied(targetPosition, movingPlayer);
  }
}