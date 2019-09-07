package com.example.android.todoapplication2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Liste1Fragment extends Fragment {

    View rootView;
    private String mName;
    ArrayList<ListElement> allListElements = new ArrayList<ListElement>();
    private String mParentCategory;
    ListElementAdapter mListElementsAdapter;
    ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.list_elements_view, container, false);

        mListElementsAdapter = new ListElementAdapter (getActivity(), allListElements);
        mListView = rootView.findViewById(R.id.listView_list_elements);
        mListView.setAdapter(mListElementsAdapter);

        allListElements.add(new ListElement("schlafen"));
        allListElements.add(new ListElement("essen"));
        allListElements.add(new ListElement("spielen"));

        addListElement("Kacka");

//        displayAllListElements();

        return rootView;
    }

    public void addListElement(String newListElementName){
        allListElements.add(new ListElement(newListElementName));
        displayAllListElements();
    }

    private void displayAllListElements(){
        mListElementsAdapter.notifyDataSetChanged();
    }

    public void setName(String name){
        mName = name;
    }

    public String getName(){
        return mName;
    }

    public void setCategory(String parentCategory){
        mParentCategory = parentCategory;
    }

    public String getParentCategory(){
        return mParentCategory;
    }


}
