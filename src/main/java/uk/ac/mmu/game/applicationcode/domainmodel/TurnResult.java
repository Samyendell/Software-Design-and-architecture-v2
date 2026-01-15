package uk.ac.mmu.game.applicationcode.domainmodel;

import uk.ac.mmu.game.applicationcode.domainmodel.board.Position;

/**
 * Value Object: Result of a turn attempt.
 */
public class TurnResult {
  private final boolean successful;
  private final Position startPosition;
  private final Position endPosition;
  private final String reason;

  private TurnResult(boolean successful, Position startPosition, 
                    Position endPosition, String reason) {
    this.successful = successful;
    this.startPosition = startPosition;
    this.endPosition = endPosition;
    this.reason = reason;
  }

  public static TurnResult success(Position from, Position to) {
    return new TurnResult(true, from, to, null);
  }

  public static TurnResult forfeit(Position at, String reason) {
    return new TurnResult(false, at, at, reason);
  }

  public boolean isSuccessful() { return successful; }
  public Position getStartPosition() { return startPosition; }
  public Position getEndPosition() { return endPosition; }
  public String getReason() { return reason; }
}