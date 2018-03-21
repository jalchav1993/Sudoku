package edu.utep.cs.cs4330.sudoku.tests;

import org.junit.Test;

import edu.utep.cs.cs4330.sudoku.model.Grid;
import edu.utep.cs.cs4330.sudoku.model.SimpleGrid;
import edu.utep.cs.cs4330.sudoku.model.Square;
import edu.utep.cs.cs4330.sudoku.select.RowSet;
import static org.junit.Assert.assertTrue;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 3/5/18.
 */

public class RowSetUnitTest {
    private final static int x1 = 2, y1 = 2, size = 9;
    private final static int expectedRoot1 = 18;
    private final static int x2 = 4, y2 = 3;
    private final static int expectedRoot2 = 27;
    private final static int x3 = 2, y3 = 4;
    private final static int expectedRoot3 = 36;
    private static Grid<Square> grid = new SimpleGrid<>(size);
    @Test
    public void getRootIndex_isCorrect(){
        Assert.assertTrue(checkRootIndex(x1,y1, expectedRoot1));
        Assert.assertTrue(checkRootIndex(x2,y2, expectedRoot2));
        Assert.assertTrue(checkRootIndex(x3,y3, expectedRoot3));
    }
    private boolean checkRootIndex(int x, int y, int expected) {
        Square s = grid.get(x, y);
        int resultIndex;
        RowSet<Square>rowSet = new RowSet<>(s, grid);
        resultIndex= rowSet.getRootIndex(s, size);
        return expected == resultIndex;
    }
    @Test
    public void parentSize_setCorrect(){
        Grid<Square> grid = new SimpleGrid<>(size);
        Square s = grid.get(x1, y1);
        RowSet<Square> rowSet;
        try {
            rowSet = new RowSet<>(s, grid);
            Assert.assertTrue(grid.length() == rowSet.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Test
    public void updChangeInDx_isCorrect()throws Exception{
        Assert.assertTrue(checkDxVector(x1, y1));
        Assert.assertTrue(checkDxVector(x2, y2));
        Assert.assertTrue(checkDxVector(x3, y3));
    }
    private boolean checkDxVector(int x, int y) throws Exception{
        Square s = grid.get(x, y);
        RowSet<Square> rowSet = new RowSet<>(s, grid);
        int i = 0, l;
        Square original = rowSet.get(i);
        for(Square t: rowSet){
            l = original.x - t.x;
            if(l != 0 ) return false;
            throw new Exception(rowSet +"");
        }
        return true;
    }
    @Test
    public void updChangeInDy_isCorrect() throws Exception {
        Assert.assertTrue(checkDyVector(x1, y1));
        Assert.assertTrue(checkDyVector(x2, y2));
        Assert.assertTrue(checkDyVector(x3, y3));
    }
    private boolean checkDyVector(int x, int y) throws Exception {
        Square s = grid.get(x, y);
        RowSet<Square> rowSet = new RowSet<>(s, grid);
        int i = 0, k;
        Square original = rowSet.get(i);

        for(Square t: rowSet){
            if(i>0){
                k = t.y- original.y;
                if(Math.abs(k) != i) return false;
                i++;
            }else i++;
        }
        return true;
    }
}
