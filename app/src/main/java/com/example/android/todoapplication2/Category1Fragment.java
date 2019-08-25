package com.example.android.todoapplication2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
    ArrayList<ListElement> allListElements;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.list_elements_view, container, false);

        allListElements = new ArrayList<ListElement>();
        allListElements.add(new ListElement("tft spielen"));
        allListElements.add(new ListElement("wow spielen"));
        allListElements.add(new ListElement("eis essen"));

        displayAllListElements();

        return rootView;
    }

    private void displayAllListElements(){

        ListElementAdapter listElementsAdapter = new ListElementAdapter(getActivity(), allListElements, Category1Fragment.this);
        ListView listView = (ListView) rootView.findViewById(R.id.listView_list_elements);
        listView.setAdapter(listElementsAdapter);
    }

}
