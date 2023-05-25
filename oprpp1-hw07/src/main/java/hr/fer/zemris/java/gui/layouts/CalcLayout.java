package hr.fer.zemris.java.gui.layouts;

import java.awt.*;
import java.util.*;
import java.util.function.Function;

/**
 * This layout is convenient for creating calculator like components
 * <p>
 * It is structured like a 5 * 7 grid with 31 elements (because the first elements spans
 * form (1,1) to (1,5) positions)
 * <p>
 * The grid is illustrated below, each position is denoted by [row, column]
 * <p>
 * [&emsp&emsp&emsp&emsp 1,1 &emsp&emsp&emsp&emsp] [1,6] [1,7] <br>
 * [2,1] [2,2] [2,3] [2,4] [2,5] [2,6] [2,7] <br>
 * [3,1] [3,2] [3,3] [3,4] [3,5] [3,6] [3,7] <br>
 * [4,1] [4,2] [4,3] [4,4] [4,5] [4,6] [4,7] <br>
 * [5,1] [5,2] [5,3] [5,4] [5,5] [5,6] [5,7]
 * <p>
 * Positions [1,2] through [1,5] are illegal.
 * <p>
 * You can also only add one component to each position
 */
public class CalcLayout implements LayoutManager2 {

    /**
     * Horizontal and vertical gap between components
     */
    private final int gap;

    /**
     * A map containing positions of all added components
     */
    private final Map<Component, RCPosition> grid;

    private final Dimension[][] cellSizes;

    /**
     * Special position in the grid with special dimensions
     */
    public static final RCPosition SPECIAL_POSITION = new RCPosition(1, 1);
    /**
     * Width of the special position
     */
    public static final int SPECIAL_POSITION_COL_SPAN = 5;
    /**
     * Height of the special position
     */
    public static final int SPECIAL_POSITION_ROW_SPAN = 1;
    /**
     * Number of grid rows
     */
    public static final int ROWS = 5;
    /**
     * Number of grid columns
     */
    public static final int COLUMNS = 7;

    /**
     * Constructor
     * <p>
     * Sets gap to 0
     */
    public CalcLayout() {
        this(0);
    }

    /**
     * Constructor
     * @param gap that will be used to space components
     */
    public CalcLayout(int gap) {
        this.gap = gap;
        this.grid = new HashMap<>();
        this.cellSizes = new Dimension[ROWS][COLUMNS];
    }

    @Override
    public void addLayoutComponent(Component component, Object o) {
        if(component == null || o == null)
            throw new NullPointerException("Component or restraint can't be null");

        if (!(o instanceof String) && !(o instanceof RCPosition))
            throw new IllegalArgumentException("Restraint must be of type String or RCPosition");

        RCPosition position;

        if (o instanceof String) position = RCPosition.parse((String) o);
        else position = (RCPosition) o;

        if (!isRCPositionValid(position))
            throw new CalcLayoutException("Invalid RCPosition: " + position);

        if (grid.containsValue(position))
            throw new CalcLayoutException("Position already occupied: " + position);

        grid.put(component, position);
    }

    @Override
    public Dimension maximumLayoutSize(Container container) {
        return getDimension(container, Component::getMaximumSize);
    }

    @Override
    public Dimension preferredLayoutSize(Container container) {
        return getDimension(container, Component::getPreferredSize);
    }

    @Override
    public Dimension minimumLayoutSize(Container container) {
        return getDimension(container, Component::getMinimumSize);
    }

    /**
     * Helper method to calculate max, min and preferred layout size
     * @param container that uses CalcLayout
     * @param bound gets specific component sizes
     * @return Desired limited sizes
     */
    private Dimension getDimension(Container container, Function<Component, Dimension> bound) {
        Dimension dimension = new Dimension();

        for (Component component: container.getComponents()) {
            if (grid.get(component) == null)
                throw new CalcLayoutException("Untracked component in layout: " + component);

            boolean isSpecialPosition = grid.get(component).equals(SPECIAL_POSITION);

            Dimension componentDim = bound.apply(component);

            if (isSpecialPosition) {
                int scaledWidth  = (componentDim.width  - (SPECIAL_POSITION_COL_SPAN - 1) * gap) / SPECIAL_POSITION_COL_SPAN;
                int scaledHeight = (componentDim.height - (SPECIAL_POSITION_ROW_SPAN - 1) * gap) / SPECIAL_POSITION_ROW_SPAN;

                dimension.width  = Math.max(dimension.width, scaledWidth);
                dimension.height = Math.max(dimension.height, scaledHeight);
            } else {
                dimension.width = Math.max(dimension.width, componentDim.width);
                dimension.height = Math.max(dimension.height, componentDim.height);
            }
        }

        dimension.width = dimension.width * COLUMNS + (COLUMNS - 1) * gap;
        dimension.height = dimension.height * ROWS + (ROWS - 1) * gap;

        return dimension;
    }

    @Override
    public float getLayoutAlignmentX(Container container) {
        return 0;
    }

    @Override
    public float getLayoutAlignmentY(Container container) {
        return 0;
    }

    @Override
    public void invalidateLayout(Container container) {
    }

    @Override
    public void addLayoutComponent(String s, Component component) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeLayoutComponent(Component component) {
        grid.remove(component);
    }

    @Override
    public void layoutContainer(Container container) {
        setCellSizes(container);

        for (Component component: container.getComponents()) {
            RCPosition position = grid.get(component);

            if (position == null)
                throw new CalcLayoutException("Untracked component in layout: " + component);

            int row = position.getRow() - 1;
            int col = position.getCol() - 1;

            Dimension size;

            if (position.equals(SPECIAL_POSITION))
                size = getSpecialPositionSize();
            else
                size = cellSizes[row][col];

            Point positionOnScreen = getPositionOnScreen(position);

            component.setBounds(new Rectangle(positionOnScreen, size));
        }
    }

    private Point getPositionOnScreen(RCPosition position) {
        Point point = new Point(0, 0);

        int row = position.getRow() - 1;
        int col = position.getCol() - 1;

        for (int i = 0; i < row; i++) {
            point.y += cellSizes[i][col].height + gap;
        }

        for (int i = 0; i < col; i++) {
            point.x += cellSizes[row][i].width + gap;
        }

        return point;
    }

    /**
     * Helper method used to calculate the size of each cell
     * @param container that uses this layout
     * @return Dimension of each cell
     */
    private Dimension[][] setCellSizes(Container container) {
        Dimension containerDim = container.getSize();
        Insets insets = container.getInsets();

        Dimension availableArea = new Dimension();
        availableArea.width  = containerDim.width  - insets.left - insets.right - (COLUMNS - 1) * gap;
        availableArea.height = containerDim.height - insets.top  - insets.bottom - (ROWS - 1) * gap;

        int avgCellWidth = availableArea.width / COLUMNS ;
        int widthRemainder = availableArea.width % COLUMNS;

        int avgCellHeight = availableArea.height / ROWS ;
        int heightRemainder = availableArea.height % ROWS;

        Set<Integer> colsToEnlarge;
        Set<Integer> rowsToEnlarge;

        if (widthRemainder != 0) {
            int widthIncreaseStep = COLUMNS / widthRemainder + COLUMNS / 2;
            if (COLUMNS % 2 == 0)
                widthIncreaseStep++;

            colsToEnlarge = getIndexesToEnlarge(widthRemainder, widthIncreaseStep, COLUMNS);
        } else {
            colsToEnlarge = new TreeSet<>();
        }

        if (heightRemainder != 0) {
            int heightIncreaseStep = ROWS / heightRemainder + ROWS / 2;
            if (ROWS % 2 == 0)
                heightRemainder++;

            rowsToEnlarge = getIndexesToEnlarge(heightRemainder, heightIncreaseStep, ROWS);
        } else {
            rowsToEnlarge = new TreeSet<>();
        }

        for (int i = 0; i < ROWS; i++) {
            int height;

            if (rowsToEnlarge.contains(i))
                height = avgCellHeight + 1;
            else
                height = avgCellHeight;

            for (int j = 0; j < COLUMNS; j++) {
                int width;

                if (colsToEnlarge.contains(j))
                    width = avgCellWidth + 1;
                else
                    width = avgCellWidth;

                cellSizes[i][j] = new Dimension(width, height);
            }
        }

        return cellSizes;
    }

    private Dimension getSpecialPositionSize() {
        int spRowStart = SPECIAL_POSITION.getRow() - 1;
        int spRowEnd = spRowStart + SPECIAL_POSITION_ROW_SPAN;

        int spColStart = SPECIAL_POSITION.getCol() - 1;
        int spColEnd = spColStart + SPECIAL_POSITION_COL_SPAN;

        int spWidth = 0;
        int spHeight = 0;

        for (int i = spRowStart; i < spRowEnd; i++) {
            spHeight += cellSizes[i][spColStart].height;
        }

        for (int i = spColStart; i < spColEnd; i++) {
            spWidth += cellSizes[spRowStart][i].width;
        }

        spWidth += (SPECIAL_POSITION_COL_SPAN - 1) * gap;
        spHeight += (SPECIAL_POSITION_ROW_SPAN - 1) * gap;

        return new Dimension(spWidth, spHeight);
    }

    private Set<Integer> getIndexesToEnlarge(int remainder, int increaseStep, int mod) {
        Set<Integer> indexes = new TreeSet<>();
        for (int i = 0; remainder > 0; i = (i + increaseStep) % mod) {
            indexes.add(i);
            remainder--;
        }

        return indexes;
    }

    private static boolean isRCPositionValid(RCPosition position) {
        int row = position.getRow();
        int col = position.getCol();
        
        if (row < 1 || col < 1)
            return false;
        
        if (row > ROWS || col > COLUMNS)
            return false;
        
        if (row == SPECIAL_POSITION.getRow() && col > SPECIAL_POSITION.getCol() && col < SPECIAL_POSITION.getCol() + SPECIAL_POSITION_COL_SPAN)
            return false;

        if (col == SPECIAL_POSITION.getCol() && row > SPECIAL_POSITION.getRow() && row < SPECIAL_POSITION.getRow() + SPECIAL_POSITION_ROW_SPAN)
            return false;
        
        return true;
    }
}
