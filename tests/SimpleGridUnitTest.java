package edu.utep.cs.cs4330.sudoku.tests;

import org.junit.Test;

import java.util.List;

import edu.utep.cs.cs4330.sudoku.model.Grid;
import edu.utep.cs.cs4330.sudoku.model.SimpleGrid;
import edu.utep.cs.cs4330.sudoku.model.Square;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 3/5/18.
 */

public class SimpleGridUnitTest {
    @Test
    public void buildGrid_isCorrect() throws Exception {
        int length = 9;
        SimpleGrid<Square> grid = new SimpleGrid<>(length);
        //do not override length, change this
        assertTrue(grid.size() == length*length);
        assertTrue(grid.length() == length);
        for (int i = 0; i < grid.length(); i++) {
            for(int j = 0; j < grid.length(); j++) {
                Square current = grid.get(i,j);
                assertTrue(current.x == i);
                assertTrue(current.y == j);
                assertTrue(current.get() == 0);
            }
        }
    }
    @Test
    public void size_IsCorrect() throws Exception {
        int length = 9;
        List grid = new SimpleGrid<Square>(length);
        assertEquals(length, ((Grid) grid).length());
    }
}
