package uk.ac.mmu.game.applicationcode.domainmodel;

import uk.ac.mmu.game.applicationcode.domainmodel.player.PlayerColour;

/**
 * Value Object: Complete record of a finished game.
 * Used for persistence and replay.
 */
public class GameRecord {
  private final GameId gameId;
  private final GameConfiguration configuration;
  private final PlayerColour winner;
  private final int winnerTurns;
  private final int totalTurns;

  public GameRecord(GameId gameId, GameConfiguration configuration,
                   PlayerColour winner, int winnerTurns, int totalTurns) {
    this.gameId = gameId;
    this.configuration = configuration;
    this.winner = winner;
    this.winnerTurns = winnerTurns;
    this.totalTurns = totalTurns;
  }

  public GameId getGameId() { return gameId; }
  public GameConfiguration getConfiguration() { return configuration; }
  public PlayerColour getWinner() { return winner; }
  public int getWinnerTurns() { return winnerTurns; }
  public int getTotalTurns() { return totalTurns; }
}