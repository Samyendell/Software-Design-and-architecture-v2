package uk.ac.mmu.game.infrastructure.driven.output;

import uk.ac.mmu.game.applicationcode.domainmodel.GameConfiguration;
import uk.ac.mmu.game.applicationcode.domainmodel.board.Board;
import uk.ac.mmu.game.applicationcode.domainmodel.board.BoardFactory;
import uk.ac.mmu.game.applicationcode.domainmodel.player.PlayerColour;

public class ConfigurationPrinter {

    public static void print(GameConfiguration config) {
        Board board = BoardFactory.createBoard(config);

        System.out.printf("Board: %d main positions, %d tail positions%n", board.getMainPositions(),
                board.getTailPositions());

        for (PlayerColour colour : config.getPlayers()) {
            printPlayerPath(colour, board);
        }

        printRules(config);
        System.out.println();
    }

    private static void printPlayerPath(PlayerColour colour, Board board) {
        var path = board.createPathForPlayer(colour);
        StringBuilder pathStr = new StringBuilder(colour.name() + ": ");

        for (int i = 0; i < path.size(); i++) {
            pathStr.append(path.get(i).getDisplayName());
            if (i < path.size() - 1) {
                pathStr.append(", ");
            }
        }

        System.out.println(pathStr);
    }

    private static void printRules(GameConfiguration config) {
        if (config.isForfeitOnHit()) {
            System.out.println("Rule: Forfeit turn if hitting another player");
        } else {
            System.out.println("Rule: Hits ignored, multiple players can occupy same position");
        }

        if (config.isExactLandingRequired()) {
            System.out.println("Rule: Must land exactly on END to win");
        } else {
            System.out.println("Rule: Can land on or beyond END to win");
        }
    }
}
