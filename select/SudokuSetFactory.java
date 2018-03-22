package edu.utep.cs.cs4330.sudoku.select;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import edu.utep.cs.cs4330.sudoku.model.Square;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 2/28/18.
 */
/*may need to refresh */
public class SudokuSetFactory<S> {

    private List<S> grid;

    /**
     * Instantiates the class
     * @param grid object from which rows, cols and regions are extracted
     */
    public SudokuSetFactory(List<S> grid){
        this.grid = grid;
    }

    /**
     * @return list of n consecutive items in a column
     * @throws Exception e logging
     */
    public Map<S, List<S>> getRegions() throws Exception {
        Map<S, List<S>> regions = Collections.synchronizedMap(new HashMap<>());
        for(S s: grid){
            regions.put(s, new RegionSet<>(s, grid));
        }
        return regions;
    }

    /**
     * @return list of n consecutive items in a row
     * @throws Exception e logging
     */
    public Map<S, List<S>> getRows() throws Exception {
        Map<S, List<S>>  rows = Collections.synchronizedMap(new HashMap<>());
        for(S s: grid){
            rows.put(s, new RowSet<>(s, grid));
        }
        return rows;
    }

    /**
     * @return list of n consecutive items in a region
     * @throws Exception e logging
     */
    public Map<S, List<S>>  getColumns() throws Exception {
        Map<S, List<S>>  cols = Collections.synchronizedMap(new HashMap<>());
        for(S s: grid){
            cols.put(s, new ColumnSet<>(s, grid));
        }
        return cols;
    }


}
