package pl.tajchert.spritzerwear;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.getbase.floatingactionbutton.AddFloatingActionButton;

import java.util.ArrayList;

import pl.tajchert.spritzerwearcommon.Story;


public class MainActivity extends ActionBarActivity {

    private RecyclerView commentsRecList;
    private RecyclerView.Adapter adapter;
    private AddFloatingActionButton fab;
    private ArrayList<Story> arrayListStories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        commentsRecList = (RecyclerView) findViewById(R.id.storyList);
        fab = (AddFloatingActionButton) findViewById(R.id.normal_plus);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });

        commentsRecList.setHasFixedSize(false);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        commentsRecList.setLayoutManager(llm);
        arrayListStories = new ArrayList<Story>();
        arrayListStories.add(new Story("titleOne", "cooooontent"));
        arrayListStories.add(new Story("titleTwo", "coooooooooontent two"));
        adapter = new AdapterComment(arrayListStories);
        commentsRecList.setAdapter(adapter);
    }

    private void showAddDialog(){
        LinearLayout layout = new LinearLayout(MainActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        TableLayout.LayoutParams params = new TableLayout.LayoutParams();
        params.setMargins(20, 20, 20, 0);

        final EditText titleBox = new EditText((MainActivity.this));
        titleBox.setHint("Title");
        titleBox.setLayoutParams(params);
        layout.addView(titleBox);

        final EditText descriptionBox = new EditText((MainActivity.this));
        descriptionBox.setHint("Description");
        descriptionBox.setLayoutParams(params);
        layout.addView(descriptionBox);


        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("New Note").setView(layout).setPositiveButton("Add",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Story story = new Story(titleBox.getText().toString(), descriptionBox.getText().toString());
                        if(arrayListStories != null && adapter != null){
                            arrayListStories.add(story);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
        alert.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        new StorySender(arrayListStories, MainActivity.this).execute();
    }
}
