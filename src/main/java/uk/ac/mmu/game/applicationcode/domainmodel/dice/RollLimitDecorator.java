package uk.ac.mmu.game.applicationcode.domainmodel.dice;

/**
 * Decorator Pattern: Adds roll counting and limit enforcement.
 * Useful for scenarios where we want to limit total number of rolls.
 */
public class RollLimitDecorator implements DiceRoller {
  private final DiceRoller baseDice;
  private final int maxRolls;
  private int rollCount;

  public RollLimitDecorator(DiceRoller baseDice, int maxRolls) {
    this.baseDice = baseDice;
    this.maxRolls = maxRolls;
    this.rollCount = 0;
  }

  @Override
  public int roll() {
    if (rollCount >= maxRolls) {
      throw new IllegalStateException(
          "Roll limit reached: " + maxRolls + " rolls");
    }
    rollCount++;
    return baseDice.roll();
  }

  public int getRollCount() {
    return rollCount;
  }

  public boolean hasRollsRemaining() {
    return rollCount < maxRolls;
  }
}