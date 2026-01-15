package uk.ac.mmu.game.applicationcode.domainmodel.board;

import uk.ac.mmu.game.applicationcode.domainmodel.player.PlayerColour;

/**
 * Large Board: 36 main positions, 6 tail positions, 4 players.
 */
public class LargeBoard extends Board {
  public LargeBoard() {
    super(36, 6);
  }

  @Override
  protected int getHomePosition(PlayerColour colour) {
    return switch (colour) {
      case RED -> 1;
      case BLUE -> 10;
      case GREEN -> 19;
      case YELLOW -> 28;
    };
  }

  @Override
  public int getNumberOfPlayers() {
    return 4;
  }
}