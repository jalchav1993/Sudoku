package edu.utep.cs.cs4330.sudoku.model.utility.grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.utep.cs.cs4330.sudoku.model.utility.select.AbstractSudokuSet;
import edu.utep.cs.cs4330.sudoku.model.utility.select.SudokuSetFactory;
/**
 * @author: Jesus Chavez
 * @macuser: aex on 3/5/18.
 */

public abstract class Grid<S> extends ArrayList<S> {
    /* static constants */
    private static final int REGION = 0;
    private static final int ROW = 1;
    private static final int COLUMN = 2;
    private final int GRID_BOUND;
    private final int GRID_LENGTH;
    private SudokuSetFactory<S> factory;
    /* class variables */
    private Map<S, List<S>>  regions;
    private Map<S, List<S>>  rows;
    private Map<S, List<S>> columns;
    private int hintCount;
    public Grid(int length) throws Exception {
        this.GRID_LENGTH = length;
        GRID_BOUND = getBound(length);
        int capacity = (int) Math.pow(length, 2);
        hintCount = 0;
        super.ensureCapacity(capacity);
        buildGrid();
        buildMaps();
        fill();

    }
    public int length() {
        return GRID_LENGTH;
    }
    /**
     *
     * @param x
     * @param y
     * @return
     */
    public S get(int x, int y){
        int index = getLinearIndex(x, y);
        return get(index);
    }
    public boolean pack(int x, int y, int z) {
        //this does not work
        S s = get(x, y);
        if(isPackable(s, z)){
            ((Square)s).setValue(z);
            return true;
        }
        return false;
    }
    /**
     *
     * @param x
     * @param y
     * @param z
     * @param sudokuSetSelector
     * @return
     */
    protected boolean inset(int x, int y, int z, int sudokuSetSelector){
        S square = get(x,y);
        List<S> area = null;
        int index;
        if(sudokuSetSelector == REGION){
            area = regions.get(square);
        } else if(sudokuSetSelector == ROW){
            area = rows.get(square);
        } else if(sudokuSetSelector == COLUMN){
            area = columns.get(square);
        }
        assert area != null;
        return area.contains(square);
    }

    /**
     * Returns linear position of index
     * @param x
     * @param y
     * @return : (return/x)-y = size
     */
    public int getLinearIndex(int x, int y){
        return x + (y* GRID_LENGTH) ;
    }
    /**
     *  0 means empty
     *    Square (x, y, z) isElement grid -> false
     *                          otherwise -> true
     */
    private boolean isPackable(S s, int z){
        List<S> region = regions.get(s);
        List<S> column = columns.get(s);
        List<S> row = rows.get(s);
        return checkSpace(s) &&
                !((AbstractSudokuSet<S>) region).inset(z) &&
                !((AbstractSudokuSet<S>) row).inset(z) &&
                !((AbstractSudokuSet<S>) column).inset(z);
    }
    /**
     * Compare Square (x,y,z) id to se if it matches a Square (x,y,z) element of gird
     */
    private boolean compareTo(S s, int z){
        //only had to fix this once and it fixed the whole class method is coherent, code is cohesive
        return ((Square) s).contains(z);
    }
    /**
     * Checks if a given space is occupied, setValue to 0
     */
    public boolean checkSpace(S s) {
        return compareTo(s, 0); /* 0  means not used */
    }
    /**
     * Concrete class implements many difficulty levels
     */
    private void buildGrid()throws Exception{
        for (int i = 0; i < length(); i++) {
            for(int j = 0; j < length(); j++){
                add((S) new Square(j, i, getBound(), false));
            }
        }
    }
    private void buildMaps() throws Exception{
        factory = new SudokuSetFactory<>(this);
        rows = factory.getRows();
        columns = factory.getColumns();
        regions = factory.getRegions();
    }

    public S getHint(){
        List<Boolean> validationBuffer = new ArrayList<>(9);{
            for(int i = 0; i < length(); i++){
                validationBuffer.add(false);
            }
        }
        if(getHintsUsed() < getMaxHint()){
            updateHintCount();
            int index = 0;
            for(S s : this){ //not this but extreact regions change sudoku set
                validationBuffer.add(index, ((Square)s).contains(index++));
                if(index == getBound()) return s;
            }
        }
        return null;
    }
    public boolean isSolvable(){
        return false;
    }
    public void solve(){

    }
    private void fill() throws Exception{
        int k = 1, x, y, z;
        while(k<= getMaxFilled()){
            x = (int) (Math.random() * 8);
            y = (int) (Math.random() * 8);
            z = (int) (Math.random() * 8 + 1);
            if(pack(x, y, z)) k++;
        }
    }
    private int getHintsUsed(){
        return getMaxHint() - getHintCount();
    }
    private void updateHintCount(){
        hintCount++;
    }
    private int getHintCount(){
        return hintCount;
    }
    private int getBound(int len){
        int sum = 0;
        for(int i = 0; i < len; i++) { sum+= i; }
        return sum;
    }
    public int getBound(){
        return GRID_BOUND;
    }
    protected abstract int getMaxFilled();
    protected abstract int getMaxHint();
}
