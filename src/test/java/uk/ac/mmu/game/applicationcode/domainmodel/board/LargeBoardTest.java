package uk.ac.mmu.game.applicationcode.domainmodel.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.ac.mmu.game.applicationcode.domainmodel.player.PlayerColour;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LargeBoardTest {

    private LargeBoard board;

    @BeforeEach
    void setUp() {
        board = new LargeBoard();
    }

    @Test
    @DisplayName("LargeBoard should have large board positions")
    void largeBoardShouldHaveLargeBoardPositions() {
        assertEquals(36, board.getMainPositions());
        assertEquals(6, board.getTailPositions());
    }

    @Test
    @DisplayName("All players should have the same amount of positions in their path")
    void allPlayersShouldHaveTheSameAmountOfPositionsInTheirPath() {
        List<Position> redPath = board.createPathForPlayer(PlayerColour.RED);
        List<Position> bluePath = board.createPathForPlayer(PlayerColour.BLUE);
        List<Position> greenPath = board.createPathForPlayer(PlayerColour.GREEN);
        List<Position> yellowPath = board.createPathForPlayer(PlayerColour.YELLOW);

        assertEquals(42, redPath.size());
        assertEquals(redPath.size(), bluePath.size());
        assertEquals(redPath.size(), yellowPath.size());
        assertEquals(redPath.size(), greenPath.size());
    }

    @Test
    @DisplayName("All players should have the correct start position")
    void allPlayersShouldHaveTheCorrectStartPosition() {
        assertEquals(1, board.createPathForPlayer(PlayerColour.RED).get(0).getNumber());
        assertEquals(10, board.createPathForPlayer(PlayerColour.BLUE).get(0).getNumber());
        assertEquals(19, board.createPathForPlayer(PlayerColour.GREEN).get(0).getNumber());
        assertEquals(28, board.createPathForPlayer(PlayerColour.YELLOW).get(0).getNumber());
    }
}