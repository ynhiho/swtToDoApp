package com.example.android.todoapplication2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Liste1Fragment extends Fragment {

    View rootView;
    private String mName;
    ArrayList<ListElement> allListElements;
    private String mParentCategory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.list_elements_view, container, false);

        allListElements = new ArrayList<ListElement>();
        allListElements.add(new ListElement("schlafen"));
        allListElements.add(new ListElement("essen"));
        allListElements.add(new ListElement("spielen"));

        displayAllListElements();

        return rootView;
    }

    private void displayAllListElements(){

        ListElementAdapter listElementsAdapter = new ListElementAdapter (getActivity(), allListElements, Liste1Fragment.this);
        ListView listView = (ListView) rootView.findViewById(R.id.listView_list_elements);
        listView.setAdapter(listElementsAdapter);
    }

    public void setName(String name){
        mName = name;
    }

    public String getName(){
        return mName;
    }

    public void setParentCategory(String parentCategory){
        mParentCategory = parentCategory;
    }

    public String getParentCategory(){
        return mParentCategory;
    }


}
