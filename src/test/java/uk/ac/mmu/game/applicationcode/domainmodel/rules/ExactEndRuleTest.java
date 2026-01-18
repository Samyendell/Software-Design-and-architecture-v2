package uk.ac.mmu.game.applicationcode.domainmodel.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.ac.mmu.game.applicationcode.domainmodel.board.BasicBoard;
import uk.ac.mmu.game.applicationcode.domainmodel.board.Position;
import uk.ac.mmu.game.applicationcode.domainmodel.player.Player;
import uk.ac.mmu.game.applicationcode.domainmodel.player.PlayerColour;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ExactEndRuleTest {

    private ExactEndRule rule;
    private Player player;

    @BeforeEach
    void setUp() {
        rule = new ExactEndRule();
        BasicBoard board = new BasicBoard();
        List<Position> path = board.createPathForPlayer(PlayerColour.RED);
        player = new Player("RED", PlayerColour.RED, path);
    }

    @Test
    @DisplayName("canWin should return true when dice roll exactly reaches end")
    void canWinShouldReturnTrueWhenDiceRollExactlyReachesEnd() {
        int distanceToEnd = player.getDistanceToEnd();
        assertTrue(rule.canWin(player, distanceToEnd));
    }

    @Test
    @DisplayName("canWin should return false when dice roll would overshoot")
    void canWinShouldReturnFalseWhenDiceRollWouldOvershoot() {
        int distanceToEnd = player.getDistanceToEnd();
        assertFalse(rule.canWin(player, distanceToEnd + 1));
    }

    @Test
    @DisplayName("processMove should forfeit turn when overshooting")
    void processMoveShouldForfeitTurnWhenOvershooting() {
        int distanceToEnd = player.getDistanceToEnd();
        player.move(distanceToEnd - 2);

        Position positionBefore = player.getCurrentPosition();
        int turnCountBefore = player.getTurnCount();

        boolean moved = rule.processMove(player, 5);

        assertFalse(moved);
        assertEquals(positionBefore, player.getCurrentPosition());
        assertEquals(turnCountBefore + 1, player.getTurnCount());
    }
}