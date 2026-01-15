package uk.ac.mmu.game.applicationcode.domainmodel.dice;

/**
 * Decorator Pattern: Adds "roll twice" behavior.
 * Wraps a DiceRoller and rolls it twice, summing the results.
 */
public class TwoDiceDecorator implements DiceRoller {
  private final DiceRoller baseDice;

  public TwoDiceDecorator(DiceRoller baseDice) {
    this.baseDice = baseDice;
  }

  @Override
  public int roll() {
    return baseDice.roll() + baseDice.roll();
  }
}