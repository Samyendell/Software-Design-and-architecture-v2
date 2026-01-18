package uk.ac.mmu.game.applicationcode.domainmodel.events;

public class StateTransitionEvent extends GameEvent {
    private final String fromState;
    private final String toState;

    public StateTransitionEvent(String fromState, String toState) {
        super(EventType.STATE_TRANSITION);
        this.fromState = fromState;
        this.toState = toState;
    }

    @Override
    public String toString() {
        return "Game state " + fromState + " -> " + toState;
    }
}