package uk.ac.mmu.game.applicationcode.domainmodel.dice;

import java.util.Random;

public class RandomDiceRoller implements DiceRoller {
    private final Random random;

    public RandomDiceRoller() {
        this.random = new Random();
    }

    @Override
    public int roll() {
        return random.nextInt(6) + 1;
    }
}