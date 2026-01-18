package uk.ac.mmu.game.applicationcode.domainmodel.player;

import uk.ac.mmu.game.applicationcode.domainmodel.board.Position;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private final PlayerColour colour;
    private final List<Position> path;
    private int currentPathIndex;
    private int turnCount;

    public Player(String name, PlayerColour colour, List<Position> path) {
        this.name = name;
        this.colour = colour;
        this.path = new ArrayList<>(path);
        this.currentPathIndex = 0;
        this.turnCount = 0;
    }

    public void move(int spaces) {
        currentPathIndex = Math.min(currentPathIndex + spaces, path.size() - 1);
        turnCount++;
    }

    public void incrementTurnCountWithoutMoving() {
        turnCount++;
    }

    public Position getCurrentPosition() {
        return path.get(currentPathIndex);
    }

    public Position getPositionAfterMove(int spaces) {
        int newIndex = currentPathIndex + spaces;
        if (newIndex >= path.size()) {
            return path.get(path.size() - 1);
        }
        return path.get(newIndex);
    }

    public boolean isAtEnd() {
        return getCurrentPosition().isEnd();
    }

    public boolean wouldReachEnd(int spaces) {
        return currentPathIndex + spaces >= path.size() - 1;
    }

    public boolean wouldOvershootEnd(int spaces) {
        return currentPathIndex + spaces > path.size() - 1;
    }

    public int getDistanceToEnd() {
        return path.size() - 1 - currentPathIndex;
    }

    public String getName() {
        return name;
    }

    public PlayerColour getColour() {
        return colour;
    }

    public int getTurnCount() {
        return turnCount;
    }
}