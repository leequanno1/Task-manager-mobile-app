package com.example.taskmanagerment.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.taskmanagerment.database.DatabaseHelper;
import com.example.taskmanagerment.models.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskService {
    private DatabaseHelper databaseHelper;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public TaskService(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
    }

    // Phương thức thêm Task
    public long addTask(int groupId, String taskName, Date deadline, String description, String imageUrl) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("GroupID", groupId);
        values.put("TaskName", taskName);
        values.put("CreatedAt", dateFormat.format(new Date()));
        values.put("Deadline", deadline != null ? dateFormat.format(deadline) : null);
        values.put("Description", description);
        values.put("ImageURL", imageUrl);

        long taskId = db.insert("Task", null, values);
        db.close();

        return taskId;
    }

    // Phương thức lấy tất cả Task ứng với GroupID
    public ArrayList<Task> getAllTasksByGroupId(int groupId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        ArrayList<Task> tasks = new ArrayList<>();

        String[] columns = {
                "TaskID", "GroupID", "TaskName", "CreatedAt", "Deadline", "CompletedAt", "Description", "ImageURL"
        };

        String selection = "GroupID = ?";
        String[] selectionArgs = {String.valueOf(groupId)};

        Cursor cursor = db.query("Task", columns, selection, selectionArgs, null, null, "CreatedAt ASC");

        if (cursor.moveToFirst()) {
            do {
                int taskId = cursor.getInt(cursor.getColumnIndexOrThrow("TaskID"));
                String taskName = cursor.getString(cursor.getColumnIndexOrThrow("TaskName"));
                String createdAtString = cursor.getString(cursor.getColumnIndexOrThrow("CreatedAt"));
                String deadlineString = cursor.getString(cursor.getColumnIndexOrThrow("Deadline"));
                String completedAtString = cursor.getString(cursor.getColumnIndexOrThrow("CompletedAt"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("Description"));
                String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow("ImageURL"));
//                String notifyWhen = cursor.getString(cursor.getColumnIndexOrThrow("NotifyWhen"));


                Date createdAt = null;
                Date deadline = null;
                Date completedAt = null;

                try {
                    createdAt = dateFormat.parse(createdAtString);
                    if (deadlineString != null) {
                        deadline = dateFormat.parse(deadlineString);
                    }
                    if (completedAtString != null) {
                        completedAt = dateFormat.parse(completedAtString);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Task task = new Task(taskId, groupId, taskName, createdAt, deadline, completedAt, description, imageUrl);
                tasks.add(task);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return tasks;
    }


    // Phương thức xóa Task dựa vào ID
    public boolean deleteTask(long taskId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        int rowsAffected = db.delete("Task", "TaskID=?", new String[]{String.valueOf(taskId)});
        db.close();

        return rowsAffected > 0;
    }


    // Phương thức xóa tất cả Task dựa vào GroupID
    public boolean deleteAllTasksByGroupId(long groupId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        int rowsAffected = db.delete("Task", "GroupID=?", new String[]{String.valueOf(groupId)});
        db.close();

        return rowsAffected > 0;
    }

    public boolean modifyTask(Task task) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TaskName", task.getTaskName());
        contentValues.put("CreatedAt", dateFormat.format(task.getCreatedAt()));
        contentValues.put("Deadline", task.getDeadline() != null ? dateFormat.format(task.getDeadline()) : null);
        contentValues.put("Description", task.getDescription());
        contentValues.put("ImageURL", task.getImageURL());
        contentValues.put("NotifyWhen", task.getNotifyWhen());
        long rowModified = db.update("Task", contentValues, "TaskID=?", new String[] {task.getTaskID()+""});
        return rowModified > 0;
    }

    public boolean setCompleteTime(Task task) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CompletedAt", task.getCompletedAt() != null ? dateFormat.format(task.getCompletedAt()) : null);
        long rowModified = db.update("Task", contentValues, "TaskID=?", new String[] {task.getTaskID()+""});
        return rowModified > 0;
    }
}
