package com.example.taskmanagerment.models;

import java.io.Serializable;

public class NotifyWhen implements Serializable {
    public static final int NO_NOTIFY = 0;
    public static final int BEFORE_ONE_DATE = 1;
    public static final int BEFORE_ONE_HOUR = 2;
    public static final int BEFORE_THIRTY_MINUTES = 3;
    public static final int BEFORE_FIFTY_MINUTES = 4;
    public static final int BEFORE_TEN_MINUTES = 5;
    public static final int BEFORE_FIVE_MINUTES = 6;
    public static final int AT_DEADLINE = 7;

}
