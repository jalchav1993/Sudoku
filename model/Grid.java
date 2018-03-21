package edu.utep.cs.cs4330.sudoku.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import edu.utep.cs.cs4330.sudoku.select.*;
/**
 * @author: Jesus Chavez
 * @macuser: aex on 3/5/18.
 */

public abstract class Grid<S> extends ArrayList<S>{

    /* static constants */
    static final int REGION = 0;
    static final int ROW = 1;
    static final int COLUMN = 2;
    /* class variables */
    private int length;
    protected int filled;
    private Map<S, List<S>> regions;
    private Map<S, List<S>> rows;
    private Map<S, List<S>> columns;
    public Grid(int length){
        this.length = length;
        int capacity = (int) Math.pow(length, 2);
        super.ensureCapacity(capacity);
        buildGrid();
        SudokuSetFactory<S> factory = new SudokuSetFactory<>(this);
        try {
            rows = factory.getRows();
            columns = factory.getColumns();
            regions = factory.getRegions();
            throw new Exception("" + rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Grid(int length, int filled){
        this.length = length;
        int capacity = (int) Math.pow(length, 2);
        super.ensureCapacity(capacity);
        this.filled = filled;
        buildGrid();
        SudokuSetFactory<S> factory = new SudokuSetFactory<>(this);
        try {
            rows = factory.getRows();
            columns = factory.getColumns();
            regions = factory.getRegions();
            throw new Exception("" + rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int length() {
        return length;
    }

    /**
     * Driver for setValue(int index) of superclass
     * @see ArrayList get()
     * @param x
     * @param y
     * @param z
     * @return
     */
    public boolean set(int x, int y, int z){
        S s = get(x, y);
        return ((Square) s).setValue(z);
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public S get(int x, int y){
        int index = getLinearIndex(x, y);
        return get(index);
    }
    public boolean pack(int x, int y, int z){
        if(isPackable(x, y, z)) return ((Square) get(getLinearIndex(x,y))).setValue(z);
        else return false;
    }
    /**
     *
     * @param x
     * @param y
     * @param z
     * @param sudokuSetSelector
     * @return
     */
    protected boolean inset(int x, int y, int z, int sudokuSetSelector){
        S square = get(x,y);
        List<S> area = null;
        int index;
        if(sudokuSetSelector == REGION){
            area = regions.get(square);
        } else if(sudokuSetSelector == ROW){
            area = rows.get(square);
        } else if(sudokuSetSelector == COLUMN){
            area = columns.get(square);
        }
        try {
            throw new Exception(square + "\n"+rows.get(square)+"\n"+columns.get(square)+"\n"+regions.get(square));
        }catch (Exception e){
            e.printStackTrace();
        }
        index = getLinearIndex(x, y);
        Square selected = (Square) ((AbstractSudokuSet<Square>)area).get(x,y);
        return selected.get() == z;

    }

    /**
     * Returns linear position of index
     * @param x
     * @param y
     * @return : (return/x)-y = size
     */
    private int getLinearIndex(int x, int y){
        return x * length + y;
    }
    /**
     *    Square (x, y, z) isElement grid -> false
     *                          otherwise -> true
     */
    protected boolean isPackable(int x, int y, int z){
        return inset(x, y, z, Grid.COLUMN)
                &&checkSpace(x, y)
                &&inset(x, y, z, Grid.ROW)
                &&inset(x, y, z, Grid.REGION);
    }
    /**
     * Compare Square (x,y,z) id to se if it matches a Square (x,y,z) element of gird
     */
    protected boolean compareTo(int x, int y, int z){
        S s = get(x, y);
        //only had to fix this once and it fixed the whole class method is coherent, code is cohesive
        return ((Square) s).hasValue(z);
    }
    /**
     * Checks if a given space is occupied, setValue to 0
     */
    private boolean checkSpace(int x, int y) {
        return compareTo(x, y, 0); /* 0  means not used */
    }
    /**
     * Concrete class implements many difficulty levels
     */
    protected abstract void buildGrid();
}
