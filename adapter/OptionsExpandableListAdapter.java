package edu.utep.cs.cs4330.sudoku.adapter;
/**
 * @author: Jesus Chavez
 * @macuser: aex on 3/26/18.
 */

import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import edu.utep.cs.cs4330.sudoku.R;

public class OptionsExpandableListAdapter extends BaseExpandableListAdapter{
    /* Class inner fields */
    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;
    public OptionsExpandableListAdapter(Context context, List<String> expandableListTitle,
                                        HashMap<String, List<String>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }
    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return this.expandableListDetail.get(this.expandableListTitle.get(i))
                .size();
    }

    @Override
    public Object getGroup(int i) {
        return this.expandableListTitle.get(i);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return  this.expandableListDetail.get(
                this.expandableListTitle.get(listPosition)
        ).get(expandedListPosition);
    }
    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent){
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.optionsListItem);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }
    @Override
    public boolean isChildSelectable(int i, int j) {
        return true;
    }
}