package uk.ac.mmu.game.infrastructure.driven.persistence;

import uk.ac.mmu.game.applicationcode.domainmodel.GameId;
import uk.ac.mmu.game.applicationcode.domainmodel.GameRecord;
import java.io.*;
import java.util.*;

/**
 * File-based persistence - data persists between runs.
 * Stores each game in a separate file.
 */
public class FilePersistenceStrategy implements PersistenceStrategy {
  private final String storageDirectory;

  public FilePersistenceStrategy() {
    this.storageDirectory = System.getProperty("user.home") + 
                           File.separator + ".simple-frustration";
    new File(storageDirectory).mkdirs();
  }

  @Override
  public void save(GameRecord record) {
    try {
      File file = new File(storageDirectory, 
                          record.getGameId().getValue() + ".ser");
      try (ObjectOutputStream oos = new ObjectOutputStream(
          new FileOutputStream(file))) {
        oos.writeObject(record);
      }
      System.out.println("Game saved to file: " + file.getAbsolutePath());
    } catch (IOException e) {
      throw new RuntimeException("Failed to save game", e);
    }
  }

  @Override
  public Optional<GameRecord> findById(GameId id) {
    try {
      File file = new File(storageDirectory, id.getValue() + ".ser");
      if (!file.exists()) {
        return Optional.empty();
      }
      try (ObjectInputStream ois = new ObjectInputStream(
          new FileInputStream(file))) {
        return Optional.of((GameRecord) ois.readObject());
      }
    } catch (IOException | ClassNotFoundException e) {
      return Optional.empty();
    }
  }

  @Override
  public List<GameId> listAll() {
    File dir = new File(storageDirectory);
    File[] files = dir.listFiles((d, name) -> name.endsWith(".ser"));
    
    List<GameId> ids = new ArrayList<>();
    if (files != null) {
      for (File file : files) {
        String id = file.getName().replace(".ser", "");
        ids.add(GameId.fromString(id));
      }
    }
    return ids;
  }

  @Override
  public String getStrategyName() {
    return "File Storage (" + storageDirectory + ")";
  }
}