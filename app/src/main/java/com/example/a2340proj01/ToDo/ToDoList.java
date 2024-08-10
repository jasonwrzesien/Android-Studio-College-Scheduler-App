package com.example.a2340proj01.ToDo;

import java.util.Vector;

/*
* Jason Yust
* 2/1/2024
 */
public class ToDoList {
    private Vector<ToDoItem> list;

    public ToDoList() {
        list = new Vector<>();
    }
    public ToDoList(Vector<ToDoItem> list) {
        this.list = list;
    }

    public void setList(Vector<ToDoItem> list) {
        this.list = list;
    }

    public Vector<ToDoItem> getList() { return list; }

    public void addItem(String description, String title) {
        ToDoItem item = new ToDoItem(description, title);
        list.add(item);
    }
    public void addItem(ToDoItem item) {
        list.add(item);
    }
    public ToDoItem removeItem(int index) {
        return list.remove(index);
    }
    public void editItemTitle(int index, String newTitle) {
        // Get and set new title
        ToDoItem item = list.get(index);
        item.setItemTitle(newTitle);

        // Replace index with new item
        list.set(index, item);
    }
    public void editItemDescription(int index, String newDescription) {
        // Get and set new title
        ToDoItem item = list.get(index);
        item.setItemDescription(newDescription);

        // Replace index with new item
        list.set(index, item);
    }
}
