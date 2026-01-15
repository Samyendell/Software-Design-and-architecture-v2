package uk.ac.mmu.game.applicationcode.domainmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Objects;
import java.util.UUID;

/**
 * GameId with Jackson annotations for JSON serialization.
 */
public class GameId {
    private final String value;

    private GameId(String value) {
        this.value = Objects.requireNonNull(value, "Game ID cannot be null");
    }

    public static GameId generate() {
        return new GameId(UUID.randomUUID().toString());
    }

    @JsonCreator  // Jackson: Use this method when deserializing from JSON
    public static GameId fromString(String value) {
        return new GameId(value);
    }

    @JsonValue  // Jackson: Serialize as just the string value
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameId gameId = (GameId) o;
        return value.equals(gameId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}