package uk.ac.mmu.game.applicationcode.domainmodel.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.ac.mmu.game.applicationcode.domainmodel.player.PlayerColour;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BasicBoardTest {

    private BasicBoard board;

    @BeforeEach
    void setUp() {
        board = new BasicBoard();
    }

    @Test
    @DisplayName("BasicBoard should have basic board positions")
    void basicBoardShouldHaveBasicBoardPositions() {
        assertEquals(18, board.getMainPositions());
        assertEquals(3, board.getTailPositions());
    }

    @Test
    @DisplayName("BasicBoard should have right amount of positions")
    void basicBoardShouldHaveRightAmountOfPositions() {
        List<Position> path = board.createPathForPlayer(PlayerColour.RED);
        assertEquals(21, path.size());
    }

    @Test
    @DisplayName("Basic board should finish with end position")
    void basicBoardShouldFinishWithEndPosition() {
        List<Position> path = board.createPathForPlayer(PlayerColour.BLUE);
        Position lastPosition = path.get(path.size() - 1);

        assertTrue(lastPosition.isEnd());
    }

    @Test
    @DisplayName("Basic Board should reject unsupported player colours")
    void basicBoardShouldRejectUnsupportedPlayerColours() {
        assertThrows(IllegalArgumentException.class, () -> {
            board.createPathForPlayer(PlayerColour.YELLOW);
        });
    }
}