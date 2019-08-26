package com.example.android.todoapplication2;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;


public class ListElementAdapter extends ArrayAdapter<ListElement> {
    private Handler mHandler = new Handler();

    public ListElementAdapter(Activity context, ArrayList<ListElement> listElements, Liste1Fragment fragment) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, listElements);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listElementView = convertView;
        if (listElementView == null) {
            listElementView = LayoutInflater.from(getContext()).inflate(
                    R.layout.single_list_element, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        ListElement currentItem = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID
        final TextView listItemTextView = listElementView.findViewById(R.id.textView_list_element_name);
        // set this text on the name TextView
        listItemTextView.setText(currentItem.getmListElementName());

        // Find the Checkbox in the list_item.xml layout with the ID
        CheckBox checkBox = listElementView.findViewById(R.id.checkBox_list_element);

        // Return the whole list item layout (containing 2 TextViews)
        // so that it can be shown in the ListView
        return listElementView;
    }
}
