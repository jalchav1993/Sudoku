package edu.utep.cs.cs4330.sudoku.select;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.utep.cs.cs4330.sudoku.model.Grid;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 2/28/18.
 */
/*may need to refresh */
public class SudokuSetFactory<S> {

    private List<S> grid;
    public SudokuSetFactory(List<S> grid){
        this.grid = grid;
    }
    public Map<S, List<S>> getRegions(){
        Map<S, List<S>> regions = Collections.synchronizedMap(new HashMap<>());
        for(S s: grid){
            //returning false
            regions.put(s, new RegionSet(s, grid));
        }
        return regions;
    }
    public Map<S, List<S>> getRows(){
        Map<S, List<S>> rows = Collections.synchronizedMap(new HashMap<>());
        for(S s: grid){
            rows.put(s, new RowSet(s, grid));
        }
        return rows;
    }
    public Map<S, List<S>> getColumns(){
        Map<S, List<S>> cols = Collections.synchronizedMap(new HashMap<>());
        for(S s: grid){
            cols.put(s, new ColumnSet(s, grid));
        }
        return cols;
    }


}
