package edu.utep.cs.cs4330.sudoku.model.utility.grid;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 3/25/18.
 */

public class NormalGrid extends Grid {
    private static final int MAX_POSSIBLE_FILL = 23;
    private static final int NORMAL_HINTS = 5;
    public NormalGrid(int length) throws Exception {
        super(length);
    }

    @Override
    protected int getMaxFilled() {
        return MAX_POSSIBLE_FILL;
    }

    @Override
    protected int getMaxHint() {
        return NORMAL_HINTS;
    }

}
