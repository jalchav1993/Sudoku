package edu.utep.cs.cs4330.sudoku.tests;

import org.junit.Test;

import edu.utep.cs.cs4330.sudoku.model.Square;

import static org.junit.Assert.assertTrue;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 3/5/18.
 */

public class SquareUnitTest {
    final static int x = 0, y = 0, size = 9;
    final static Square s = new Square(x,y,size, Square.READ_WRITE_DELETE);
    @Test
    public void isPermission_isCorrect(){
        assertTrue(s.permissions == Square.READ_WRITE_DELETE);
    }
    @Test
    public void get_isCorrect(){
        assertTrue(s.get() == 0);
        assertTrue(s.setValue(2));
        assertTrue(s.get() == 2);
    }
    @Test
    public void set_isCorrect(){
        assertTrue(s.setValue(9));
        assertTrue(s.get() == 9);
    }
    @Test
    public void equals_isCorrect(){
        Square d = new Square(x,y,size,Square.READ_WRITE_DELETE);
        assertTrue(d.equals(s.get()));
        assertTrue(s.equals(d.get()));
    }
}
