package edu.utep.cs.cs4330.sudoku.model.solve;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 3/29/18.
 */

// This forces every solution class to implement a common interface,
// which is helpful for unit testing like in the EulerTest implementation.
public interface EulerSolution {

    public String run();

}
