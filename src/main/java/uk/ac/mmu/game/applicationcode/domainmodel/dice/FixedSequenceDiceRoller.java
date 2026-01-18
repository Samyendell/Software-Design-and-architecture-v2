package uk.ac.mmu.game.applicationcode.domainmodel.dice;

import java.util.Arrays;
import java.util.List;

public class FixedSequenceDiceRoller implements DiceRoller {
    private final List<Integer> sequence;
    private int currentIndex;

    public FixedSequenceDiceRoller(Integer... rolls) {
        this.sequence = Arrays.asList(rolls);
        this.currentIndex = 0;
    }

    @Override
    public int roll() {
        if (currentIndex >= sequence.size()) {
            throw new IllegalStateException(
                    "No more dice rolls in sequence (needed " + (currentIndex + 1) +
                            " rolls but only " + sequence.size() + " provided)");
        }
        return sequence.get(currentIndex++);
    }
}