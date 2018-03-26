package edu.utep.cs.cs4330.sudoku.model.utility.select;
import java.util.List;

import edu.utep.cs.cs4330.sudoku.model.utility.grid.Square;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 2/27/18.
 */

public class RowSet<S> extends AbstractSudokuSet<S>{


    public RowSet(S key, List<S> keySet) throws Exception {
        super(key, keySet);
    }
    // good use of abstraction, localized the problem, changed from y to x
    @Override
    public int getRootIndex(Object root, int length) {
        return ((Square)root).y*length;
    }

    @Override
    public int updChangeInDy(int delta, int i, int length) {
        return 0;
    }

    @Override
    public int updChangeInDx(int delta, int i, int length) {
        return delta+1;
    }

    @Override
    public int getInitDelta() {
        return -1;
    }

}
