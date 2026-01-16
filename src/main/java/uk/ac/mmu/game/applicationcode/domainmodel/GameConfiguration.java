package uk.ac.mmu.game.applicationcode.domainmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.ac.mmu.game.applicationcode.domainmodel.player.PlayerColour;
import java.util.List;

/**
 * GameConfiguration with Jackson annotations.
 */
public class GameConfiguration {
    private final List<PlayerColour> players;
    private final boolean useSingleDie;
    private final boolean exactLandingRequired;
    private final boolean forfeitOnHit;
    private final boolean isLargeBoard;

    @JsonCreator  // Jackson: Use this constructor when deserializing
    public GameConfiguration(
            @JsonProperty("players") List<PlayerColour> players,
            @JsonProperty("useSingleDie") boolean useSingleDie,
            @JsonProperty("exactLandingRequired") boolean exactLandingRequired,
            @JsonProperty("forfeitOnHit") boolean forfeitOnHit,
            @JsonProperty("isLargeBoard") boolean isLargeBoard) {
        this.players = List.copyOf(players);
        this.useSingleDie = useSingleDie;
        this.exactLandingRequired = exactLandingRequired;
        this.forfeitOnHit = forfeitOnHit;
        this.isLargeBoard = isLargeBoard;
    }

    public static GameConfiguration basicGame() {
        return new GameConfiguration(
                List.of(PlayerColour.RED, PlayerColour.BLUE),
                false, false, false, false
        );
    }

    public static GameConfiguration largeBoardGame() {
        return new GameConfiguration(
                List.of(PlayerColour.RED, PlayerColour.BLUE,
                        PlayerColour.GREEN, PlayerColour.YELLOW),
                false, false, false, true
        );
    }

    // Jackson will use these getters for serialization
    public List<PlayerColour> getPlayers() { return players; }
    public boolean isUseSingleDie() { return useSingleDie; }
    public boolean isExactLandingRequired() { return exactLandingRequired; }
    public boolean isForfeitOnHit() { return forfeitOnHit; }
    public boolean isLargeBoard() { return isLargeBoard; }
}