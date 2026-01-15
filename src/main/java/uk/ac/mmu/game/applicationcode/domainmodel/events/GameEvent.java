package uk.ac.mmu.game.applicationcode.domainmodel.events;

/**
 * Base class for all game events.
 * Events are immutable value objects.
 */
public abstract class GameEvent {
  private final EventType type;
  private final long timestamp;

  protected GameEvent(EventType type) {
    this.type = type;
    this.timestamp = System.currentTimeMillis();
  }

  public EventType getType() { return type; }
  public long getTimestamp() { return timestamp; }

  public enum EventType {
    STATE_TRANSITION,
    TURN_START,
    TURN_END,
    MOVE,
    FORFEIT,
    HIT,
    WIN,
    GAME_OVER_MESSAGE
  }

  @Override
  public String toString() { return type.name(); }
}
