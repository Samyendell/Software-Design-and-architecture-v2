package uk.ac.mmu.game.applicationcode.domainmodel;

import uk.ac.mmu.game.applicationcode.domainmodel.player.PlayerColour;
import java.util.List;

/**
 * Immutable configuration for a game.
 * Encapsulates all game setup parameters.
 */
public class GameConfiguration {
  private final List<PlayerColour> players;
  private final boolean useSingleDie;
  private final boolean exactLandingRequired;
  private final boolean forfeitOnHit;
  private final boolean isLargeBoard;

  public GameConfiguration(List<PlayerColour> players, 
                          boolean useSingleDie,
                          boolean exactLandingRequired, 
                          boolean forfeitOnHit,
                          boolean isLargeBoard) {
    this.players = List.copyOf(players);
    this.useSingleDie = useSingleDie;
    this.exactLandingRequired = exactLandingRequired;
    this.forfeitOnHit = forfeitOnHit;
    this.isLargeBoard = isLargeBoard;
  }

  // Factory methods for common configurations
  public static GameConfiguration basicGame() {
    return new GameConfiguration(
        List.of(PlayerColour.RED, PlayerColour.BLUE),
        false, false, false, false
    );
  }

  public static GameConfiguration largeBoardGame() {
    return new GameConfiguration(
        List.of(PlayerColour.RED, PlayerColour.BLUE, 
                PlayerColour.GREEN, PlayerColour.YELLOW),
        false, false, false, true
    );
  }

  public List<PlayerColour> getPlayers() { return players; }
  public boolean isUseSingleDie() { return useSingleDie; }
  public boolean isExactLandingRequired() { return exactLandingRequired; }
  public boolean isForfeitOnHit() { return forfeitOnHit; }
  public boolean isLargeBoard() { return isLargeBoard; }
}