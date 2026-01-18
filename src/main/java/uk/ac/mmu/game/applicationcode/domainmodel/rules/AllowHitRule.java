package uk.ac.mmu.game.applicationcode.domainmodel.rules;

import uk.ac.mmu.game.applicationcode.domainmodel.board.Board;
import uk.ac.mmu.game.applicationcode.domainmodel.board.Position;
import uk.ac.mmu.game.applicationcode.domainmodel.player.Player;

public class AllowHitRule implements HitRule {
    @Override
    public boolean processHit(Player player, Position targetPosition, Board board) {
        return true;
    }

    public boolean isHit(Position targetPosition, Board board, Player movingPlayer) {
        return board.isPositionOccupied(targetPosition, movingPlayer);
    }
}