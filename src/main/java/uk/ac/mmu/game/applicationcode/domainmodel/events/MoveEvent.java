package uk.ac.mmu.game.applicationcode.domainmodel.events;

import uk.ac.mmu.game.applicationcode.domainmodel.board.Position;

/**
 * Event fired when a player successfully moves.
 */
public class MoveEvent extends GameEvent {
  private final String playerName;
  private final Position from;
  private final Position to;

  public MoveEvent(String playerName, Position from, Position to) {
    super(EventType.MOVE);
    this.playerName = playerName;
    this.from = from;
    this.to = to;
  }

  public String getPlayerName() { return playerName; }
  public Position getFrom() { return from; }
  public Position getTo() { return to; }

  @Override
  public String toString() {
    return playerName + " moves from " + from + " to " + to;
  }
}