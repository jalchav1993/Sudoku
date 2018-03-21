package edu.utep.cs.cs4330.sudoku.tests;

import org.junit.Test;

import edu.utep.cs.cs4330.sudoku.model.Grid;
import edu.utep.cs.cs4330.sudoku.model.SimpleGrid;
import edu.utep.cs.cs4330.sudoku.model.Square;
import edu.utep.cs.cs4330.sudoku.select.ColumnSet;

import static junit.framework.Assert.assertEquals;
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
    private static Grid<Square> grid = new SimpleGrid<>(size);
    @Test
    public void getRootIndex_isCorrect() throws Exception {
        Assert.assertTrue(trueIndex(x1, y1, expectedRoot1));
        Assert.assertTrue(trueIndex(x2, y2, expectedRoot2));
        Assert.assertTrue(trueIndex(x3, y3, expectedRoot3));
    }
    private boolean trueIndex(int x, int y, int expected) throws Exception {
        Square s = grid.get(x, y);
        ColumnSet<Square> columnSet = new ColumnSet<>(s, grid);
        int resultIndex = columnSet.getRootIndex(s, size);
        return resultIndex == expected;
    }
    @Test
    public void parentSize_setCorrect() throws Exception {
        checkParentSize(x1, y1);
        checkParentSize(x2, y2);
        checkParentSize(x3, y3);
    }
    private void checkParentSize(int x, int y) throws Exception {
        Square s = grid.get(x, y);
        ColumnSet<Square> columnSet = new ColumnSet<>(s, grid);
        Assert.assertTrue(grid.length() == columnSet.size());
    }
    @Test
    public void updChangeInDx_isCorrect() throws Exception {
        checkDxVector(x1, y1);
        checkDxVector(x2, y2);
        checkDxVector(x2, y2);
    }
    private void checkDxVector(int x, int y) throws Exception {
        Square s = grid.get(x, y);
        ColumnSet<Square> columnSet = new ColumnSet<>(s, grid);
        int i = 0, l;
        Square original = columnSet.get(i);
        for(Square t: columnSet){
            l = original.y - t.y;
            assertEquals(0, l);
        }
    }
    @Test
    public void updChangeInDy_isCorrect() throws Exception {
        Assert.assertTrue(checkDyVector(x1, y1));
        Assert.assertTrue(checkDyVector(x2, y2));
        Assert.assertTrue(checkDyVector(x2, y2));
    }
    private boolean checkDyVector(int x, int y) throws Exception {
        Square s = grid.get(x, y);
        ColumnSet<Square> columnSet = new ColumnSet<>(s, grid);
        int i = 0, k;
        Square original = columnSet.get(i);
        for(Square t: columnSet){
            if(i>0){
                k = t.x- original.x;
                if(Math.abs(k) != i) return false;
                i++;
            }else i++;
        }
        return true;
    }
}
