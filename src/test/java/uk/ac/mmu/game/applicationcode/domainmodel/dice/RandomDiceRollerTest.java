package uk.ac.mmu.game.applicationcode.domainmodel.dice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class RandomDiceRollerTest {

    @Test
    @DisplayName("Should return value between 1 and 6")
    void shouldReturnValueBetween1And6() {
        RandomDiceRoller dice = new RandomDiceRoller();

        int roll = dice.roll();
        assertTrue(roll >= 1 && roll <= 6);
    }

    @Test
    @DisplayName("Should produce different results over multiple rolls")
    void shouldProduceDifferentResultsOverMultipleRolls() {
        RandomDiceRoller dice = new RandomDiceRoller();
        Set<Integer> uniqueRolls = new HashSet<>();

        for (int i = 0; i < 20; i++) {
            uniqueRolls.add(dice.roll());
        }

        assertTrue(uniqueRolls.size() > 1);
    }
}