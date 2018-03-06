package edu.utep.cs.cs4330.sudoku.subregion.select;

import java.util.ArrayList;
import java.util.List;

import edu.utep.cs.cs4330.sudoku.model.Square;
/**
 * @author: Jesus Chavez
 * @macuser: aex on 2/28/18.
 */
// extends hash map, factory should take care of this
public abstract class AbstractSudokuSet<T> extends ArrayList<T>{
    /* Class Variables */
    private final List<T> keySet;
    protected final int parentSize;

    /* Class Constructor */
    public AbstractSudokuSet(T key,int parentSize, List<T> keySet){
        super(parentSize);
        this.parentSize = parentSize;
        this.keySet = keySet;
        build(key);
    }

    /**
     * Returns true if item is in the set
     * @param o
     * @return E set s: inset(o) -> true;
     * otherwise -> false;
     */
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
    protected int getSigma() {
        return (int)Math.sqrt(parentSize);
    }
    protected abstract int calculateIndex(T root);
    protected abstract int updChangeInDelta(int delta, int axis);
    private List<Integer> getDelta(int axis) {
        int delta = 0;
        List<Integer> dVector = new ArrayList<>();
        for(int i = 0; i < parentSize; i++){
            delta = updChangeInDelta(delta, axis);
            dVector.add(Integer.valueOf(delta));
        }
        return dVector;
    }
    private void getTConsecutives(T key, List<Integer> x, List<Integer> y){

        for (int i = 1; i < parentSize; i ++){
            int index = ((((Square)key).x+ x.get(i))*parentSize )+((Square)key).y+y.get(i);
            add(keySet.get(index));
        }
    }

    /**
     * @return subset of keySet equal to matrix multiplication of VectorX X VectorY
     * This subset X*Y defines an area such as row, column and n-(size)-size region
     */
    private void build(T root){
        int index =  calculateIndex(root); //use area selector
        List<Integer> xVector = getDelta(1);
        List<Integer> yVector = getDelta(-1);
        getTConsecutives(root, xVector, yVector);

    }
}

