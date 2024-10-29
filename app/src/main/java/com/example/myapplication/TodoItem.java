// TodoItem.java
package com.example.myapplication;

public class TodoItem {
    private String date;
    private String content;
    private boolean isChecked;

    public TodoItem(String date, String content, boolean isChecked) {
        this.date = date;
        this.content = content;
        this.isChecked = isChecked;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
