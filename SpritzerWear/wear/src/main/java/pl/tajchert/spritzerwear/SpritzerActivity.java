package pl.tajchert.spritzerwear;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.andrewgiang.textspritzer.lib.SpritzerTextView;

import pl.tajchert.spritzerwearcommon.StoryRealm;


public class SpritzerActivity extends Activity {
    private SpritzerTextView spritzerTV;
    private LinearLayout linearLayout;
    private StoryRealm story;
    private boolean isPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spritzer);
        spritzerTV = (SpritzerTextView) findViewById(R.id.spritzTV);
        spritzerTV.setWpm(400);
        if(ShapeWear.getShape() != ShapeWear.ScreenShape.RECTANGLE){
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) spritzerTV.getLayoutParams();
            mlp.setMargins(5, 0, 0, 0);
        }
        linearLayout = (LinearLayout) findViewById(R.id.layoutMain);
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(isPlaying){
                    spritzerTV.pause();
                    isPlaying = false;
                } else {
                    spritzerTV.play();
                    isPlaying = true;
                }
                return false;
            }
        });
        spritzerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlaying){
                    spritzerTV.pause();
                    isPlaying = false;
                } else {
                    spritzerTV.play();
                    isPlaying = true;
                }
            }
        });
        String storyTitle = getIntent().getExtras().getString("title", "");
        if(!storyTitle.equals("")){
            story = WearSpritzerApplication.getStoryRealm(storyTitle);
            spritzerTV.setSpritzText(story.getContent());
        }
    }

    private void checkFirstRun() {
        SharedPreferences sharedPreferences = SpritzerActivity.this.getSharedPreferences("pl.tajchert.spritzerwear", Context.MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        if(isFirstRun){
            Toast.makeText(SpritzerActivity.this, "Touch to play", Toast.LENGTH_LONG).show();
            sharedPreferences.edit().putBoolean("isFirstRun", false).apply();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPlaying = true;//as it doesn't work (?)
        spritzerTV.play();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPlaying = false;//as it doesn't work (?)
        spritzerTV.pause();
    }
}
