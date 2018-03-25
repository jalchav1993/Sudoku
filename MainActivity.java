package edu.utep.cs.cs4330.sudoku;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import edu.utep.cs.cs4330.BoardView;
import edu.utep.cs.cs4330.sudoku.model.Board;
import edu.utep.cs.cs4330.sudoku.model.HardGrid;
import edu.utep.cs.cs4330.sudoku.model.Square;

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

    private Board board;
    private int[] selected;

    private BoardView boardView;

    /** All the number buttons. */
    private List<View> numberButtons;
    private static final int[] numberIds = new int[] {
            R.id.n0, R.id.n1, R.id.n2, R.id.n3, R.id.n4,
            R.id.n5, R.id.n6, R.id.n7, R.id.n8, R.id.n9
    };
    private Button newButton;
    /** Width of number buttons automatically calculated from the screen size. */
    private static int buttonWidth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            board = new Board<Square>(9, new HardGrid<>(9, 17));
        } catch (Exception e) {
            e.printStackTrace();
        }
        boardView = findViewById(R.id.boardView);
        boardView.setBoard(board);
        boardView.addSelectionListener(this::squareSelected);
        newButton = findViewById(R.id.new_button);
        newButton.setOnClickListener(e -> newClicked(boardView));
        numberButtons = new ArrayList<>(numberIds.length);
        for (int i = 0; i < numberIds.length; i++) {
            final int number = i; // 0 for delete button
            View button = findViewById(numberIds[i]);
            button.setOnClickListener(e -> numberClicked(number));
            numberButtons.add(button);
            setButtonWidth(button);
        }
    }

    /** Callback to be invoked when the new button is tapped. */
    public void newClicked(View view) {
        toast("New clicked.");
        //create thread
        new Thread(() -> {
            runOnUiThread(()->{
                boardView.invalidate(); /* for a new game */
            });
            while (board.getStatus().equals("running")) {
                Log.d("solving", "solving");
                try {
                    if(!board.getStatus().equals("won")) {
                        runOnUiThread(()->{
                            toast("game over: win");
                        });
                        Thread.sleep(5000);
                        board.start();
                        runOnUiThread(()->{
                            boardView.postInvalidate();
                        });
                    }
                    Thread.sleep(5);
                } catch (InterruptedException e) {e.printStackTrace();}
            }
        }).start();
    }
    /** Callback to be invoked when a number button is tapped.
     *
     * @param n Number represented by the tapped button
     *          or 0 for the delete button.
     */
    public void numberClicked(int n) {
        String out;
        boolean result;
        if(selected[0] == -1 || selected[1] == -1 ){
            toast("incorrect move");
            android.util.Log.d("numberClicked", "incorrect move, null");
        }
        android.util.Log.d("numberClicked", selected[0] +" "+selected[1]);
        result = board.put(selected[0],selected[1], n);
        out = result ? "correct move": "incorrect move";
        runOnUiThread(()->{
            boardView.postInvalidate();
            toast(out);
            Log.d("invalidating", "invalidating");
        });
    }

    /**
     * Callback to be invoked when a square is selected in the board view.
     *
     * @param x 0-based column index of the selected square.
     * @param x 0-based row index of the selected square.
     */
    private void squareSelected(int x, int y) {
        boolean valid = board.isValidSpace(x,y);
        String out = valid ? "correct selection Square selected: (%d, %d)":
                "incorrect selection Square selected: (%d, %d)";
        toast(String.format(out, x, y));
        if(valid)
            selected = new int[] {x,y};
        android.util.Log.d("squareSelected", selected[0] +" "+selected[1]+" "+valid);

    }

    /** Show a toast message. */
    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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