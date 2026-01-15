package uk.ac.mmu.game;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import uk.ac.mmu.game.infrastructure.driven.persistence.GameRepository;
import uk.ac.mmu.game.infrastructure.driving.ConsoleUI;
import uk.ac.mmu.game.infrastructure.driving.PlayRunner;
import uk.ac.mmu.game.applicationcode.usecase.replay.Provided;

@SpringBootApplication
public class SoftwareProduct {

  public static void main(String[] args) {
    SpringApplication.run(SoftwareProduct.class, args);
  }

  @Bean
  public CommandLineRunner run(PlayRunner playRunner, 
                               ConsoleUI consoleUI,
                               GameRepository repository) {
    return args -> {
      System.out.println();
      System.out.println("╔" + "═".repeat(58) + "╗");
      System.out.println("║" + " ".repeat(10) + 
          "SIMPLE FRUSTRATION GAME SIMULATION" + " ".repeat(14) + "║");
      System.out.println("╚" + "═".repeat(58) + "╝");
      System.out.println();

      // Show which persistence strategy is being used
      System.out.println("Using: " + repository.getStrategyName());
      System.out.println();

      // Run all game scenarios
      System.out.println("Running game scenarios...");
      System.out.println();
      
      playRunner.runAllScenarios();

      System.out.println();
      System.out.println("╔" + "═".repeat(58) + "╗");
      System.out.println("║" + " ".repeat(17) + 
          "ALL SCENARIOS COMPLETE" + " ".repeat(19) + "║");
      System.out.println("╚" + "═".repeat(58) + "╝");

      // Ask if user wants to replay
      consoleUI.askForReplay();
    };
  }
}