package uk.ac.mmu.game;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import uk.ac.mmu.game.infrastructure.driving.PlayRunner;

/**
 * Main Spring Boot Application.
 */
@SpringBootApplication
public class SoftwareProduct {

  public static void main(String[] args) {
    SpringApplication.run(SoftwareProduct.class, args);
  }

  @Bean
  public CommandLineRunner run(PlayRunner playRunner) {
    return args -> {
      System.out.println();
      System.out.println("╔" + "═".repeat(58) + "╗");
      System.out.println("║" + " ".repeat(10) + 
          "SIMPLE FRUSTRATION GAME SIMULATION" + " ".repeat(14) + "║");
      System.out.println("╚" + "═".repeat(58) + "╝");
      System.out.println();

      playRunner.runAllScenarios();

      System.out.println("╔" + "═".repeat(58) + "╗");
      System.out.println("║" + " ".repeat(17) + 
          "ALL SCENARIOS COMPLETE" + " ".repeat(19) + "║");
      System.out.println("╚" + "═".repeat(58) + "╝");
    };
  }
}
