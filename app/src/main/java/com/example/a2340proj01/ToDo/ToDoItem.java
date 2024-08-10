package com.example.a2340proj01.ToDo;
/*
* Jason Yust
* 2/1/2024
 */
public class ToDoItem {
    private String itemDescription;
    private String itemTitle;

    public ToDoItem(String itemDescription, String itemTitle) {
        this.itemDescription = itemDescription;
        this.itemTitle = itemTitle;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemDescription() { return itemDescription; }
    public String getItemTitle() { return itemTitle; }
}
