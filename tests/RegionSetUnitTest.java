package edu.utep.cs.cs4330.sudoku.tests;

import org.junit.Test;

import edu.utep.cs.cs4330.sudoku.model.*;
import edu.utep.cs.cs4330.sudoku.select.RegionSet;

import static org.junit.Assert.assertTrue;
/**
 * x =  {0_0,0_1,0_2,...,0_n,1_0,1_1,1_2,...,1_n,2_0,2_1,...,2_n,...,n_0,n_1,...,n_n};
 * y = {0_0,1_0,2_0,...,n_0,0_1,1_1,2_1,...,0_n,1_n,2_n,...,n_n};
 * @author: Jesus Chavez
 * @macuser: aex on 3/5/18.
 */

public class RegionSetUnitTest {
    private final static int x1 = 2, y1 = 2, size = 9;
    private final static int expectedIndex1 = 0;
    private final static int x2 = 4, y2 = 3;
    private final static int expectedIndex2 = 30;
    private final static int x3 = 2, y3 = 4;
    private final static int expectedIndex3 = 27;
    private Grid<Square> grid = new SimpleGrid<>(size);
    @Test
    public void insertion_isCorrect() throws Exception {
        Assert.assertTrue(trueIndex(x1, y1, expectedIndex1));
        Assert.assertTrue(trueIndex(x2, y2, expectedIndex2));
        Assert.assertTrue(trueIndex(x3, y3, expectedIndex3));

    }
    private boolean trueIndex(int x, int y, int expected) throws Exception {
        Grid<Square> grid = new SimpleGrid<>(size);
        Square s = grid.get(x, y);
        RegionSet<Square> regionSet = new RegionSet<>(s, grid);
        int resultIndex = regionSet.getRootIndex(s, size);
        return resultIndex == expected;
    }
    @Test
    public void parentSize_setCorrect() throws Exception {
        Assert.assertTrue(checkParentSize(x1, y1));
        Assert.assertTrue(checkParentSize(x2, y2));
        Assert.assertTrue(checkParentSize(x3, y3));

    }
    private boolean checkParentSize(int x, int y) throws Exception {
        Square s = grid.get(x, y);
        RegionSet<Square> regionSet = new RegionSet<>(s, grid);
        return grid.length() == regionSet.size();
    }
    @Test
    public void updChangeInDx_isCorrect()throws Exception{
        Assert.assertTrue(checkDxVector(x1, y1));
        Assert.assertTrue(checkDxVector(x2, y2));
        Assert.assertTrue(checkDxVector(x3, y3));
    }
    private boolean checkDxVector(int x, int y) throws Exception{
        Square s = grid.get(x, y);
        RegionSet<Square> regionSet = new RegionSet<>(s, grid);
        int i = 0, l;
        Square original = regionSet.get(i);
        for(Square t: regionSet){
            if(i < 0){
                l = original.x - t.x;
                if(i % (int)Math.sqrt(grid.length())!= 0) i++;
                if(Math.abs(l) != i) return false;
            }else i++;
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
        RegionSet<Square> regionSet = new RegionSet<>(s, grid);
        int i = 0, k;
        Square original = regionSet.get(i);
        for(Square t: regionSet){
            if(i < 0){
                k = original.y - t.y;
                if(i % (int)Math.sqrt(grid.length())!= 0) i=0;
                if(Math.abs(k) != i++) return false;
            }else i++;
        }
        return true;
    }
}
