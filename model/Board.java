package edu.utep.cs.cs4330.sudoku.model;

import java.util.List;

/**
 * Created by aex on 2/28/18.
 */

public interface Board {
    int getStatus();
    int size();
    String getState();
    List<?> keySet();
}
