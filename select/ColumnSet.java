package edu.utep.cs.cs4330.sudoku.select;
import java.util.List;

import edu.utep.cs.cs4330.sudoku.model.Square;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 2/28/18.
 */

public class ColumnSet<T> extends AbstractSudokuSet<T> {

    public ColumnSet(T key, List<T> keySet) {
        super(key, keySet);
    }

    @Override
    protected int getRootIndex(Object root) {
        return ((Square)root).y;
    }

    @Override
    protected int updChangeInDelta(int delta, int axis) {
        if(axis < 0){
            return 0;
        } else if(axis > 0){
            return delta+1;
        }
        return -1;
    }
}
