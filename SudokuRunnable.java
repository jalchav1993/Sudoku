package edu.utep.cs.cs4330.sudoku;

import edu.utep.cs.cs4330.sudoku.model.*;
/**
 * @author: Jesus Chavez
 * @macuser: aex on 3/4/18.
 */

public class SudokuRunnable extends Thread{
    private Board current;
    private final BoardView boardView;
    private final long timelapse;
    public SudokuRunnable(BoardView boardView, long timelapse){
        this.timelapse = timelapse;
        this.boardView = boardView;
    }
//    @Override
//    public void run(){
//        while (true){
//            try {
//                create();
//                while (!current.solve()){
//                    sleep(timelapse);
//                    update();
//                }
//                score();
//                delete();
//                sleep(timelapse);
//            }catch (InterruptedException ex){}
//
//        }
//    }
//    public synchronized void create(){
//        current = new SimpleGrid(9);
//        boardView.setBoard((SimpleGrid) current);
//        update();
//    }
//    public synchronized void update(){
//        boardView.invalidate();
//    }
//    public void score(){
//
//    }
//    public void delete(){
//        current = null;
//    }
}
