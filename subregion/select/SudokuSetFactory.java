package edu.utep.cs.cs4330.sudoku.subregion.select;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author: Jesus Chavez
 * @macuser: aex on 2/28/18.
 */
/*may need to refresh */
public class SudokuSetFactory<S> {

    private List<S> grid;
    private final int parentSize;
    public SudokuSetFactory(List<S> grid){
        this.grid = grid;
        this.parentSize = grid.size();
    }
    public Map<S, List<S>> getRegions(){
        Map<S, List<S>> regions = Collections.synchronizedMap(new HashMap<>());
        for(S s: grid){
            regions.put(s, new RegionSet(s, parentSize, grid));
        }
        return regions;
    }
    public Map<S, List<S>> getRows(){
        Map<S, List<S>> rows = Collections.synchronizedMap(new HashMap<>());
        for(S s: grid){
            rows.put(s, new RowSet(s, parentSize, grid));
        }
        return rows;
    }
    public Map<S, List<S>> getColumns(){
        Map<S, List<S>> cols = Collections.synchronizedMap(new HashMap<>());
        for(S s: grid){
            cols.put(s, new ColumnSet(s, parentSize, grid));
        }
        return cols;
    }


}
