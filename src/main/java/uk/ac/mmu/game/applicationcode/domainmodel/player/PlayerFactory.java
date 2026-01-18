package uk.ac.mmu.game.applicationcode.domainmodel.player;

import uk.ac.mmu.game.applicationcode.domainmodel.GameConfiguration;
import uk.ac.mmu.game.applicationcode.domainmodel.board.Board;
import uk.ac.mmu.game.applicationcode.domainmodel.board.Position;

import java.util.ArrayList;
import java.util.List;

public class PlayerFactory {

    public static Player createPlayer(PlayerColour colour, Board board) {
        List<Position> path = board.createPathForPlayer(colour);
        return new Player(colour.name(), colour, path);
    }

    public static List<Player> createPlayers(GameConfiguration configuration, Board board) {
        List<Player> players = new ArrayList<>();
        for (PlayerColour colour : configuration.getPlayers()) {
            players.add(createPlayer(colour, board));
        }
        return players;
    }
}