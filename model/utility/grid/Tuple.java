package edu.utep.cs.cs4330.sudoku.model.utility.grid;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 3/26/18.
 */

public class Tuple{
    private final int difficulty, area;
    public Tuple(int difficulty, int area){
        this.difficulty = difficulty;
        this.area = area;
    }
    public int getDifficulty(){
        return difficulty;
    }
    public int len(){
        return area;
    }
}
