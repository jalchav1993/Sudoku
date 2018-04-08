
package edu.utep.cs.cs4330.sudoku;
/**
 * Thanks to Anupam Chugh for the tutorial
 * https://www.journaldev.com/9942/android-expandablelistview-example-tutorial
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;
import edu.utep.cs.cs4330.sudoku.adapter.OptionsExpandableListAdapter;

public class SetupActivity extends AppCompatActivity {
    ExpandableListView optionsListView;
    ExpandableListAdapter optionsListAdapter;
    List<String> optionsListTitle;
    HashMap<String, List<String>> optionListDetail;
    private final static String difficulty_list_title = "Select Difficulty";
    private final static String size_list_title = "Select Size";
    private final static String list_difficulty_easy = "Easy";
    private final static String list_difficulty_medium= "Medium";
    private final static String list_difficulty_hard = "Hard";
    private final static String list_size_four="4 X 4";
    private final static String list_size_nine = "9 X 9";
    private TextView difficulty, size;
    private Button start;
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        optionsListView = (ExpandableListView) findViewById(R.id.optionsListView);
        optionListDetail = buildListDetail();
        optionsListTitle = new ArrayList<String>(optionListDetail.keySet());
        optionsListAdapter = new OptionsExpandableListAdapter(this, optionsListTitle, optionListDetail);
        optionsListView.setAdapter(optionsListAdapter);
        difficulty = findViewById(R.id.difficulty);
        size = findViewById(R.id.size);
        //start = findViewById(R.id.start);
        //start.setOnClickListener(e -> newClicked());
        optionsListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        optionsListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });
        optionsListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        optionsListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });
        optionsListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view,
                                        int groupPosition, int childPosition,
                                        long id) {
                String group = optionsListTitle.get(groupPosition);
                String selected = optionListDetail.get(
                        optionsListTitle.get(groupPosition)).get(
                        childPosition);
                Toast.makeText(getApplicationContext(),
                        optionsListTitle.get(groupPosition)
                                + " -> "
                                + optionListDetail.get(
                                optionsListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                int index = expandableListView.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
                expandableListView.setItemChecked(index, true);
                if(group.equals(difficulty_list_title)){
                    difficulty.setText(selected);
                } else if(group.equals(size_list_title)){
                    size.setText(selected);
                }
                return false;
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        android.util.Log.d("try", item.getItemId()+" ");
        switch (item.getItemId())  {
            case android.R.id.home:{
                //hate cases
                Intent result = new Intent();
                if(difficulty.getText().equals(list_difficulty_easy)){
                    if(size.getText().equals(list_size_nine)){
                        result.putExtra("setup", MainActivity.EASY_REGION_THREE);
                    }else if(size.getText().equals(list_size_four)){
                        result.putExtra("setup",MainActivity.EASY_REGION_FOUR);
                    }
                }else if(difficulty.getText().equals(list_difficulty_medium)){
                    if(size.getText().equals(list_size_nine)){
                        result.putExtra("setup",MainActivity.EASY_REGION_THREE);
                    }else if(size.getText().equals(list_size_four)){
                        result.putExtra("setup",MainActivity.EASY_REGION_FOUR);
                    }
                } else if(difficulty.getText().equals(list_difficulty_hard)){
                    if(size.getText().equals(list_size_nine)){
                        result.putExtra("setup",MainActivity.HARD_REGION_THREE);
                    }else if(size.getText().equals(list_size_four)){
                        result.putExtra("setup",MainActivity.HARD_REGION_FOUR);
                    }
                }
                setResult(RESULT_OK, result);
                finish();
                return true;
            }
        }
        return onContextItemSelected(item);
    }
    @Override
    public boolean onNavigateUp(){
        //is this necessary?
        finish();
        return super.onNavigateUp();
    }
    private static HashMap<String, List<String>> buildListDetail(){
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        List<String> difficulties = new ArrayList<String>();
        difficulties.add(list_difficulty_easy);
        difficulties.add(list_difficulty_medium);
        difficulties.add(list_difficulty_hard);
        List<String> sizes = new ArrayList<String>();
        sizes.add(list_size_nine);
        sizes.add(list_size_four);
        expandableListDetail.put(difficulty_list_title, difficulties);
        expandableListDetail.put(size_list_title, sizes);
        return expandableListDetail;
    }
}
