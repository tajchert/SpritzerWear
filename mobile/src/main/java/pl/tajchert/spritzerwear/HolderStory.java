package pl.tajchert.spritzerwear;

import android.support.v7.widget.CardView;
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

    public HolderStory(View v) {
        super(v);
        CardView cardView = (CardView) v.findViewById(R.id.card_view);
        storyTitle =  (TextView) v.findViewById(R.id.storyTitle);
        storyContet = (TextView) v.findViewById(R.id.storyContent);
        storyDelete = (ImageButton)  v.findViewById(R.id.storyDelete);
        storyDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventStoryChanged(storyTitle.getText() + "", EventStoryChanged.EventType.Deleted));
            }
        });
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventStoryChanged(storyTitle.getText() + "", EventStoryChanged.EventType.Test));
            }
        });

    }

}
