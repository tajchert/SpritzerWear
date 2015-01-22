package pl.tajchert.spritzerwear;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import pl.tajchert.spritzerwearcommon.Story;


public class MainActivity extends ActionBarActivity {

    private RecyclerView commentsRecList;
    private RecyclerView.Adapter adapter;
    //private AddFloatingActionButton fab;
    private ArrayList<Story> arrayListStories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        commentsRecList = (RecyclerView) findViewById(R.id.storyList);
        //fab = (AddFloatingActionButton) findViewById(R.id.normal_plus);

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

    @Override
    protected void onPause() {
        super.onPause();
        new StorySender(arrayListStories, MainActivity.this).execute();
    }
}
