package com.example.taskmanagerment.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TaskManagement.db";

    private static final int DATABASE_VERSION = 1;

    private Context context;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createProjectTable = "CREATE TABLE IF NOT EXISTS Project (" +
                "ProjectID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ProjectName TEXT NOT NULL DEFAULT 'New Project', " +
                "CreatedAt TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP);";

        String createTaskGroupTable = "CREATE TABLE IF NOT EXISTS TaskGroup (" +
                "GroupID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ProjectID INTEGER NOT NULL, " +
                "GroupName TEXT NOT NULL DEFAULT 'New Group', " +
                "CreatedAt TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (ProjectID) REFERENCES Project(ProjectID) " +
                "ON UPDATE CASCADE ON DELETE CASCADE);";

        String createTaskTable = "CREATE TABLE IF NOT EXISTS Task (" +
                "TaskID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "GroupID INTEGER NOT NULL, " +
                "TaskName TEXT NOT NULL DEFAULT 'New Task', " +
                "CreatedAt TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                "Deadline TEXT, " +
                "CompletedAt TEXT, " +
                "Description TEXT, " +
                "ImageURL TEXT, " +
                "notifyWhen TEXT, " +
                "FOREIGN KEY (GroupID) REFERENCES TaskGroup(GroupID) " +
                "ON UPDATE CASCADE ON DELETE CASCADE);";

        String createTagTable = "CREATE TABLE IF NOT EXISTS Tag (" +
                "TagID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ProjectID INTEGER NOT NULL, " +
                "TagName TEXT NOT NULL UNIQUE, " +
                "TagColor TEXT NOT NULL, " +
                "FOREIGN KEY (ProjectID) REFERENCES Project(ProjectID) " +
                "ON UPDATE CASCADE ON DELETE CASCADE);";

        String createTaskTagTable = "CREATE TABLE IF NOT EXISTS TaskTag (" +
                "TaskTagID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TaskID INTEGER NOT NULL, " +
                "TagID INTEGER NOT NULL, " +
                "FOREIGN KEY (TaskID) REFERENCES Task(TaskID) " +
                "ON UPDATE CASCADE ON DELETE CASCADE, " +
                "FOREIGN KEY (TagID) REFERENCES Tag(TagID) " +
                "ON UPDATE CASCADE ON DELETE CASCADE);";

        String createNotificationTable = "CREATE TABLE IF NOT EXISTS Notification (" +
                "NotificationID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TaskID INTEGER NOT NULL, " +
                "ProjectName TEXT NOT NULL, " +
                "TaskName TEXT NOT NULL, " +
                "DeadlineTime TEXT NOT NULL, " +
                "FOREIGN KEY (TaskID) REFERENCES Task(TaskID) " +
                "ON UPDATE CASCADE ON DELETE CASCADE);";

        db.execSQL(createProjectTable);
        db.execSQL(createTaskGroupTable);
        db.execSQL(createTaskTable);
        db.execSQL(createTagTable);
        db.execSQL(createTaskTagTable);
        db.execSQL(createNotificationTable);

        // Thêm 2 dòng dữ liệu vào bảng Project với ngày giờ hiện tại
        ContentValues values = new ContentValues();
        values.put("ProjectName", "Project 1");
        values.put("CreatedAt", "CURRENT_TIMESTAMP");
        db.insert("Project", null, values);

        values.clear();
        values.put("ProjectName", "Project 2");
        values.put("CreatedAt", "CURRENT_TIMESTAMP");
        db.insert("Project", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void deleteDatabase() {
        context.deleteDatabase(DATABASE_NAME);
    }
}
