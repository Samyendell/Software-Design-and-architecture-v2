package uk.ac.mmu.game.applicationcode.domainmodel.events;

public abstract class GameEvent {
    private final EventType type;

    protected GameEvent(EventType type) {
        this.type = type;
    }

    public EventType getType() {
        return type;
    }

    public enum EventType {
        STATE_TRANSITION, TURN_START, MOVE, FORFEIT, HIT, WIN, GAME_OVER_MESSAGE
    }

    @Override
    public String toString() {
        return type.name();
    }
}
