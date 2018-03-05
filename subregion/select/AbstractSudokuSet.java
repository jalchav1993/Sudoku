package edu.utep.cs.cs4330.sudoku.subregion.select;

import java.util.ArrayList;
import java.util.List;

import edu.utep.cs.cs4330.sudoku.model.Square;

/**
 * Created by aex on 2/27/18.
 */
// extends hash map, factory should take care of this
public abstract class AbstractSudokuSet<T> extends ArrayList<T>{

    /* Class Variables */
    protected final List<T> keySet;
    protected final int parentSize;

    /* Class Constructor */
    public AbstractSudokuSet(Square key,int parentSize, List<T> keySet){
        super(parentSize);
        this.parentSize = parentSize;
        this.keySet = keySet;
        build(key);
    }

    /* Class Public Methods */
    @Override
    public boolean contains(Object o){
        return inset((int) o);
    }
    /* Inner Logic, Implemented by user for especial area */
    /* row n * 1, column 1 * n, or region sqrt(n) * sqrt(n) */
    public boolean inset(int z){
        for(T compareTo: this){
            if(compareTo.equals(z)) return false;
        }
        return true;
    }

    private List<Integer> getDelta(int axis) {
        int delta = 0;
        List<Integer> dVector = new ArrayList<>();
        for(int i = 0; i < parentSize; i++){
            delta = updChangeInDelta(delta, axis);
            dVector.add(Integer.valueOf(delta));
        }
        return dVector;
    }
    protected int getSigma() {
        return (int)Math.sqrt(parentSize);
    }

    protected abstract int calculateIndex(Square root);
    protected abstract int updChangeInDelta(int delta, int axis);

    private void getSquareConsecutives(Square key, List<Integer> x, List<Integer> y){
        for (int i = 1; i < parentSize; i ++){
            int index = ((key.x+ x.get(i))*parentSize )+key.y+y.get(i);
            add(keySet.get(index));
        }
    }
    /**
     * @return subset of keySet equal to matrix multiplication of VectorX X VectorY
     * This subset X*Y defines an area such as row, column and n-(size)-size region
     */
    private void build(Square root){
        int index =  calculateIndex(root); //use area selector
        List<Integer> xVector = getDelta(1);
        List<Integer> yVector = getDelta(-1);
        getSquareConsecutives(root, xVector, yVector);

    }
}

