package edu.utep.cs.cs4330.sudoku.model;

/** An abstraction of Sudoku puzzle. */
/** Wrap for a new game */
/** future use as a container for players */
public class Board extends Grid{
    //private final Players p1, p2;
    private final int size;
    private final int status;
    /** Create a new board of the given size. */
    // public Board (int size, Player p1, Player p2, NetIntent... )
    public Board(int size) {
        super(size);
        this.size = size;
        status = 0;
    }
    public int getStatus(){
        return status;
    }

    public int size() {
        return size;
    }

}

