package uk.ac.mmu.game.applicationcode.usecase.play;

import uk.ac.mmu.game.applicationcode.domainmodel.GameId;
import uk.ac.mmu.game.applicationcode.domainmodel.player.PlayerColour;

/**
 * Response object for Play use case.
 * Contains result of playing a game.
 */
public class PlayResponse {
  private final GameId gameId;
  private final PlayerColour winner;
  private final int totalTurns;
  private final boolean success;
  private final String errorMessage;

  private PlayResponse(GameId gameId, PlayerColour winner, int totalTurns,
                      boolean success, String errorMessage) {
    this.gameId = gameId;
    this.winner = winner;
    this.totalTurns = totalTurns;
    this.success = success;
    this.errorMessage = errorMessage;
  }

  public static PlayResponse success(GameId gameId, PlayerColour winner, 
                                     int totalTurns) {
    return new PlayResponse(gameId, winner, totalTurns, true, null);
  }

  public static PlayResponse failure(String errorMessage) {
    return new PlayResponse(null, null, 0, false, errorMessage);
  }

  public GameId getGameId() { return gameId; }
  public PlayerColour getWinner() { return winner; }
  public int getTotalTurns() { return totalTurns; }
  public boolean isSuccess() { return success; }
  public String getErrorMessage() { return errorMessage; }
}