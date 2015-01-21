package pl.tajchert.spritzerwear;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.andrewgiang.textspritzer.lib.Spritzer;
import com.andrewgiang.textspritzer.lib.SpritzerTextView;

public class MainActivity extends Activity {
    private SpritzerTextView spritzerTV;
    private LinearLayout linearLayout;
    private boolean isPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
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
        });
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
}
