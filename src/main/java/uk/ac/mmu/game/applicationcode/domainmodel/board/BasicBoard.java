package uk.ac.mmu.game.applicationcode.domainmodel.board;

import uk.ac.mmu.game.applicationcode.domainmodel.player.PlayerColour;

/**
 * Basic Board: 18 main positions, 3 tail positions, 2 players.
 */
public class BasicBoard extends Board {
  public BasicBoard() {
    super(18, 3);
  }

  @Override
  protected int getHomePosition(PlayerColour colour) {
    return switch (colour) {
      case RED -> 1;
      case BLUE -> 10;
      default -> throw new IllegalArgumentException(
          "Basic board only supports RED and BLUE players");
    };
  }

  @Override
  public int getNumberOfPlayers() {
    return 2;
  }
}