package pl.tajchert.spritzerwear;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.andrewgiang.textspritzer.lib.Spritzer;
import com.andrewgiang.textspritzer.lib.SpritzerTextView;
import com.triggertrap.seekarc.SeekArc;

import pl.tajchert.spritzerwearcommon.StoryRealm;


public class SpritzerActivity extends Activity {
    private SpritzerTextView spritzerTV;
    private SeekArc seekArc;
    private SeekBar seekBar;
    private StoryRealm story;
    private boolean isPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spritzer);
        seekArc = (SeekArc) findViewById(R.id.seekArc);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        spritzerTV = (SpritzerTextView) findViewById(R.id.spritzTV);
        if(DetectShape.getShape() == DetectShape.ScreenShape.RECTANGLE){
            seekArc.setVisibility(View.GONE);
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) spritzerTV.getLayoutParams();
            mlp.setMargins(0, 0, 0, 0);
        } else {
            seekBar.setVisibility(View.GONE);
        }
        spritzerTV.setWpm(250);
        seekArc.setProgress(spritzerTV.getWpm());
        seekBar.setProgress(spritzerTV.getWpm());
        spritzerTV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isPlaying) {
                    spritzerTV.pause();
                } else {
                    spritzerTV.play();
                }
                return false;
            }

        });
        spritzerTV.setOnLongClickListener(onLongClickListener);
        seekArc.setOnLongClickListener(onLongClickListener);
        seekBar.setOnLongClickListener(onLongClickListener);
        seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        seekArc.setOnSeekArcChangeListener(onSeekBarChangeListenerRound);
        String storyTitle = getIntent().getExtras().getString("title", "");
        if(!storyTitle.equals("")){
            story = WearSpritzerApplication.getStoryRealm(storyTitle);
            spritzerTV.setSpritzText(story.getContent());
        }
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
    View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            finish();
            return false;
        }
    };
    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            spritzerTV.setWpm(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    SeekArc.OnSeekArcChangeListener onSeekBarChangeListenerRound = new SeekArc.OnSeekArcChangeListener() {
        @Override
        public void onProgressChanged(SeekArc seekArc, int i, boolean b) {
            spritzerTV.setWpm(i);
        }

        @Override
        public void onStartTrackingTouch(SeekArc seekArc) {

        }

        @Override
        public void onStopTrackingTouch(SeekArc seekArc) {

        }
    };
}
