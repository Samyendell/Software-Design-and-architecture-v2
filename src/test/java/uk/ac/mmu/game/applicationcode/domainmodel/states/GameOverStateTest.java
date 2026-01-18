package uk.ac.mmu.game.applicationcode.domainmodel.states;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameOverStateTest {

    @Test
    @DisplayName("Should have correct state name")
    void shouldHaveCorrectStateName() {
        GameOverState state = new GameOverState();
        assertEquals("GameOver", state.getStateName());
    }
}