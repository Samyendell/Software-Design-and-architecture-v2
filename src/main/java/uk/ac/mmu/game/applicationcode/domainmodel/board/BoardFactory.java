package uk.ac.mmu.game.applicationcode.domainmodel.board;

import uk.ac.mmu.game.applicationcode.domainmodel.GameConfiguration;

public class BoardFactory {

    public static Board createBoard(GameConfiguration configuration) {
        if (configuration.isLargeBoard()) {
            return new LargeBoard();
        } else {
            return new BasicBoard();
        }
    }
}