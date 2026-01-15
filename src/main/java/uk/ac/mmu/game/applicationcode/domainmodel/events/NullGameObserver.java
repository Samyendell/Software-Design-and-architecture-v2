package uk.ac.mmu.game.applicationcode.domainmodel.events;

/**
 * Null Object Pattern: Provides default "do nothing" observer.
 * Singleton Pattern: Only one instance needed.
 * Eliminates null checks throughout the codebase.
 */
public class NullGameObserver implements GameObserver {
  private static NullGameObserver instance;

  private NullGameObserver() {}

  public static synchronized NullGameObserver getInstance() {
    if (instance == null) {
      instance = new NullGameObserver();
    }
    return instance;
  }

  @Override
  public void onGameEvent(GameEvent event) {
    // Do nothing - null object behavior
  }
}