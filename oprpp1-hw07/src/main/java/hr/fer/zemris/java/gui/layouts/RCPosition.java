package hr.fer.zemris.java.gui.layouts;

import java.util.Objects;

/**
 * Used to represent a position in a grid
 */
public class RCPosition {

    private final int row;
    private final int col;

    public RCPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Factory method for creating RCPosition objects
     * @param text to be parsed
     * @return RCPosition object created from text
     */
    public static RCPosition parse(String text) {
        String[] rc = text.split(",");

        try {
            int row = Integer.parseInt(rc[0]);
            int col = Integer.parseInt(rc[1]);

            return new RCPosition(row, col);
        } catch (Exception e) {
            throw new IllegalArgumentException("Text can't be parsed into a RCPosition.");
        }
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RCPosition position = (RCPosition) o;
        return row == position.row && col == position.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}
