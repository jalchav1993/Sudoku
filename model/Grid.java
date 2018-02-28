package edu.utep.cs.cs4330.sudoku.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.utep.cs.cs4330.sudoku.subregion.factory.*;


/**
 * Created by aex on 2/27/18.
 */

public abstract class Grid {
    /* inner class variables */
    private final int size;
    private final int parentCapacity;

    /** List of consecutive squares **/
    private List<Square> grid;
    private Map<Square, AbstractSudokuConsecutiveSet<Integer>> regions;
    private Map<Square, AbstractSudokuConsecutiveSet<Integer>> rows;
    private Map<Square, AbstractSudokuConsecutiveSet<Integer>> columns;

    public Grid(int size){
        this.size = size;
        parentCapacity = size * size;
        grid = new ArrayList<Square>(parentCapacity);
        rows = Collections.synchronizedMap(new HashMap<>());
        columns = Collections.synchronizedMap(new HashMap<>());
        regions = Collections.synchronizedMap(new HashMap<>());
    }
    public synchronized String getState(){
        return grid.toString();
    }

    protected int getLinearIndex(int x, int y){
            return x * parentCapacity + y;
    }
    protected boolean set(int x, int y, int z){
        int index = getLinearIndex(x, y);
        Square current = grid.get(index);
        return current.set(z);
    }
    protected Square get(int x, int y){
        int index = getLinearIndex(x, y);
        return grid.get(index);
    }
    /** returns true is a token can be legally placed based on region, column and row**/
    protected boolean isPackable(int x, int y, int n){
        return checkRegion(x, y, n)
                &&checkSpace(x, y)
                &&checkRow(x, y,n)
                &&checkCol(x, y,n);
    }
    protected boolean solve(List<Square> grid){
        return solve();
    }
    protected boolean inSet(int x, int y, int z, List<?> areaSelector){
        Square s = get(x, y);
        if(areaSelector == AbstractSudokuConsecutiveSet.REGION){
            return regions.get(s).equals(z);
        }else if(areaSelector == AbstractSudokuConsecutiveSet.ROW){
            return rows.get(s).equals(z);
        }else if(areaSelector == AbstractSudokuConsecutiveSet.COL){
            return columns.get(s).equals(z);
        }else{return false;}
    }
    protected boolean solve(){
        for(Square s: grid) if(!s.equals((Object) 0)){
            return false;
        }
        return true;
    }
    protected boolean compareTo(int x, int y, int z){
        return get(x, y).equals((Object)z);
    }
    protected boolean checkCol(int x, int y, int z) {
        return inSet(x, y, z, AbstractSudokuConsecutiveSet.COL);
    }

    protected boolean checkRow(int x, int y, int z) {
        return inSet(x, y, z, AbstractSudokuConsecutiveSet.ROW);
    }
    protected boolean checkRegion(int x, int y, int z) {
        return inSet(x, y, z, AbstractSudokuConsecutiveSet.REGION);
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
    public abstract int getStatus();
    public abstract int size();

    /**
     * init an empty grid
     * @return empty grid structure of type <List><Square> </Square></List>
     */
    private List<Square> buildGrid(){
        ArrayList<Square>  temp = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++){
                temp.add(new Square(i, j, size, Square.READ_WRITE_DELETE));
            }
        }
        return temp;
    }

    private Map<Square, AbstractSudokuConsecutiveSet<Integer>> getLogicSet(List<?> areaSelector){
        /* dynamic, relies on polymorphism */
        Map<Square, AbstractSudokuConsecutiveSet<Integer>> temp = Collections.synchronizedMap(new HashMap<>());
        for(Square s: grid){
            if(areaSelector == AbstractSudokuConsecutiveSet.REGION){
                temp.put(s, new RegionSet<>(s, size));
            }else if(areaSelector == AbstractSudokuConsecutiveSet.ROW){
                temp.put(s, new RowSet<>(s, size, grid));
            }else if(areaSelector == AbstractSudokuConsecutiveSet.COL){
                temp.put(s, new ColumnSet<>(s, size, grid));
            }else{}
        }
        return temp;
    }



}
