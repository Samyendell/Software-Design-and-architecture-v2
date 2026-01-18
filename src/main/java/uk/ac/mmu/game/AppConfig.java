package uk.ac.mmu.game;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import uk.ac.mmu.game.applicationcode.domainmodel.events.GameObserver;
import uk.ac.mmu.game.infrastructure.driven.output.ConsoleGameObserver;
import uk.ac.mmu.game.infrastructure.driven.persistence.*;
import uk.ac.mmu.game.infrastructure.driving.ConsoleUI;
import uk.ac.mmu.game.infrastructure.driving.PlayRunner;

@Configuration
public class AppConfig {

    @Value("${game.persistence.strategy:memory}")
    private String persistenceStrategy;

    @Bean
    @Scope("singleton")
    public GameRepository gameRepository() {
        return switch (persistenceStrategy.toLowerCase()) {
            case "json" -> {
                System.out.println("Using JSON FILE persistence strategy");
                yield new JsonGameRepository();
            }
            case "memory" -> {
                System.out.println("Using IN-MEMORY persistence strategy");
                yield new InMemoryGameRepository();
            }
            default -> {
                System.out.println("Unknown strategy '" + persistenceStrategy + "', defaulting to IN-MEMORY");
                yield new InMemoryGameRepository();
            }
        };
    }

    @Bean
    @Scope("singleton")
    public GameObserver gameObserver() {
        return new ConsoleGameObserver();
    }

    @Bean
    @Scope("singleton")
    public uk.ac.mmu.game.applicationcode.usecase.play.UseCase playUseCase(
            GameRepository repository, GameObserver observer) {
        return new uk.ac.mmu.game.applicationcode.usecase.play.UseCase(repository, observer);
    }

    @Bean
    @Scope("singleton")
    public uk.ac.mmu.game.applicationcode.usecase.replay.UseCase replayUseCase(
            GameRepository repository, GameObserver observer) {
        return new uk.ac.mmu.game.applicationcode.usecase.replay.UseCase(repository, observer);
    }

    @Bean
    @Scope("singleton")
    public PlayRunner playRunner(uk.ac.mmu.game.applicationcode.usecase.play.UseCase playUseCase) {
        return new PlayRunner(playUseCase);
    }

    @Bean
    @Scope("singleton")
    public ConsoleUI consoleUI(GameRepository repository, uk.ac.mmu.game.applicationcode.usecase.replay.UseCase replayUseCase) {
        return new ConsoleUI(repository, replayUseCase);
    }
}