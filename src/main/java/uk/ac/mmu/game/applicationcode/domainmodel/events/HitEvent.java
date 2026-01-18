package uk.ac.mmu.game.applicationcode.domainmodel.events;

import uk.ac.mmu.game.applicationcode.domainmodel.board.Position;

public class HitEvent extends GameEvent {
    private final Position position;

    public HitEvent(Position position) {
        super(EventType.HIT);
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return position + " hit";
    }
}