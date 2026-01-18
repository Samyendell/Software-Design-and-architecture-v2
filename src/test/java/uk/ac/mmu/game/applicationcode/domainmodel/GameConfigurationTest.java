package uk.ac.mmu.game.applicationcode.domainmodel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.ac.mmu.game.applicationcode.domainmodel.player.PlayerColour;
import static org.junit.jupiter.api.Assertions.*;

class GameConfigurationTest {

    @Test
    @DisplayName("basicGame should create 2 player standard configuration")
    void basicGameShouldCreate2PlayerConfiguration() {
        GameConfiguration config = GameConfiguration.basicGame();

        assertEquals(2, config.getPlayers().size());
        assertTrue(config.getPlayers().contains(PlayerColour.RED));
        assertTrue(config.getPlayers().contains(PlayerColour.BLUE));
        assertFalse(config.isLargeBoard());
    }

    @Test
    @DisplayName("largeBoardGame should create 4 player configuration")
    void largeBoardGameShouldCreate4PlayerConfiguration() {
        GameConfiguration config = GameConfiguration.largeBoardGame();

        assertEquals(4, config.getPlayers().size());
        assertTrue(config.isLargeBoard());
    }

    @Test
    @DisplayName("Factory methods should enable correct variations")
    void factoryMethodsShouldEnableCorrectVariations() {
        assertTrue(GameConfiguration.singleDieGame().isUseSingleDie());
        assertTrue(GameConfiguration.exactLandingGame().isExactLandingRequired());
        assertTrue(GameConfiguration.forfeitOnHitGame().isForfeitOnHit());
    }

    @Test
    @DisplayName("getPlayers should return immutable list")
    void getPlayersShouldReturnImmutableList() {
        GameConfiguration config = GameConfiguration.basicGame();

        assertThrows(UnsupportedOperationException.class, () -> {
            config.getPlayers().add(PlayerColour.GREEN);
        });
    }
}