package uk.ac.mmu.game.applicationcode.domainmodel.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.ac.mmu.game.applicationcode.domainmodel.board.BasicBoard;
import uk.ac.mmu.game.applicationcode.domainmodel.board.Board;
import uk.ac.mmu.game.applicationcode.domainmodel.board.Position;
import uk.ac.mmu.game.applicationcode.domainmodel.player.Player;
import uk.ac.mmu.game.applicationcode.domainmodel.player.PlayerColour;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ForfeitOnHitRuleTest {

    private ForfeitOnHitRule rule;
    private Board board;
    private Player player1;
    private Player player2;

    @BeforeEach
    void setUp() {
        rule = new ForfeitOnHitRule();
        board = new BasicBoard();

        player1 = new Player("RED", PlayerColour.RED,
                board.createPathForPlayer(PlayerColour.RED));
        player2 = new Player("BLUE", PlayerColour.BLUE,
                board.createPathForPlayer(PlayerColour.BLUE));
    }

    @Test
    @DisplayName("processHit should return true when position is empty")
    void processHitShouldReturnTrueWhenPositionIsEmpty() {
        board.updateOccupancy(List.of(player1, player2));
        Position emptyPosition = player1.getPositionAfterMove(5);

        assertTrue(rule.processHit(player1, emptyPosition, board));
    }

    @Test
    @DisplayName("processHit should return false and forfeit when hitting opponent")
    void processHitShouldReturnFalseAndForfeitWhenHittingOpponent() {
        player2.move(5);
        board.updateOccupancy(List.of(player1, player2));

        Position startPosition = player1.getCurrentPosition();
        int startTurnCount = player1.getTurnCount();
        Position occupiedPosition = player2.getCurrentPosition();

        boolean allowed = rule.processHit(player1, occupiedPosition, board);

        assertFalse(allowed);
        assertEquals(startPosition, player1.getCurrentPosition());
        assertEquals(startTurnCount + 1, player1.getTurnCount());
    }
}