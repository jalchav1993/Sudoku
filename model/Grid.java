package edu.utep.cs.cs4330.sudoku.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.utep.cs.cs4330.sudoku.subregion.select.SudokuSetFactory;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 3/5/18.
 */

public abstract class Grid<S> extends ArrayList<S>{
    /**
     * @assert(S is a square)
     */
    /* static constants */
    public static final int REGION = 0;
    public static final int ROW = 1;
    public static final int COLUMN = 2;
    /* class variables */
    private Map<S, List<S>> regions;
    private Map<S, List<S>> rows;
    private Map<S, List<S>> columns;
    private SudokuSetFactory factory;
    private int size;
    public Grid(int size){
        factory = new SudokuSetFactory(this);
        rows = factory.getRows();
        columns = factory.getColumns();
        regions = factory.getRegions();
        this.size = size;
    }
    @Override
    public int size() {
        return size;
    }

    /**
     * Driver for set(int index) of superclass
     * @param x
     * @param y
     * @param z
     * @return
     */
    public boolean set(int x, int y, int z){
        S s = get(x, y);
        return ((Square) s).set(z);
    }
    public S get(int x, int y){
        int index = getLinearIndex(x, y);
        return get(index);
    }
    public boolean inset(int x, int y, int z, int areaSelector){
        S square = get(x,y);
        List<S> area = null;
        int index;
        int compareTo;
        if(areaSelector == REGION){
            area = regions.get(square);
        } else if(areaSelector == ROW){
            area = rows.get(square);
        } else if(areaSelector == COLUMN){
            area = columns.get(square);
        } else{
            area = null;
        }
        index = getLinearIndex(x, y);
        compareTo = (int) area.get(index);
        return compareTo == z;

    }

    /**
     * Returns linear position of index
     * @param x
     * @param y
     * @return : (return/x)-y = size
     */
    private int getLinearIndex(int x, int y){
        return x * size + y;
    }
    protected abstract List<Square> buildGrid();


}
