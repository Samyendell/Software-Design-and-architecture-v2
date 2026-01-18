package uk.ac.mmu.game.applicationcode.domainmodel.events;

public interface GameObserver {
    void onGameEvent(GameEvent event);
}