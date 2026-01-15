package uk.ac.mmu.game.infrastructure.driving;

import uk.ac.mmu.game.applicationcode.domainmodel.GameId;
import uk.ac.mmu.game.applicationcode.usecase.replay.Provided;
import uk.ac.mmu.game.applicationcode.usecase.replay.ReplayRequest;
import uk.ac.mmu.game.applicationcode.usecase.replay.ReplayResponse;
import uk.ac.mmu.game.infrastructure.driven.persistence.GameRepository;
import java.util.List;
import java.util.Scanner;

/**
 * Interactive Console UI for game replay.
 * Provides simple text-based menu for users to select and replay games.
 */
public class ConsoleUI {
  private final GameRepository repository;
  private final Provided replayUseCase;
  private final Scanner scanner;

  public ConsoleUI(GameRepository repository, Provided replayUseCase) {
    this.repository = repository;
    this.replayUseCase = replayUseCase;
    this.scanner = new Scanner(System.in);
  }

  public void showReplayMenu() {
    System.out.println();
    System.out.println("╔" + "═".repeat(58) + "╗");
    System.out.println("║" + " ".repeat(20) + "GAME REPLAY MENU" + 
                      " ".repeat(22) + "║");
    System.out.println("╚" + "═".repeat(58) + "╝");
    System.out.println();

    // Show persistence strategy
    System.out.println("Storage: " + repository.getStrategyName());
    System.out.println();

    // List available games
    List<GameId> gameIds = repository.listAllGameIds();
    
    if (gameIds.isEmpty()) {
      System.out.println("No saved games available for replay.");
      System.out.println("Run some games first to generate saved games.");
      return;
    }

    System.out.println("Available games (" + gameIds.size() + " total):");
    System.out.println("-".repeat(60));
    
    for (int i = 0; i < gameIds.size(); i++) {
      GameId gameId = gameIds.get(i);
      System.out.printf("%d. Game ID: %s%n", i + 1, 
                       gameId.getValue().substring(0, 8) + "...");
      
      // Show game details if available
      repository.findById(gameId).ifPresent(record -> {
        System.out.printf("   Winner: %s in %d turns (Total: %d turns)%n",
            record.getWinner(),
            record.getWinnerTurns(),
            record.getTotalTurns());
        System.out.printf("   Configuration: %s board, %d players%s%s%s%n",
            record.getConfiguration().isLargeBoard() ? "Large" : "Basic",
            record.getConfiguration().getPlayers().size(),
            record.getConfiguration().isUseSingleDie() ? ", Single Die" : "",
            record.getConfiguration().isExactLandingRequired() ? ", Exact Landing" : "",
            record.getConfiguration().isForfeitOnHit() ? ", Forfeit on Hit" : "");
      });
      System.out.println();
    }

    System.out.println("-".repeat(60));
    System.out.println();
    System.out.print("Enter game number to replay (or 'q' to quit): ");
    
    String input = scanner.nextLine().trim();
    
    if (input.equalsIgnoreCase("q")) {
      System.out.println("Exiting replay menu.");
      return;
    }

    try {
      int selection = Integer.parseInt(input);
      
      if (selection < 1 || selection > gameIds.size()) {
        System.out.println("Invalid selection. Please choose a number between 1 and " + 
                          gameIds.size());
        return;
      }

      GameId selectedGameId = gameIds.get(selection - 1);
      replayGame(selectedGameId);
      
    } catch (NumberFormatException e) {
      System.out.println("Invalid input. Please enter a number or 'q' to quit.");
    }
  }

  private void replayGame(GameId gameId) {
    System.out.println();
    System.out.println("Replaying game: " + gameId.getValue().substring(0, 8) + "...");
    System.out.println();

    ReplayResponse response = replayUseCase.execute(new ReplayRequest(gameId));

    if (!response.isSuccess()) {
      System.out.println("Error: " + response.getErrorMessage());
    }
  }

  public void askForReplay() {
    System.out.println();
    System.out.print("Would you like to replay a saved game? (y/n): ");
    String answer = scanner.nextLine().trim();
    
    if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) {
      showReplayMenu();
    }
  }
}