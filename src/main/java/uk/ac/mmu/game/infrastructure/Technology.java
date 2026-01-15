package uk.ac.mmu.game.infrastructure;

import uk.ac.mmu.game.applicationcode.domainmodel.*;
import uk.ac.mmu.game.applicationcode.usecase.Required;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Technology class contains all infrastructure implementations.
 * Implements the Required interfaces from use case layer.
 */
public class Technology {

  // ============================================
  // DECORATOR PATTERN - Dice Rollers
  // ============================================

  /**
   * Base dice roller - rolls a single die.
   */
  public static class RandomDiceRoller implements DiceRoller {
    private final Random random;
    private final int sides;

    public RandomDiceRoller(int sides) {
      this.random = new Random();
      this.sides = sides;
    }

    @Override
    public int roll() {
      return random.nextInt(sides) + 1;
    }
  }

  /**
   * Decorator: Wraps a dice roller to roll twice and sum.
   */
  public static class TwoDiceDecorator implements DiceRoller {
    private final DiceRoller baseDice;

    public TwoDiceDecorator(DiceRoller baseDice) {
      this.baseDice = baseDice;
    }

    @Override
    public int roll() {
      return baseDice.roll() + baseDice.roll();
    }
  }

  /**
   * Fixed sequence dice roller for testing.
   */
  public static class FixedSequenceDiceRoller implements DiceRoller {
    private final List<Integer> sequence;
    private int currentIndex;

    public FixedSequenceDiceRoller(int... rolls) {
      this.sequence = new ArrayList<>();
      for (int roll : rolls) {
        sequence.add(roll);
      }
      this.currentIndex = 0;
    }

    @Override
    public int roll() {
      if (currentIndex >= sequence.size()) {
        throw new IllegalStateException(
            "No more dice rolls in sequence");
      }
      return sequence.get(currentIndex++);
    }
  }

  /**
   * Concrete Factory Method implementation for creating dice rollers.
   */
  public static class ConcreteDiceRollerFactory extends DiceRollerFactory {
    @Override
    public DiceRoller createDiceRoller() {
      return new TwoDiceDecorator(new RandomDiceRoller(6));
    }

    @Override
    protected DiceRoller createSingleDiceRoller() {
      return new RandomDiceRoller(6);
    }

    @Override
    protected DiceRoller createTwoDiceRoller() {
      return new TwoDiceDecorator(new RandomDiceRoller(6));
    }
  }

  // ============================================
  // OBSERVER PATTERN - Console Output Observer
  // ============================================

  /**
   * Console output observer - prints game events to console.
   * Implements Observer pattern to receive game notifications.
   */
  public static class ConsoleGameObserver implements GameObserver {
    private GameConfiguration currentConfig;
    private final Map<String, Position> playerStartPositions;

    public ConsoleGameObserver() {
      this.playerStartPositions = new HashMap<>();
    }

    public void setConfiguration(GameConfiguration config) {
      this.currentConfig = config;
      printConfiguration();
    }

    private void printConfiguration() {
      if (currentConfig == null) return;

      System.out.printf("Board length Board positions=%d Tail positions=%d%n",
          currentConfig.getMainPositions(), currentConfig.getTailPositions());

      for (PlayerColor color : currentConfig.getPlayerColors()) {
        printPlayerPath(color, currentConfig.getMainPositions(), 
                       currentConfig.getTailPositions());
      }

      if (currentConfig.isForfeitOnHit()) {
        System.out.println("Player's turn is forfeit if the player would HIT another player");
      } else {
        System.out.println("HITS are ignored, multiple players can occupy the same position");
      }

      if (currentConfig.isExactLandingRequired()) {
        System.out.println("Player must land exactly on the END position to win else the turn is forfeited");
      } else {
        System.out.println("Player can land on or beyond the END position to win");
      }

      System.out.println();
    }

    private void printPlayerPath(PlayerColor color, int mainPositions, int tailPositions) {
      StringBuilder path = new StringBuilder(color.name() + " ");
      int homePos = color.getHomePositionForBoardSize(mainPositions);

      for (int i = 0; i < mainPositions; i++) {
        int pos = ((homePos - 1 + i) % mainPositions) + 1;
        if (i == 0) {
          path.append(pos).append(" (Home)");
        } else {
          path.append(pos);
        }
        path.append(", ");
      }

      String colorPrefix = color.name().substring(0, 1);
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

    @Override
    public void onGameEvent(GameEvent event) {
      switch (event.getType()) {
        case STATE_TRANSITION:
          StateTransitionEvent ste = (StateTransitionEvent) event;
          System.out.println("Game state " + ste.getFromState() + 
                           " -> " + ste.getToState());
          break;

        case TURN_START:
          TurnStartEvent tse = (TurnStartEvent) event;
          System.out.printf("%s turn %d rolls %d%n",
              tse.getPlayer().getName(),
              tse.getPlayer().getTurnCount() + 1,
              tse.getDiceRoll());
          playerStartPositions.put(tse.getPlayer().getName(), tse.getPositionBefore());
          break;

        case HIT:
          HitEvent he = (HitEvent) event;
          System.out.printf("%s hit!%n", he.getPosition());
          break;

        case HIT_FORFEIT:
          HitForfeitEvent hfe = (HitForfeitEvent) event;
          System.out.printf("%s hit!%n", hfe.getTargetPosition());
          System.out.printf("%s remains at %s%n",
              hfe.getPlayer().getName(),
              hfe.getPlayer().getCurrentPosition());
          break;

        case OVERSHOOT_FORFEIT:
          OvershootForfeitEvent ofe = (OvershootForfeitEvent) event;
          System.out.printf("%s overshoots!%n", ofe.getPlayer().getName());
          System.out.printf("%s remains at %s%n",
              ofe.getPlayer().getName(),
              ofe.getPlayer().getCurrentPosition());
          break;

        case TURN_END:
          TurnEndEvent tee = (TurnEndEvent) event;
          Position startPos = playerStartPositions.get(tee.getPlayer().getName());
          if (startPos != null) {
            System.out.printf("%s moves from %s to %s%n",
                tee.getPlayer().getName(),
                startPos,
                tee.getPlayer().getCurrentPosition());
          }
          break;

        case WINNER:
          WinnerEvent we = (WinnerEvent) event;
          System.out.printf("%s wins in %d turns!%n",
              we.getWinner().getName(),
              we.getWinner().getTurnCount());
          System.out.printf("Total turns %d%n", we.getTotalTurns());
          break;

        case GAME_OVER_MESSAGE:
          System.out.println("Game over");
          break;
      }
    }
  }

  // ============================================
  // REPOSITORY IMPLEMENTATION - In-Memory
  // ============================================

  /**
   * In-memory game repository.
   * Implements Required.GameRepository interface.
   */
  public static class InMemoryGameRepository implements Required.GameRepository {
    private final Map<String, Game> games;

    public InMemoryGameRepository() {
      this.games = new HashMap<>();
    }

    @Override
    public void save(Game game) {
      games.put(game.getId(), game);
    }

    @Override
    public Optional<Game> findById(String id) {
      return Optional.ofNullable(games.get(id));
    }

    @Override
    public List<String> listAllIds() {
      return new ArrayList<>(games.keySet());
    }

    public void clear() {
      games.clear();
    }
  }

  // ============================================
  // DICE ROLLER PROVIDER IMPLEMENTATION
  // ============================================

  /**
   * Provides dice rollers to use cases.
   * Implements Required.DiceRollerProvider interface.
   */
  public static class DiceRollerProvider implements Required.DiceRollerProvider {
    private final DiceRollerFactory factory;

    public DiceRollerProvider() {
      this.factory = new ConcreteDiceRollerFactory();
    }

    @Override
    public DiceRoller createDiceRoller(boolean useSingleDie) {
      return factory.createConfiguredDiceRoller(useSingleDie);
    }

    @Override
    public DiceRoller createFixedSequenceDiceRoller(int... rolls) {
      return new FixedSequenceDiceRoller(rolls);
    }
  }

  // ============================================
  // MEDIATOR PATTERN - Game Coordinator
  // ============================================

  /**
   * Mediator Pattern: Coordinates between game components.
   * Simplifies communication between game, observer, and repository.
   */
  public static class GameMediator {
    private final Required.GameRepository repository;
    private final ConsoleGameObserver observer;

    public GameMediator(Required.GameRepository repository, 
                       ConsoleGameObserver observer) {
      this.repository = repository;
      this.observer = observer;
    }

    public String playGame(GameFactory factory, DiceRoller diceRoller) {
      Game game = factory.createGame();
      observer.setConfiguration(game.getConfiguration());
      game.addObserver(observer);

      while (!game.isGameOver()) {
        game.takeTurn(diceRoller.roll());
      }

      repository.save(game);
      return game.getId();
    }

    public void replayGame(String gameId) {
      Game game = repository.findById(gameId)
          .orElseThrow(() -> new IllegalArgumentException(
              "Game not found: " + gameId));

      System.out.println("Replaying game: " + gameId);
      System.out.println("Winner: " + game.getWinner().getName());
      System.out.println("Total turns: " + game.getTotalTurns());
    }
  }
}