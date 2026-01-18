package uk.ac.mmu.game.infrastructure.driven.persistence;

import uk.ac.mmu.game.applicationcode.domainmodel.GameId;
import uk.ac.mmu.game.applicationcode.domainmodel.GameRecord;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends uk.ac.mmu.game.applicationcode.usecase.play.Required.GameRepository,
        uk.ac.mmu.game.applicationcode.usecase.replay.Required.GameRepository {

    @Override
    void save(GameRecord record);

    @Override
    Optional<GameRecord> findById(GameId id);

    @Override
    List<GameId> listAll();

    String getStrategyName();
}