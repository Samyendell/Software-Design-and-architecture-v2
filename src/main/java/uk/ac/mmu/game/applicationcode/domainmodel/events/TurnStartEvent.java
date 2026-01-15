package uk.ac.mmu.game.applicationcode.domainmodel.events;

import uk.ac.mmu.game.applicationcode.domainmodel.board.Position;

/**
 * Event fired when a player's turn starts.
 */
public class TurnStartEvent extends GameEvent {
  private final String playerName;
  private final int diceRoll;
  private final Position positionBefore;
  private final int turnNumber;

  public TurnStartEvent(String playerName, int diceRoll, 
                       Position positionBefore, int turnNumber) {
    super(EventType.TURN_START);
    this.playerName = playerName;
    this.diceRoll = diceRoll;
    this.positionBefore = positionBefore;
    this.turnNumber = turnNumber;
  }

  public String getPlayerName() { return playerName; }
  public int getDiceRoll() { return diceRoll; }
  public Position getPositionBefore() { return positionBefore; }
  public int getTurnNumber() { return turnNumber; }

  @Override
  public String toString() {
    return playerName + " turn " + turnNumber + " rolls " + diceRoll;
  }
}