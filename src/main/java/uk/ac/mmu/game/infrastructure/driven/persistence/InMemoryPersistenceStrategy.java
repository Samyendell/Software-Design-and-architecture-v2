package uk.ac.mmu.game.infrastructure.driven.persistence;

import uk.ac.mmu.game.applicationcode.domainmodel.GameId;
import uk.ac.mmu.game.applicationcode.domainmodel.GameRecord;
import java.util.*;

/**
 * In-memory persistence - fast but data lost on shutdown.
 * Good for testing and demonstrations.
 */
public class InMemoryPersistenceStrategy implements PersistenceStrategy {
  private final Map<GameId, GameRecord> games = new LinkedHashMap<>();

  @Override
  public void save(GameRecord record) {
    games.put(record.getGameId(), record);
    System.out.println("Game saved to memory: " + record.getGameId());
  }

  @Override
  public Optional<GameRecord> findById(GameId id) {
    return Optional.ofNullable(games.get(id));
  }

  @Override
  public List<GameId> listAll() {
    return new ArrayList<>(games.keySet());
  }

  @Override
  public String getStrategyName() {
    return "In-Memory Storage";
  }
}