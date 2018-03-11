package edu.utep.cs.cs4330.select;

import java.util.List;

import edu.utep.cs.cs4330.model.Square;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 2/27/18.
 */

public class RowSet<T> extends AbstractSudokuSet<T>{


    public RowSet(T key, List<T> keySet) {
        super(key, keySet);
    }

    @Override
    protected int calculateIndex(Object root) {
        return ((Square) root).x * parentSize;
    }


    @Override
    protected int updChangeInDelta(int delta, int axis) {
        if(axis > 0){
            return 0;
        } else if(axis < 0){
            return delta+1;
        }
        return -1;
    }


}
