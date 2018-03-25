package edu.utep.cs.cs4330.sudoku.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import edu.utep.cs.cs4330.sudoku.select.AbstractSudokuSet;
import edu.utep.cs.cs4330.sudoku.select.SudokuSetFactory;
/**
 * @author: Jesus Chavez
 * @macuser: aex on 3/5/18.
 */

public abstract class Grid<S> extends ArrayList<S>{

    /* static constants */
    static final int REGION = 0;
    static final int ROW = 1;
    static final int COLUMN = 2;
    private SudokuSetFactory<S> factory;
    /* class variables */
    private int length;
    private Map<S, List<S>>  regions;
    private Map<S, List<S>>  rows;
    private Map<S, List<S>> columns;
    public Grid(int length) throws Exception {
        this.length = length;
        int capacity = (int) Math.pow(length, 2);
        super.ensureCapacity(capacity);
        buildGrid();
        buildMaps();
        fill(0);
    }
    public Grid(int length, int filled) throws Exception {
        this.length = length;
        int capacity = (int) Math.pow(length, 2);
        super.ensureCapacity(capacity);
        buildGrid();
        buildMaps();
        fill(filled);
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
    public boolean pack(int x, int y, int z) {
        //this does not work
        return isPackable(x, y, z) &&
                ((Square) get(getLinearIndex(x, y))).setValue(z);
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
        AbstractSudokuSet<S> area = null;
        int index;
        if(sudokuSetSelector == REGION){
            area = (AbstractSudokuSet<S>) regions.get(square);
        } else if(sudokuSetSelector == ROW){
            area = (AbstractSudokuSet<S>) rows.get(square);
        } else if(sudokuSetSelector == COLUMN){
            area = (AbstractSudokuSet<S>) columns.get(square);
        }
        assert area != null;
        return area.inset(z);
    }

    /**
     * Returns linear position of index
     * @param x
     * @param y
     * @return : (return/x)-y = size
     */
    public int getLinearIndex(int x, int y){
        return x + (y* length) ;
    }
    /**
     *  0 means empty
     *    Square (x, y, z) isElement grid -> false
     *                          otherwise -> true
     */
    protected boolean isPackable(int x, int y, int z){
        return !inset(x, y, z, Grid.COLUMN)
                &&checkSpace(x, y)
                &&!inset(x, y, z, Grid.ROW)
                &&!inset(x, y, z, Grid.REGION);
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
    public boolean checkSpace(int x, int y) {
        return compareTo(x, y, 0); /* 0  means not used */
    }
    /**
     * Concrete class implements many difficulty levels
     */
    private void buildGrid()throws Exception{
        for (int i = 0; i < length(); i++) {
            for(int j = 0; j < length(); j++){
                add((S) new Square(j, i,0, Square.READ_WRITE_DELETE));
            }
        }
    }
    private void buildMaps() throws Exception{
        factory = new SudokuSetFactory<>(this);
        rows = factory.getRows();
        columns = factory.getColumns();
        regions = factory.getRegions();
    }
    protected abstract void fill(int filled) throws Exception;
}
