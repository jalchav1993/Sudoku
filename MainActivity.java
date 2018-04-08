package edu.utep.cs.cs4330.sudoku;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.Menu;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;
import edu.utep.cs.cs4330.sudoku.model.Board;
import edu.utep.cs.cs4330.sudoku.model.utility.grid.HardGrid;
import edu.utep.cs.cs4330.sudoku.model.utility.grid.NormalGrid;
import edu.utep.cs.cs4330.sudoku.model.utility.grid.SimpleGrid;
import edu.utep.cs.cs4330.sudoku.model.utility.grid.Square;
import edu.utep.cs.cs4330.sudoku.model.utility.grid.Grid;
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

    public static final String EASY_REGION_THREE = "0";
    public static final String EASY_REGION_FOUR = "1";
    public static final String MEDIUM_REGION_THREE = "2";
    public static final String MEDIUM_REGION_FOUR = "3";
    public static final String HARD_REGION_THREE = "4";
    public static final String HARD_REGION_FOUR = "5";

    private static final int[] numberIds = new int[] {
            R.id.n0, R.id.n1, R.id.n2, R.id.n3, R.id.n4,
            R.id.n5, R.id.n6, R.id.n7, R.id.n8, R.id.n9
    };
   // private Dictionary<GridFactory<Integer, String>, Grid> grids;{
       // grids = new ArrayList<>();
       // grids.put(new HardGrid(REGION_FOUR));
   // }
    GridLayout gridLayout;
    SquareView head, tail;
    int h, w;
    private List<View> squareView;
    /**/
    private Board<Square> board;
    /** All the number buttons. */
    private List<View> numberButtons;
    /* ids*/
    private int selected = -1;
    //private static final List<Integer> numberIds;
    private Button newButton;
    private int length,c = 0, r =0;
    private String difficulty;
    /** Width of number buttons automatically calculated from the screen size. */
    private static int buttonWidth;
    private Square current, next;
    private boolean madeSelection = false;
    public MainActivity() throws Exception {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridLayout = findViewById(R.id.grid_layout);
        gridLayout.setVisibility(View.VISIBLE);
        numberButtons = new ArrayList<>();
        gridLayout.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                h = gridLayout.getHeight();
                w = gridLayout.getWidth();
            }
        });
        for (int i = 0; i < numberIds.length; i++) {
            final int number = i; // 0 for delete button
            View button = findViewById(numberIds[i]);
            button.setOnClickListener(e -> numberClicked(number));
            numberButtons.add(button);
            setButtonWidth(button);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        assert menu.hasVisibleItems();
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_new: create(); break;
            case R.id.action_p2p: createP2Pwifi(); break;
            case R.id.action_solve: solve(); break;
            case R.id.action_hint: hint(); break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void createP2PblueToth(){

    }
    private void createP2PwifiDirect(){

    }
    private void createP2Pwifi() {
        Intent intent = new Intent("edu.utep.cs.cs4330.sudoku.JoinActivity");
        startActivityForResult(intent, 2);
    }
    /** Callback to be invoked when a number button is tapped.
     *
     * @param n Number represented by the tapped button
     *          or 0 for the delete button.
     */
    public void numberClicked(int n) {
        int number = n, x, y;
        Square select = null;
        // here you get the current selected square view
        for(Square s: board.grid){
            if(s.isSelected()){
                select = s;
                break;
            }
        }
        if(select != null){
            x = select.x;
            y =select.y;
            board.put(x, y, n);
            Log.d("selected ok", select.toString());
        }
        SquareView current = head.next();
        while(current!= null){
            current.invalidate();
            current = current.next();
        }
        gridLayout.invalidate();
    }
    private void create(){
        Intent intent = new Intent("edu.utep.cs.cs4330.sudoku.SetupActivity");
        startActivityForResult(intent, 1);
        //startActivity(intent);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        Bundle bundle;
        String selected = "";
        if (requestCode == 1 && resultCode == RESULT_OK) {
            bundle = result.getExtras();
            assert bundle != null;
            selected = bundle.getString("setup");
            initVars(selected);
            start();
        }

    }
    private void initVars(String selector) {
        int index = 0;
        gridLayout.setVisibility(View.VISIBLE);
        try {
            if (selector.equals(EASY_REGION_THREE)) {
                board = new Board<>(9, new SimpleGrid<>(9));
                c = r= 9;
            } else if (selector.equals(EASY_REGION_FOUR)) {
                board = new Board<>(4, new SimpleGrid<>(4));
                c = r= 4;
            } else if (selector.equals(MEDIUM_REGION_THREE)) {
                board = new Board<>(9, new SimpleGrid<>(9));
                c = r= 9;
            } else if (selector.equals(MEDIUM_REGION_FOUR)) {
                board = new Board<>(4, new SimpleGrid<>(4));
                c = r= 4;
            } else if (selector.equals(HARD_REGION_THREE)) {
                board = new Board<>(9, new HardGrid<>(9));
                c = r= 9;
            } else if (selector.equals(HARD_REGION_FOUR)) {
                board = new Board<>(4, new HardGrid<>(4));
                c = r= 4;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        gridLayout.removeAllViews();
        gridLayout.setColumnCount(c);
        gridLayout.setRowCount(r);
        head = new SquareView(this);
        tail = new SquareView(this);
        SquareView next = new SquareView(this);
        head.setSquare(board.grid.get(0));
        tail.setSquare(board.grid.get(((Grid) board.grid).length() - 1));
        head.link(head, next, tail);
        tail.link(head, next, tail);
        int sigma = ((Grid) board.grid).length();
        sigma = (int) Math.sqrt(sigma);
        for(int i = 0; i < board.grid.size(); i++){
            Square current = board.grid.get(i);
            next.setSquare(current);
            next.setSigma(sigma);
            next.link(head, new SquareView(this), tail);
            index = ((Grid )board.grid).getLinearIndex(current.x, current.y);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout
                    .LayoutParams.MATCH_PARENT);
            params.rightMargin = 7;
            params.topMargin = 7;
            params.height = w/c;
            params.width = w/c;
            next.setLayoutParams(params);
            gridLayout.addView(next, index);
            next.invalidate();
            next = next.next();
        }
        gridLayout.invalidate();
        try {
            board.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void start(){
        new Thread(){
            public void run(){
                while(board.getStatus().equals(Board.RUNNING_)){
                    Square select = null;
                    List<Boolean> available = null;
                    int i =0;
                    // here you get the current selected square view
                    for(Square s: board.grid){
                        if(s.isSelected()){
                            select = s;
                            break;
                        }
                    }
                    if(select != null){
                        available= ((Grid<Square>)board.grid).getAvailable(select);
                        Log.d("avail, ", available.toString());
                        for(View button: numberButtons){
                            i = numberButtons.indexOf(button);
                            if(i > 0){
                                final int visibility = available.get(i)? View.VISIBLE:View.INVISIBLE;
                                runOnUiThread(()->{
                                    button.setVisibility(visibility);
                                    button.invalidate();
                                });
                                Log.d("avail, ", visibility+"");
                            }
                        }
                        List<List<Square>> shadowSet = ((Grid<Square>)board.grid).getShadowSet(select);
                        Square finalSelect = select;
                        runOnUiThread(()->{
                            final Square s = finalSelect;
                            head.unShadowAll(shadowSet.get(0), shadowSet.get(1), shadowSet.get(2), finalSelect);
                        });
                    }
                    try{
                        sleep(10);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    private void solve(){
        
    }
    private void hint(){
        
    }

    /** Set the width of the given button calculated from the screen size. */
    private void setButtonWidth(View view) {
        if (buttonWidth == 0) {
            final int distance = 2;
            int screen = getResources().getDisplayMetrics().widthPixels;
            buttonWidth = (screen - ((9 + 1) * distance)) / 9; // 9 (1-9)  buttons in a row
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = buttonWidth;
        view.setLayoutParams(params);
    }
   
}