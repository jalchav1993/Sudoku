package edu.utep.cs.cs4330.sudoku.model;

import java.util.List;

/**
 * Created by aex on 2/28/18.
 */

public class HardBoard extends Grid implements Board{
    public HardBoard(int size) {
        super(size);
    }

    @Override
    protected List<Square> buildGrid() {
        return null;
    }
}
