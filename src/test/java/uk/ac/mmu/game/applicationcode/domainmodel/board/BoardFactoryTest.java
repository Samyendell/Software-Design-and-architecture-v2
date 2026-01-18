package uk.ac.mmu.game.applicationcode.domainmodel.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.ac.mmu.game.applicationcode.domainmodel.GameConfiguration;
import static org.junit.jupiter.api.Assertions.*;

class BoardFactoryTest {

    @Test
    @DisplayName("Should create BasicBoard for basic configuration")
    void shouldCreateBasicBoardForBasicConfiguration() {
        GameConfiguration config = GameConfiguration.basicGame();
        Board board = BoardFactory.createBoard(config);

        assertInstanceOf(BasicBoard.class, board);
        assertEquals(18, board.getMainPositions());
    }

    @Test
    @DisplayName("Should create LargeBoard for large board configuration")
    void shouldCreateLargeBoardForLargeBoardConfiguration() {
        GameConfiguration config = GameConfiguration.largeBoardGame();
        Board board = BoardFactory.createBoard(config);

        assertInstanceOf(LargeBoard.class, board);
        assertEquals(36, board.getMainPositions());
    }
}