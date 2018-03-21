package edu.utep.cs.cs4330.sudoku.select;
import java.util.List;

import edu.utep.cs.cs4330.sudoku.model.Square;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 2/27/18.
 */

public class RowSet<S> extends AbstractSudokuSet<S>{


    public RowSet(S key, List<S> keySet) {
        super(key, keySet);
    }

    @Override
    public int getRootIndex(Object root, int length) {
        return ((Square)root).y*length;
    }

    @Override
    public int updChangeInDx(int delta, int length) {
        return 0;
    }

    @Override
    public int updChangeInDy(int delta, int length) {
        return delta + 1;
    }

}
