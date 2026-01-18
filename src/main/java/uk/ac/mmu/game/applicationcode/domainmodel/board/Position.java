package uk.ac.mmu.game.applicationcode.domainmodel.board;

import java.util.Objects;

public class Position {
    private final int number;
    private final PositionType type;
    private final String displayName;

    private Position(int number, PositionType type, String displayName) {
        this.number = number;
        this.type = type;
        this.displayName = displayName;
    }

    public static Position home(String colour, int homeNumber) {
        return new Position(homeNumber, PositionType.MAIN, "Home (Position " + homeNumber + ")");
    }

    public static Position main(int number) {
        return new Position(number, PositionType.MAIN, "Position " + number);
    }

    public static Position tail(String colour, int tailNumber) {
        return new Position(tailNumber, PositionType.TAIL, colour.substring(0, 1).toUpperCase()
                + tailNumber);
    }

    public static Position end(String colour, int endNumber) {
        return new Position(endNumber, PositionType.END,
                "End (Position " + colour.substring(0, 1).toUpperCase() + endNumber + ")");
    }

    public boolean isEnd() {
        return type == PositionType.END;
    }

    public int getNumber() {
        return number;
    }

    public PositionType getType() {
        return type;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return number == position.number && type == position.type && displayName.equals(position.displayName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, type, displayName);
    }
}