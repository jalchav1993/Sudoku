package edu.utep.cs.cs4330.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.utep.cs.cs4330.select.SudokuSetFactory;

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
    private int length;
    public Grid(int length){
        this.length = length;
        factory = new SudokuSetFactory(this);
        rows = factory.getRows();
        columns = factory.getColumns();
        regions = factory.getRegions();
        buildGrid();
    }
    public int length() {
        return length;
    }
    @Override
    public int size(){
        return (int) Math.pow(length(), 2);
    }
    /**
     * Driver for set(int index) of superclass
     * @see ArrayList get()
     * @param x
     * @param y
     * @param z
     * @return
     */
    public boolean set(int x, int y, int z){
        S s = get(x, y);
        return ((Square) s).set(z);
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

    /**
     *
     * @param x
     * @param y
     * @param z
     * @param areaSelector
     * @return
     */
    public boolean inset(int x, int y, int z, int areaSelector){
        S square = get(x,y);
        List<S> area = null;
        int index;
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
        Square selected = (Square) area.get(index);
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
     * Concrete class implements many difficulty levels
     */
    protected abstract void buildGrid();
}
