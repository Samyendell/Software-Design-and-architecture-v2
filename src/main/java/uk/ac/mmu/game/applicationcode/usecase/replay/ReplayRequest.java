package uk.ac.mmu.game.applicationcode.usecase.replay;

import uk.ac.mmu.game.applicationcode.domainmodel.GameId;

public class ReplayRequest {
  private final GameId gameId;

  public ReplayRequest(GameId gameId) {
    this.gameId = gameId;
  }

  public GameId getGameId() { return gameId; }
}
