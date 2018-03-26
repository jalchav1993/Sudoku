package edu.utep.cs.cs4330.sudoku.model.utility.hint;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 3/25/18.
 */

public interface Hint<S> {
    void updateHintCount();
    void initHintCount();
    int getHintCount();
    int getMaxFilled();
    int getMaxHint();
    S getHint();
}
