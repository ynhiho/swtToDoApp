package com.example.android.todoapplication2;

public class ListElement {

    private String mName;
    private boolean checked;

    public ListElement(String name){
        mName = name;
        checked = false;
    }

    public String getName(){
        return mName;
    }

    public boolean getChecked(){
        return checked;
    }

    public void setChecked(boolean checkedState){
        checked = checkedState;
    }
}
