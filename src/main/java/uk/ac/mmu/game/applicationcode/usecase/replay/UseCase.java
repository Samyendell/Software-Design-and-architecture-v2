package uk.ac.mmu.game.applicationcode.usecase.replay;

import uk.ac.mmu.game.applicationcode.domainmodel.GameRecord;

public class UseCase implements Provided {
  private final Required.GameRepository repository;

  public UseCase(Required.GameRepository repository) {
    this.repository = repository;
  }

  @Override
  public ReplayResponse execute(ReplayRequest request) {
    return repository.findById(request.getGameId())
        .map(ReplayResponse::success)
        .orElse(ReplayResponse.failure(
            "Game not found: " + request.getGameId()));
  }
}