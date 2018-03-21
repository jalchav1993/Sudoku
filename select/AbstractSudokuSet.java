package edu.utep.cs.cs4330.sudoku.select;

import java.util.ArrayList;
import java.util.List;

import edu.utep.cs.cs4330.sudoku.model.Grid;
import edu.utep.cs.cs4330.sudoku.model.Square;

import static junit.framework.Assert.assertTrue;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 2/28/18.
 */
public abstract class AbstractSudokuSet<S> extends ArrayList<S>{

    /**
     * Class constant definitions for coherency
     */
    private static final int SET_AXIS_X = 1;
    private static final int SET_AXIS_Y = 0;

    /**
     * Class variables
     */
    private final List<S> parentGridRef;
    private final int parentGridLength;

    /**
     * Instantiates a set of consecutive S s_i with respect to a given S s
     * @param   key S object for which a set of logically (row, col, region) co
     * @param   parentGridRef Parent set of consecutive Objects S of size n x n
     */
    public AbstractSudokuSet(S key, List<S> parentGridRef){
        this.parentGridLength = ((Grid) parentGridRef).length();
        super.ensureCapacity(parentGridLength);
        this.parentGridRef = parentGridRef;
        S root = parentGridRef.get(getRootIndex(key, parentGridLength));
        build(root); /* build expects a root not a key */
    }
    //needs a get
    public S get(int x, int y){
        for (S s: this){
            if(((Square) s).x == x && ((Square) s).y == y) return s;
        }
        return null;
    }
    /**
     * @param   o Some Object
     * @return  s: inset(o) -> true;
     *          otherwise -> false;
     */
    @Override
    public boolean contains(Object o){
        return inset((int) o);
    }

    /**
     * Inner Logic, Implemented by user for especial area row n * 1, column 1 * n,
     * or region sqrt(n) * sqrt(n).
     */
    private boolean inset(int z){
        for(S compareTo: this){
            if(compareTo.equals(z)) return false;
        }
        return true;
    }

    /**
     * This method is a driver for getDeltaVector(axis) Per UML design, this class is extended by
     * RowSet, ColumnSet, RegionSet. for row setValue, the goal is to generate changes in delta
     * with respect to the axis encoding.
     * <driver>AbstractSudokuSet getDeltaVector(axis)</driver>
     */
    private int updChangeInDelta(int delta, int axis) throws Exception {
        if(axis == SET_AXIS_X)
            return updChangeInDx(delta, parentGridLength);
        else if (axis == SET_AXIS_Y)
            return updChangeInDy(delta, parentGridLength);
        else throw new Exception("wrong selector");
    }

    /**
     * Builds List L such /foreach l /element L, l /is_element circulating setValue defined
     * in concrete classes extended by this class. Per design, only 3 classes extend this class,
     * RowSet, ColumnSet, RegionSet. And /foreach l /element_of L, l is an index increment on
     * y or x such that this increment advances the position of (x,y) one square into the
     * direction of the origin. /definition origin: the bottom end of column, left end of row,
     * or bottom left corner of area(nxn). Each implementation subclass should aid get delta in
     * building the vector, this vector could be in the x or y direction.
     *
     * For Rows,
     *     Sum_n_x(rowSet) = {0,0,0,0,....,0}
     *     where Sum_n_x(rowSet).len() == n
     *     Sum_n_y(rowSet) = {1,2,3,4,....,n}
     *     where Sum_n_y(rowSet).len() == n
     *
     * For Columns,
     *     Sum_n_x(colSet) = {1,2,3,4,....,n}
     *     where Sum_n_x(colSet).len() == n
     *     Sum_n_y(colSet) = {0,0,0,0,....,0}
     *     where Sum_n_y(colSet).len() == n
     *
     * For Regions,
     *     Matrix_n_x(colSet) = {1,2,3,...,sqrt_0(n),...,1,2,3,...,sqrt_1(n),..,sqrt_nth(n)}
     *     where Sum_n_x(colSet).len() == n
     *     Matrix_n_y(colSet) = {0_0,0_1,..,0_sqrt(n),
     *                           1_0,..,1_sqrt(n),..,sqrt(n)_0,
     *                           ..,sqrt(n)_sqrt(n)}
     *     where Sum_n_y(colSet).len() == n
     */
    private List<Integer> getDeltaVector(int axis) {
        int delta = -1;
        List<Integer> dVector = new ArrayList<>();
        for(int i = 0; i < parentGridLength; i++){
            try {
                delta = updChangeInDelta(delta, axis);
            } catch (Exception e) {
                e.printStackTrace();
            }
            dVector.add(delta);
        }
        return dVector;
    }

    /**
     * get Consecutives S objects in a specified area
     */
    private void getSConsecutives(S key, List<Integer> dx, List<Integer> dy){
        Square s = (Square) key;
        int index =(s.x* parentGridLength) + s.y;
        super.add(parentGridRef.get(index));
        for (int i = 1; i < parentGridLength; i++){
            index = ((s.x+ dx.get(i))* parentGridLength)+(s.y+ dy.get(i));//+?
            super.add(parentGridRef.get(index));
        }
    }

    /**
     * Builds the java.util.list
     */
    private void build(S root) {
        List<Integer> xVector = getDeltaVector(SET_AXIS_X);
        List<Integer> yVector = getDeltaVector(SET_AXIS_Y);
        getSConsecutives(root, xVector, yVector);
    }

    public abstract int getRootIndex(S root, int length);   /* Concrete set logic */
    public abstract int updChangeInDx(int delta, int length);/* Concrete set logic */
    public abstract int updChangeInDy(int delta, int length);/* Concrete set logic */
    // may be concrete classes should handle more implementation
}
class AbstractSudokuSetSquareExeption extends Exception{
    private final String status;
    public AbstractSudokuSetSquareExeption(Square s, int x, int y, List<Integer> dx, List<Integer> dy, int i, int index){
        status = "i = "+ i+
                " index "+ index+
                " x "+s.x+" y "+s.y +
                " val " + s.get() +
                " dx "+ dx + " dy "+ dy;
    }
    @Override
    public String toString(){
        return status;
    }
}
//create some helper exceptions for docu