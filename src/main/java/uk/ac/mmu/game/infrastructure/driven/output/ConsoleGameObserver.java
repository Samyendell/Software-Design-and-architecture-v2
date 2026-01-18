package uk.ac.mmu.game.infrastructure.driven.output;

import uk.ac.mmu.game.applicationcode.domainmodel.events.*;

public class ConsoleGameObserver implements GameObserver {

    @Override
    public void onGameEvent(GameEvent event) {
        String output = event.toString();
        if (!output.isEmpty()) {
            System.out.println(output);
        }
    }
}
