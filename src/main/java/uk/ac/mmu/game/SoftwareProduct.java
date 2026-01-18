package uk.ac.mmu.game;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import uk.ac.mmu.game.infrastructure.driven.persistence.GameRepository;
import uk.ac.mmu.game.infrastructure.driving.ConsoleUI;
import uk.ac.mmu.game.infrastructure.driving.PlayRunner;

@SpringBootApplication
public class SoftwareProduct {

    public static void main(String[] args) {
        SpringApplication.run(SoftwareProduct.class, args);
    }

    @Bean
    public CommandLineRunner run(PlayRunner playRunner, ConsoleUI consoleUI, GameRepository repository) {
        return args -> {
            System.out.println();
            System.out.println("=".repeat(60));
            System.out.println("SIMPLE FRUSTRATION GAME SIMULATION");
            System.out.println("=".repeat(60));
            System.out.println();

            System.out.println("Using: " + repository.getStrategyName());
            System.out.println();

            System.out.println("Running game scenarios...");
            System.out.println();

            playRunner.runAllScenarios();

            System.out.println();
            System.out.println("=".repeat(60));
            System.out.println("ALL SCENARIOS COMPLETE");
            System.out.println("=".repeat(60));

            consoleUI.askForReplay();
        };
    }
}