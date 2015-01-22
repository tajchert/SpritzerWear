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
        FragmentSpritzer fragmentSpritzer = new FragmentSpritzer();

        fragmentStorySelector.fragmentSpritzer = fragmentSpritzer;

        fragments.add(fragmentStorySelector);
        fragments.add(fragmentSpritzer);

        mGridPager = (GridViewPagerOnlyHorizontal) findViewById(R.id.gridPager);
        GridPagerAdapter adapter = new GridViewPagerAdapter(this, getFragmentManager(), fragments);
        mGridPager.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


}
