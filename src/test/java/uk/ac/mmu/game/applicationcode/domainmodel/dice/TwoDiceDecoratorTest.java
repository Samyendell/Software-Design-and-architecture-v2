package uk.ac.mmu.game.applicationcode.domainmodel.dice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TwoDiceDecoratorTest {

    @Test
    @DisplayName("Should add two dice rolls together")
    void shouldAddTwoDiceRollsTogether() {
        FixedSequenceDiceRoller baseDice = new FixedSequenceDiceRoller(1, 2);
        TwoDiceDecorator twoDice = new TwoDiceDecorator(baseDice);

        assertEquals(3, twoDice.roll());
    }

    @Test
    @DisplayName("Should always return result between 2 and 12")
    void resultShouldAlwaysReturnResultBetween2And12() {
        RandomDiceRoller baseDice = new RandomDiceRoller();
        TwoDiceDecorator twoDice = new TwoDiceDecorator(baseDice);

        for (int i = 0; i < 10; i++) {
            int roll = twoDice.roll();
            assertTrue(roll >= 2 && roll <= 12);
        }
    }
}