package pl.tajchert.spritzerwear;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pl.tajchert.spritzerwearcommon.Story;
import pl.tajchert.spritzerwearcommon.Tools;


public class FragmentStorySelector extends Fragment implements WearableListView.ClickListener {
    private WearableListView.Adapter adapter;
    private WearableListView listView;
    public FragmentSpritzer fragmentSpritzer;
    private ArrayList<Story> stories;

    public FragmentStorySelector() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_story_selector, container, false);
        listView = (WearableListView) view.findViewById(R.id.story_listview);
        stories = new ArrayList<>();
        stories.add(new Story("Story 1", ""));
        stories.add(new Story("Story 2", ""));
        adapter = new Adapter(getActivity(), stories);
        listView.setAdapter(adapter);
        listView.setClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        readStories();
    }

    @Override
    public void onClick(WearableListView.ViewHolder v) {
        if(fragmentSpritzer != null){
            fragmentSpritzer.setStoryContent(stories.get(v.getPosition()).content);
        }
    }

    @Override
    public void onTopEmptyRegionClick() {

    }

    public void setStory(int num){
        if(listView != null){
            listView.smoothScrollToPosition(num);
        }
    }

    private static final class Adapter extends WearableListView.Adapter {
        private final Context mContext;
        private final LayoutInflater mInflater;
        public ArrayList<Story> storyList = new ArrayList<>();

        private Adapter(Context context, ArrayList<Story> stories) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
            storyList = stories;
        }

        @Override
        public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new WearableListView.ViewHolder(mInflater.inflate(R.layout.list_story_item, null));
        }

        @Override
        public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
            TextView view = (TextView) holder.itemView.findViewById(R.id.name);
            view.setText(storyList.get(position).title);
            Log.d("FragmentStorySelector", "title: " +storyList.get(position).title);
            holder.itemView.setTag(position);
        }

        @Override
        public int getItemCount() {
            return storyList.size();
        }
    }

    private void readStories(){
        String content = getActivity().getBaseContext().getSharedPreferences(Tools.PREFS, Context.MODE_PRIVATE).getString(Tools.WEAR_KEY, "");
        stories = storyFromJson(content);
        adapter = new Adapter(getActivity(), stories);
        listView.setAdapter(adapter);
    }

    private ArrayList<Story> storyFromJson(String in){
        ArrayList<Story> stories = new ArrayList<>();
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(in);
            for (int i = 0; i < jsonArray.length(); i++) {
                Story story = new Story();
                story.title = ((JSONObject) jsonArray.get(i)).optString("title");
                story.content = ((JSONObject) jsonArray.get(i)).optString("content");
                stories.add(story);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stories;
    }
}
