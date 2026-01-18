package uk.ac.mmu.game.applicationcode.domainmodel.events;

import uk.ac.mmu.game.applicationcode.domainmodel.board.Position;

public class ForfeitEvent extends GameEvent {
    private final String playerName;
    private final ForfeitReason reason;
    private final Position attemptedPosition;
    private final Position remainsAt;

    public ForfeitEvent(String playerName, ForfeitReason reason, Position attemptedPosition, Position remainsAt) {
        super(EventType.FORFEIT);
        this.playerName = playerName;
        this.reason = reason;
        this.attemptedPosition = attemptedPosition;
        this.remainsAt = remainsAt;
    }

    public String getPlayerName() {
        return playerName;
    }

    public ForfeitReason getReason() {
        return reason;
    }

    public Position getAttemptedPosition() {
        return attemptedPosition;
    }

    public Position getRemainsAt() {
        return remainsAt;
    }

    public enum ForfeitReason {
        OVERSHOOT, HIT
    }

    @Override
    public String toString() {
        String message = playerName + " ";
        message += reason == ForfeitReason.OVERSHOOT ? "overshoots" : "hit";
        message += "\n" + playerName + " remains at " + remainsAt;
        return message;
    }
}