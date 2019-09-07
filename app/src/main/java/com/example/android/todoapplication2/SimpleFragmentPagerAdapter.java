package com.example.android.todoapplication2;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Liste1Fragment> mAllLists = new ArrayList<>();
    private ArrayList<String> mAllListsNames = new ArrayList<>();
    private Context context;

    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.context = context;
    }


    @Override
    public Liste1Fragment getItem(int position) {
        return mAllLists.get(position);
    }

    public void addList(Liste1Fragment list, String title) {
        mAllLists.add(list);
        mAllListsNames.add(title);
    }

    public void clearTodoLists(){
        mAllLists.clear();
        mAllListsNames.clear();
    }

    public void removeList(Liste1Fragment list, int position) {
        mAllLists.remove(position);
        mAllListsNames.remove(position);
    }

    @Override
    public int getCount() {
        return mAllLists.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mAllListsNames.get(position);
    }

    public int getListsCount() {
        return mAllListsNames.size();
    }

    /*final int PAGE_COUNT = 5;
    private String tabTitles[] = new String[] { "Liste 1", "Liste 2", "Liste 3", "Liste 4"," Liste 5"};
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
        } else if (position == 3){
            return new Liste1Fragment();
        } else if (position == 4){
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
        return tabTitles[position];*/
}

