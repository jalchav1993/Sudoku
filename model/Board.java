package edu.utep.cs.cs4330.sudoku.model;

import java.util.List;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 2/28/18.
 */

public class Board<S>{
    /* inner class variables */
    //private final Players p1, p2;

    public List<S> grid;
    public String status;
    public Board(int size, List<S> grid) throws Exception{
        this.grid = grid;
        status = "";
    }
    public synchronized String getStatus(){
        return status;
    }
    public synchronized void start(){
        status = "running";
    }
    public void setStatus(String status){
        this.status = status;
    }
    /**
     * @return  string representation of the board such that
     *          every char element of string is a square;
     */
    public synchronized List<S> getState(){
        return grid;
    }
    public int getGridLength(){
        return ((Grid) grid).length();
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
        for(S s: grid) if(!((Square) s).hasValue(0)){
            return false;
        }
        setStatus("won");
        return true;
    }
    public List<S> solve(){
        int k = 1, x, y, z;
        while(k<=((Grid) grid).size()){
            //get values not within rows
            x = (int) (Math.random() * 8);
            y = (int) (Math.random() * 8);
            z = (int) (Math.random() * 8 + 1);
            if(((Grid) grid).pack(x, y, z)) k++;
        }
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
        return ((Grid) grid).pack(x, y, z);
    }
    public boolean isValidSpace(int x, int y){
        return ((Grid) grid).checkSpace(x,y);
    }
}
