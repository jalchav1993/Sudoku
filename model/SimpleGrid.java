package edu.utep.cs.cs4330.sudoku.model;
/** An abstraction of Sudoku puzzle. */
/** Wrap for a new game */

/**
 * @author: Jesus Chavez
 * @macuser: aex on 2/28/18.
 */
/** future use as a container for players */
public class SimpleGrid<S> extends Grid<S> {
    /** Create a new board of the given size. */
    // public SimpleGrid (int size, Player p1, Player p2, NetIntent... )
    public SimpleGrid(int length) throws Exception {
        super(length);
    }

    @Override
    protected void fill(int filled) throws Exception {

    }


}

