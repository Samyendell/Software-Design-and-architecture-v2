package uk.ac.mmu.game.applicationcode.domainmodel.events;

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

    @Override
    public String toString() {
        return winnerName + " wins in " + winnerTurns + " turns\n" + "Total turns " + totalTurns;
    }
}