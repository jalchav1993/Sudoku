package edu.utep.cs.cs4330.sudoku.subregion.select;

import java.util.List;

import edu.utep.cs.cs4330.sudoku.model.Square;

/**
 * Created by aex on 2/27/18.
 */

public class ColumnSet extends AbstractSudokuSet {

    public ColumnSet(Square key, int parentSize, List<Square> keySet) {
        super(key, parentSize, keySet);
    }

    @Override
    protected int calculateIndex(Square root) {
        return root.y;
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
