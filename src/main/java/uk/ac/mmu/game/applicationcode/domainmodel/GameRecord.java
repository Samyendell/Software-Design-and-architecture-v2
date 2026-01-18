package uk.ac.mmu.game.applicationcode.domainmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.ac.mmu.game.applicationcode.domainmodel.player.PlayerColour;

import java.util.ArrayList;
import java.util.List;

public class GameRecord {
    private final GameId gameId;
    private final GameConfiguration configuration;
    private final List<Integer> diceSequence;
    private final PlayerColour winner;
    private final int winnerTurns;
    private final int totalTurns;
    private final long timestamp;

    @JsonCreator
    public GameRecord(@JsonProperty("gameId") GameId gameId,
                      @JsonProperty("configuration") GameConfiguration configuration,
                      @JsonProperty("diceSequence") List<Integer> diceSequence,
                      @JsonProperty("winner") PlayerColour winner,
                      @JsonProperty("winnerTurns") int winnerTurns,
                      @JsonProperty("totalTurns") int totalTurns) {
        this.gameId = gameId;
        this.configuration = configuration;
        this.diceSequence = new ArrayList<>(diceSequence);
        this.winner = winner;
        this.winnerTurns = winnerTurns;
        this.totalTurns = totalTurns;
        this.timestamp = System.currentTimeMillis();
    }

    public GameId getGameId() {
        return gameId;
    }

    public GameConfiguration getConfiguration() {
        return configuration;
    }

    public List<Integer> getDiceSequence() {
        return new ArrayList<>(diceSequence);
    }

    public PlayerColour getWinner() {
        return winner;
    }

    public int getWinnerTurns() {
        return winnerTurns;
    }

    public int getTotalTurns() {
        return totalTurns;
    }

    public long getTimestamp() {
        return timestamp;
    }
}