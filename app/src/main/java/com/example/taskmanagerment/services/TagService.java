package com.example.taskmanagerment.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.example.taskmanagerment.database.DatabaseHelper;
import com.example.taskmanagerment.models.Project;
import com.example.taskmanagerment.models.Tag;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TagService {
    private DatabaseHelper databaseHelper;
    private Context context;

    public TagService(Context context) {
        databaseHelper = new DatabaseHelper(context);
        this.context = context;
    }

    public long addNewTag(Tag tag) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("ProjectID", tag.getProjectID());
        values.put("TagName", tag.getTagName());
        values.put("TagColor", tag.getTagColor());

        long tagID = db.insert("Tag", null, values);
        db.close();
        return tagID;
    }

    public boolean deleteTag(int tagID) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        int rowsAffected = db.delete("Tag", "TagID=?", new String[]{tagID+""});
        db.close();

        return rowsAffected > 0;
    }

    public List<Tag> getTags(int projectID) {
        List<Tag> tags = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.query("Tag", null, "ProjectID = ?", new String[] {projectID+""}, null, null, null);
        if (cursor != null) {
            int tagIdIndex = cursor.getColumnIndex("TagID");
            int projectIdIndex = cursor.getColumnIndex("ProjectID");
            int tagNameIndex = cursor.getColumnIndex("TagName");
            int tagColorIndex = cursor.getColumnIndex("TagColor");

            while (cursor.moveToNext()) {
                if (tagIdIndex != -1 && projectIdIndex != -1 && tagNameIndex != -1 && tagColorIndex != -1) {
                    int tagId = cursor.getInt(tagIdIndex);
                    int projectId = cursor.getInt(projectIdIndex);
                    String tagName = cursor.getString(tagNameIndex);
                    String tagColor = cursor.getString(tagColorIndex);

                    Tag tag = new Tag(tagId, projectId, tagName, tagColor);
                    tags.add(tag);
                }
            }
            cursor.close();
        }
        db.close();
        return tags;
    }

    public List<Tag> getTags(int projectID, int taskID) {
        List<Tag> tags = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT Tag.* FROM Tag JOIN TaskTag ON Tag.TagID = TaskTag.TagID WHERE TaskID = ? AND ProjectID = ?",
                new String[] {taskID +"", projectID +""});
        if (cursor != null) {
            int tagIdIndex = cursor.getColumnIndex("TagID");
            int projectIdIndex = cursor.getColumnIndex("ProjectID");
            int tagNameIndex = cursor.getColumnIndex("TagName");
            int tagColorIndex = cursor.getColumnIndex("TagColor");

            while (cursor.moveToNext()) {
                if (tagIdIndex != -1 && projectIdIndex != -1 && tagNameIndex != -1 && tagColorIndex != -1) {
                    int tagId = cursor.getInt(tagIdIndex);
                    int projectId = cursor.getInt(projectIdIndex);
                    String tagName = cursor.getString(tagNameIndex);
                    String tagColor = cursor.getString(tagColorIndex);

                    Tag tag = new Tag(tagId, projectId, tagName, tagColor);
                    tags.add(tag);
                }
            }
            cursor.close();
        }
        db.close();
        return tags;
    }

    public Tag replace(@NonNull Tag oldTag, String newTagName, String newTagColor) {
        if(newTagName == null || newTagColor == null) {
            return new Tag(oldTag.getTagID(),oldTag.getProjectID(),oldTag.getTagName(),oldTag.getTagColor());
        } else {
            if(newTagName == null) {
                newTagName = oldTag.getTagName();
            }
            if(newTagColor == null) {
                newTagColor = oldTag.getTagColor();
            }
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("TagName", newTagName);
            values.put("TagColor", newTagColor);
            db.update("Tag", values, "TagID = ?", new String[] {oldTag.getTagID()+""});
            db.close();
            return new Tag(oldTag.getTagID(),oldTag.getProjectID(),newTagName,newTagColor);
        }
    }

    public Tag replace(@NonNull Tag tag) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TagName", tag.getTagName());
        values.put("TagColor", tag.getTagColor());
        db.update("Tag", values, "TagID = ?", new String[] {tag.getTagID()+""});
        db.close();
        return tag;
    }

}
