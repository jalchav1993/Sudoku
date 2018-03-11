package edu.utep.cs.cs4330.select;

import java.util.ArrayList;
import java.util.List;

import edu.utep.cs.cs4330.model.Grid;
import edu.utep.cs.cs4330.model.Square;
/**
 * @author: Jesus Chavez
 * @macuser: aex on 2/28/18.
 */
// extends hash map, factory should take care of this
public abstract class AbstractSudokuSet<T> extends ArrayList<T>{
    /* Class Variables */
    private final List<T> keySet;
    protected final int parentSize, parentLength;

    /* Class Constructor */
    public AbstractSudokuSet(T key, List<T> keySet){
        super(((Grid) keySet).size());
        this.parentLength = ((Grid) keySet).length();
        this.parentSize = ((Grid) keySet).size();
        this.keySet = keySet;
        build(key);
    }
    public int length(){
        return parentSize;
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

    /** Update Change In Delta
     * @implNote
     * <description>
     *     This method is a driver for getDelta(axis)
     *     Per UML design, this class is extended by RowSet, ColumnSet, RegionSet,
     *     for row set, the goal is to generate changes in delta. Then x+=x + d; y += y + d;
     *     depending on the axis encoding.
     * </description>
     * @param delta initial d to which the method applies transformation dx
     * @param axis <driver>AbstractSudokuSet getDelta(axis)</driver>
     * @return List such that /foreach l /element List, l /element Integers and
     *         l is part of a set of circulating series specified in the
     *         description of the concrete class in the UML design
     * @see ColumnSet
     * @see RowSet
     * @see RegionSet
     */
    protected abstract int updChangeInDelta(int delta, int axis);
    /** Get Delta
     * @implNote
     * <description>
     *     Creates a vector of indices subset of a function defined in the subclass.
     *     Per design, only 3 classes extend this class, RowSet, ColumnSet, RegionSet.
     *     Each implementation subclass should aid get delta in building the vector, this
     *     vector could be in the x or y direction.
     *     /foreach v /element_of V v is an index increment on y or x such that this
     *     increment advances the position of (x,y) one square into the direction of the
     *     origin. /definition origin: the bottom end of column, left end of row, or
     *     bottom left corner of region nxn.</br>
     *     For Rows,
     *     Sum_n_x(rowSet) = {0,0,0,0,....,0}
     *     where Sum_n_x(rowSet).len() == n</br>
     *     Sum_n_y(rowSet) = {1,2,3,4,....,n}
     *     where Sum_n_y(rowSet).len() == n</br>
     *     For Columns,
     *     Sum_n_x(colSet) = {1,2,3,4,....,n}
     *     where Sum_n_x(colSet).len() == n</br>
     *     Sum_n_y(colSet) = {0,0,0,0,....,0}
     *     where Sum_n_y(colSet).len() == n</br>
     *     For Regions,
     *     Matrix_n_x(colSet) = {1,2,3,...,sqrt_0(n),...,1,2,3,...,sqrt_1(n),..,sqrt_nth(n)}
     *     where Sum_n_x(colSet).len() == n
     *     Matrix_n_y(colSet) = {0_0,0_1,..,0_sqrt(n),
     *                           1_0,..,1_sqrt(n),..,sqrt(n)_0,
     *                           ..,sqrt(n)_sqrt(n)}
     *     where Sum_n_y(colSet).len() == n</br>
     * </description>
     * @param axis encoding for x and y where dx -> updChangeInDelta(delta, 1) &&
     *             dy -> updChangeInDelta(delta, -1)
     * @return List L such /foreach l /element L, l /is_element circulating set defined
     *         in concrete classes extended by this class.
     * @author Jesus Chavez
     * @see ColumnSet
     * @see RowSet
     * @see RegionSet
     * @see AbstractSudokuSet getSqaureConsecutives(key, x, y)
     */
    protected List<Integer> getDelta(int axis) {
        int delta = 0;
        List<Integer> dVector = new ArrayList<>();
        for(int i = 0; i < parentSize; i++){
            delta = updChangeInDelta(delta, axis);
            dVector.add(Integer.valueOf(delta));
        }
        return dVector;
    }
    private void getTConsecutives(T key, List<Integer> x, List<Integer> y){

        for (int i = 0; i < parentSize; i ++){
            Square s = (Square) key;
            int index = (s.x+ x.get(i))*parentSize+(s.y+y.get(i)) ;
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

