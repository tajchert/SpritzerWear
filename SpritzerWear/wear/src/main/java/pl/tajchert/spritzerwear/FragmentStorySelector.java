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

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import pl.tajchert.spritzerwearcommon.Story;
import pl.tajchert.spritzerwearcommon.StoryRealm;

public class FragmentStorySelector extends Fragment implements WearableListView.ClickListener {
    private WearableListView.Adapter adapter;
    private WearableListView listView;
    public FragmentSpritzer fragmentSpritzer;
    private ArrayList<Story> stories;
    private Realm realm;

    public FragmentStorySelector() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_story_selector, container, false);
        listView = (WearableListView) view.findViewById(R.id.story_listview);
        stories = new ArrayList<>();
        adapter = new Adapter(getActivity(), stories);
        listView.setAdapter(adapter);
        listView.setClickListener(this);
        realm = Realm.getInstance(getActivity());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        readStoriesRealm();
    }
    private void readStoriesRealm(){
        RealmQuery<StoryRealm> query = realm.where(StoryRealm.class);
        RealmResults<StoryRealm> resultAllStories = query.findAll();
        stories = new ArrayList<>();
        for(StoryRealm storyRealm : resultAllStories){
            stories.add(new Story(storyRealm));
        }
        adapter = new Adapter(getActivity(), stories);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(WearableListView.ViewHolder v) {
        if(fragmentSpritzer != null){
            fragmentSpritzer.setStoryContent(stories.get(v.getPosition()).getContent());
        }
    }

    @Override
    public void onTopEmptyRegionClick() {
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
            view.setText(storyList.get(position).getTitle());
            Log.d("FragmentStorySelector", "title: " +storyList.get(position).getTitle());
            holder.itemView.setTag(position);
        }

        @Override
        public int getItemCount() {
            return storyList.size();
        }
    }
}
