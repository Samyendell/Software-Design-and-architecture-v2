package uk.ac.mmu.game.applicationcode.domainmodel.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.ac.mmu.game.applicationcode.domainmodel.GameConfiguration;
import uk.ac.mmu.game.applicationcode.domainmodel.board.BasicBoard;
import uk.ac.mmu.game.applicationcode.domainmodel.board.Board;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PlayerFactoryTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new BasicBoard();
    }

    @Test
    @DisplayName("Should create single player with correct properties")
    void shouldCreateSinglePlayerWithCorrectProperties() {
        Player player = PlayerFactory.createPlayer(PlayerColour.RED, board);

        assertNotNull(player);
        assertEquals("RED", player.getName());
        assertEquals(PlayerColour.RED, player.getColour());
    }

    @Test
    @DisplayName("Should create correct number of players from configuration")
    void shouldCreateCorrectNumberOfPlayersFromConfiguration() {
        GameConfiguration config = GameConfiguration.basicGame();
        List<Player> players = PlayerFactory.createPlayers(config, board);

        assertEquals(2, players.size());
        assertEquals(PlayerColour.RED, players.get(0).getColour());
        assertEquals(PlayerColour.BLUE, players.get(1).getColour());
    }
}