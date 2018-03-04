package edu.utep.cs.cs4330.sudoku.model;

/** An abstraction of Sudoku puzzle. */
/** Wrap for a new game */

import java.util.ArrayList;
import java.util.List;

/** future use as a container for players */
public class SimpleBoard extends Grid implements Board{
    /** Create a new board of the given size. */
    // public SimpleBoard (int size, Player p1, Player p2, NetIntent... )
    public SimpleBoard(int size) {
        super(size);
    }

    /**
     * Simple and Easy SimpleBoard
     * @return
     */
    @Override
    protected List<Square> buildGrid() {
        ArrayList<Square> temp = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++){
                temp.add(new Square(i, j, size, Square.READ_WRITE_DELETE));
            }
        }
        return temp;
    }

}

