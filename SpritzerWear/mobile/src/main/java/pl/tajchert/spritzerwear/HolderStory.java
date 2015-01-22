package pl.tajchert.spritzerwear;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class HolderStory extends RecyclerView.ViewHolder {
    protected TextView storyTitle;
    protected Button storyDelete;

    public HolderStory(View v) {
        super(v);
        storyTitle =  (TextView) v.findViewById(R.id.storyTitle);
        storyDelete = (Button)  v.findViewById(R.id.storyDelete);
    }
}
