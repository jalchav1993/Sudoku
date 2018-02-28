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
public class Factory {

    private List<Square> keySet;
    private final int parentCapacity;
    public Factory(List<Square> keySet, int parentCapacity){
        this.keySet = keySet;
        this.parentCapacity = parentCapacity;
    }
    public Map<Square, AbstractSudokuSet<Integer>> getRegions(){
        Map<Square, AbstractSudokuSet<Integer>> regions = Collections.synchronizedMap(new HashMap<>());
        for(Square s: keySet){
            regions.put(s, new RegionSet<Integer>(s,parentCapacity));
        }
        return regions;
    }
    public Map<Square, AbstractSudokuSet<Integer>> getRows(){
        Map<Square, AbstractSudokuSet<Integer>> rows = Collections.synchronizedMap(new HashMap<>());
        for(Square s: keySet){
            rows.put(s, new RowSet<Integer>(s,parentCapacity, keySet));
        }
        return rows;
    }
    public Map<Square, AbstractSudokuSet<Integer>> getColumns(){
        Map<Square, AbstractSudokuSet<Integer>> columns = Collections.synchronizedMap(new HashMap<>());
        for(Square s: keySet){
            columns.put(s, new ColumnSet<>(s,parentCapacity, keySet));
        }
        return columns;
    }
//    private Map<Square, AbstractSudokuSet<Integer>> getLogicSet(List<?> areaSelector){
//        /* dynamic, relies on polymorphism */
//        Map<Square, AbstractSudokuSet<Integer>> temp = Collections.synchronizedMap(new HashMap<>());
//        for(Square s: keySet){
//            if(areaSelector == AbstractSudokuSet.REGION){
//                temp.put(s, new RegionSet<>(s, size));
//            }else if(areaSelector == AbstractSudokuSet.ROW){
//                temp.put(s, new RowSet<>(s, size, keySet));
//            }else if(areaSelector == AbstractSudokuSet.COL){
//                temp.put(s, new ColumnSet<>(s, size, keySet));
//            }else{}
//        }
//        return temp;
//    }
//


}
