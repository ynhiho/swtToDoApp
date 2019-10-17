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

public class TodoTabFragment extends Fragment
{
    public static TodoTabFragment newInstance() {
        TodoTabFragment view = new TodoTabFragment();
        return view;
    }
}
