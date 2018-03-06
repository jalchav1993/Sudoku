package edu.utep.cs.cs4330.sudoku.model;

import java.util.List;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 2/28/18.
 */

public class HardGrid extends Grid {

    public HardGrid(int size) {
        super(size);
    }

    @Override
    protected List<Square> buildGrid() {
        return null;
    }


}
