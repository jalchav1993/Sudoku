package edu.utep.cs.cs4330.sudoku;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.FrameLayout;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.utep.cs.cs4330.sudoku.model.utility.grid.Grid;
import edu.utep.cs.cs4330.sudoku.model.utility.grid.Square;
import edu.utep.cs.cs4330.sudoku.p2p.NetworkAdapter;
import edu.utep.cs.cs4330.sudoku.p2p.Request;
import edu.utep.cs.cs4330.sudoku.views.SquareView;

/**
 * HW1 template for developing an app to play simple Sudoku games.
 * You need to write code for three callback methods:
 * newClicked(), numberClicked(int) and selected(int,int).
 * Feel free to improved the given UI or design your own.
 *
 * <p>
 *  This template uses Java 8 notations. Enable Java 8 for your project
 *  by adding the following two lines to build.gradle (Module: app).
 * </p>
 *
 * <pre>
 *  compileOptions {
 *  sourceCompatibility JavaVersion.VERSION_1_8
 *  targetCompatibility JavaVersion.VERSION_1_8
 *  }
 * </pre>
 *
 * @author Yoonsik Cheon
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Configuration settings
     */
    public enum Config{
        EASY_REGION_NINE(new Tuple(9, 17)),
        EASY_REGION_FOUR(new Tuple(4, 1)),
        MEDIUM_REGION_NINE(new Tuple(9, 40)),
        MEDIUM_REGION_FOUR(new Tuple(4, 2)),
        HARD_REGION_NINE(new Tuple(9, 65)),
        HARD_REGION_FOUR(new Tuple(4, 3)),
        NETWORK_REGION_NINE(new Tuple(9, 81));
        Tuple t;
        Config(Tuple t) {
            this.t = t;
        }
    }
    /**
     * Session, local, joined, or shared
     */
    public enum Session{
        JOIN("join"),
        SHARED("shared"),
        LOCAL("local");
        String s;
        Session(String s) {
            this.s = s;
        }
    }
    /**
     * number Ids
     */
    private static final int[] numberIds = new int[] {
            R.id.n0, R.id.n1, R.id.n2, R.id.n3, R.id.n4,
            R.id.n5, R.id.n6, R.id.n7, R.id.n8, R.id.n9
    };
    /**
     * Workers, independent threads
     */
    private Thread localWorker, squareWorker, hostWorker, peerWorker;
    /**
     * Layout assigned programmatically
     */
    private GridLayout gridLayout;
    /**
     * Linked list of all squares
     */
    private SquareView head, tail;
    /**
     * height and width of board
     */
    private int height, width;
    /**
     * List of squares
     */
    private List<Square> squares;
    /** All the number buttons. */
    private List<View> numberButtons;

    /**
     * Local, Join, or shared
     * To specify the type of game, network or local
     */
    private Session session;

    /** instance */
    public MainActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridLayout = findViewById(R.id.grid_layout);
        gridLayout.setVisibility(View.VISIBLE);
        numberButtons = new ArrayList<>();
        gridLayout.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            height = gridLayout.getHeight();
            width = gridLayout.getWidth();
        });
        for (int i = 0; i < numberIds.length; i++) {
            final int number = i; // 0 for delete button
            View button = findViewById(numberIds[i]);
            button.setOnClickListener(e -> numberClicked(number));
            numberButtons.add(button);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_new: create(); break;
            case R.id.action_share: share(); break;
            case R.id.action_join: join(); break;
            case R.id.action_solve: solve(); break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        Bundle bundle;
        Config selected;
        bundle = result.getExtras();
        String address;
        int port;
        /* must assert not null but dalvik does not allow assertions*/
        if (requestCode == 1 && resultCode == RESULT_OK && bundle != null) {
            if((selected = (Config) bundle.get("setup"))!=null){
                Log.d("starting workers", "true");
                session = Session.LOCAL;
                setSquares(selected);
                buildUI(selected);
                localWorker = new LocalWorker();
                squareWorker = new SquareWorker();
                localWorker.start();
                squareWorker.start();
            }
        }
        if (requestCode == 2 && resultCode == RESULT_OK && bundle != null) {
            session = Session.JOIN;
            address = bundle.getString("address");
            port = Integer.parseInt(bundle.getString("port"));
            Log.d("__joining", address +" "+port);
            session = Session.JOIN;
            peerWorker = new PeerWorker(address, port);
            peerWorker.start();
            ((PeerWorker)peerWorker).setReq(new Request(NetworkAdapter.MessageType.JOIN));

        }
        if (requestCode == 3 && resultCode == RESULT_OK && bundle != null) {
            session = Session.SHARED;
            hostWorker = new HostWorker();
            hostWorker.start();
        }
    }


    /**
     * Starts a new game for a specified int array board, checks if a game has been initialized
     * @param len length of one side of the board
     * @param others the actual board
     */
    private void joinGame(int len, int[] others){
        int dropout = Config.NETWORK_REGION_NINE.t.dropout();
        int x, y, z;
        int count = 1;
        if(squares !=null){
            ((Grid) squares).dropAll();
        }else{
            Log.d("building", "all");
            setSquares(Config.NETWORK_REGION_NINE);
            for(int i = 0; i < others.length-2;i+=4){
                x = others[i];
                y = others[i+1];
                z = others[i+2];
                ((Grid)squares).pack(x, y, z);
                count++;
            }
            buildUI(Config.NETWORK_REGION_NINE);
            localWorker = new LocalWorker();
            squareWorker = new SquareWorker();
            localWorker.start();
            squareWorker.start();
        }

    }

    /**
     * Sets the List Squares. Supertype? of Grid.
     * @param config configuration tuple
     * @see edu.utep.cs.cs4330.sudoku.model.utility.grid.Grid
     * @see edu.utep.cs.cs4330.sudoku.Tuple
     */
    private void setSquares(Config config){
        squares = new Grid<>(config.t.len(), config.t.dropout());
    }

    /**
     * Builds a ui to specified column and width
     * @param config configuration tuple
     * @see edu.utep.cs.cs4330.sudoku.Tuple
     */
    private void buildUI(Config config){
        int index;
        gridLayout.removeAllViews();
        gridLayout.setColumnCount(config.t.len());
        gridLayout.setRowCount(config.t.len());
        head = new SquareView(this);
        tail = new SquareView(this);
        SquareView next = new SquareView(this);
        head.setSquare(squares.get(0));
        tail.setSquare(squares.get(((Grid) squares).length() - 1));
        head.link(head, next, tail);
        tail.link(head, next, tail);
        int sigma = ((Grid) squares).length();
        sigma = (int) Math.sqrt(sigma);
        for(int i = 0; i < squares.size(); i++){
            Square current = squares.get(i);
            next.setSquare(current);
            next.setSigma(sigma);
            next.link(head, new SquareView(this), tail);
            index = ((Grid ) squares).getLinearIndex(current.x, current.y);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout
                    .LayoutParams.MATCH_PARENT);
            params.rightMargin = 7;
            params.topMargin = 7;
            params.height = width/config.t.len();
            params.width = width/config.t.len();
            next.setLayoutParams(params);
            gridLayout.addView(next, index);
            next = next.next();
        }
        gridLayout.invalidate();
    }
    /**
     * Called by number clicked listener
     * @param number num clicked
     */
    private void numberClicked(int number) {
        int  x, y;
        Request req;
        Square select = null;
        for(Square s: squares){
            if(s.isUserSelected()){
                select = s;
                break;
            }
        }
        if(select != null){
            x = select.x;
            y =select.y;
            if(((Grid<Square>) squares).pack(x, y, number)){
                switch (session){
                    case JOIN:case SHARED: {
                        req = new Request(NetworkAdapter.MessageType.FILL, x, y, number);
                        ((PeerWorker) peerWorker).setReq(req);
                        break;
                    }
                    case LOCAL:{
                        break;
                    }
                }
            }
        }

        SquareView current = head.next();
        while(current!= null){
            current.invalidate();
            current = current.next();
        }
        gridLayout.invalidate();
    }
    /**
     * Creates a new game
     */
    private void create(){
        Intent intent = new Intent("edu.utep.cs.cs4330.sudoku.GameSetupActivity");
        startActivityForResult(intent, 1);
    }
    /**
     * Join a game hosted by another client, starts activity to set up connection
     */
    private void join(){
        Intent intent = new Intent("edu.utep.cs.cs4330.sudoku.PeerSetupActivity");
        intent.putExtra("session", "join");
        startActivityForResult(intent, 2);
    }
    /**
     * Share a game hosted by another client, starts activity to set up connection
     */
    private void share() {
        Intent intent = new Intent("edu.utep.cs.cs4330.sudoku.HostSetupActivity");
        intent.putExtra("session", "share");
        startActivityForResult(intent, 3);
    }
    /**
     * Solves the game
     */
    private void solve(){
        Log.d("solv", ((Grid)squares).solve() +" ");
    }
    public class LocalWorker extends Thread{
        Square userSelect, networkSelect;
        int i;
        public void run(){
            while(true){
                synchronized (this){
                    try {
                        wait();
                    } catch (InterruptedException e) {
                            e.printStackTrace();
                    }
                }
                Log.d("pass>?","");
                if(userSelect!= null && ((Grid<Square>)squares).get(userSelect.x, userSelect.y).get() == 0){
                    runOnUiThread(()->{
                        SquareView v = head.next();
                        Square s;
                        List<Square> region = ((Grid<Square>)squares).getRegionShadowSet(userSelect);
                        List<Square> col = ((Grid<Square>)squares).getColShadowSet(userSelect);
                        List<Square> row = ((Grid<Square>)squares).getRowShadowSet(userSelect);
                        List<Boolean> available = ((Grid<Square>)squares).getSelected(userSelect);
                        while (v != null &&(s = v.getSquare())!=null) {
                            if (region.contains(s) ||
                                    row.contains(s) ||
                                    col.contains(s)) {
                                s.shadowSelect();
                            } else {
                                s.shadowDeSelect();
                            }
                            v = v.next();
                        }
                        for (View button : numberButtons) {
                            i = numberButtons.indexOf(button);
                            if (i > 0) {
                                final int visibility = available.get(i) ? View.VISIBLE : View.INVISIBLE;
                                button.setVisibility(visibility);
                                button.invalidate();

                            }
                        }
                        head.invalidateALL();
                    });
                } else if(userSelect!= null && ((Grid<Square>)squares).get(userSelect.x, userSelect.y).get() > 0){
                    runOnUiThread(()->{
                        SquareView v = head.next();
                        Square s;
                        while (v!= null&&(s = v.getSquare())!=null){
                            if(s.equals(userSelect)){
                                Log.d("placing select", s.toString());
                                s.placeSelect();
                            }else{
                                s.placeDeSelect();
                            }
                            s.userDeselect();
                            s.shadowDeSelect();
                            v.invalidate();
                            v = v.next();
                        }

                        for (View button : numberButtons) {
                            i = numberButtons.indexOf(button);
                            if (i > 0) {
                                button.setVisibility(View.VISIBLE);
                                button.invalidate();
                            }
                        }
                    });
                } else if(networkSelect!= null && ((Grid<Square>)squares).get(networkSelect.x, networkSelect.y).get() > 0){
                    runOnUiThread(()->{
                        SquareView v = head.next();
                        Square s;
                        while (v!= null&&(s = v.getSquare())!=null){
                            if(s.equals(networkSelect)){
                                Log.d("placing net select", s.toString());
                                s.networkSelect();
                            }else{
                                s.networkDeselect();
                            }
                            s.userDeselect();
                            s.shadowDeSelect();
                            v.invalidate();
                            v = v.next();
                        }

                        for (View button : numberButtons) {
                            i = numberButtons.indexOf(button);
                            if (i > 0) {
                                button.setVisibility(View.VISIBLE);
                                button.invalidate();
                            }
                        }
                    });
                }
            }

        }
        public synchronized void setNetworkSelect(Square select){
            networkSelect = select;
            userSelect = null;
            notify();
        }
        public synchronized void setUserSelect(Square select){
            userSelect = select;
            networkSelect = null;
            notify();
        }
    }
    public class SquareWorker extends Thread{
        public void run(){
            while(true){
                for (Square s: squares){
                    if(s.isUserSelected()){
                        ((LocalWorker)localWorker).setUserSelect(s);
                    }
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public class HostWorker extends Thread{
        //TODO implement the share worker
        //should start a server socket and listen for connections
    }
    public class PeerWorker extends Thread{
        private NetworkAdapter adapter;
        private Socket socket;
        private Request req;
        private String address;
        private int port;
        PeerWorker(Socket socket){
            this.socket =socket;
        }
        PeerWorker(String address, int port){
            Log.d("__creating", address+" "+port);
            this.address = address;
            this.port = port;
        }
        @Override
        public void run() {
            if(address !=null){
                Log.d("__connecting", address+" "+port);
                this.socket =new Socket();
                try {
                    socket.connect(new InetSocketAddress(address, port));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            adapter=new NetworkAdapter(socket);
            adapter.setMessageListener(new NetworkAdapter.MessageListener() {
                @Override
                public void messageReceived(NetworkAdapter.MessageType type, int x, int y, int z, int[] others) {
                    switch (type){
                        case NEW:{
                            adapter.writeNewAck(true);
                            break;
                        }
                        case FILL:{
                            if(((Grid<Square>)squares).pack(x, y, z)) {
                                adapter.writeFillAck(x, y, z);
                                runOnUiThread(()-> {
                                    Square s = ((Grid<Square>) squares).get(x, y);
                                    ((LocalWorker)localWorker).setNetworkSelect(s);
                                });
                            }
                            break;
                        }
                        case JOIN:{
                            adapter.writeJoinAck(((Grid)squares).length(), ((Grid)squares).toIntArray());
                            break;
                        }
                        case CLOSE:case QUIT:case UNKNOWN:{
                            break;
                        }
                        case NEW_ACK:{
                            break;
                        }
                        case FILL_ACK: {
                            break;
                        }
                        case JOIN_ACK:{
                            runOnUiThread(()->{
                                joinGame(y,others);
                            });
                            break;
                        }
                    }
                    req = null;
                }
            });
            while(true){
                if(req!= null){
                    Log.d("__submitting", req.type +" ");
                    new Thread(new P2PTask(adapter, req)).start();
                    adapter.receiveMessagesAsync();
                    req = null;
                }else{
                    synchronized (this) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        };
                    }
                    adapter.receiveMessagesAsync();
                }
            }
        }
        public synchronized void setReq(Request req){
            this.req = req;
            notify();
        }
    }

    public class P2PTask implements Runnable{
        NetworkAdapter adapter;
        Request req;
        public P2PTask(NetworkAdapter adapter, Request req){
            this.adapter = adapter;
            this.req = req;
        }
        @Override
        public void run() {
            switch (req.type){
                case NEW:{
                    adapter.writeNew(req.board.length, req.board);
                    break;
                }
                case FILL:{
                    adapter.writeFill(req.x, req.y, req.z);
                    break;
                }
                case JOIN:{
                    adapter.writeJoin();
                    break;
                }
                case CLOSE:case QUIT:case UNKNOWN:{
                    adapter.writeQuit();
                    break;
                }
            }
        }
    }
}
/** utility class */
class Tuple{
    /**
     * len length
     * drop dropout
     */
    private final int len, dropout;
    Tuple(int len, int dropout) {
        this.len = len;
        this.dropout = dropout;
    }
    public int len(){
        return len;
    }
    public int dropout(){
        return dropout;
    }
}