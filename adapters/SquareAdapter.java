package edu.utep.cs.cs4330.sudoku.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.GridView;
import java.util.List;
import edu.utep.cs.cs4330.sudoku.MainActivity;
import edu.utep.cs.cs4330.sudoku.model.utility.grid.Square;
import edu.utep.cs.cs4330.sudoku.model.utility.grid.Grid;

import edu.utep.cs.cs4330.sudoku.views.SquareView;
import edu.utep.cs.cs4330.sudoku.R;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 3/25/18.
 */

public class SquareAdapter extends BaseAdapter {
    private Context mContext;
    private List<Square> grid;
    public SquareAdapter(Context c) {
        mContext = c;
        grid = ((MainActivity) mContext).getGrid();
    }

    public int getCount() {
        return grid.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        SquareView squareView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            squareView = new SquareView(mContext);
            squareView.setLayoutParams(new GridView.LayoutParams(65, 65));
            squareView.setPadding(8, 8, 8, 8);
        } else {
            squareView = (SquareView) convertView;
        }
        squareView = (SquareView) squareView;
        return squareView;
    }

}
