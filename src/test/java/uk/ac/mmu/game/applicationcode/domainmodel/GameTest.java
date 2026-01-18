package uk.ac.mmu.game.applicationcode.domainmodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.ac.mmu.game.applicationcode.domainmodel.board.Board;
import uk.ac.mmu.game.applicationcode.domainmodel.board.BoardFactory;
import uk.ac.mmu.game.applicationcode.domainmodel.dice.FixedSequenceDiceRoller;
import uk.ac.mmu.game.applicationcode.domainmodel.player.Player;
import uk.ac.mmu.game.applicationcode.domainmodel.player.PlayerFactory;
import uk.ac.mmu.game.applicationcode.domainmodel.rules.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for Game class.
 */
class GameTest {

    private GameConfiguration config;
    private Board board;
    private List<Player> players;

    @BeforeEach
    void setUp() {
        config = GameConfiguration.basicGame();
        board = BoardFactory.createBoard(config);
        players = PlayerFactory.createPlayers(config, board);
    }

    @Test
    @DisplayName("New game should start in Ready state")
    void newGameShouldStartInReadyState() {
        Game game = createGame(new OvershootEndRule(), new AllowHitRule());

        assertEquals("Ready", game.getCurrentState().getStateName());
        assertFalse(game.isGameOver());
    }

    @Test
    @DisplayName("Game should transition to InPlay after first turn")
    void gameShouldTransitionToInPlayAfterFirstTurn() {
        Game game = createGame(new OvershootEndRule(), new AllowHitRule());

        game.takeTurn(5);

        assertEquals("InPlay", game.getCurrentState().getStateName());
    }

    @Test
    @DisplayName("Players should alternate turns")
    void playersShouldAlternateTurns() {
        Game game = createGame(new OvershootEndRule(), new AllowHitRule());

        Player firstPlayer = game.getCurrentPlayer();
        game.takeTurn(3);

        Player secondPlayer = game.getCurrentPlayer();
        assertNotEquals(firstPlayer, secondPlayer);
    }

    @Test
    @DisplayName("Game should count total turns correctly")
    void gameShouldCountTotalTurnsCorrectly() {
        Game game = createGame(new OvershootEndRule(), new AllowHitRule());

        assertEquals(0, game.getTotalTurns());
        game.takeTurn(3);
        assertEquals(1, game.getTotalTurns());
        game.takeTurn(4);
        assertEquals(2, game.getTotalTurns());
    }

    @Test
    @DisplayName("Game should end when player reaches end position")
    void gameShouldEndWhenPlayerReachesEndPosition() {
        Game game = createGame(new OvershootEndRule(), new AllowHitRule());

        FixedSequenceDiceRoller dice = new FixedSequenceDiceRoller(12, 12, 12, 12);

        while (!game.isGameOver()) {
            game.takeTurn(dice.roll());
        }

        assertTrue(game.isGameOver());
        assertNotNull(game.getWinner());
        assertEquals("GameOver", game.getCurrentState().getStateName());
    }

    @Test
    @DisplayName("Winner should be correctly identified")
    void winnerShouldBeCorrectlyIdentified() {
        Game game = createGame(new OvershootEndRule(), new AllowHitRule());

        Player expectedWinner = game.getCurrentPlayer();

        FixedSequenceDiceRoller dice = new FixedSequenceDiceRoller(
                12, 1, 12, 1, 12, 1
        );

        while (!game.isGameOver()) {
            game.takeTurn(dice.roll());
        }

        assertEquals(expectedWinner, game.getWinner());
    }

    private Game createGame(EndRule endRule, HitRule hitRule) {
        return new Game(GameId.generate(), config, board, players,
                endRule, hitRule);
    }
}