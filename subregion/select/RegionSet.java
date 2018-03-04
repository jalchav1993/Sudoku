package edu.utep.cs.cs4330.sudoku.subregion.select;
import java.util.List;
import java.util.Map;

import edu.utep.cs.cs4330.sudoku.model.Square;

/**
 * Created by aex on 2/27/18.
 */

public class RegionSet extends AbstractSudokuSet{


    public RegionSet(Square key, int parentSize, List<Square> keySet) {
        super(key, parentSize, keySet);
    }

    @Override
    protected int calculateIndex(Square root) {
        //could use setters and getters
        return (root.x - (root.x % getSigma()) *parentSize)+(root.y- (root.y % getSigma()) *parentSize);
    }

    @Override
    protected int updChangeInDelta(int delta, int axis) {
        if (axis < 0 && delta % getSigma() == 0){
            return 0;
        } else if ((axis > 0 && delta % getSigma() == 0)
            ||(axis < 0 && delta % getSigma() != 0)){ //for change in delta x
            return delta+1;
        } else{
            return delta;
        }
    }

}
