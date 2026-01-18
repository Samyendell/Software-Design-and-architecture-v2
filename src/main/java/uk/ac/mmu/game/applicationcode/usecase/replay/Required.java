package uk.ac.mmu.game.applicationcode.usecase.replay;

import uk.ac.mmu.game.applicationcode.domainmodel.GameRecord;
import uk.ac.mmu.game.applicationcode.domainmodel.GameId;

import java.util.List;
import java.util.Optional;

public interface Required {
    interface GameRepository {
        void save(GameRecord record);

        Optional<GameRecord> findById(GameId id);

        List<GameId> listAll();
    }
}