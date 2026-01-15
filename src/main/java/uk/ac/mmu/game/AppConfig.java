package uk.ac.mmu.game;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.ac.mmu.game.applicationcode.domainmodel.events.GameObserver;
import uk.ac.mmu.game.applicationcode.usecase.play.UseCase as PlayUseCase;
import uk.ac.mmu.game.applicationcode.usecase.replay.UseCase as ReplayUseCase;
import uk.ac.mmu.game.infrastructure.driven.output.ConsoleGameObserver;
import uk.ac.mmu.game.infrastructure.driven.persistence.FileGameRepository;
import uk.ac.mmu.game.infrastructure.driven.persistence.GameRepository;
import uk.ac.mmu.game.infrastructure.driving.PlayRunner;
import uk.ac.mmu.game.infrastructure.driving.ReplayRunner;

/**
 * Spring Boot Configuration.
 * Dependency Injection: Wires all components together.
 */
@Configuration
public class AppConfig {

  @Bean
  public GameRepository gameRepository() {
    return new FileGameRepository();
  }

  @Bean
  public ConsoleGameObserver consoleObserver() {
    return new ConsoleGameObserver();
  }

  @Bean
  public PlayUseCase playUseCase(GameRepository repository, 
                                 ConsoleGameObserver observer) {
    return new PlayUseCase(
        repository,
        () -> observer
    );
  }

  @Bean
  public ReplayUseCase replayUseCase(GameRepository repository) {
    return new ReplayUseCase(repository);
  }

  @Bean
  public PlayRunner playRunner(PlayUseCase playUseCase, 
                               ConsoleGameObserver observer) {
    return new PlayRunner(playUseCase, observer);
  }

  @Bean
  public ReplayRunner replayRunner(ReplayUseCase replayUseCase) {
    return new ReplayRunner(replayUseCase);
  }
}