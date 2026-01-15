package uk.ac.mmu.game;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.ac.mmu.game.infrastructure.driven.output.ConsoleGameObserver;
import uk.ac.mmu.game.infrastructure.driven.persistence.*;
import uk.ac.mmu.game.infrastructure.driving.ConsoleUI;
import uk.ac.mmu.game.infrastructure.driving.PlayRunner;

@Configuration
public class AppConfig {

    @Value("${game.persistence.strategy:memory}")
    private String persistenceStrategy;

    @Bean
    public PersistenceStrategy persistenceStrategy() {
        return switch (persistenceStrategy.toLowerCase()) {
            case "file", "json" -> {
                System.out.println("Using JSON FILE persistence strategy");
                yield new JsonPersistenceStrategy();  // Use Jackson JSON
            }
            case "memory" -> {
                System.out.println("Using IN-MEMORY persistence strategy");
                yield new InMemoryPersistenceStrategy();
            }
            default -> {
                System.out.println("Unknown strategy '" + persistenceStrategy +
                        "', defaulting to IN-MEMORY");
                yield new InMemoryPersistenceStrategy();
            }
        };
    }

    @Bean
    public GameRepository gameRepository(PersistenceStrategy strategy) {
        return new FileGameRepository(strategy);
    }

    @Bean
    public ConsoleGameObserver consoleObserver() {
        return new ConsoleGameObserver();
    }

    @Bean
    public uk.ac.mmu.game.applicationcode.usecase.play.UseCase playUseCase(
            GameRepository repository,
            ConsoleGameObserver observer) {
        return new uk.ac.mmu.game.applicationcode.usecase.play.UseCase(repository, () -> observer);
    }

    @Bean
    public uk.ac.mmu.game.applicationcode.usecase.replay.UseCase replayUseCase(
            GameRepository repository,
            ConsoleGameObserver observer) {
        return new uk.ac.mmu.game.applicationcode.usecase.replay.UseCase(repository, () -> observer);
    }

    @Bean
    public PlayRunner playRunner(
            uk.ac.mmu.game.applicationcode.usecase.play.UseCase playUseCase,
            ConsoleGameObserver observer) {
        return new PlayRunner(playUseCase, observer);
    }

    @Bean
    public ConsoleUI consoleUI(
            GameRepository repository,
            uk.ac.mmu.game.applicationcode.usecase.replay.UseCase replayUseCase) {
        return new ConsoleUI(repository, replayUseCase);
    }
}