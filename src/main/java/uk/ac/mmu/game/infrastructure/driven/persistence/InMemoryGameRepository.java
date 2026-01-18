package uk.ac.mmu.game.infrastructure.driven.persistence;

import uk.ac.mmu.game.applicationcode.domainmodel.GameId;
import uk.ac.mmu.game.applicationcode.domainmodel.GameRecord;

import java.util.*;

public class InMemoryGameRepository implements GameRepository {
    private final Map<GameId, GameRecord> storage = new HashMap<>();

    @Override
    public void save(GameRecord record) {
        storage.put(record.getGameId(), record);
    }

    @Override
    public Optional<GameRecord> findById(GameId id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<GameId> listAll() {
        return new ArrayList<>(storage.keySet());
    }

    @Override
    public String getStrategyName() {
        return "In memory storage";
    }
}