package edu.utep.cs.cs4330.sudoku.subregion.factory;
import java.util.ArrayList;

import edu.utep.cs.cs4330.sudoku.model.Square;

/**
 * Created by aex on 2/27/18.
 */

public class RegionSet<T> extends AbstractSudokuConsecutiveSet<T> {
    public RegionSet(Square root, int capacity) {
        super(root, capacity, (T) AbstractSudokuConsecutiveSet.REGION, new ArrayList<Integer>());
    }

}
