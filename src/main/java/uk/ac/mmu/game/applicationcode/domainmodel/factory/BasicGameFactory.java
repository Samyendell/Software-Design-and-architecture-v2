package uk.ac.mmu.game.applicationcode.domainmodel.factory;

import uk.ac.mmu.game.applicationcode.domainmodel.*;
import uk.ac.mmu.game.applicationcode.domainmodel.board.Board;
import uk.ac.mmu.game.applicationcode.domainmodel.board.Position;
import uk.ac.mmu.game.applicationcode.domainmodel.player.Player;
import uk.ac.mmu.game.applicationcode.domainmodel.player.PlayerColor;
import uk.ac.mmu.game.applicationcode.domainmodel.rules.*;

import java.util.List;

public final class BasicGameFactory extends AbstractGameFactory {

  private final GameConfiguration config;

  public BasicGameFactory() {
    this(new GameConfiguration(true, false, false, false));
  }

  public BasicGameFactory(GameConfiguration config) {
    this.config = config;
  }

  public BasicGameFactory(boolean useSingleDie, boolean exactLandingRequired, boolean forfeitOnHit) {
    this(new GameConfiguration(useSingleDie, exactLandingRequired, forfeitOnHit, false));
  }

  @Override
  public GameConfiguration configuration() {
    return config;
  }

  @Override
  protected Game factoryMethod() {
    Board board = new Board(18, 3);

    EndRule endRule = config.isExactLandingRequired() ? new ExactEndRule() : new OvershootEndRule();
    HitRule hitRule = config.isForfeitOnHit() ? new ForfeitOnHitRule() : new AllowHitRule();
    GameRules rules = new GameRules(endRule, hitRule);

    Player red = new Player("RED", PlayerColor.RED, createPath("RED"));
    Player blue = new Player("BLUE", PlayerColor.BLUE, createPath("BLUE"));

    return new Game(board, List.of(red, blue), rules, config);
  }

  private static List<Position> createPath(String color) {
    return List.of(
        Position.home(color, 1),
        Position.main(2),
        Position.main(3),
        Position.main(4),
        Position.main(5),
        Position.main(6),
        Position.main(7),
        Position.main(8),
        Position.main(9),
        Position.main(10),
        Position.main(11),
        Position.main(12),
        Position.main(13),
        Position.main(14),
        Position.main(15),
        Position.main(16),
        Position.main(17),
        Position.tail(color, 1),
        Position.tail(color, 2),
        Position.end(color, 1)
    );
  }
}
