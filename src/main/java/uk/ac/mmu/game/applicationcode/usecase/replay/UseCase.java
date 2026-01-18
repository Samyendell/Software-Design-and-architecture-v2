package uk.ac.mmu.game.applicationcode.usecase.replay;

import uk.ac.mmu.game.applicationcode.domainmodel.*;
import uk.ac.mmu.game.applicationcode.domainmodel.board.Board;
import uk.ac.mmu.game.applicationcode.domainmodel.board.BoardFactory;
import uk.ac.mmu.game.applicationcode.domainmodel.dice.FixedSequenceDiceRoller;
import uk.ac.mmu.game.applicationcode.domainmodel.events.GameObserver;
import uk.ac.mmu.game.applicationcode.domainmodel.player.Player;
import uk.ac.mmu.game.applicationcode.domainmodel.player.PlayerFactory;
import uk.ac.mmu.game.applicationcode.domainmodel.rules.*;

import java.util.List;

public class UseCase implements Provided {
    private final Required.GameRepository repository;
    private final GameObserver observer;

    public UseCase(Required.GameRepository repository, GameObserver observer) {
        this.repository = repository;
        this.observer = observer;
    }

    @Override
    public ReplayResponse execute(ReplayRequest request) {
        try {
            GameRecord record = repository.findById(request.getGameId()).
                    orElseThrow(() -> new IllegalArgumentException("Game not found: " + request.getGameId()));

            FixedSequenceDiceRoller replayDice = new FixedSequenceDiceRoller(record.getDiceSequence()
                    .toArray(new Integer[0]));

            Game game = createGame(record.getConfiguration());

            game.addObserver(observer);

            while (!game.isGameOver()) {
                int roll = replayDice.roll();
                game.takeTurn(roll);
            }

            return ReplayResponse.success(record);

        } catch (Exception e) {
            return ReplayResponse.failure("Failed to replay game: " + e.getMessage());
        }
    }

    private Game createGame(GameConfiguration config) {
        GameId id = GameId.generate();

        Board board = BoardFactory.createBoard(config);
        List<Player> players = PlayerFactory.createPlayers(config, board);

        EndRule endRule = config.isExactLandingRequired() ? new ExactEndRule() : new OvershootEndRule();

        HitRule hitRule = config.isForfeitOnHit() ? new ForfeitOnHitRule() : new AllowHitRule();

        return new Game(id, config, board, players, endRule, hitRule);
    }
}