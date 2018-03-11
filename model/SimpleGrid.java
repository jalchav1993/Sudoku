package edu.utep.cs.cs4330.model;

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
    public SimpleGrid(int size) {
        super(size);
    }

    /**
     * where does documentation go, interface?
     * Simple and Easy SimpleGrid
     * @return List l: forevery s in (i,k), l.add(s);
     */
    @Override
    protected void buildGrid() {
        for (int i = 0; i < length(); i++) {
            for(int j = 0; j < length(); j++){
                add((S) new Square(i, j,0, Square.READ_WRITE_DELETE));
            }
        }
    }

}

