package edu.utep.cs.cs4330.sudoku.model;

/** An abstraction of Sudoku puzzle. */
/** Wrap for a new game */

import java.util.ArrayList;
import java.util.List;
/**
 * @author: Jesus Chavez
 * @macuser: aex on 2/28/18.
 */
/** future use as a container for players */
public class SimpleGrid extends Grid {
    /** Create a new board of the given size. */
    // public SimpleGrid (int size, Player p1, Player p2, NetIntent... )
    public SimpleGrid(int size) {
        super(size);
    }

    /**
     * where does documentation go, interface?
     * Simple and Easy SimpleGrid
     * @return List l: forevery s in (i,k), l.add(s);
     */
    @Override
    protected List<Square> buildGrid() {
        List<Square> temp = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            for(int j = 0; j < size(); j++){
                temp.add(new Square(i, j, size(), Square.READ_WRITE_DELETE));
            }
        }
        return temp;
    }

}

