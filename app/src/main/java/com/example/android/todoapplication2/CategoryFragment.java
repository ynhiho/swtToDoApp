package com.example.android.todoapplication2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    View rootView;
    private String name;
    private ArrayList<TodoTabFragment> allLists = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_category, container, false);

        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new TodoTabFragment()).commit();

        return rootView;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void addList (TodoTabFragment newList){
        allLists.add(newList);
    }
}
