package uk.ac.mmu.game.applicationcode.usecase.play;

import uk.ac.mmu.game.applicationcode.domainmodel.*;
import uk.ac.mmu.game.applicationcode.domainmodel.board.Board;
import uk.ac.mmu.game.applicationcode.domainmodel.board.BoardFactory;
import uk.ac.mmu.game.applicationcode.domainmodel.dice.RecordingDiceRoller;
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
    public PlayResponse execute(PlayRequest request) {
        try {
            RecordingDiceRoller recordingDice = new RecordingDiceRoller(request.getDiceRoller());

            Game game = createGame(request.getConfiguration());

            game.addObserver(observer);

            while (!game.isGameOver()) {
                int roll = recordingDice.roll();
                game.takeTurn(roll);
            }

            GameRecord record = new GameRecord(game.getId(), game.getConfiguration(), recordingDice.getRecordedRolls(),
                    game.getWinner().getColour(), game.getWinner().getTurnCount(), game.getTotalTurns());

            repository.save(record);

            return PlayResponse.success(game.getId(), game.getWinner().getColour(), game.getTotalTurns());

        } catch (Exception e) {
            return PlayResponse.failure("Failed to play game: " + e.getMessage());
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