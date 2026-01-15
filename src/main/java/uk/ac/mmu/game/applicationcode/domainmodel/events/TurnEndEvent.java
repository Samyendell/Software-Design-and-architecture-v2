package uk.ac.mmu.game.applicationcode.domainmodel.events;

/**
 * Event fired when a player's turn ends successfully.
 */
public class TurnEndEvent extends GameEvent {
  private final String playerName;
  private final int turnNumber;

  public TurnEndEvent(String playerName, int turnNumber) {
    super(EventType.TURN_END);
    this.playerName = playerName;
    this.turnNumber = turnNumber;
  }

  public String getPlayerName() { return playerName; }
  public int getTurnNumber() { return turnNumber; }
}