package pl.tajchert.spritzerwear;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.wearable.view.FragmentGridPagerAdapter;

import java.util.ArrayList;

public class GridViewPagerAdapter extends FragmentGridPagerAdapter {
    public ArrayList<Fragment> fragments = new ArrayList<>();



    public GridViewPagerAdapter(Context mContext, FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getFragment(int row, int col) {
        Fragment fragment;
        fragment = fragments.get(col);
        return fragment;
    }

    @Override
    public int getRowCount() {
        return (1);
    }

    @Override
    public int getColumnCount(int rowNum) {
        return fragments.size();
    }
}