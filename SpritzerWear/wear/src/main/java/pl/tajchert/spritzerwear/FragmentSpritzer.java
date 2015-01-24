package pl.tajchert.spritzerwear;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.andrewgiang.textspritzer.lib.Spritzer;
import com.andrewgiang.textspritzer.lib.SpritzerTextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSpritzer extends Fragment {
    private SpritzerTextView spritzerTV;
    private LinearLayout linearLayout;
    private boolean isPlaying;


    public FragmentSpritzer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_spritzer, container, false);

        spritzerTV = (SpritzerTextView) v.findViewById(R.id.spritzTV);
        linearLayout = (LinearLayout) v.findViewById(R.id.layoutMain);
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

        return v;
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
