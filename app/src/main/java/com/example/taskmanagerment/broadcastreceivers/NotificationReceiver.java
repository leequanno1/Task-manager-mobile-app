package com.example.taskmanagerment.broadcastreceivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.app.NotificationChannel;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.taskmanagerment.R;
import com.example.taskmanagerment.TaskDetails;
import com.example.taskmanagerment.models.Task;
import com.example.taskmanagerment.services.NotificationService;
import com.example.taskmanagerment.services.TaskService;

public class NotificationReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "Channel_ID";
    private static int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotificationChannel(context);

        String taskName = intent.getStringExtra("taskName");
        String projectName = intent.getStringExtra("projectName");
        String deadlineTime = intent.getStringExtra("deadlineTimeFormatted");

        Intent resultIntent = new Intent(context, TaskDetails.class);
        TaskService taskService = new TaskService(context);
        NotificationService notificationService = new NotificationService(context);
        int taskID = intent.getIntExtra("taskID", 0);
        int notificationID = (int) intent.getLongExtra("notificationID", 0);

        notificationService.markAsRead(notificationID);

        resultIntent.putExtra("task", taskService.getTaskById(taskID));
        resultIntent.putExtra("projectID", intent.getIntExtra("projectID", -1));
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.taskmanagement)
                .setContentTitle("New announcement from Task Management")
                .setContentText(taskName + " in " + projectName + " expired at " + deadlineTime)
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
}



