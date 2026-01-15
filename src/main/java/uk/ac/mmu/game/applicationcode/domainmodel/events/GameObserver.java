package uk.ac.mmu.game.applicationcode.domainmodel.events;

/**
 * Observer Pattern: Interface for objects that observe game events.
 */
public interface GameObserver {
  void onGameEvent(GameEvent event);
}