package pl.tajchert.spritzerwear;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.wearable.view.GridPagerAdapter;

import java.util.ArrayList;

import pl.tajchert.spritzerwearcommon.Tools;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private ArrayList<Fragment> fragments;
    private GridViewPagerOnlyHorizontal mGridPager;
    private BroadcastReceiver receiver;
    private FragmentStorySelector fragmentStorySelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridviewpager);

        fragments = new ArrayList<>();
        fragmentStorySelector = new FragmentStorySelector();
        fragments.add(fragmentStorySelector);

        mGridPager = (GridViewPagerOnlyHorizontal) findViewById(R.id.gridPager);
        GridPagerAdapter adapter = new GridViewPagerAdapter(this, getFragmentManager(), fragments);
        mGridPager.setAdapter(adapter);


        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(fragmentStorySelector != null){
                    WearSpritzerApplication.closeRealm();
                    WearSpritzerApplication.getRealm();
                    fragmentStorySelector.readStoriesRealm();
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Tools.DATA_STORY_CHANGED);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        WearSpritzerApplication.closeRealm();
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
