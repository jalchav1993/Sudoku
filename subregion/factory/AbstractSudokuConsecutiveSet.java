package edu.utep.cs.cs4330.sudoku.subregion.factory;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

import edu.utep.cs.cs4330.sudoku.model.Square;

/**
 * Created by aex on 2/27/18.
 */

public abstract class AbstractSudokuConsecutiveSet<T> {
    /* Class Constants */
    public static final List<?> REGION = new ArrayList<Integer>(){{
        add(1); add(1); add(1); add(1); add(0);
    }};
    public static final List<?> ROW = new ArrayList<Integer>(){{
        add(0); add(1); add(0); add(1); add(0);
    }};
    public static final List<?> COL = new ArrayList<Integer>(){{
        add(1); add(0); add(1); add(1); add(0);

    }};

    protected final int alpha, beta, gama, sigma, dx, dy, tx, ty;
    protected final Square key;
    protected final List<?> grid;

    /* Class Variables */

    public final int capacity;

    /* Inner Variables */

    /* Class Type */
    private T t;

    private Map<Square, T> sudokuSet;

    /* Class Constructor T must be an array list*/
    public AbstractSudokuConsecutiveSet(Square key, int capacity, T t, List<?> grid){
        this.key = key;
        this.capacity = capacity;
        this.t = t;
        alpha = ((List<Integer>) t).get(0);
        beta = ((List<Integer>) t).get(1);
        gama = ((List<Integer>) t).get(2);
        dx =((List<Integer>) t).get(3);
        dy =((List<Integer>) t).get(4);
        tx =((List<Integer>) t).get(5);
        ty =((List<Integer>) t).get(6);
        sigma = getSigma();
        this.grid = grid;
        sudokuSet = build(key);
    }

    /* Class Public Methods */
    public Map<Square, T> get(){
        return sudokuSet;
    }
    /* Inner Logic, Implemented by user for especial area */
    /* row n * 1, column 1 * n, or region sqrt(n) * sqrt(n) */

    /**
     * @return subset of grid equal to matrix multiplication of VectorX X VectorY
     * This subset X*Y defines an area such as row, column and n-(capacity)-size region
     */
    protected Map<Square, T>build(Square root){
       int index =  calculateIndex(root);
       T xVector = getDelta(root, dx);
       T yVector = getDelta(root, dy);
       return null;

    }
    protected int calculateIndex(Square root){
        return (root.x - (root.x % sigma)*alpha)*capacity*gama + (root.y - (root.y % sigma)*beta);
    }

    protected T getDelta(Square root, int d) {
        int delta = 0;
        List<Integer> dVector = new ArrayList<>();
        for(int i = 0; i < capacity; i++){
            if(i % sigma == 0) delta += d;
            dVector.add(Integer.valueOf(delta));
        }
        return (T) dVector;
    }

    protected int getSigma(){
        return (int) Math.sqrt(capacity);
    }
}
