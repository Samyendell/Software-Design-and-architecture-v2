package uk.ac.mmu.game.applicationcode.domainmodel.dice;

import java.util.ArrayList;
import java.util.List;

/**
 * Decorator that records all dice rolls for later replay.
 * Demonstrates Decorator pattern for adding recording behavior.
 */
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

  public void clearRecording() {
    recordedRolls.clear();
  }
}