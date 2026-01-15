package uk.ac.mmu.game.applicationcode.domainmodel;

import java.util.Objects;
import java.util.UUID;

/**
 * Value Object: Represents game identity.
 * Immutable and type-safe.
 */
public class GameId {
  private final String value;

  private GameId(String value) {
    this.value = Objects.requireNonNull(value, "Game ID cannot be null");
  }

  public static GameId generate() {
    return new GameId(UUID.randomUUID().toString());
  }

  public static GameId fromString(String value) {
    return new GameId(value);
  }

  public String getValue() { return value; }

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