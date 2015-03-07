package pl.tajchert.spritzerwear;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.andrewgiang.textspritzer.lib.SpritzerTextView;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.gms.wearable.Node;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import pl.tajchert.detectwear.DetectWear;
import pl.tajchert.spritzerwear.events.EventStoryChanged;
import pl.tajchert.spritzerwearcommon.Story;
import pl.tajchert.spritzerwearcommon.StoryRealm;


public class MainActivity extends ActionBarActivity implements RealmChangeListener, DetectWear.NodesListener {

    private static final String TAG = "MainActivity";
    private RecyclerView commentsRecList;
    private RecyclerView.Adapter adapter;
    private FloatingActionButton fab;
    private ArrayList<Story> arrayListStories;
    private Realm realm;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DetectWear.setNodesListener(this);

        sharedPreferences = this.getSharedPreferences("pl.tajchert.spritzerwear", Context.MODE_PRIVATE);

        commentsRecList = (RecyclerView) findViewById(R.id.storyList);
        fab = (FloatingActionButton) findViewById(R.id.normal_plus);
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
        arrayListStories = new ArrayList<>();
        adapter = new AdapterComment(arrayListStories);
        commentsRecList.setAdapter(adapter);

    }

    private void firstRunCheck() {
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        if(isFirstRun) {
            ShowcaseView showcaseView = new ShowcaseView.Builder(this)
                    .setTarget(new ViewTarget(fab))
                    .setContentTitle("Add!")
                    .setContentText("Add new text clips here - they will be automatically synchronized with Android Wear device.")
                    .hideOnTouchOutside()
                    .setStyle(R.style.CustomShowcaseTheme)
                    .singleShot(234342)
                    .build();
            showcaseView.hideButton();
            showcaseView.show();

            createStory("Sample note", MainActivity.this.getResources().getString(R.string.samuel_speech));
            readStories();

            sharedPreferences.edit().putBoolean("isFirstRun", false).apply();
        }
    }

    public void onEvent(EventStoryChanged storyChanged) {
        if(storyChanged.eventType == EventStoryChanged.EventType.Deleted){
            showDeleteConfirmationDialog(storyChanged.title);
        } else if(storyChanged.eventType == EventStoryChanged.EventType.Test){
            StoryRealm storyRealm = realm.where(StoryRealm.class).equalTo("title", storyChanged.title).findFirst();
            showTestDialog(storyRealm, MainActivity.this);
        }
    }

    private void deleteStory(String title){
        realm.beginTransaction();
        realm.where(StoryRealm.class).equalTo("title", title).findFirst().removeFromRealm();
        realm.commitTransaction();
        readStories();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        realm = Realm.getInstance(this);
        realm.addChangeListener(this);

        firstRunCheck();
        readStories();
    }

    @Override
    protected void onStop() {
        if(realm != null) {
            realm.removeAllChangeListeners();
            realm.close();
        }
        super.onStop();
    }

    private void showTestDialog(StoryRealm story, Context context){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_spritz);
        dialog.setTitle(story.getTitle());

        SpritzerTextView text = (SpritzerTextView) dialog.findViewById(R.id.spritzTV);
        text.setSpritzText(story.getContent());
        text.play();

        Button dialogButton = (Button) dialog.findViewById(R.id.buttonClose);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
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
                        createStory(titleBox.getText().toString(), descriptionBox.getText().toString());
                        readStories();
                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
        alert.show();
    }

    private void showDeleteConfirmationDialog(final String storyTitle){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete").setMessage("Do you want to delete " + storyTitle +"?").setPositiveButton("Delete",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        deleteStory(storyTitle);
                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
        alert.show();
    }

    private void showNoWearDevice(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("No Android Wear!").setMessage("No Android Wear device detected, for best experience you need smartwatch with Android Wear.").setPositiveButton("Ok, I understand",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
        alert.show();
    }

    private void readStories(){
        RealmQuery<StoryRealm> query = realm.where(StoryRealm.class);
        RealmResults<StoryRealm> resultAllStories = query.findAll();
        arrayListStories = new ArrayList<>();
        for(StoryRealm storyRealm : resultAllStories){
            arrayListStories.add(new Story(storyRealm));
        }
        adapter = new AdapterComment(arrayListStories);
        commentsRecList.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
    }

    private void createStory(String title, String content){
        realm.beginTransaction();
        StoryRealm storyRealm = realm.createObject(StoryRealm.class);
        storyRealm.setContent(content);
        storyRealm.setTitle(title);
        realm.commitTransaction();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //new StorySender(arrayListStories, MainActivity.this).execute();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onChange() {
        FileSender.syncRealm(MainActivity.this);
    }

    @Override
    public void nodesChanged(ArrayList<Node> nodes) {

    }

    @Override
    public void onNoConnectedNode() {
        if(sharedPreferences == null) {
            return;
        }
        boolean isFirstTimeWarningWear = sharedPreferences.getBoolean("isFirstTimeWarningWear", true);
        if(isFirstTimeWarningWear){
            showNoWearDevice();
            sharedPreferences.edit().putBoolean("isFirstTimeWarningWear", false).apply();
        }
    }

    @Override
    public void onNewConnectedNode(Node node) {

    }
}
