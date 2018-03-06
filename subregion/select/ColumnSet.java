package edu.utep.cs.cs4330.sudoku.subregion.select;

import java.util.List;

import edu.utep.cs.cs4330.sudoku.model.Square;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 2/28/18.
 */

public class ColumnSet<T> extends AbstractSudokuSet<T> {

    public ColumnSet(T key, int parentSize, List<T> keySet) {
        super(key, parentSize, keySet);
    }

    @Override
    protected int calculateIndex(Object root) {
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
