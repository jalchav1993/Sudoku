package edu.utep.cs.cs4330.sudoku.views;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

import edu.utep.cs.cs4330.sudoku.model.utility.grid.SimpleGrid;
import edu.utep.cs.cs4330.sudoku.model.utility.grid.Square;

/**
 * A special view class to display a Sudoku square modeled by the
 * {@link SimpleGrid} class. You need to write code for
 * the <code>onDraw()</code> method.
 *
 * @see SimpleGrid
 * @author cheon
 */
public class SquareView extends View {

    /** To notify a square selection. */
    public interface SelectionListener {
        /** Called when a square of the square is selected by tapping.
         * @param z value to be inserted in the square where z is element of Square(x,y,z)
         */
        void onSelection(int z);
    }

    /** Listeners to be notified when a square is selected. */
    private SelectionListener listener;

    /** SimpleGrid to be displayed by this view. */
    private Square square;
    private boolean selected = false;
    /** Width and height of each square. This is automatically calculated
     /** Width and height of each square. This is automatically calculated
     * this view's dimension is changed. */
    private float boardSize = 9;

    /** Translation of screen coordinates to display the keySet at the center. */
    private float transX;

    /** Translation of screen coordinates to display the keySet at the center. */
    private float transY;

    /** Paint to draw the background of the keySet. */
    private final Paint selectedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    {
        int boardColor = Color.rgb(255, 0, 0);
        selectedPaint.setColor(boardColor);
        selectedPaint.setAlpha(80); // semi transparent
    }
    /** Paint to draw the numbers **/
    private final Paint numberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    {
        int numberColor = Color.rgb(0, 0, 0);
        numberPaint.setColor(numberColor);
        numberPaint.setTextSize(50);
    }
    /** Paint to draw the squares **/
    private final Paint squarePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    {
        int squareColor = Color.rgb(201, 186, 145);
        squarePaint.setColor(squareColor);
    }
    private Paint currentPaint;
    /** Create a new square view to be run in the given context. */
    public SquareView(Context context) { //@cons
        this(context, null);
    }

    /** Create a new square view by inflating it from XML. */
    public SquareView(Context context, AttributeSet attrs) { //@cons
        this(context, attrs, 0);
    }

    /** Create a new instance by inflating it from XML and apply a class-specific base
     * style from a theme attribute. */
    public SquareView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setSaveEnabled(true);
        getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);
        currentPaint = squarePaint;
    }

    /** Set the square to be displayed by this view. */
    public void setSquare(Square square) {
        this.square = square;
    }

    /** Draw a 2-D graphics representation of the associated square. */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //if (square != null) {
            Log.d("draw", "not null");
            drawSquare(canvas);
        //}else Log.d("draw",  "null");
    }

    /** Draw all the squares (numbers) of the associated square. */
    private void drawSquare(Canvas canvas) {
        final float maxCoord = maxCoord();
        canvas.translate(transX, transY);
        if(square.isSelected()) currentPaint = selectedPaint;
        else currentPaint = squarePaint;
        canvas.drawRect(0, 0, maxCoord, maxCoord, currentPaint);
        canvas.drawText(square.get()+"", maxCoord/2, maxCoord/2, numberPaint);
        canvas.translate(-transX, -transY);
    }
    /** Return the number of horizontal/vertical lines. */
    private int numOfLines() { //@helper
        return (int) boardSize + 1;
    }

    /** Return the maximum screen coordinate. */
    protected float maxCoord() { //@helper
        return lineGap();
    }
    /** Overridden here to detect tapping on the square and
     * to notify the selected square if exists. */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:{
               if (!square.isSelected()) select();
               else deselect();
            } break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }
    public void select(){
        square.select();
        //currentPaint = selectedPaint;
        //square.select();
        invalidate();
    }
    public void deselect(){
        square.deselect();
        //currentPaint = squarePaint;
        //square.deselect();
        invalidate();
    }
    @Override
    public boolean performClick(){
        return  super.performClick();
    }
    /** To obtain the dimension of this view. */
    private final ViewTreeObserver.OnGlobalLayoutListener layoutListener
            =  new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            boardSize = lineGap();
            float width = Math.min(getMeasuredWidth(), getMeasuredHeight());
            transX = (getMeasuredWidth() - width) / 2f;
            transY = (getMeasuredHeight() - width) / 2f;
        }
    };

    /** Return the distance between two consecutive horizontal/vertical lines. */
    protected float lineGap() {
        return Math.min(getMeasuredWidth(), getMeasuredHeight());
    }
    public void setSelectionListener(SelectionListener listener){
        this.listener = listener;
    }
    public void removeSelectionListener() {
        this.listener = null;
    }
    private void notifySelection(int z) {
        listener.onSelection(z);
    }
    public boolean isSelected(){
        return selected;
    }
    public Square getSquare(){
        return square;
    }
}


