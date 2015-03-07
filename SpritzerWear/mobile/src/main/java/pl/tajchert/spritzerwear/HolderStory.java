package pl.tajchert.spritzerwear;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import de.greenrobot.event.EventBus;
import pl.tajchert.spritzerwear.events.EventStoryChanged;


public class HolderStory extends RecyclerView.ViewHolder {
    protected TextView storyTitle;
    protected TextView storyContet;
    protected ImageButton storyDelete;
    protected ImageButton storyTest;

    public HolderStory(View v) {
        super(v);
        storyTitle =  (TextView) v.findViewById(R.id.storyTitle);
        storyContet = (TextView) v.findViewById(R.id.storyContent);
        storyDelete = (ImageButton)  v.findViewById(R.id.storyDelete);
        storyTest = (ImageButton)  v.findViewById(R.id.storyTest);
        storyDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventStoryChanged(storyTitle.getText() + "", EventStoryChanged.EventType.Deleted));
            }
        });
        storyTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventStoryChanged(storyTitle.getText() + "", EventStoryChanged.EventType.Test));
            }
        });
    }
}
