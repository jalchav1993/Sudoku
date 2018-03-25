package edu.utep.cs.cs4330.sudoku.model;
/**
 * @author: Jesus Chavez
 * @macuser: aex on 2/28/18.
 */

public class HardGrid<S> extends Grid<S> {
    public HardGrid(int size, int filled) throws Exception {
        super(size, filled);
    }

    @Override
    protected void fill(int filled) throws Exception{
        int k = 1, x, y, z;
        while(k<=filled){
            x = (int) (Math.random() * 8);
            y = (int) (Math.random() * 8);
            z = (int) (Math.random() * 8);
            if(super.pack(x, y, z)) k++;
        }
    }


}
