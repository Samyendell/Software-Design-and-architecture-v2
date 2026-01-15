package uk.ac.mmu.game.infrastructure.driven.persistence;

import uk.ac.mmu.game.applicationcode.domainmodel.GameId;
import uk.ac.mmu.game.applicationcode.domainmodel.GameRecord;
import java.util.List;
import java.util.Optional;

public class FileGameRepository implements GameRepository {
  private final PersistenceStrategy strategy;

  public FileGameRepository(PersistenceStrategy strategy) {
    this.strategy = strategy;
  }

  @Override
  public void save(GameRecord record) {
    strategy.save(record);
  }

  @Override
  public Optional<GameRecord> findById(GameId id) {
    return strategy.findById(id);
  }

  @Override
  public List<GameId> listAllGameIds() {
    return strategy.listAll();
  }

  public String getStrategyName() {
    return strategy.getStrategyName();
  }
}