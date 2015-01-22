package pl.tajchert.spritzerwear;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pl.tajchert.spritzerwearcommon.Story;


public class AdapterComment extends RecyclerView.Adapter<HolderStory> {
    private static final String TAG = "AdapterComment";
    private List<Story> storyList;

    public AdapterComment(List<Story> commentList) {
        this.storyList = commentList;
    }

    public void setStoryList(List<Story> storyList) {
        this.storyList = storyList;
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    @Override
    public void onBindViewHolder(HolderStory contactViewHolder, int i) {
        Story ci = storyList.get(i);
        contactViewHolder.storyTitle.setText(ci.title);
    }

    @Override
    public HolderStory onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.card_story, viewGroup, false);
        return new HolderStory(itemView);
    }
}
