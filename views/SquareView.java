package edu.utep.cs.cs4330.sudoku.views;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
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
    private SquareView head, next , tail;
    private int sigma;
    public SquareView next() {
        return next;
    }
    private Paint currentPaint;

    /** To notify a square selection. */
    public interface SelectionListener {
        /** Called when a square of the square is selected by tapping.
         * @param z value to be inserted in the square where z is element of Square(x,y,z)
         */
        void onSelection(int z);
    }
    public void link(SquareView head, SquareView next, SquareView tail){
        this.head = head;
        this.next = next;
        this.tail = tail;
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
        selectedPaint.setColor(Color.RED);
        selectedPaint.setAlpha(80); // semi transparent
        selectedPaint.setStyle(Paint.Style.FILL);
    }
    /** Paint to draw the background of the keySet. */
    private final Paint shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    {
        shadowPaint.setColor(Color.RED);
        shadowPaint.setAlpha(95); // semi transparent
        shadowPaint.setStyle(Paint.Style.FILL);
    }
    /** Paint to draw the background of the keySet. */
    private final Paint separatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    {
        separatorPaint.setColor(Color.RED);
        separatorPaint.setStyle(Paint.Style.STROKE);
    }
    /** Paint to draw the numbers **/
    private final Paint numberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    {
        numberPaint.setColor(Color.BLACK);
        numberPaint.setTextSize(25);
        numberPaint.setStyle(Paint.Style.STROKE);
    }
    /** Paint to draw the squares **/
    private final Paint squarePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    {
        int squareColor = Color.rgb(201, 186, 145);
        squarePaint.setColor(squareColor);
        squarePaint.setStyle(Paint.Style.FILL);
    }
    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    {
        borderPaint.setColor(Color.BLACK);
        borderPaint.setStyle(Paint.Style.STROKE);
    }
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
    public void setSigma(int sigma){
        this.sigma = sigma;
    }
    public void shadow(){
        currentPaint = shadowPaint;
        invalidate();
    }
    public void unShadow(){
        currentPaint = squarePaint;
    }
    public void unShadowAll(List<Square> region, List<Square> row,List<Square> col, Square selected){
        SquareView i = head;
        Square compareTo = null;
        while (i != null){
            if(i != head){
                compareTo = i.square;
                if(region.contains(compareTo)||
                        row.contains(compareTo)||
                        col.contains(compareTo)){
                    i.shadow();
                    Log.d("shadow","nalways" + i.square);
                }else{
                    Log.d("shadow","always");
                    i.unShadow();
                }
            }
            i.invalidate();
            i = i.next();
        }
    }
    /** Set the square to be displayed by this view. */
    public void setSquare(Square square) {
        this.square = square;
    }

    /** Draw a 2-D graphics representation of the associated square. */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (square != null) {
            Log.d("draw", "not null");
            drawSquare(canvas);
            drawText(canvas);
            drawBorder(canvas);
        }else Log.d("draw",  "null");
    }

    /** Draw all the squares (numbers) of the associated square. */
    private void drawSquare(Canvas canvas) {
        final float maxCoord = maxCoord();
        canvas.translate(transX, transY);
        Rect r = new Rect(0, 0, (int) maxCoord, (int) maxCoord);
        if(square.isSelected())
            canvas.drawRect(r, selectedPaint);
        else canvas.drawRect(r, currentPaint);
        canvas.translate(-transX, -transY);
    }
    private void drawText(Canvas canvas){
        canvas.translate(transX, transY);
        int xPos =  canvas.getWidth() / 2 + (int)((numberPaint.descent() + numberPaint.ascent()) / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((numberPaint.descent() + numberPaint.ascent()) / 2)) ;
        if(square.get()> 0)
            canvas.drawText(square.get()+"", xPos,yPos, numberPaint);
        canvas.translate(-transX, -transY);
    }
    private void drawBorder(Canvas canvas){
        canvas.translate(transX, transY);
        int x = square.x;
        int y = square.y;
        Log.d("x, y", x+" "+y);
        canvas.drawLine(maxCoord(), 0, 0,0, borderPaint);
        canvas.drawLine(0, maxCoord(), 0,0, borderPaint);
        if(x % sigma == 0 && x >=sigma){
            canvas.drawLine(0, 0, 0, maxCoord(), separatorPaint);
        } else if(x >= sigma*sigma-1){
            canvas.drawLine(maxCoord(), 0, maxCoord(), maxCoord(), borderPaint);
        }
        if(y % sigma == 0 && y >=sigma){
            canvas.drawLine(0, 0, maxCoord(), 0, separatorPaint);
        } else if (y >=sigma*sigma-1){
            canvas.drawLine(0, maxCoord(), maxCoord(), maxCoord(), borderPaint);
        }

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
               if (!square.isSelected()){
                   deselectAll();
                   select();
               }
            } break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }
    public void deselectAll(){
        SquareView v = head;
        while(v != null){
            v.deselect();
            v =v.next;
        }
    }
    public void enableAll(){
        SquareView v = head;
        while(v != null){
            v.enable();
            v =v.next;
        }
    }
    public void disableAll(){
        SquareView v = head;
        while(v != null){
            v.disable();
            v =v.next;
        }
    }
    public void enable(){
        if(square != null){
            square.enable();
            invalidate();
        }
    }
    public void disable(){
        if(square != null){
            square.disable();
            invalidate();
        }
    }
    public void select(){
        if(square != null){
            square.select();
            invalidate();
        }
    }
    public void deselect(){
        if(square != null){
            square.deselect();
            invalidate();
        }
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


