package edu.utep.cs.cs4330.sudoku.tests;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

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
    public void instance_isFilledCorrect(){
        int testCaseFilled0 = 17,testCaseSize0 = 9;
        HardGrid<Square> hardGrid = new HardGrid<>(testCaseSize0,testCaseFilled0);
        int nZeros = 0, nPos = 0;
        for(Square h : hardGrid){
            if(h.get() > 0) nPos++;
            else nZeros++;
        }
        assertEquals(testCaseFilled0, nPos);

    }
}
