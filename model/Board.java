package edu.utep.cs.cs4330.sudoku.model;
/**
 * @author: Jesus Chavez
 * @macuser: aex on 2/28/18.
 */

public class Board{
    /* inner class variables */
    //private final Players p1, p2;

    public Grid<Square> grid;
    public Board(int size){
        grid = new SimpleGrid<>(size);
    }

    /**
     * @return  string representation of the board such that
     *          every char element of string is a square;
     */
    public synchronized String getState(){
        return "";
    }


    /**
     * @return  <foreach>
     *              <Square s elementOf="grid">
     *                  s == 0 -> false
     *              </Square>
     *          </foreach>
     *          otherwise -> true
     */
    public boolean check(){
        for(Square s: grid) if(!s.hasValue(0)){
            return false;
        }
        return true;
    }
    public String solve(){
        //solve here
        return getState();
    }
    /** Add a new number to grid
     * @see     Board isPackable(x,y,z)
     * @param   x coordinate for columns
     * @param   y coordinate for rows
     * @param   z value to be packed in the grid
     * @return  isPackable(x,y,z) == true -> grid.setValue(x,y,z)
     *          otherwise -> false
     */
    public boolean put(int x, int y, int z) {
        //may be
        return grid.set(x, y, z);
    }

}
