package com.example.android.todoapplication2;

public class Todo
{
    public String Text;
    public String tab;
    public boolean isChecked;
    public int Id;

    public Todo(int id, String tab, String text, boolean isChecked) {
        Id = id;
        Text = text;
        this.tab = tab;
        this.isChecked = isChecked;
    }
}
