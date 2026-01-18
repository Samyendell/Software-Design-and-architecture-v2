package uk.ac.mmu.game.applicationcode.domainmodel.board;

import uk.ac.mmu.game.applicationcode.domainmodel.player.Player;
import uk.ac.mmu.game.applicationcode.domainmodel.player.PlayerColour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Board {
    protected final int mainPositions;
    protected final int tailPositions;
    private final Map<Position, List<Player>> occupancy;

    protected Board(int mainPositions, int tailPositions) {
        this.mainPositions = mainPositions;
        this.tailPositions = tailPositions;
        this.occupancy = new HashMap<>();
    }

    protected abstract int getHomePosition(PlayerColour colour);

    public final List<Position> createPathForPlayer(PlayerColour colour) {
        List<Position> path = new ArrayList<>();
        int homePos = getHomePosition(colour);

        for (int i = 0; i < mainPositions; i++) {
            int pos = ((homePos - 1 + i) % mainPositions) + 1;
            if (i == 0) {
                path.add(Position.home(colour.name(), pos));
            } else {
                path.add(Position.main(pos));
            }
        }

        for (int i = 1; i <= tailPositions; i++) {
            if (i == tailPositions) {
                path.add(Position.end(colour.name(), i));
            } else {
                path.add(Position.tail(colour.name(), i));
            }
        }
        return path;
    }

    public void updateOccupancy(List<Player> players) {
        occupancy.clear();
        for (Player player : players) {
            Position pos = player.getCurrentPosition();
            occupancy.computeIfAbsent(pos, k -> new ArrayList<>()).add(player);
        }
    }

    public List<Player> getPlayersAt(Position position) {
        return occupancy.getOrDefault(position, new ArrayList<>());
    }

    public boolean isPositionOccupied(Position position, Player movingPlayer) {
        List<Player> playersAtPosition = getPlayersAt(position);
        return playersAtPosition.stream().anyMatch(p -> !p.equals(movingPlayer));
    }

    public int getMainPositions() {
        return mainPositions;
    }

    public int getTailPositions() {
        return tailPositions;
    }
}