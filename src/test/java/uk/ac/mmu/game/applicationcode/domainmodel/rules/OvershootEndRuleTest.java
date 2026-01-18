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

class OvershootEndRuleTest {

    private OvershootEndRule rule;
    private Player player;
    private List<Position> path;

    @BeforeEach
    void setUp() {
        rule = new OvershootEndRule();
        BasicBoard board = new BasicBoard();
        path = board.createPathForPlayer(PlayerColour.RED);
        player = new Player("RED", PlayerColour.RED, path);
    }

    @Test
    @DisplayName("canWin should return true when dice roll overshoots end")
    void canWinShouldReturnTrueWhenDiceRollOvershoots() {
        int distanceToEnd = player.getDistanceToEnd();
        assertTrue(rule.canWin(player, distanceToEnd + 5));
    }

    @Test
    @DisplayName("processMove should always allow the move")
    void processMoveShouldAlwaysAllowTheMove() {
        boolean moved = rule.processMove(player, 5);

        assertTrue(moved);
        assertEquals(1, player.getTurnCount());
    }

    @Test
    @DisplayName("processMove should clamp to end when overshooting")
    void processMoveShouldClampToEndWhenOvershooting() {
        int distanceToEnd = player.getDistanceToEnd();

        rule.processMove(player, distanceToEnd + 10);

        assertTrue(player.isAtEnd());
        assertEquals(path.get(path.size() - 1), player.getCurrentPosition());
    }
}