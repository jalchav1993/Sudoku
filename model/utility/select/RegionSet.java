package edu.utep.cs.cs4330.sudoku.model.utility.select;
import java.util.List;

import edu.utep.cs.cs4330.sudoku.model.utility.grid.Square;

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
        return(s.x - s.x % sigma)+(  (s.y - (s.y % sigma))* length);
    }

    @Override
    public int updChangeInDy(int delta, int i, int length) {
        int sigma = getSigma(length);
        return i % sigma;

    }

    @Override
    public int updChangeInDx(int delta, int i, int length) {
        int sigma = getSigma(length);
        return i / sigma;

    }

    @Override
    public int getInitDelta() {
        return 0;
    }

    private int getSigma(int length){
        return (int) Math.sqrt((double) length);
    }
}
