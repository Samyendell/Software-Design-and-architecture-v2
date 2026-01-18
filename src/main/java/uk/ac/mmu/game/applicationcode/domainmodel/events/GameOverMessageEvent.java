package uk.ac.mmu.game.applicationcode.domainmodel.events;

public class GameOverMessageEvent extends GameEvent {
    public GameOverMessageEvent() {
        super(EventType.GAME_OVER_MESSAGE);
    }

    @Override
    public String toString() {
        return "Game over";
    }
}