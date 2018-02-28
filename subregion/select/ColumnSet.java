package edu.utep.cs.cs4330.sudoku.subregion.select;

import java.util.List;

import edu.utep.cs.cs4330.sudoku.model.Square;

/**
 * Created by aex on 2/27/18.
 */

public class ColumnSet<T> extends AbstractSudokuSet<T> {
    public ColumnSet(Square key, int capacity, List<?> grid) {
        super(key, capacity, (T) AbstractSudokuSet.COL, grid);
    }
}
