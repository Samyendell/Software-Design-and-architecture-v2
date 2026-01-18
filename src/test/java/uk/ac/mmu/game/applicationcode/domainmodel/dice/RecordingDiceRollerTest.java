package uk.ac.mmu.game.applicationcode.domainmodel.dice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class RecordingDiceRollerTest {

    @Test
    @DisplayName("Should record all dice rolls in order")
    void shouldRecordAllDiceRollsInOrder() {
        FixedSequenceDiceRoller baseDice = new FixedSequenceDiceRoller(1, 2, 3, 4);
        RecordingDiceRoller recordingDice = new RecordingDiceRoller(baseDice);

        recordingDice.roll();
        recordingDice.roll();
        recordingDice.roll();
        recordingDice.roll();

        List<Integer> recorded = recordingDice.getRecordedRolls();
        assertEquals(List.of(1, 2, 3, 4), recorded);
    }

    @Test
    @DisplayName("Should record the correct value")
    void shouldRecordTheCorrectValue() {
        FixedSequenceDiceRoller baseDice = new FixedSequenceDiceRoller(1, 2);
        RecordingDiceRoller recordingDice = new RecordingDiceRoller(baseDice);

        assertEquals(1, recordingDice.roll());
        assertEquals(2, recordingDice.roll());
    }

    @Test
    @DisplayName("Should work with TwoDiceDecorator")
    void shouldWorkWithTwoDiceDecorator() {
        FixedSequenceDiceRoller baseDice = new FixedSequenceDiceRoller(3, 4, 5, 6);
        TwoDiceDecorator twoDice = new TwoDiceDecorator(baseDice);
        RecordingDiceRoller recordingDice = new RecordingDiceRoller(twoDice);

        recordingDice.roll();
        recordingDice.roll();

        assertEquals(List.of(7, 11), recordingDice.getRecordedRolls());
    }
}