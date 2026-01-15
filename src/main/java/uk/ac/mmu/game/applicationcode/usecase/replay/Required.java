package uk.ac.mmu.game.applicationcode.usecase.replay;

import uk.ac.mmu.game.applicationcode.domainmodel.GameId;
import uk.ac.mmu.game.applicationcode.domainmodel.GameRecord;
import java.util.Optional;

public interface Required {
  interface GameRepository {
    Optional<GameRecord> findById(GameId id);
  }
}