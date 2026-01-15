package uk.ac.mmu.game.applicationcode.ports.driven;

import uk.ac.mmu.game.applicationcode.domainmodel.GameRecord;

import java.util.List;
import java.util.Optional;

public interface GameRepository {
  void save(GameRecord record);
  Optional<GameRecord> findById(String id);
  List<String> listIds();
}
