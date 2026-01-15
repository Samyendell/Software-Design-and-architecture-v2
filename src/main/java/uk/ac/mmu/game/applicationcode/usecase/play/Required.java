package uk.ac.mmu.game.applicationcode.usecase.play;

import uk.ac.mmu.game.applicationcode.domainmodel.GameRecord;
import uk.ac.mmu.game.applicationcode.domainmodel.events.GameObserver;

/**
 * Ports: What the Play use case requires from infrastructure.
 * Dependency Inversion: Use case defines interfaces, infrastructure implements.
 */
public interface Required {
  
  /**
   * Repository for persisting game records.
   */
  interface GameRepository {
    void save(GameRecord record);
  }
  
  /**
   * Factory for creating observers.
   */
  interface ObserverFactory {
    GameObserver createObserver();
  }
}