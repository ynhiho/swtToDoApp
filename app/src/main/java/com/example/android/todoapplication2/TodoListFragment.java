package com.example.android.todoapplication2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class TodoListFragment extends Fragment {

    View rootView;
    ArrayList<ListElement> todos = new ArrayList<ListElement>();
    ListElementAdapter mListElementsAdapter;
    ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.list_elements_view, container, false);

        mListElementsAdapter = new ListElementAdapter (getActivity(), todos);
        mListView = rootView.findViewById(R.id.listView_list_elements);
        mListView.setAdapter(mListElementsAdapter);

        mListElementsAdapter.setNotifyOnChange(true);
        return rootView;
    }

    public void update(ArrayList<Todo> newTodos)
    {
        if(mListElementsAdapter == null)
        {
            Log.i("MY", "Updated called before onCreateView?");
            return;
        }

        todos.clear();
        //mListElementsAdapter.clear();
        for (Todo todo : newTodos) {
            ListElement le = new ListElement(todo.Id, todo.Text, todo.isChecked);
            todos.add(le);
//            mListElementsAdapter.add(le);
            Log.i("MY", "TodoListFragment -> update: " + todo.Text);
        }

        mListElementsAdapter.notifyDataSetChanged();
    }

    public void clear()
    {
        todos.clear();

        if(mListElementsAdapter != null)
            mListElementsAdapter.notifyDataSetChanged();
    }

    public static TodoListFragment newInstance() {
        TodoListFragment view = new TodoListFragment();
        return view;
    }
}
