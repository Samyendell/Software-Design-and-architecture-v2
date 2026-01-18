package uk.ac.mmu.game.applicationcode.domainmodel.board;

import uk.ac.mmu.game.applicationcode.domainmodel.player.PlayerColour;

public class BasicBoard extends Board {
    public BasicBoard() {
        super(18, 3);
    }

    @Override
    protected int getHomePosition(PlayerColour colour) {
        return switch (colour) {
            case RED -> 1;
            case BLUE -> 10;
            default -> throw new IllegalArgumentException("Basic board can only be used with red and blue players");
        };
    }
}