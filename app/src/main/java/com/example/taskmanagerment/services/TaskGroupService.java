package com.example.taskmanagerment.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.taskmanagerment.BoardActivity;
import com.example.taskmanagerment.database.DatabaseHelper;
import com.example.taskmanagerment.models.Task;
import com.example.taskmanagerment.models.TaskGroup;

import java.util.ArrayList;
import java.util.List;

public class TaskGroupService {
    private DatabaseHelper databaseHelper;
    private Context context;

    public TaskGroupService(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
        this.context = context;
    }

    // Phương thức tạo mới TaskGroup
    public long addTaskGroup(int projectId, String groupName) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("ProjectID", projectId);
        values.put("GroupName", groupName);

        long groupId = db.insert("TaskGroup", null, values);
        db.close();

        return groupId;
    }

    // Phương thức lấy danh sách tất cả group dựa vào project id
    public List<TaskGroup> getTaskGroupsByProjectId(int projectId) {
        List<TaskGroup> taskGroups = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        TaskService taskService = new TaskService(context);

        Cursor cursor = db.query("TaskGroup", null, "ProjectID=?", new String[]{String.valueOf(projectId)}, null, null, null);
        if (cursor != null) {
            int groupIdIndex = cursor.getColumnIndex("GroupID");
            int projectNameIndex = cursor.getColumnIndex("ProjectID");
            int groupNameIndex = cursor.getColumnIndex("GroupName");
            int createdAtIndex = cursor.getColumnIndex("CreatedAt");

            while (cursor.moveToNext()) {
                if (groupIdIndex != -1 && projectNameIndex != -1 && groupNameIndex != -1 && createdAtIndex != -1) {
                    int groupId = cursor.getInt(groupIdIndex);
                    int projId = cursor.getInt(projectNameIndex);
                    String groupName = cursor.getString(groupNameIndex);
                    String createdAt = cursor.getString(createdAtIndex);

                    TaskGroup taskGroup = new TaskGroup(groupId, projId, groupName, null, taskService.getAllTasksByGroupId(groupId));
                    taskGroups.add(taskGroup);
                }
            }
            cursor.close();
        }

        db.close();
        return taskGroups;
    }


    // Phương thức cập nhật tên group dựa vào ID
    public boolean updateGroupNameById(long groupId, String newName) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("GroupName", newName);

        int rowsAffected = db.update("TaskGroup", values, "GroupID=?", new String[]{String.valueOf(groupId)});
        db.close();

        return rowsAffected > 0;
    }


    // Phương thức xóa TaskGroup dựa vào ID
    public boolean deleteTaskGroup(long groupId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        int rowsAffected = db.delete("TaskGroup", "GroupID=?", new String[]{String.valueOf(groupId)});
        db.close();

        return rowsAffected > 0;
    }

    // Phương thức xóa tất cả TaskGroup dựa vào ProjectID
    public boolean deleteAllTaskGroupsByProjectId(long projectId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        int rowsAffected = db.delete("TaskGroup", "ProjectID=?", new String[]{String.valueOf(projectId)});
        db.close();

        return rowsAffected > 0;
    }

}
