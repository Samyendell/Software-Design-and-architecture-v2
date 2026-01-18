package uk.ac.mmu.game.infrastructure.driven.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import uk.ac.mmu.game.applicationcode.domainmodel.GameId;
import uk.ac.mmu.game.applicationcode.domainmodel.GameRecord;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JsonGameRepository implements GameRepository {
    private static final String SAVE_DIR = System.getProperty("user.home") + "/.simple-frustration/games/";
    private final ObjectMapper objectMapper;

    public JsonGameRepository() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        ensureDirectoryExists();
    }

    private void ensureDirectoryExists() {
        try {
            Files.createDirectories(Paths.get(SAVE_DIR));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create save directory", e);
        }
    }

    @Override
    public void save(GameRecord record) {
        try {
            File file = new File(SAVE_DIR + record.getGameId().getValue() + ".json");
            objectMapper.writeValue(file, record);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save game", e);
        }
    }

    @Override
    public Optional<GameRecord> findById(GameId id) {
        try {
            File file = new File(SAVE_DIR + id.getValue() + ".json");
            if (!file.exists()) {
                return Optional.empty();
            }
            GameRecord record = objectMapper.readValue(file, GameRecord.class);
            return Optional.of(record);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<GameId> listAll() {
        File dir = new File(SAVE_DIR);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));

        List<GameId> gameIds = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                try {
                    GameRecord record = objectMapper.readValue(file, GameRecord.class);
                    gameIds.add(record.getGameId());
                } catch (IOException e) {
                    System.err.println("Failed to read file: " + file.getName());
                }
            }
        }
        return gameIds;
    }

    @Override
    public String getStrategyName() {
        return "JSON Storage (" + SAVE_DIR + ")";
    }
}