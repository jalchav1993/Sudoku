package edu.utep.cs.cs4330.sudoku.select;

import java.util.ArrayList;
import java.util.List;

import edu.utep.cs.cs4330.sudoku.model.Grid;
import edu.utep.cs.cs4330.sudoku.model.Square;

import static junit.framework.Assert.assertEquals;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 2/28/18.
 */
public abstract class AbstractSudokuSet<S> extends ArrayList<S> {

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
    public AbstractSudokuSet(S key, List<S> parentGridRef) throws Exception {
        super(parentGridRef.size());
        int index = 0;
        this.parentGridLength = ((Grid) parentGridRef).length();
        this.parentGridRef = parentGridRef;
        super.ensureCapacity(parentGridRef.size());
        index = getRootIndex(key, parentGridLength);
        S root = parentGridRef.get(index);
        build(root); /* build expects a root not a key */
    }
    /**
     * @param   o Some Object
     * @return  s: inset(o) -> true;
     *          otherwise -> false;
     */
    /**
     * Inner Logic, Implemented by user for especial area row n * 1, column 1 * n,
     * or region sqrt(n) * sqrt(n). in is a positive word
     *
     */
    public boolean inset(int z){
        int i = 0;
        for(S compareTo: this){
            i++;
            if(compareTo.equals(z)) return true;
        }
        assertEquals(parentGridLength, i);
        return false;
    }

    /**
     * This method is a driver for getDeltaVector(axis) Per UML design, this class is extended by
     * RowSet, ColumnSet, RegionSet. for row setValue, the goal is to generate changes in delta
     * with respect to the axis encoding.
     * <driver>AbstractSudokuSet getDeltaVector(axis)</driver>
     */
    private int updChangeInDelta(int delta, int i, int axis) throws Exception {
        if(axis == SET_AXIS_X)
            return updChangeInDx(delta, i, parentGridLength);
        else if (axis == SET_AXIS_Y)
            return updChangeInDy(delta, i, parentGridLength);
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
    private List<Integer> getDeltaVector(int axis) throws Exception {
        int delta = getInitDelta();
        List<Integer> dVector = new ArrayList<>();
        for(int i = 0; i < parentGridLength; i++){
            delta = updChangeInDelta(delta, i, axis);
            dVector.add(delta);
        }
        return dVector;
    }

    /**
     * get Consecutives S objects in a specified area
     */
    private void getSConsecutives(S key, List<Integer> dx, List<Integer> dy){
        Square s = (Square) key;
        S next;
        int index = ((Grid) parentGridRef).getLinearIndex(s.x, s.y);
        super.add(parentGridRef.get(index));
        for (int i = 1; i < parentGridLength; i++){
            index = ((Grid) parentGridRef).getLinearIndex(s.x+dx.get(i),s.y+dy.get(i) );//+?,
            next = parentGridRef.get(index);
            super.add(next);
        }
    }
    /**
     * Builds the java.util.list
     */
    private void build(S root) throws Exception {
        List<Integer> yVector = getDeltaVector(SET_AXIS_Y);
        List<Integer> xVector = getDeltaVector(SET_AXIS_X);
        getSConsecutives(root, xVector,yVector);
    }

    public abstract int getRootIndex(S root, int length);   /* Concrete set logic */
    public abstract int updChangeInDy(int delta, int i, int length);/* Concrete set logic */
    public abstract int updChangeInDx(int delta, int i, int length);/* Concrete set logic */
    public abstract int getInitDelta();
    // may be concrete classes should handle more implementation
}

//create some helper exceptions for docu