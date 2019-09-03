package com.example.android.todoapplication2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class Category1Fragment extends Fragment {

    View rootView;
    private String name;
    private ArrayList<Liste1Fragment> allLists = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_category1, container, false);

        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new Liste1Fragment()).commit();

        return rootView;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void addList (Liste1Fragment newList){
        allLists.add(newList);
    }
}
