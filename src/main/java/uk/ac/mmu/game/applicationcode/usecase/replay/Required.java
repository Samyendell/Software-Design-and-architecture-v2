package uk.ac.mmu.game.applicationcode.usecase.replay;

import uk.ac.mmu.game.applicationcode.domainmodel.GameId;
import uk.ac.mmu.game.applicationcode.domainmodel.GameRecord;
import uk.ac.mmu.game.applicationcode.domainmodel.events.GameObserver;
import java.util.Optional;
import java.util.List;

public interface Required {
  interface GameRepository {
    Optional<GameRecord> findById(GameId id);
    List<GameId> listAllGameIds();  // New method for listing games
  }
  
  interface ObserverFactory {
    GameObserver createObserver();
  }
}