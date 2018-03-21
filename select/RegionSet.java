package edu.utep.cs.cs4330.sudoku.select;
import java.util.List;

import edu.utep.cs.cs4330.sudoku.model.Square;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 2/27/18.
 */

public class RegionSet<S> extends AbstractSudokuSet<S>{


    public RegionSet(S key, List<S> keySet) throws Exception {
        super(key, keySet);
    }

    @Override
    public int getRootIndex(S root, int length) {
        Square s = (Square) root;
        int sigma = getSigma(length);
        return(s.x - s.x % sigma)+(  (s.y- s.y % sigma)* length);
    }

    @Override
    public int updChangeInDx(int delta, int length) {
        int sigma = getSigma(length);
        if(delta % sigma== 0){
            return delta + 1;
        } else if(delta % sigma != 0){
            return 0;
        } else return delta;
    }

    @Override
    public int updChangeInDy(int delta, int length) {
        int sigma = getSigma(length);
        if(delta % sigma == 0){
            return 0;
        } else if(delta % sigma != 0){
            return delta + 1;
        } else return delta;
    }
    private int getSigma(int length){
        return (int) Math.sqrt((double) length);
    }
}
