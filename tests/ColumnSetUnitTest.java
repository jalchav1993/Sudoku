package edu.utep.cs.cs4330.sudoku.tests;

import org.junit.Test;

import java.util.List;

import edu.utep.cs.cs4330.sudoku.model.Grid;
import edu.utep.cs.cs4330.sudoku.model.SimpleGrid;
import edu.utep.cs.cs4330.sudoku.model.Square;
import edu.utep.cs.cs4330.sudoku.select.ColumnSet;

import static org.junit.Assert.assertTrue;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 3/5/18.
 */

public class ColumnSetUnitTest {
    private final static int x1 = 2, y1 = 2, size = 9;
    private final static int expectedRoot1 = 2;
    private final static int x2 = 4, y2 = 3;
    private final static int expectedRoot2 = 3;
    private final static int x3 = 2, y3 = 4;
    private final static int expectedRoot3 = 4;
    private static Grid<Square> grid;{
        try{
            grid = new SimpleGrid<>(size);
        }catch(Exception e){
            e.printStackTrace();
        };
    }
    @Test
    public void getRootIndex_isCorrect() throws Exception {
        assertTrue(checkRootIndex(x1,y1, expectedRoot1));
        assertTrue(checkRootIndex(x2,y2, expectedRoot2));
        assertTrue(checkRootIndex(x3,y3, expectedRoot3));
    }
    private boolean checkRootIndex(int x, int y, int expected) throws Exception {
        Square s = grid.get(x, y);
        int resultIndex;
        ColumnSet<Square> columnSet = new ColumnSet<>(s, grid);
        resultIndex= columnSet.getRootIndex(s, size);
        return expected == resultIndex;
    }
    @Test
    public void parentSize_setCorrect() throws Exception {
        Grid<Square> grid = new SimpleGrid<>(size);
        Square s = grid.get(x1, y1);
        ColumnSet<Square> columnSet;
        try {
            columnSet = new ColumnSet<>(s, grid);
            assertTrue(grid.length() == columnSet.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Test
    public void updChangeInDx_isCorrect()throws Exception{
        assertTrue(checkDxVector(x1, y1));
        assertTrue(checkDxVector(x2, y2));
        assertTrue(checkDxVector(x3, y3));
    }
    private boolean checkDxVector(int x, int y) throws Exception{
        Square s = grid.get(x, y);
        List<Square> columnSet = new ColumnSet<>(s, grid);
        int i = 0, lastY  = 0, index;
        Square current;
        for(Square t: columnSet){
            i+= t.y;
            lastY = t.y;
        }
        return i == lastY*size;
    }
    @Test
    public void updChangeInDy_isCorrect() throws Exception {
        assertTrue(checkDyVector(x1, y1));
        assertTrue(checkDyVector(x2, y2));
        assertTrue(checkDyVector(x3, y3));
    }
    private boolean checkDyVector(int x, int y) throws Exception {
        //1+2+3..+8 = 36
        Square s = grid.get(x, y);
        List<Square> columnSet = new ColumnSet<>(s, grid);
        int i = 0;
        for(Square t: columnSet){
            i+= t.x;
        }
        return i == 36;
    }
}
