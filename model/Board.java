package edu.utep.cs.cs4330.sudoku.model;
/**
 * @author: Jesus Chavez
 * @macuser: aex on 2/28/18.
 */

public class Board{
    /* inner class variables */
    //private final Players p1, p2;

    protected final int status;

    public Grid<Square> grid;
    public Board(int size){
        grid = new SimpleGrid(size);
        status = 0;

    }

    /**
     *
     * @return
     */
    public synchronized String getState(){
        return toString();
    }

    /**
     *
     * @return
     */
    public int getStatus(){
        return status;
    }

    /**
     *
     * @return
     */
    public boolean solve(){
        for(Square s: grid) if(!s.hasValue(0)){
            return false;
        }
        return true;
    }

    /** Add a new number to grid
     * @see Board isPackable(x,y,z)
     * @param x
     * @param y
     * @param z
     * @return <code>grid.set(x,y,z)</code> isPackable(x,y,z) == true
     *         <code>false</code> otherwise
     */
    public boolean pack(int x, int y, int z){
        if(isPackable(x,y,z))
            return grid.set(x,y,z);
        return false;
    }

    /**
     * @param x
     * @param y
     * @param z
     * @return
     */
    private boolean isPackable(int x, int y, int z){
        return grid.inset(x, y, z, Grid.COLUMN)
                &&checkSpace(x, y)
                &&grid.inset(x, y, z, Grid.ROW)
                &&grid.inset(x, y, z, Grid.REGION);
    }

    /** Compare (x,y,z) id to se if it matches a square (x,y,z) element of gird
     * @see Grid hasValue()
     * @param x direction
     * @param y direction
     * @param z value
     * @return <code>true</code> z belongs to s;
     *         <code>false</code> otherwise
     */
    private boolean compareTo(int x, int y, int z){
        Square s = grid.get(x, y);
        //only had to fix this once and it fixed the whole class, code is coherent
        return s.hasValue(z);
    }

    /** Checks if a given space is occupied, set to 0
     * @see Board compareTo(int x, int y, int z)
     * @param x direction
     * @param y direction
     * @return <code>true</code> compareTo(x,y,0) == true;
     *         <code>false</code> otherwise)
     */
    private boolean checkSpace(int x, int y) {
        return compareTo(x, y, 0); /* 0  means not used */
    }
}
