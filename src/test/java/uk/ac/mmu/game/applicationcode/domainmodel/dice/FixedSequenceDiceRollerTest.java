package uk.ac.mmu.game.applicationcode.domainmodel.dice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FixedSequenceDiceRollerTest {

    @Test
    @DisplayName("Should return rolls in correct order")
    void shouldReturnRollsInCorrectOrder() {
        FixedSequenceDiceRoller dice = new FixedSequenceDiceRoller(1, 2, 3, 4);

        assertEquals(1, dice.roll());
        assertEquals(2, dice.roll());
        assertEquals(3, dice.roll());
        assertEquals(4, dice.roll());
    }
}