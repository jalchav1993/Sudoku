package edu.utep.cs.cs4330.sudoku.tests;

import org.junit.Test;

import edu.utep.cs.cs4330.sudoku.model.Grid;
import edu.utep.cs.cs4330.sudoku.model.HardGrid;
import edu.utep.cs.cs4330.sudoku.model.Square;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 3/5/18.
 */

public class HardGridUnitTest {
    @Test
    public void instance_isFilledCorrect() throws Exception {
        int testCaseFilled0 = 17,testCaseSize0 = 9;
        Grid<Square> hardGrid = new HardGrid<>(testCaseSize0,testCaseFilled0);
        int nZeros = 0, nPos = 0;
        //assertTrue(hardGrid.pack(3,4,5));
        //assertEquals(5, hardGrid.get(3,4).get());
        for(Square h : hardGrid){
            if(h.get() > 0) nPos++;
            else nZeros++;
        }
        assertEquals(testCaseFilled0, nPos);

    }
    @Test
    public void insert_isCorrect()throws Exception{
        int testCaseFilled0 = 0,testCaseSize0 = 9;
        Grid<Square> hardGrid = new HardGrid<>(testCaseSize0,testCaseFilled0);
        int nZeros = 0, nPos = 0;
        assertTrue(hardGrid.pack(3,4,5));
        assertEquals(5, hardGrid.get(3,4).get());
        assertTrue(!hardGrid.pack(3,3,5));
        assertTrue(!hardGrid.pack(2,4,5));
        assertTrue(!hardGrid.pack(4,4,5));
        assertTrue(hardGrid.pack(7,8,5));
    }
}
