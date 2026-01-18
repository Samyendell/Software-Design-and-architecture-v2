package uk.ac.mmu.game.applicationcode.domainmodel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for GameId.
 */
class GameIdTest {

    @Test
    @DisplayName("generate should create unique IDs")
    void generateShouldCreateUniqueIds() {
        GameId id1 = GameId.generate();
        GameId id2 = GameId.generate();

        assertNotEquals(id1, id2);
        assertNotEquals(id1.getValue(), id2.getValue());
    }

    @Test
    @DisplayName("fromString should create GameId from string value")
    void fromStringShouldCreateGameIdFromStringValue() {
        String value = "test-game-id-123";
        GameId id = GameId.fromString(value);

        assertEquals(value, id.getValue());
    }

    @Test
    @DisplayName("GameIds with same value should be equal")
    void gameIdsWithSameValueShouldBeEqual() {
        String value = "same-value";
        GameId id1 = GameId.fromString(value);
        GameId id2 = GameId.fromString(value);

        assertEquals(id1, id2);
        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    @DisplayName("Generated IDs should be valid UUID format")
    void generatedIdsShouldBeValidUuidFormat() {
        GameId id = GameId.generate();
        String value = id.getValue();

        assertTrue(value.matches(
                "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"));
    }
}