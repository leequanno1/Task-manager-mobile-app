package com.example.taskmanagerment.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TaskManagement.db";

    private static final int DATABASE_VERSION = 1;

    // SQL statements
    private static final String CREATE_TABLE_PROJECT = "CREATE TABLE IF NOT EXISTS Project (" +
            "ProjectID INTEGER NOT NULL UNIQUE, " +
            "ProjectName TEXT NOT NULL DEFAULT 'New Project', " +
            "CreatedAt TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
            "FirstGroupID INTEGER, " +
            "PRIMARY KEY(ProjectID));";

    private static final String CREATE_TABLE_TASKGROUP = "CREATE TABLE IF NOT EXISTS TaskGroup (" +
            "GroupID INTEGER NOT NULL UNIQUE, " +
            "ProjectID INTEGER NOT NULL, " +
            "GroupName TEXT NOT NULL DEFAULT 'New Group', " +
            "CreatedAt TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
            "PrevGroupID INTEGER, " +
            "NextGroupID INTEGER, " +
            "PRIMARY KEY(GroupID), " +
            "FOREIGN KEY (ProjectID) REFERENCES Project(ProjectID) " +
            "ON UPDATE NO ACTION ON DELETE NO ACTION);";

    private static final String CREATE_TABLE_TASK = "CREATE TABLE IF NOT EXISTS Task (" +
            "TaskID INTEGER NOT NULL UNIQUE, " +
            "GroupID INTEGER NOT NULL, " +
            "TaskName TEXT NOT NULL DEFAULT 'New Task', " +
            "CreatedAt TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
            "Deadline TEXT, " +
            "CompletedAt TEXT, " +
            "Description TEXT, " +
            "ImageURL TEXT, " +
            "PRIMARY KEY(TaskID), " +
            "FOREIGN KEY (GroupID) REFERENCES TaskGroup(GroupID) " +
            "ON UPDATE NO ACTION ON DELETE NO ACTION);";

    private static final String CREATE_TABLE_TAG = "CREATE TABLE IF NOT EXISTS Tag (" +
            "TagID INTEGER NOT NULL UNIQUE, " +
            "ProjectID INTEGER NOT NULL, " +
            "TagName TEXT NOT NULL UNIQUE, " +
            "TagColor TEXT NOT NULL, " +
            "PRIMARY KEY(TagID));";

    private static final String CREATE_TABLE_TASKTAG = "CREATE TABLE IF NOT EXISTS TaskTag (" +
            "TaskTagID INTEGER NOT NULL UNIQUE, " +
            "TaskID INTEGER NOT NULL, " +
            "TagID INTEGER NOT NULL, " +
            "PRIMARY KEY(TaskTagID), " +
            "FOREIGN KEY (TaskID) REFERENCES Task(TaskID) " +
            "ON UPDATE NO ACTION ON DELETE NO ACTION);";

    private static final String CREATE_TABLE_NOTIFICATION = "CREATE TABLE IF NOT EXISTS Notification (" +
            "NotificationID INTEGER NOT NULL UNIQUE, " +
            "TaskID INTEGER NOT NULL, " +
            "ProjectName TEXT NOT NULL, " +
            "TaskName TEXT NOT NULL, " +
            "DeadlineTime TEXT NOT NULL, " +
            "PRIMARY KEY(NotificationID), " +
            "FOREIGN KEY (TaskID) REFERENCES Task(TaskID) " +
            "ON UPDATE NO ACTION ON DELETE NO ACTION);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PROJECT);
        db.execSQL(CREATE_TABLE_TASKGROUP);
        db.execSQL(CREATE_TABLE_TASK);
        db.execSQL(CREATE_TABLE_TAG);
        db.execSQL(CREATE_TABLE_TASKTAG);
        db.execSQL(CREATE_TABLE_NOTIFICATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}