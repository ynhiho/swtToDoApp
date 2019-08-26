package com.example.android.todoapplication2;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[] { "Liste 1", "Liste 2", "Liste 3", "Liste 4" };
    private Context context;

    public SimpleFragmentPagerAdapter(FragmentManager fm){
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new Liste1Fragment();
        } else if (position == 1){
            return new Liste1Fragment();
        } else if (position == 2){
            return new Liste1Fragment();
        } else {
            return new Liste1Fragment();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
