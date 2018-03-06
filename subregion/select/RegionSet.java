package edu.utep.cs.cs4330.sudoku.subregion.select;
import java.util.List;
import java.util.Map;

import edu.utep.cs.cs4330.sudoku.model.Square;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 2/27/18.
 */

public class RegionSet<T> extends AbstractSudokuSet<T>{


    public RegionSet(T key, int parentSize, List<T> keySet) {
        super(key, parentSize, keySet);
    }

    @Override
    protected int calculateIndex(Object root) {
        root = ((Square) root);
        //could use setters and getters
        return (((Square) root).x - (((Square) root).x % getSigma()) *parentSize)+
                (((Square) root).y- (((Square) root).y % getSigma()) *parentSize);
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
