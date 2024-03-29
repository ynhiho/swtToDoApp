package com.example.android.todoapplication2;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class NewListElementDialog extends AppCompatDialogFragment {
    private EditText editTextNewListElement;

    //Use this instance of the interface to deliver action events
    private NewListElementDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Instantiate an Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Inflater for creating custom Dialog layouts
        LayoutInflater inflater = getActivity().getLayoutInflater();
        //first param: layout resource ID; second id: parent view for the layout
        View view = inflater.inflate(R.layout.new_list_element_dialog, null);

        //Chain together various setter methods to set the dialog characteristics
        //setView() to place the layout in the dialog
        builder.setView(view)
                .setTitle("Neue Aufgabe")
                //Action Buttons
                .setNegativeButton("abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String new_list_element = editTextNewListElement.getText().toString().trim();
                        Log.i("NewListElementDialog", "listener: " + listener);
                        listener.addNewListElement(new_list_element);
                    }
                });

        editTextNewListElement = view.findViewById(R.id.editText_list_element_name);

        return builder.create();
    }

    //Override the Fragment.onAttach() method to instantiate the ProduktDialogListener(Interface)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the ProduktDialogListener so we can send events to the host (ProduktlisteFragment)
            // Hack: get(1), because ProduktlisteFragment has index 1 (see the order of the tabs in the navigation)
            // getFragmentManager().getFragments().get(1)
            listener = (NewListElementDialogListener) getActivity();
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString() + " must implement NewListElementDialogListener");
        }
    }

    public interface NewListElementDialogListener{
        void addNewListElement(String new_list_element);
    }
}
