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
        Assert.assertTrue(s.permissions == Square.READ_WRITE_DELETE);
    }
    @Test
    public void get_isCorrect(){
        Assert.assertTrue(s.get() == 0);
        Assert.assertTrue(s.setValue(2));
        Assert.assertTrue(s.get() == 2);
    }
    @Test
    public void set_isCorrect(){
        Assert.assertTrue(s.setValue(9));
        Assert.assertTrue(s.get() == 9);
    }
    @Test
    public void equals_isCorrect(){
        Square d = new Square(x,y,size,Square.READ_WRITE_DELETE);
        Assert.assertTrue(d.equals(s));
        Assert.assertTrue(s.equals(d));
    }
}
