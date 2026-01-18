package uk.ac.mmu.game.applicationcode.domainmodel.dice;

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