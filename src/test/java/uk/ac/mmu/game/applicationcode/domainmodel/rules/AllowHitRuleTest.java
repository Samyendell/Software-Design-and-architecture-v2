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

class AllowHitRuleTest {

    private AllowHitRule rule;
    private Board board;
    private Player player1;
    private Player player2;

    @BeforeEach
    void setUp() {
        rule = new AllowHitRule();
        board = new BasicBoard();

        player1 = new Player("RED", PlayerColour.RED,
                board.createPathForPlayer(PlayerColour.RED));
        player2 = new Player("BLUE", PlayerColour.BLUE,
                board.createPathForPlayer(PlayerColour.BLUE));
    }

    @Test
    @DisplayName("processHit should always return true")
    void processHitShouldAlwaysReturnTrue() {
        Position targetPosition = player1.getPositionAfterMove(5);

        assertTrue(rule.processHit(player1, targetPosition, board));
    }

    @Test
    @DisplayName("isHit should detect when other player occupies position")
    void isHitShouldDetectWhenOtherPlayerOccupiesPosition() {
        // Move player2 to position 15 (BLUE starts at 10, moves 5 steps)
        player2.move(5);
        board.updateOccupancy(List.of(player1, player2));

        // Check if player1 moving to position 15 would hit player2
        // RED starts at 1, so needs to move 14 steps to reach position 15
        Position targetPosition = player1.getPositionAfterMove(14);

        assertTrue(rule.isHit(targetPosition, board, player1));
    }

    @Test
    @DisplayName("isHit should return false when position is empty")
    void isHitShouldReturnFalseWhenPositionIsEmpty() {
        board.updateOccupancy(List.of(player1, player2));
        Position emptyPosition = player1.getPositionAfterMove(5);

        assertFalse(rule.isHit(emptyPosition, board, player1));
    }
}