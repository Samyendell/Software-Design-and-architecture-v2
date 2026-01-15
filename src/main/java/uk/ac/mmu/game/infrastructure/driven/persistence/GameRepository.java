package uk.ac.mmu.game.infrastructure.driven.persistence;

import uk.ac.mmu.game.applicationcode.domainmodel.GameId;
import uk.ac.mmu.game.applicationcode.domainmodel.GameRecord;
import java.util.Optional;

/**
 * Repository interface combining both use case requirements.
 * Adapter Pattern: Adapts use case interfaces to infrastructure.
 */
public interface GameRepository
    extends uk.ac.mmu.game.applicationcode.usecase.play.Required.GameRepository,
        uk.ac.mmu.game.applicationcode.usecase.replay.Required.GameRepository {

  // The extended interfaces already declare the required methods, but we keep
  // these declarations here for clarity and to satisfy adapters.
  @Override
  void save(GameRecord record);

  @Override
  Optional<GameRecord> findById(GameId id);

  /**
   * Human-friendly name of the underlying persistence strategy.
   */
  String getStrategyName();
}