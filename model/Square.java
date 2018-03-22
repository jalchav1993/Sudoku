package edu.utep.cs.cs4330.sudoku.model;
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

    public final static int READ_WRITE_DELETE = 666;
    public final static int READ_N_N = 600;
    public final static int WRITE_N_N = 060;
    public final static int DELETE_N_N = 006;
    public final static int READ_WRITE_N = 660;
    public final static int READ_N_DELETE = 606;
    public final static int N_N_N = 000;
    public final static int PERMISSION_RATIO = 6;

    /* Class final variables */
    public final int x;
    public final int y;
    private final int parentCapacity;
    public final int permissions;
    private int value;

    public Square(int x, int y, int parentCapacity, int permissions){
        this.x = x;
        this.y = y;
        this.parentCapacity = parentCapacity;
        if(isAPermission(permissions))
            this.permissions = permissions;
        else this.permissions = Square.N_N_N;
        value = 0;
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o){
        /* casting for polymorphism */
        if (o.getClass().equals(Integer.class))
            return this.value == ((int) o);
        else
            return o.getClass() == Square.class &&
                    this.y == ((Square) o).y &&
                    this.x == ((Square) o).x &&
                    this.get() == ((Square) o).get();
    }
    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return value+" "+x+" "+ y;
    }
    public boolean hasValue(int v){
        return value == v;
    }

    /**
     *
     * @param value
     * @return
     */
    public boolean setValue(int value){
       // if(checkPermission(Square.WRITE_N_N)) {
            this.value = value;
          //  return true;
        //} else return false;
        return true;
    }

    /**
     *
     * @return
     */
    public int get(){
        //if(checkPermission(Square.READ_N_N))
            return this.value;
        //else return -1;
    }

    /**
     *
     */
    public void delete(){
        //if(checkPermission(Square.DELETE_N_N))
            this.value = 0;
    }
    /**
     * Checks if privileges is part of permission setValue
     * @param privileges look at data documentation
     * @return if privileges c (Universe_Permissions) -> true else -> false
     */
    private boolean isAPermission(int privileges){
        return (privileges == Square.READ_WRITE_DELETE ||
                privileges == Square.READ_WRITE_N ||
                privileges == Square.READ_N_DELETE ||
                privileges == Square.READ_N_N ||
                privileges == Square.WRITE_N_N ||
                privileges == Square.DELETE_N_N||
                privileges == Square.N_N_N
        );
    }

    /**
     * Checks weather requested action's privileges are allowed using the squares permissions
     * @param privileges
     * @see public final static int READ_WRITE_DELETE
     * @return if privileges c (permissions(of the square)) -> true else -> false
     */
    private boolean checkPermission(int privileges){
        /* top permission is always 666 for read, write, delete */
        /* ratio is 6 gives order of magnitude of request*/
        int magnitude_tc = privileges/Square.PERMISSION_RATIO;
        if(magnitude_tc >= 100){ /* mtc > 10^2 means they want to read */
            return permissions - Square.READ_N_N >= 0;
        } else if(magnitude_tc < 100 && magnitude_tc >= 10){
            /*666/6 < 100 && 666/6 > 10 means they want to delete */
            return permissions - Square.READ_N_N-Square.WRITE_N_N >= 0;
        } else{ /*666/6 < 10 means they want to delete */
            return permissions - Square.READ_N_N-Square.WRITE_N_N-Square.DELETE_N_N >=0;
        }
    }
}