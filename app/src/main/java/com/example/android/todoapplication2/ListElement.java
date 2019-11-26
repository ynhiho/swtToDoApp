package com.example.android.todoapplication2;

public class ListElement {

    private String mName;
    private boolean isChecked;
    private int Id;

    public ListElement(int id, String name, boolean isChecked){
        Id = id;
        mName = name;
        this.isChecked = isChecked;
    }

    public String getName(){
        return mName;
    }

    public boolean getChecked(){
        return isChecked;
    }

    public int getId() {return Id; }

    public void setChecked(boolean checkedState){
        isChecked = checkedState;
    }
}
