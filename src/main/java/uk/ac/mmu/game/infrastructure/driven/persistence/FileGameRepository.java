package uk.ac.mmu.game.infrastructure.driven.persistence;

import uk.ac.mmu.game.applicationcode.domainmodel.GameId;
import uk.ac.mmu.game.applicationcode.domainmodel.GameRecord;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory implementation of game repository.
 * For production, this would write to files or database.
 */
public class FileGameRepository implements GameRepository {
  private final Map<GameId, GameRecord> games;

  public FileGameRepository() {
    this.games = new HashMap<>();
  }

  @Override
  public void save(GameRecord record) {
    games.put(record.getGameId(), record);
  }

  @Override
  public Optional<GameRecord> findById(GameId id) {
    return Optional.ofNullable(games.get(id));
  }

  public int size() {
    return games.size();
  }

  public void clear() {
    games.clear();
  }
}