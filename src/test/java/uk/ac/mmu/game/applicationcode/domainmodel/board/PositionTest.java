package uk.ac.mmu.game.applicationcode.domainmodel.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    @DisplayName("Should create different position types with correct properties")
    void shouldCreateDifferentPositionTypesWithCorrectProperties() {
        Position home = Position.home("RED", 1);
        Position main = Position.main(8);
        Position tail = Position.tail("BLUE", 3);
        Position end = Position.end("GREEN", 3);

        assertEquals(PositionType.MAIN, home.getType());
        assertEquals(PositionType.MAIN, main.getType());
        assertEquals(PositionType.TAIL, tail.getType());
        assertEquals(PositionType.END, end.getType());
    }

    @Test
    @DisplayName("End positions should return true for isEnd")
    void endPositionsShouldReturnTrueForIsEnd() {
        Position end = Position.end("RED", 3);
        Position main = Position.main(3);

        assertTrue(end.isEnd());
        assertFalse(main.isEnd());
    }

    @Test
    @DisplayName("Positions with same values should be equal")
    void positionsWithSameValuesShouldBeEqual() {
        Position pos1 = Position.main(10);
        Position pos2 = Position.main(10);

        assertEquals(pos1, pos2);
    }

    @Test
    @DisplayName("toString should return display name")
    void toStringShouldReturnDisplayName() {
        Position pos = Position.main(11);
        assertEquals("Position 11", pos.toString());
    }
}