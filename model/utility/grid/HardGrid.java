package edu.utep.cs.cs4330.sudoku.model.utility.grid;

import edu.utep.cs.cs4330.sudoku.model.utility.grid.Grid;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 2/28/18.
 */

public class HardGrid<S> extends Grid<S> {
    private static final int MIN_POSSIBLE_FILL = 17;
    private static final int NO_HINTS = 0;
    public HardGrid(int size) throws Exception {
        super(size);
    }

    @Override
    protected int getMaxFilled() {
        return MIN_POSSIBLE_FILL;
    }

    @Override
    protected int getMaxHint() {
        return NO_HINTS;
    }
}
