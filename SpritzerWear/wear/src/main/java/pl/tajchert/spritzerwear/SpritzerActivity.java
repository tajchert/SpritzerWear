package pl.tajchert.spritzerwear;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.andrewgiang.textspritzer.lib.Spritzer;
import com.andrewgiang.textspritzer.lib.SpritzerTextView;


public class SpritzerActivity extends ActionBarActivity {
    private SpritzerTextView spritzerTV;
    private LinearLayout linearLayout;
    private boolean isPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spritzer);
        spritzerTV = (SpritzerTextView) findViewById(R.id.spritzTV);
        linearLayout = (LinearLayout) findViewById(R.id.layoutMain);
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(isPlaying){
                    spritzerTV.pause();
                } else {
                    spritzerTV.play();
                }
                return false;
            }
        });
        spritzerTV.setSpritzText("add the spritz text here");
        setSpritzer();
    }

    private void setSpritzer() {
        spritzerTV.setOnClickControlListener(new SpritzerTextView.OnClickControlListener() {
            @Override
            public void onPause() {
                isPlaying = false;
            }

            @Override
            public void onPlay() {
                isPlaying = true;
            }
        });
        spritzerTV.setOnCompletionListener(new Spritzer.OnCompletionListener() {
            @Override
            public void onComplete() {
                isPlaying = false;
            }
        });
    }

    public void setStoryContent(String content){
        if(spritzerTV != null){
            spritzerTV.setSpritzText(content);
        }
    }
}
