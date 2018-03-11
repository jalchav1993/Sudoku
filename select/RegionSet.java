package edu.utep.cs.cs4330.select;
import java.util.List;

import edu.utep.cs.cs4330.model.Square;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 2/27/18.
 */

public class RegionSet<T> extends AbstractSudokuSet<T>{


    public RegionSet(T key, List<T> keySet) {
        super(key, keySet);
    }

    @Override
    public int calculateIndex(T root) {
        //could use setters and getters
        Square s = (Square) root;
        return (s.x - s.x % getSigma())+ (s.y- (s.y % getSigma()))*parentSize;
    }
    @Override
    public int updChangeInDelta(int delta, int axis) {
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
