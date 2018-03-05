package edu.utep.cs.cs4330.sudoku.model;

import java.util.List;
import java.util.Map;

import edu.utep.cs.cs4330.sudoku.subregion.select.*;


/**
 * Created by aex on 2/27/18.
 */

public abstract class Grid implements Board{
    /* inner class variables */
    //private final Players p1, p2;

    final int size;
    final int status;
    private final int parentCapacity;
    private SudokuSetFactory factory;
    /** List of consecutive squares **/
    private List<Square> keySet;
    private Map<Square, List<Square>> regions;
    private Map<Square, List<Square>> rows;
    private Map<Square, List<Square>> columns;

    public Grid(int size){
        this.size = size;
        parentCapacity = size * size;
        keySet = buildGrid();
        /* factory should buid the keySquare set not ask for it */
        factory = new SudokuSetFactory(keySet, size);
        rows = factory.getRows();
        columns = factory.getColumns();
        regions = factory.getRegions();
        status = 0;

    }
    @Override
    public synchronized String getState(){
        return keySet.toString();
    }
    @Override
    public int getStatus(){
        return status;
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public List<Square> keySet(){
        return keySet;
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
        Square compareTo = get(x, y);
        return columns.get(compareTo).contains(z);
    }

    protected boolean checkRow(int x, int y, int z) {
        Square compareTo = get(x, y);
        return rows.get(compareTo).contains(z);
    }
    protected boolean checkRegion(int x, int y, int z) {
        Square compareTo = get(x, y);
        return regions.get(compareTo).contains(z);
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
    /*different levels of difficulty in keySet */
    protected abstract List<Square> buildGrid();

}
