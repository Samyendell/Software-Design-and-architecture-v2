package uk.ac.mmu.game.applicationcode.domainmodel.factory;

import uk.ac.mmu.game.applicationcode.domainmodel.*;
import uk.ac.mmu.game.applicationcode.domainmodel.board.Board;
import uk.ac.mmu.game.applicationcode.domainmodel.board.Position;
import uk.ac.mmu.game.applicationcode.domainmodel.player.Player;
import uk.ac.mmu.game.applicationcode.domainmodel.player.PlayerColor;
import uk.ac.mmu.game.applicationcode.domainmodel.rules.*;

import java.util.List;

public final class LargeBoardGameFactory extends AbstractGameFactory {

  private final GameConfiguration config;

  public LargeBoardGameFactory() {
    this(new GameConfiguration(true, false, false, true));
  }

  public LargeBoardGameFactory(GameConfiguration config) {
    this.config = config;
  }

  @Override
  public GameConfiguration configuration() {
    return config;
  }

  @Override
  protected Game factoryMethod() {
    Board board = new Board(36, 6);

    EndRule endRule = config.isExactLandingRequired() ? new ExactEndRule() : new OvershootEndRule();
    HitRule hitRule = config.isForfeitOnHit() ? new ForfeitOnHitRule() : new AllowHitRule();
    GameRules rules = new GameRules(endRule, hitRule);

    Player red = new Player("RED", PlayerColor.RED, createPath("RED"));
    Player blue = new Player("BLUE", PlayerColor.BLUE, createPath("BLUE"));
    Player green = new Player("GREEN", PlayerColor.GREEN, createPath("GREEN"));
    Player yellow = new Player("YELLOW", PlayerColor.YELLOW, createPath("YELLOW"));

    return new Game(board, List.of(red, blue, green, yellow), rules, config);
  }

  private static List<Position> createPath(String color) {
    // Simple long path for demonstration; main positions 2..37 then tail then end.
    java.util.ArrayList<Position> path = new java.util.ArrayList<>();
    path.add(Position.home(color, 1));
    for (int i = 2; i <= 37; i++) {
      path.add(Position.main(i));
    }
    path.add(Position.tail(color, 1));
    path.add(Position.tail(color, 2));
    path.add(Position.tail(color, 3));
    path.add(Position.tail(color, 4));
    path.add(Position.tail(color, 5));
    path.add(Position.end(color, 1));
    return java.util.List.copyOf(path);
  }
}
