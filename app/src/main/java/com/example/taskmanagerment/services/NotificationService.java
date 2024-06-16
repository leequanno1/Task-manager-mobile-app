package com.example.taskmanagerment.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.taskmanagerment.database.DatabaseHelper;
import com.example.taskmanagerment.models.Notification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationService {

    private DatabaseHelper databaseHelper;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public NotificationService(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
    }

    // Phương thức thêm Notification
    public long addNotification(String notificationContent, Date notificationTime, int taskID, int projectID) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("NotificationContent", notificationContent);
        values.put("NotificationTime", dateFormat.format(notificationTime));
        values.put("TaskID", taskID);
        values.put("ProjectID", projectID);

        long notificationID = db.insert("Notification", null, values);
        db.close();

        return notificationID;
    }

    // Phương thức kiểm tra xem notificationID và taskID có tồn tại trong cơ sở dữ liệu hay không
    public boolean areIdsValid(int notificationID, int taskID) {
        boolean isNotificationExist = isNotificationExist(notificationID);
        boolean isTaskExist = isTaskExist(taskID);

        // Nếu cả notificationID và taskID đều tồn tại thì trả về true, ngược lại trả về false
        return isNotificationExist && isTaskExist;
    }

    // Phương thức kiểm tra xem notificationID có tồn tại trong bảng Notification hay không
    public boolean isNotificationExist(int notificationID) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.query("Notification", new String[]{"NotificationID"}, "NotificationID = ?", new String[]{String.valueOf(notificationID)}, null, null, null);
        boolean exists = cursor != null && cursor.getCount() > 0;

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return exists;
    }


    // Phương thức kiểm tra xem taskID có tồn tại trong bảng Task hay không
    public boolean isTaskExist(int taskID) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.query("Task", new String[]{"TaskID"}, "TaskID = ?", new String[]{String.valueOf(taskID)}, null, null, null);
        boolean exists = cursor != null && cursor.getCount() > 0;

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return exists;
    }


    // Phương thức lấy danh sách tất cả thông báo
    public List<Notification> getAllNotifications() {
        List<Notification> notifications = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.query("Notification", null, null, null, null, null, "NotificationTime DESC");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Notification notification = parseNotification(cursor);
                notifications.add(notification);
            } while (cursor.moveToNext());

            cursor.close();
        }
        db.close();
        return notifications;
    }

    // Phương thức lấy tất cả thông báo đã đọc
    public List<Notification> getReadNotifications() {
        List<Notification> notifications = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.query("Notification", null, "IsRead = ?", new String[]{"1"}, null, null, "NotificationTime DESC");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Notification notification = parseNotification(cursor);
                notifications.add(notification);
            } while (cursor.moveToNext());

            cursor.close();
        }
        db.close();
        return notifications;
    }

    // Phương thức lấy tất cả thông báo chưa đọc
    public List<Notification> getUnreadNotifications() {
        List<Notification> notifications = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.query("Notification", null, "IsRead = ?", new String[]{"0"}, null, null, "NotificationTime DESC");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Notification notification = parseNotification(cursor);
                notifications.add(notification);
            } while (cursor.moveToNext());

            cursor.close();
        }
        db.close();
        return notifications;
    }

    // Phương thức đánh dấu thông báo là đã đọc
    public void markAsRead(int notificationID) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("IsRead", 1);

        db.update("Notification", values, "NotificationID = ?", new String[]{String.valueOf(notificationID)});
        db.close();
    }

    // Phương thức đánh dấu thông báo là chưa đọc đọc
    public void markAsUnRead(int notificationID) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("IsRead", 0);

        db.update("Notification", values, "NotificationID = ?", new String[]{String.valueOf(notificationID)});
        db.close();
    }

    // Phương thức để parse dữ liệu từ cursor sang đối tượng Notification
    private Notification parseNotification(Cursor cursor) {
        try {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("NotificationID"));
            int taskId = cursor.getInt(cursor.getColumnIndexOrThrow("TaskID"));
            int projectID = cursor.getInt(cursor.getColumnIndexOrThrow("ProjectID"));
            String content = cursor.getString(cursor.getColumnIndexOrThrow("NotificationContent"));
            String timeStr = cursor.getString(cursor.getColumnIndexOrThrow("NotificationTime"));
            int isRead = cursor.getInt(cursor.getColumnIndexOrThrow("IsRead"));

            Date time = dateFormat.parse(timeStr);
            return new Notification(id, content, time, isRead == 1, taskId, projectID);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Phương thức đánh dấu tất cả thông báo là đã đọc
    public void markAllAsRead() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("IsRead", 1);

        db.update("Notification", values, null, null);
        db.close();
    }

    // Phương thức xóa một thông báo dựa vào notificationID và trả về boolean biểu thị việc xóa thành công hay không
    public boolean deleteNotification(int notificationID) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int rowsAffected = db.delete("Notification", "NotificationID = ?", new String[]{String.valueOf(notificationID)});
        db.close();
        return rowsAffected > 0;
    }



}
