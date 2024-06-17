package com.example.taskmanagerment.broadcastreceivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.app.NotificationChannel;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.taskmanagerment.R;
import com.example.taskmanagerment.TaskDetails;
import com.example.taskmanagerment.models.NotifyWhen;
import com.example.taskmanagerment.models.Task;
import com.example.taskmanagerment.services.NotificationService;
import com.example.taskmanagerment.services.TaskService;

import java.util.Date;

public class NotificationReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "Channel_ID";

    private static int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotificationChannel(context);

        // Data to show content of notification
        String taskName = intent.getStringExtra("taskName");
        String projectName = intent.getStringExtra("projectName");
        String deadlineTime = intent.getStringExtra("deadlineTimeFormatted");
        int notifyWhen = intent.getIntExtra("notifyWhen", -1);

        // Send intent to TaskDetails
        Intent resultIntent = new Intent(context, TaskDetails.class);
        TaskService taskService = new TaskService(context);
        NotificationService notificationService = new NotificationService(context);

        // Get data for save to the database
        int taskID = intent.getIntExtra("taskID", 0);
        int projectID = intent.getIntExtra("projectID", 0);
        Date deadline = (Date) intent.getSerializableExtra("deadline");
        String notificationContent = intent.getStringExtra("notificationContent");

        int notificationID = (int) notificationService.addNotification(notificationContent, deadline, taskID, projectID);

        resultIntent.putExtra("task", taskService.getTaskById(taskID));
        resultIntent.putExtra("projectID", intent.getIntExtra("projectID", -1));
        resultIntent.putExtra("isNotification", 1);
        resultIntent.putExtra("notificationID", notificationID);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.taskmanagement)
                .setContentTitle("New announcement from Task Management")
                .setContentText(makeBoldText(taskName, projectName, deadlineTime, notifyWhen))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID++, notification);
        }
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Task Notifications";
            String description = "Notification for task reminders";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    // Format notification services
    private SpannableString makeBoldText(String taskName, String projectName, String deadlineTime, int notifyWhen) {
        String content;
        if (notifyWhen == NotifyWhen.AT_DEADLINE) {
            content = taskName + " in " + projectName + " expired at " + deadlineTime;
        } else {
            content = taskName + " in " + projectName + " will expire on " + deadlineTime;
        }

        SpannableString spannableContent = new SpannableString(content);
        spannableContent.setSpan(new StyleSpan(Typeface.BOLD), 0, taskName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableContent.setSpan(new StyleSpan(Typeface.BOLD), content.indexOf(projectName), content.indexOf(projectName) + projectName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableContent.setSpan(new StyleSpan(Typeface.BOLD), content.indexOf(deadlineTime), content.indexOf(deadlineTime) + deadlineTime.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableContent;
    }
}



