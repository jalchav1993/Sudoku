package edu.utep.cs.cs4330.sudoku.model;
/**
 * @author: Jesus Chavez
 * @macuser: aex on 2/28/18.
 */

public class HardGrid<S> extends Grid<S> {
    public HardGrid(int size, int filled) {
        super(size, filled);
        fill();
    }

    @Override
    protected void buildGrid() {
        for (int i = 0; i < length(); i++) {
            for(int j = 0; j < length(); j++){
                add((S) new Square(i, j,0, Square.READ_WRITE_DELETE));
            }
        }
    }
    protected void fill(){
        int k = 0;
        boolean flag = false;
        while(k<filled){
            for (int i =0; i < length(); i++){
                for(int j = 0; j < length(); j++){
                    // pack is always false
                    flag = pack(i, j, k);
                    if(flag){
                        k++;
                        break;
                    }
                    try{
                        throw new Exception(flag +" "+k);
                    }catch (Exception e){e.printStackTrace();}
                }
                if(flag)break;

            }
        }
    }


}
