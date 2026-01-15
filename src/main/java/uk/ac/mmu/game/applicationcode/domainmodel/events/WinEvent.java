package uk.ac.mmu.game.applicationcode.domainmodel.events;

/**
 * Event fired when a player wins the game.
 */
public class WinEvent extends GameEvent {
  private final String winnerName;
  private final int winnerTurns;
  private final int totalTurns;

  public WinEvent(String winnerName, int winnerTurns, int totalTurns) {
    super(EventType.WIN);
    this.winnerName = winnerName;
    this.winnerTurns = winnerTurns;
    this.totalTurns = totalTurns;
  }

  public String getWinnerName() { return winnerName; }
  public int getWinnerTurns() { return winnerTurns; }
  public int getTotalTurns() { return totalTurns; }

  @Override
  public String toString() {
    return winnerName + " wins in " + winnerTurns + " turns!\n" +
           "Total turns " + totalTurns;
  }
}