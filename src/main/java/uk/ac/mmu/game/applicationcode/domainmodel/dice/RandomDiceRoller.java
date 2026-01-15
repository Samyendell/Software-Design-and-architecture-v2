package uk.ac.mmu.game.applicationcode.domainmodel.dice;

import java.util.Random;

/**
 * Concrete Component: Base dice roller with random number generation.
 */
public class RandomDiceRoller implements DiceRoller {
  private final Random random;
  private final int sides;

  public RandomDiceRoller(int sides) {
    this.random = new Random();
    this.sides = sides;
  }

  @Override
  public int roll() {
    return random.nextInt(sides) + 1;
  }
}