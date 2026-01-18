package uk.ac.mmu.game.applicationcode.domainmodel.states;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.ac.mmu.game.applicationcode.domainmodel.Game;
import uk.ac.mmu.game.applicationcode.domainmodel.GameConfiguration;
import uk.ac.mmu.game.applicationcode.domainmodel.GameId;
import uk.ac.mmu.game.applicationcode.domainmodel.board.Board;
import uk.ac.mmu.game.applicationcode.domainmodel.board.BoardFactory;
import uk.ac.mmu.game.applicationcode.domainmodel.player.Player;
import uk.ac.mmu.game.applicationcode.domainmodel.player.PlayerFactory;
import uk.ac.mmu.game.applicationcode.domainmodel.rules.AllowHitRule;
import uk.ac.mmu.game.applicationcode.domainmodel.rules.OvershootEndRule;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class InPlayStateTest {

    private Game game;

    @BeforeEach
    void setUp() {
        GameConfiguration config = GameConfiguration.basicGame();
        Board board = BoardFactory.createBoard(config);
        List<Player> players = PlayerFactory.createPlayers(config, board);

        game = new Game(GameId.generate(), config, board, players,
                new OvershootEndRule(), new AllowHitRule());

        game.takeTurn(1);
    }

    @Test
    @DisplayName("Should have correct state name")
    void shouldHaveCorrectStateName() {
        InPlayState state = new InPlayState();
        assertEquals("InPlay", state.getStateName());
    }

    @Test
    @DisplayName("takeTurn should process the turn")
    void takeTurnShouldProcessTheTurn() {
        Player currentPlayer = game.getCurrentPlayer();
        int initialTurnCount = currentPlayer.getTurnCount();

        InPlayState state = new InPlayState();
        state.takeTurn(game, 5);

        assertEquals(initialTurnCount + 1, currentPlayer.getTurnCount());
    }

    @Test
    @DisplayName("Game should remain in InPlayState after normal turn")
    void gameShouldRemainInInPlayStateAfterNormalTurn() {
        game.takeTurn(4);
        assertEquals("InPlay", game.getCurrentState().getStateName());
    }
}