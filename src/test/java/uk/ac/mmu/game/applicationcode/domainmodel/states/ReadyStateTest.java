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

class ReadyStateTest {

    private Game game;

    @BeforeEach
    void setUp() {
        GameConfiguration config = GameConfiguration.basicGame();
        Board board = BoardFactory.createBoard(config);
        List<Player> players = PlayerFactory.createPlayers(config, board);

        game = new Game(GameId.generate(), config, board, players,
                new OvershootEndRule(), new AllowHitRule());
    }

    @Test
    @DisplayName("Should have correct state name")
    void shouldHaveCorrectStateName() {
        ReadyState state = new ReadyState();
        assertEquals("Ready", state.getStateName());
    }

    @Test
    @DisplayName("takeTurn should transition game to InPlayState")
    void takeTurnShouldTransitionGameToInPlayState() {
        ReadyState state = new ReadyState();
        state.takeTurn(game, 6);

        assertEquals("InPlay", game.getCurrentState().getStateName());
    }

    @Test
    @DisplayName("Game should start in ReadyState")
    void gameShouldStartInReadyState() {
        assertEquals("Ready", game.getCurrentState().getStateName());
    }
}