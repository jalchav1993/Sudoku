package edu.utep.cs.cs4330.sudoku;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

import edu.utep.cs.cs4330.sudoku.adapters.SquareAdapter;
import edu.utep.cs.cs4330.sudoku.model.Board;
import edu.utep.cs.cs4330.sudoku.model.utility.grid.Square;


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
    private static final int REGION_FOUR= 4;
    private static final int REGION_THREE= 3;

    private Board board;

   // private Dictionary<GridFactory<Integer, String>, Grid> grids;{
       // grids = new ArrayList<>();
       // grids.put(new HardGrid(REGION_FOUR));
   // }
    GridView gridView;
    private List<View> squareView;
    /**/
    /** All the number buttons. */
    private List<View> numberButtons;
    /* ids*/
    private List<Integer> selected;
    //private static final List<Integer> numberIds;
    private Button newButton;

    /** Width of number buttons automatically calculated from the screen size. */
    private static int buttonWidth;

    public MainActivity() throws Exception {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView gridView = (GridView) findViewById(R.id.gridView);
        SquareAdapter adapter = new SquareAdapter(this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener((parent, v, position, id) -> Toast.makeText(
                MainActivity.this,
                "" + position,
                Toast.LENGTH_SHORT).show());

    }
    public List<Square> getGrid(){
        return board.getGrid();
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
            case R.id.action_new: create();
            case R.id.action_size: restart();
            case R.id.action_solve: solve();
            case R.id.action_hint: hint();
        }
        return super.onOptionsItemSelected(item);
    }
    /** Callback to be invoked when a number button is tapped.
     *
     * @param n Number represented by the tapped button
     *          or 0 for the delete button.
     */
    public void numberClicked(int n) {

    }
    private void create(){
        startActivity(new Intent("edu.utep.cs.cs4330.sudoku.SetupActivity"));
    }
    private void restart(){

    }
    private void solve(){

    }
    private void hint(){

    }

}