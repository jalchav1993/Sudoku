package edu.utep.cs.cs4330.sudoku.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.utep.cs.cs4330.sudoku.subregion.select.*;


/**
 * Created by aex on 2/27/18.
 */

public abstract class Grid{
    /* inner class variables */
    //private final Players p1, p2;

    final int size;
    final int status;
    private final int parentCapacity;
    private Factory factory;
    /** List of consecutive squares **/
    private List<Square> keySet;
    private Map<Square, AbstractSudokuSet<Integer>> regions;
    private Map<Square, AbstractSudokuSet<Integer>> rows;
    private Map<Square, AbstractSudokuSet<Integer>> columns;

    public Grid(int size){
        this.size = size;
        parentCapacity = size * size;
        keySet = buildGrid();
        factory = new Factory(keySet, parentCapacity);
        rows = factory.getRows();
        columns = factory.getColumns();
        regions = factory.getRegions();
        status = 0;

    }
    public synchronized String getState(){
        return keySet.toString();
    }

    protected int getLinearIndex(int x, int y){
            return x * parentCapacity + y;
    }
    protected boolean set(int x, int y, int z){
        int index = getLinearIndex(x, y);
        Square current = keySet.get(index);
        return current.set(z);
    }
    protected Square get(int x, int y){
        int index = getLinearIndex(x, y);
        return keySet.get(index);
    }
    /** returns true is a token can be legally placed based on region, column and row**/
    protected boolean isPackable(int x, int y, int z){
        return checkRegion(x, y, z)
                &&checkSpace(x, y)
                &&checkRow(x, y,z)
                &&checkCol(x, y,z);
    }
    protected boolean solve(List<Square> grid){
        return solve();
    }
    protected boolean inSet(int x, int y, int z, List<?> areaSelector){
        Square s = get(x, y);
        if(areaSelector == AbstractSudokuSet.REGION){
            return regions.get(s).equals(z);
        }else if(areaSelector == AbstractSudokuSet.ROW){
            return rows.get(s).equals(z);
        }else if(areaSelector == AbstractSudokuSet.COL){
            return columns.get(s).equals(z);
        }else{return false;}
    }
    protected boolean solve(){
        for(Square s: keySet) if(!s.equals((Object) 0)){
            return false;
        }
        return true;
    }
    protected boolean compareTo(int x, int y, int z){
        return get(x, y).equals((Object)z);
    }
    protected boolean checkCol(int x, int y, int z) {
        return inSet(x, y, z, AbstractSudokuSet.COL);
    }

    protected boolean checkRow(int x, int y, int z) {
        return inSet(x, y, z, AbstractSudokuSet.ROW);
    }
    protected boolean checkRegion(int x, int y, int z) {
        return inSet(x, y, z, AbstractSudokuSet.REGION);
    }

    protected boolean checkSpace(int x, int y) {
        return compareTo(x, y, 0); /* 0  means not used */
    }
    /**
     * returns true is a token was placed in a box
     * **/
    protected Boolean put(int x, int y, int z){
        if(isPackable(x, y, z)){
            return set(x, y, z);
        }
        return false;
    }
    protected Boolean delete(int x, int y, int z) {
        return null;
    }
    public List<?> keySet(){
        return keySet;
    }

    protected abstract List<Square> buildGrid();

}
