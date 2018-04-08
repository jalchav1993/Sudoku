package edu.utep.cs.cs4330.sudoku.model.solve;

import java.util.HashSet;
import java.util.List;


import edu.utep.cs.cs4330.sudoku.model.utility.grid.Square;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 4/2/18.
 */
public class Solve {
    private final EulerSolution p096;
    private String currentBoard;
    public Solve() throws Exception {
        p096 = new p096();
        String buffer_ = p096.run(); //I hacked this haha
        if(!setBoard(buffer_)) throw new Exception("Wrong-euler-solution");
    }
    public String getBoard(){
        return currentBoard;
    }
    public boolean setBoard(String board){
        //turn list to 2d
        //validate
        //update inner ref
        int len = (int) Math.sqrt(board.length());
        char[][] boardArray = new char[len][len];
        for(int i = 0; i < len; i++){
            for (int j = 0; j < len; j++){
                int index = i * 9 + j;
                boardArray[i][j] = board.charAt(index);
                if(!isValidSudoku(boardArray)){
                    return false;
                }
            }
        }
        this.currentBoard = board;
        return true;
    }
    private boolean isValidSudoku(char[][] board) {
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(!isValid(board,i,j)){
                    return false;
                }
            }
        }
        return true;

    }
    private boolean isValid(char[][] board, int a, int b) {
        HashSet<Character> contained = new HashSet<Character>();
        // cache the occurrence and return false in case of a duplicate
        for (int i = 0; i < 9; i++) {
            if (contained.contains(board[a][i])) {
                return false;
            }
            if (board[a][i] > '0' && board[a][i] <= '9') {
                contained.add(board[a][i]);
            }
        }



        contained.clear();
        for (int i = 0; i < 9; i++) {
            if (contained.contains(board[i][b])) {
                return false;
            }
            if (board[i][b] > '0' && board[i][b] <= '9') {
                contained.add(board[i][b]);
            }
        }

        // Check sub-box board[a][b] located.
        contained.clear();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int x = a / 3 * 3 + i, y = b / 3 * 3 + j;
                if (contained.contains(board[x][y])) {
                    return false;
                }

                if (board[x][y] > '0' && board[x][y] <= '9') {
                    contained.add(board[x][y]);
                }
            }
        }
        return true;
    }
}
