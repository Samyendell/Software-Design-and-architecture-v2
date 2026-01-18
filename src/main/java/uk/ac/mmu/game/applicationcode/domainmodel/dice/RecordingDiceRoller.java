package uk.ac.mmu.game.applicationcode.domainmodel.dice;

import java.util.ArrayList;
import java.util.List;

public class RecordingDiceRoller implements DiceRoller {
    private final DiceRoller baseDice;
    private final List<Integer> recordedRolls;

    public RecordingDiceRoller(DiceRoller baseDice) {
        this.baseDice = baseDice;
        this.recordedRolls = new ArrayList<>();
    }

    @Override
    public int roll() {
        int value = baseDice.roll();
        recordedRolls.add(value);
        return value;
    }

    public List<Integer> getRecordedRolls() {
        return new ArrayList<>(recordedRolls);
    }
}