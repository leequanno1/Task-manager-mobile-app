package com.example.taskmanagerment.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.taskmanagerment.database.DatabaseHelper;
import com.example.taskmanagerment.models.Project;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProjectService {
    private DatabaseHelper databaseHelper;

    public ProjectService(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public long addProject(String projectName) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        // Lấy thời gian hiện tại
        String currentDateTime = getCurrentDateTime();

        ContentValues values = new ContentValues();
        values.put("ProjectName", projectName);
        values.put("CreatedAt", currentDateTime);

        long projectId = db.insert("Project", null, values);
        db.close();

        return projectId;
    }

    // Phương thức để lấy thời gian hiện tại dưới dạng chuỗi
    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }


    public boolean deleteProject(long projectId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        int rowsAffected = db.delete("Project", "ProjectID=?", new String[]{String.valueOf(projectId)});
        db.close();

        return rowsAffected > 0;
    }

    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.query("Project", null, null, null, null, null, null);
        if (cursor != null) {
            int projectIdIndex = cursor.getColumnIndex("ProjectID");
            int projectNameIndex = cursor.getColumnIndex("ProjectName");
            int createdAtIndex = cursor.getColumnIndex("CreatedAt");

            while (cursor.moveToNext()) {
                if (projectIdIndex != -1 && projectNameIndex != -1 && createdAtIndex != -1) {
                    int projectId = cursor.getInt(projectIdIndex);
                    String projectName = cursor.getString(projectNameIndex);
                    String createdAt = cursor.getString(createdAtIndex);

                    Project project = new Project(projectId, projectName, createdAt);
                    projects.add(project);
                }
            }

            cursor.close();
        }

        db.close();

        return projects;
    }

    public List<Project> getProjectsByName(String name) {
        List<Project> projects = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String selection = "ProjectName LIKE ?";
        String[] selectionArgs = new String[]{"%" + name + "%"};

        Cursor cursor = db.query("Project", null, selection, selectionArgs, null, null, null);
        if (cursor != null) {
            int projectIdIndex = cursor.getColumnIndex("ProjectID");
            int projectNameIndex = cursor.getColumnIndex("ProjectName");
            int createdAtIndex = cursor.getColumnIndex("CreatedAt");

            while (cursor.moveToNext()) {
                if (projectIdIndex != -1 && projectNameIndex != -1 && createdAtIndex != -1) {
                    int projectId = cursor.getInt(projectIdIndex);
                    String projectName = cursor.getString(projectNameIndex);
                    String createdAt = cursor.getString(createdAtIndex);

                    Project project = new Project(projectId, projectName, createdAt);
                    projects.add(project);
                }
            }

            cursor.close();
        }

        db.close();

        return projects;
    }

    public boolean isProjectNameExists(String projectName) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projection = {"ProjectID"};
        String selection = "ProjectName = ?";
        String[] selectionArgs = {projectName};

        Cursor cursor = db.query("Project", projection, selection, selectionArgs, null, null, null);

        boolean exists = cursor.getCount() > 0;

        cursor.close();

        db.close();

        return exists;
    }

}