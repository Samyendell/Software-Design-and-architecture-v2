package uk.ac.mmu.game.infrastructure.driven.output;

import uk.ac.mmu.game.applicationcode.domainmodel.GameConfiguration;
import uk.ac.mmu.game.applicationcode.domainmodel.events.*;
import uk.ac.mmu.game.applicationcode.domainmodel.player.PlayerColour;

/**
 * Observer implementation that outputs to console.
 * Facade Pattern: Simplifies formatting complex output.
 */
public class ConsoleGameObserver implements GameObserver {
  private GameConfiguration config;

  public void setConfiguration(GameConfiguration config) {
    this.config = config;
    printConfiguration();
  }

  private void printConfiguration() {
    if (config == null) return;

    int mainPos = config.isLargeBoard() ? 36 : 18;
    int tailPos = config.isLargeBoard() ? 6 : 3;

    System.out.printf("Board length Board positions=%d Tail positions=%d%n",
        mainPos, tailPos);

    // Print player paths
    for (PlayerColour colour : config.getPlayers()) {
      printPlayerPath(colour, mainPos, tailPos);
    }

    // Print rules
    if (config.isForfeitOnHit()) {
      System.out.println("Player's turn is forfeit if the player would HIT another player");
    } else {
      System.out.println("HITS are ignored, multiple players can occupy the same position");
    }

    if (config.isExactLandingRequired()) {
      System.out.println("Player must land exactly on the END position to win else the turn is forfeited");
    } else {
      System.out.println("Player can land on or beyond the END position to win");
    }

    System.out.println();
  }

  private void printPlayerPath(PlayerColour colour, int mainPositions, 
                               int tailPositions) {
    StringBuilder path = new StringBuilder(colour.name() + " ");
    int homePos = getHomePosition(colour, mainPositions);

    for (int i = 0; i < mainPositions; i++) {
      int pos = ((homePos - 1 + i) % mainPositions) + 1;
      if (i == 0) {
        path.append(pos).append(" (Home)");
      } else {
        path.append(pos);
      }
      path.append(", ");
    }

    String colorPrefix = colour.name().substring(0, 1);
    for (int i = 1; i <= tailPositions; i++) {
      path.append(colorPrefix).append(i);
      if (i == tailPositions) {
        path.append(" (End)");
      } else {
        path.append(", ");
      }
    }

    System.out.println(path);
  }

  private int getHomePosition(PlayerColour colour, int mainPositions) {
    if (mainPositions == 18) {
      return colour == PlayerColour.RED ? 1 : 10;
    }
    return switch (colour) {
      case RED -> 1;
      case BLUE -> 10;
      case GREEN -> 19;
      case YELLOW -> 28;
    };
  }

  @Override
  public void onGameEvent(GameEvent event) {
    System.out.println(event.toString());
  }
}