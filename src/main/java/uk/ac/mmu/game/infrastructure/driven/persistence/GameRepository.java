package uk.ac.mmu.game.infrastructure.driven.persistence;

import uk.ac.mmu.game.applicationcode.domainmodel.GameId;
import uk.ac.mmu.game.applicationcode.domainmodel.GameRecord;
import uk.ac.mmu.game.applicationcode.usecase.play.Required.GameRepository 
    as PlayGameRepository;
import uk.ac.mmu.game.applicationcode.usecase.replay.Required.GameRepository 
    as ReplayGameRepository;
import java.util.Optional;

/**
 * Repository interface combining both use case requirements.
 * Adapter Pattern: Adapts use case interfaces to infrastructure.
 */
public interface GameRepository extends PlayGameRepository, ReplayGameRepository {
  void save(GameRecord record);
  Optional<GameRecord> findById(GameId id);
}