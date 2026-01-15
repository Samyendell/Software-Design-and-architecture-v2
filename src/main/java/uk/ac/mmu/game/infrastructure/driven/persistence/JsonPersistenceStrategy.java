package uk.ac.mmu.game.infrastructure.driven.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import uk.ac.mmu.game.applicationcode.domainmodel.GameId;
import uk.ac.mmu.game.applicationcode.domainmodel.GameRecord;
import java.io.*;
import java.util.*;

/**
 * JSON-based file persistence using Jackson.
 * Stores games as pretty-printed JSON files.
 */
public class JsonPersistenceStrategy implements PersistenceStrategy {
    private final String storageDirectory;
    private final ObjectMapper objectMapper;

    public JsonPersistenceStrategy() {
        // Use user's home directory (OS-independent)
        this.storageDirectory = System.getProperty("user.home") +
                File.separator + ".simple-frustration";

        // Create directory if it doesn't exist
        new File(storageDirectory).mkdirs();

        // Configure Jackson ObjectMapper
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);  // Pretty print
    }

    @Override
    public void save(GameRecord record) {
        try {
            File file = new File(storageDirectory,
                    record.getGameId().getValue() + ".json");

            // Write GameRecord as JSON
            objectMapper.writeValue(file, record);

            System.out.println("Game saved to JSON: " + file.getAbsolutePath());

        } catch (IOException e) {
            throw new RuntimeException("Failed to save game to JSON", e);
        }
    }

    @Override
    public Optional<GameRecord> findById(GameId id) {
        try {
            File file = new File(storageDirectory, id.getValue() + ".json");

            if (!file.exists()) {
                return Optional.empty();
            }

            // Read JSON and deserialize to GameRecord
            GameRecord record = objectMapper.readValue(file, GameRecord.class);
            return Optional.of(record);

        } catch (IOException e) {
            System.err.println("Warning: Could not load game " + id.getValue() +
                    ": " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<GameId> listAll() {
        File dir = new File(storageDirectory);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));

        List<GameId> ids = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                String id = file.getName().replace(".json", "");
                ids.add(GameId.fromString(id));
            }
        }
        return ids;
    }

    @Override
    public String getStrategyName() {
        return "JSON File Storage (" + storageDirectory + ")";
    }
}