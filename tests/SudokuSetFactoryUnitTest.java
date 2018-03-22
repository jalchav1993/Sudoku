package edu.utep.cs.cs4330.sudoku.tests;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import edu.utep.cs.cs4330.sudoku.model.Grid;
import edu.utep.cs.cs4330.sudoku.model.SimpleGrid;
import edu.utep.cs.cs4330.sudoku.model.Square;
import edu.utep.cs.cs4330.sudoku.select.SudokuSetFactory;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SudokuSetFactoryUnitTest {
    public int length = 9;
    public int x0 = 3, y0 = 4, x1 = 4, y1 = 6, x2 = 6, y2 = 8, x3 = 0, y3 = 1;
    public Grid<Square> grid;{
        try{
            grid = new SimpleGrid<>(length);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public SudokuSetFactory<Square> factory = new SudokuSetFactory<>(grid);
    public Map<Square, List<Square>> rows;
    public Map<Square, List<Square>> cols;
    public Map<Square, List<Square>> regions;


    @Test
    public void size_isCorrect(){
        try{rows = factory.getRows();
            factory.getColumns();
            factory.getRegions();
        }
        catch(Exception e){

        }
        assertEquals(grid.size(), rows.keySet().size());
        assertEquals(grid.size(), rows.keySet().size());
        assertEquals(grid.size(), rows.keySet().size());

    }
    @Test
    public void keySet_isCorrect(){
        try{rows = factory.getRows();
            factory.getColumns();
            factory.getRegions();}
        catch(Exception e){
            e.printStackTrace();
        }
        for(Square s : rows.keySet()){
            Square compareTo = grid.get(s.x, s.y);
            assertTrue(s.equals(compareTo));
            assertTrue(compareTo.equals(s));
        }
    }
    @Test
    public void listFormat_isCorrect() throws Exception {
        rows = factory.getRows();
        cols = factory.getColumns();
        regions = factory.getRegions();
        for(Square s : grid){
            List<Square> temp = rows.get(s);
            for(int i = 0; i < grid.length(); i ++){
                assertTrue(""+i+" "+temp.get(i).get(), !(i >temp.size()));
            }
        }
    }

}