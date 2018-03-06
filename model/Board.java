package edu.utep.cs.cs4330.sudoku.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.utep.cs.cs4330.sudoku.subregion.select.*;
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
    public synchronized String getState(){
        return toString();
    }
    public int getStatus(){
        return status;
    }
    public boolean solve(){
        for(Square s: grid) if(!s.equals((Object) 0)){
            return false;
        }
        return true;
    }
    public boolean pack(int x, int y, int z){
        if(isPackable(x,y,z))
            return grid.set(x,y,z);
        return false;
    }
    /** returns true is a token can be legally placed based on region, column and row**/
    private boolean isPackable(int x, int y, int z){
        return grid.inset(x, y, z, Grid.COLUMN)
                &&checkSpace(x, y)
                &&grid.inset(x, y, z, Grid.ROW)
                &&grid.inset(x, y, z, Grid.REGION);
    }
    private boolean compareTo(int x, int y, int z){
        Square s = grid.get(x, y);
        return s.equals(z);
    }

    private boolean checkSpace(int x, int y) {
        return compareTo(x, y, 0); /* 0  means not used */
    }
}
