package edu.utep.cs.cs4330.sudoku.tests;

import org.junit.Test;

import edu.utep.cs.cs4330.sudoku.model.Board;
import edu.utep.cs.cs4330.sudoku.model.HardGrid;
import edu.utep.cs.cs4330.sudoku.model.Square;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 3/20/18.
 */

public class BoardUnitTest {
    @Test
    public void solve_isCorrect() throws Exception {
        Board b = new Board<Square>(9, new HardGrid<>(9, 17));
    }
}
