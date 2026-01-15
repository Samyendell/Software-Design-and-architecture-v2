package uk.ac.mmu.game.applicationcode.domainmodel.rules;

import uk.ac.mmu.game.applicationcode.domainmodel.board.Board;
import uk.ac.mmu.game.applicationcode.domainmodel.board.Position;
import uk.ac.mmu.game.applicationcode.domainmodel.player.Player;

/**
 * Strategy Pattern: Interface for hit rules.
 */
public interface HitRule {
  boolean processHit(Player player, Position targetPosition, Board board);
}