package uk.ac.mmu.game.infrastructure.driven.persistence;

import uk.ac.mmu.game.applicationcode.domainmodel.GameRecord;
import uk.ac.mmu.game.applicationcode.ports.driven.GameRepository;

import java.util.*;

public final class InMemoryGameRepository implements GameRepository {

  private final Map<String, GameRecord> store = new HashMap<>();

  @Override
  public void save(GameRecord record) {
    store.put(record.id(), record);
  }

  @Override
  public Optional<GameRecord> findById(String id) {
    return Optional.ofNullable(store.get(id));
  }

  @Override
  public List<String> listIds() {
    return new ArrayList<>(store.keySet());
  }
}
