package edu.utep.cs.cs4330.sudoku.model.utility.grid;
/** An abstraction of Sudoku puzzle. */
/** Wrap for a new game */

/**
 * @author: Jesus Chavez
 * @macuser: aex on 2/28/18.
 */

import edu.utep.cs.cs4330.sudoku.model.utility.grid.Grid;

/** future use as a container for players */
public class SimpleGrid<S> extends Grid<S> {
    /** Create a new board of the given size. */
    // public SimpleGrid (int size, Player p1, Player p2, NetIntent... )
    private static final int NO_FILL = 0;
    private static final int MAX_POSSIBLE_HINTS = 17;
    public SimpleGrid(int length) throws Exception {
        super(length);
    }

    @Override
    protected int getMaxFilled() {
        return NO_FILL;
    }

    @Override
    protected int getMaxHint() {
        return MAX_POSSIBLE_HINTS;
    }


}

