package pl.tajchert.spritzerwear;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.wearable.view.GridPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private ArrayList<Fragment> fragments;
    private GridViewPagerOnlyHorizontal mGridPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridviewpager);

        fragments = new ArrayList<>();
        FragmentStorySelector fragmentStorySelector = new FragmentStorySelector();
        fragments.add(fragmentStorySelector);

        mGridPager = (GridViewPagerOnlyHorizontal) findViewById(R.id.gridPager);
        GridPagerAdapter adapter = new GridViewPagerAdapter(this, getFragmentManager(), fragments);
        mGridPager.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        WearSpritzerApplication.closeRealm();
        super.onDestroy();
    }
}
