package uk.ac.mmu.game.applicationcode.domainmodel.events;

public class TurnStartEvent extends GameEvent {
    private final String playerName;
    private final int diceRoll;
    private final int turnNumber;

    public TurnStartEvent(String playerName, int diceRoll, int turnNumber) {
        super(EventType.TURN_START);
        this.playerName = playerName;
        this.diceRoll = diceRoll;
        this.turnNumber = turnNumber;
    }

    @Override
    public String toString() {
        return playerName + " turn " + turnNumber + " rolls " + diceRoll;
    }
}