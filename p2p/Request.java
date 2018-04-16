package edu.utep.cs.cs4330.sudoku.p2p;
/**
 * @author: Jesus Chavez
 * @macuser: aex on 4/9/18.
 */
public class Request {
    final public NetworkAdapter.MessageType type;
    final public int x;
    final public int y;
    final public int z;
    final public boolean response;
    final public int[] board;
    public Request(NetworkAdapter.MessageType type, int x, int y, int z, boolean response, int[] board){
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.response = response;
        this.board = board;
    }
    public Request(NetworkAdapter.MessageType type, int x, int y, int z){
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        response = false;
        board = new int[]{};
    }
    public Request(NetworkAdapter.MessageType type){
        this.type = type;
        x = 0;
        y = 0;
        z = 0;
        response = false;
        board = new int[]{};
    }
    public Request(NetworkAdapter.MessageType type, int [] others){
        this.type = type;
        x = 0;
        y = 0;
        z = 0;
        response = false;
        board = others;
    }
    public Request(NetworkAdapter.MessageType type, int x, int y, int z, int[] others) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.response = false;
        this.board = new int[]{};
    }
}