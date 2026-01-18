package uk.ac.mmu.game.applicationcode.domainmodel.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.ac.mmu.game.applicationcode.domainmodel.board.BasicBoard;
import uk.ac.mmu.game.applicationcode.domainmodel.board.Position;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;
    private List<Position> path;

    @BeforeEach
    void setUp() {
        BasicBoard board = new BasicBoard();
        path = board.createPathForPlayer(PlayerColour.RED);
        player = new Player("RED", PlayerColour.RED, path);
    }

    @Test
    @DisplayName("Player should move forward by dice roll amount")
    void playerShouldMoveForwardByDiceRollAmount() {
        player.move(3);

        assertEquals(path.get(3), player.getCurrentPosition());
        assertEquals(1, player.getTurnCount());
    }

    @Test
    @DisplayName("Player should not move beyond end of path")
    void playerShouldNotMoveBeyondEndOfPath() {
        int pathLength = path.size();
        player.move(pathLength + 10);

        assertEquals(path.get(pathLength - 1), player.getCurrentPosition());
        assertTrue(player.isAtEnd());
    }

    @Test
    @DisplayName("getPositionAfterMove should not actually move player")
    void getPositionAfterMoveShouldNotActuallyMovePlayer() {
        Position futurePosition = player.getPositionAfterMove(5);

        assertEquals(path.get(5), futurePosition);
        assertEquals(path.get(0), player.getCurrentPosition());
        assertEquals(0, player.getTurnCount());
    }

    @Test
    @DisplayName("wouldOvershootEnd should detect overshooting")
    void wouldOvershootEndShouldDetectOvershooting() {
        int distanceToEnd = path.size() - 1;

        assertFalse(player.wouldOvershootEnd(distanceToEnd));
        assertTrue(player.wouldOvershootEnd(distanceToEnd + 1));
    }
}