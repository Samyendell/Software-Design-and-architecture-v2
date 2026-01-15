package uk.ac.mmu.game.infrastructure.driven.persistence;

import uk.ac.mmu.game.applicationcode.domainmodel.GameId;
import uk.ac.mmu.game.applicationcode.domainmodel.GameRecord;
import java.util.List;
import java.util.Optional;

/**
 * Strategy Pattern: Different persistence strategies.
 * Allows choosing storage mechanism at runtime via Spring DI.
 */
public interface PersistenceStrategy {
  void save(GameRecord record);
  Optional<GameRecord> findById(GameId id);
  List<GameId> listAll();
  String getStrategyName();
}