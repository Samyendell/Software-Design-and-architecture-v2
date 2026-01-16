package uk.ac.mmu.game.infrastructure.driving;

import uk.ac.mmu.game.applicationcode.domainmodel.GameId;
import uk.ac.mmu.game.applicationcode.usecase.replay.*;

public class ReplayRunner {
  private final Provided replayUseCase;

  public ReplayRunner(Provided replayUseCase) {
    this.replayUseCase = replayUseCase;
  } // not being used ????

  public void replay(String gameId) {
    ReplayResponse response = replayUseCase.execute(
        new ReplayRequest(GameId.fromString(gameId)));

    if (response.isSuccess()) {
      System.out.println("Game: " + response.getRecord().getGameId());
      System.out.println("Winner: " + response.getRecord().getWinner());
      System.out.println("Total turns: " + response.getRecord().getTotalTurns());
    } else {
      System.out.println("Error: " + response.getErrorMessage());
    }
  }
}