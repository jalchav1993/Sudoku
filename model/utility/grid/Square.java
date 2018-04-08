package edu.utep.cs.cs4330.sudoku.model.utility.grid;
/**
 * @author: Jesus Chavez
 * @macuser: aex on 2/19/18.
 */

public class Square {
    /* Permissions may only be setValue once for security reasons */
    /* For permissions and flexible programing toolkit */
    /* Useful for when more than 2 players use the keySet,*/
    /* since the owner of the gui will need flexible permissions to access the keySet */
    /* for instance a square belongs to another client, this client will have permission 600 */
    /* indicate ownership, 666 means the square belongs to the client */
    /* permissions will probably be handled by a we service later on, for multiplaying */

    /* Class final variables */
    public final int x;
    public final int y;
    private boolean selected;
    private boolean isEnabled;
    private int value;

    public static final int EMPTY = 0;
    public Square(int x, int y, int parentCapacity, boolean slected){
        this.x = x;
        this.y = y;
        this.selected = slected;
        isEnabled = false;
        value = 0;
    }
    public void enable(){
        //for game initialization
        if(!isEnabled) isEnabled = true;
    }
    public void disable(){
        if(!isEnabled) isEnabled = false;
    }
    public boolean isEnabled(){
        return isEnabled;
    }
    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o){
        return this.y == ((Square) o).y &&
                this.x == ((Square) o).x &&
                this.get() == ((Square) o).get();
    }
    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return value+" = (x:"+x+",y:"+ y+")";
    }

    /**
     *
     * @param value
     * @return
     */
    public void setValue(int value){
        this.value = value;
    }

    /**
     *
     * @return
     */
    public int get(){
            return this.value;
    }

    /**
     *
     */
    public void delete(){
            this.value = 0;
    }
    public boolean contains(int value){
        return this.value == value;
    }
    public boolean select(){
        return selected = true;
    }
    public boolean deselect(){
        return selected = false;
    }

    /**
     * Checks weather requested action's privileges are allowed using the squares permissions
     */
    public boolean isSelected(){
        /* top permission is always 666 for read, write, delete */
        /* ratio is 6 gives order of magnitude of request*/
        return selected;
    }
}