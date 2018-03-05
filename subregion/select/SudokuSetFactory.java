package edu.utep.cs.cs4330.sudoku.subregion.select;

import edu.utep.cs.cs4330.sudoku.model.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by aex on 2/28/18.
 */
/*may need to refresh */
public class SudokuSetFactory {

    private List<Square> keySet;
    private final int parentSize;
    public SudokuSetFactory(List<Square> keySet, int parentSize){
        this.keySet = keySet;
        this.parentSize = parentSize;
    }
    public Map<Square, List<Square>> getRegions(){
        Map<Square, List<Square>> regions = Collections.synchronizedMap(new HashMap<>());
        for(Square s: keySet){
            regions.put(s, new RegionSet(s, parentSize, keySet));
        }
        return regions;
    }
    public Map<Square, List<Square>> getRows(){
        Map<Square, List<Square>> rows = Collections.synchronizedMap(new HashMap<>());
        for(Square s: keySet){
            rows.put(s, new RowSet(s, parentSize, keySet));
        }
        return rows;
    }
    public Map<Square, List<Square>> getColumns(){
        Map<Square, List<Square>> cols = Collections.synchronizedMap(new HashMap<>());
        for(Square s: keySet){
            cols.put(s, new ColumnSet(s, parentSize, keySet));
        }
        return cols;
    }


}
