package uk.ac.mmu.game.applicationcode.domainmodel.events;

/**
 * Event fired when game transitions between states.
 */
public class StateTransitionEvent extends GameEvent {
  private final String fromState;
  private final String toState;

  public StateTransitionEvent(String fromState, String toState) {
    super(EventType.STATE_TRANSITION);
    this.fromState = fromState;
    this.toState = toState;
  }

  public String getFromState() { return fromState; }
  public String getToState() { return toState; }

  @Override
  public String toString() {
    return "Game state " + fromState + " -> " + toState;
  }
}