package com.example.android.todoapplication2;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.lang.reflect.Array;
import java.util.ArrayList;

//adapter that knows which fragment should be shown on each page
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    ArrayList<TodoTabFragment> mTodoTabs = new ArrayList<>();
    ArrayList<String> mTodoTabTitles = new ArrayList<>();
    TodoListFragment mTodoList;

    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        mTodoList = TodoListFragment.newInstance();
//        FragmentManager fm = MainActivity.this.getSupportFragmentManager();
        fm.executePendingTransactions();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_container, mTodoList).commit();
    }

    @Override
    public TodoTabFragment getItem(int position) {
        return mTodoTabs.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return mTodoTabTitles.get(position);
    }

    public TodoListFragment getTodoList()
    {
        return mTodoList;
    }

    public void UpdateTodoList(ArrayList<Todo> todos)
    {
        mTodoList.update(todos);
        this.notifyDataSetChanged();
    }

    public void addPage(String title) {
        mTodoTabs.add(TodoTabFragment.newInstance());
        mTodoTabTitles.add(title);
        notifyDataSetChanged();
    }

    public void removePage(int position) {
        mTodoTabs.remove(position);
        mTodoTabTitles.remove(position);
    }

    public void clearView(){
        mTodoTabs.clear();
        mTodoTabTitles.clear();
        mTodoList.clear();
    }

    @Override
    public int getCount() {
        return mTodoTabs.size();
    }
}

