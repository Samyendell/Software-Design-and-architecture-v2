package uk.ac.mmu.game.applicationcode.domainmodel.events;

public interface GameEvent {
  enum EventType { MOVE, HIT, WIN, STATE_TRANSITION }

  EventType getType();
  String toDisplayString();
}
