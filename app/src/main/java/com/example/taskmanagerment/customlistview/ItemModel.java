package com.example.taskmanagerment.customlistview;

public abstract class ItemModel {

    public static final int TYPE_BOARD = 0;

    public static final int TYPE_NOTIFICATION = 1;

    abstract public int getType();

    abstract public String getTitle();

    abstract public String getDate();

}

