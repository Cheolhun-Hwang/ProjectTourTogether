package com.hch.hooney.tourtogether.SettingForEvent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hch.hooney.tourtogether.Fragments.Bookmarking.BCourseFragment;
import com.hch.hooney.tourtogether.Fragments.Bookmarking.MyDairyFragment;
import com.hch.hooney.tourtogether.Fragments.Bookmarking.SpotFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qewqs on 2018-01-24.
 */

public class AccountTabAdapter extends FragmentStatePagerAdapter {
    private int tabCount;

    public AccountTabAdapter(FragmentManager fm) {
        super(fm);
    }

    public AccountTabAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                SpotFragment tabFragment1 = new SpotFragment();
                return tabFragment1;
            case 1:
                BCourseFragment tabFragment2 = new BCourseFragment();
                return tabFragment2;
            case 2:
                MyDairyFragment tabFragment3 = new MyDairyFragment();
                return tabFragment3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
