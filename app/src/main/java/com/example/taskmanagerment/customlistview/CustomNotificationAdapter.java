package com.example.taskmanagerment.customlistview;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.taskmanagerment.R;
import com.example.taskmanagerment.models.Notification;
import com.example.taskmanagerment.models.Project;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CustomNotificationAdapter extends ArrayAdapter<Notification> {

    private Context context;
    private int resource;
    private List<Notification> notifications;

    public CustomNotificationAdapter(Context context, int resource, List<Notification> notifications) {
        super(context, resource, notifications);
        this.context = context;
        this.resource = resource;
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, null);
        }

        LinearLayout containNotificationItem = view.findViewById(R.id.contain_notification_item);
        TextView notifyIdTextView = view.findViewById(R.id.notification_id);
        TextView taskIdTextView = view.findViewById(R.id.task_id);
        TextView notifyContentTextView = view.findViewById(R.id.notification_title);
        TextView notifyTimeTextView = view.findViewById(R.id.notification_date);
        TextView projectID = view.findViewById(R.id.project_id);

        Notification notification = notifications.get(position);

        notifyIdTextView.setText(String.valueOf(notification.getId()));
        taskIdTextView.setText(String.valueOf(notification.getTaskID()));
        notifyContentTextView.setText(notification.getContent());
        notifyTimeTextView.setText(dateFormat(notification.getTime()));
        projectID.setText(String.valueOf(notification.getProjectID()));

        if (notification.isRead()) {
            containNotificationItem.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            containNotificationItem.setBackgroundColor(Color.parseColor("#F6F1F1"));
        }


        return view;
    }

    private String dateFormat(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return dateFormat.format(date);
    }

}
