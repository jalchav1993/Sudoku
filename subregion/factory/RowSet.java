package edu.utep.cs.cs4330.sudoku.subregion.factory;

import java.util.List;

import edu.utep.cs.cs4330.sudoku.model.Square;

/**
 * Created by aex on 2/27/18.
 */

public class RowSet<T> extends AbstractSudokuConsecutiveSet<T> {
    public RowSet(Square key, int capacity, List<?> grid) {
        super(key, capacity, (T) AbstractSudokuConsecutiveSet.ROW, grid);
    }
}
