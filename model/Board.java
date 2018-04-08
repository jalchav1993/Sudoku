package edu.utep.cs.cs4330.sudoku.model;

import android.util.Log;

import java.util.List;

import edu.utep.cs.cs4330.sudoku.model.utility.grid.Grid;
import edu.utep.cs.cs4330.sudoku.model.utility.grid.Square;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 2/28/18.
 */

public class Board<S>{
    /* inner class variables */
    //private final Players p1, p2;

    public List<S> grid;
    private String status;
    public static final String RUNNING_ = "r";
    public static final String N_RUNNING = "nr";

    public Board(int size, List<S> grid) throws Exception{
        this.grid = grid;
        status = Board.N_RUNNING;
    }
    //synchronized when multi threads
    public String getStatus(){
        return status;
    }
    public void start() throws Exception {
        Log.d("start", "start");
        status = Board.RUNNING_;
    }
    private void setStatus(String status){
        this.status = status;
    }
    /**
     * @return  string representation of the board such that
     *          every char element of string is a square;
     */
    public synchronized List<S> getGrid(){
        return grid;
    }
    /**
     * @return  <foreach>
     *              <Square s elementOf="grid">
     *                  s == 0 -> false
     *              </Square>
     *          </foreach>
     *          otherwise -> true
     */
    public boolean check(List<S> l){
        for(S s: l) if(((Square) s).contains(0)){
            return false;
        }
        setStatus(Board.N_RUNNING);
        return true;
    }
    public boolean check(){
        return check(grid);
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
        return ((Grid) grid).pack(x, y, z);
    }

    public void deselectAll() {

    }
}
