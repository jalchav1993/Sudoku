package edu.utep.cs.cs4330.sudoku;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.view.Menu;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

import edu.utep.cs.cs4330.sudoku.adapters.SquareAdapter;
import edu.utep.cs.cs4330.sudoku.model.Board;
import edu.utep.cs.cs4330.sudoku.model.utility.grid.Grid;
import edu.utep.cs.cs4330.sudoku.model.utility.grid.HardGrid;
import edu.utep.cs.cs4330.sudoku.model.utility.grid.SimpleGrid;
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
    private Menu menu;
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
        try {
            //board = new Board<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    /** Callback to be invoked when a number button is tapped.
     *
     * @param n Number represented by the tapped button
     *          or 0 for the delete button.
     */
    public void numberClicked(int n) {

    }

}