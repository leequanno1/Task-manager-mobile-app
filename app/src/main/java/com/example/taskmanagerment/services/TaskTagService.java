package com.example.taskmanagerment.services;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.taskmanagerment.database.DatabaseHelper;
import com.example.taskmanagerment.models.Tag;

import java.util.ArrayList;
import java.util.List;

public class TaskTagService {
    private DatabaseHelper databaseHelper;
    private Context context;

    public TaskTagService(Context context) {
        this.context = context;
        this.databaseHelper = new DatabaseHelper(context);
    }

    public void addOrDelete(int taskID, List<Tag> newSelectedTags) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        db.delete("TaskTag", "TaskID=?", new String[]{String.valueOf(taskID)});
        for(int i = 0; i < newSelectedTags.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("TaskID", taskID);
            contentValues.put("TagID", newSelectedTags.get(i).getTagID());
            db.insert("TaskTag",null,contentValues);
        }
        db.close();
    }

//    public void deleteTags(List<Tag> tags) {
//        SQLiteDatabase db = databaseHelper.getWritableDatabase();
//
//        int rowsAffected = db.delete("TaskTag", "TaskID=?", new String[]{String.valueOf(taskId)});
//        db.close();
//
//        return rowsAffected > 0;
//    }

}
