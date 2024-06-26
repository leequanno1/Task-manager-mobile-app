package com.example.taskmanagerment.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taskmanagerment.R;
import com.example.taskmanagerment.TaskDetails;
import com.example.taskmanagerment.models.Task;

import java.util.Date;
import java.util.List;


public class TaskAdapter extends ArrayAdapter<Task> {

    Context context;

    List<Task> resource;

    AppCompatActivity activity;

    int projectID = -1;

    public TaskAdapter(@NonNull AppCompatActivity context, List<Task> resource) {
        super(context, R.layout.task_item, resource);
        this.context = context;
        this.resource = resource;
        this.activity = context;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Task task = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
        }
        LinearLayout taskItem = convertView.findViewById(R.id.taskItem);
        activity.registerForContextMenu(taskItem);

        LinearLayout beginTimeContainer = convertView.findViewById(R.id.beginTimeContainer);
        LinearLayout deadlineTimeContainer = convertView.findViewById(R.id.deadlineTimeContainer);
        TextView beginTime = convertView.findViewById(R.id.beginTime);
        TextView deadlineTime = convertView.findViewById(R.id.deadlineTime);
        TextView taskName = convertView.findViewById(R.id.taskName);

        taskName.setText(task.getTaskName());

        // hide start date if null
        if (task.getCreatedAt() != null) {
            beginTimeContainer.setVisibility(View.VISIBLE);
            beginTime.setText(task.getCreatedAt().toString());
        }else {
            beginTimeContainer.setVisibility(View.GONE);
        }
        // hide end date if null
        if (task.getDeadline() != null && task.getCompletedAt() == null) {
            // set background for end date: yellow if not deadline - red if deadlined
            deadlineTimeContainer.setVisibility(View.VISIBLE);
            deadlineTime.setText(task.getDeadline().toString());
            if(task.getDeadline().after(new Date())) {
                // yellow
                deadlineTimeContainer.setBackgroundColor(context.getResources().getColor(R.color.color2));
            } else {
                // red
                deadlineTimeContainer.setBackgroundColor(context.getResources().getColor(R.color.color3));
            }
        }else {
            deadlineTimeContainer.setVisibility(View.GONE);
        }

        // task click event
        taskItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TaskDetails.class);
                intent.putExtra("task", task);
                intent.putExtra("projectID", projectID);
                activity.startActivityForResult(intent, 1);
            }
        });


        return convertView;
    }




}
