package com.example.taskmanagerment.services;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.taskmanagerment.R;
import com.example.taskmanagerment.customlistview.TaskGroupViewHolder;
import com.example.taskmanagerment.models.TaskGroup;

import java.util.List;

public class TaskGroupAdapter extends RecyclerView.Adapter<TaskGroupViewHolder> {

    private List<TaskGroup> groups;
    private Context context;
    private AppCompatActivity activity;

    public TaskGroupAdapter(List<TaskGroup> groups, AppCompatActivity activity) {
        this.groups = groups;
        this.context = activity;
        this.activity = activity;
    }

    @NonNull
    @Override
    public TaskGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_group, parent, false);
        return new TaskGroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskGroupViewHolder holder, int position) {
        if(position == groups.size()) {
            holder.getAddNewGroup().setVisibility(View.VISIBLE);
            holder.getGroupNameContainer().setVisibility(View.GONE);
            holder.getListContentContainer().setVisibility(View.GONE);
        }else {
            holder.getGroupName().setText(groups.get(position).getGroupName());
            holder.getTaskListView().setAdapter(new TaskAdapter(activity, groups.get(position).getTasks()));
        }
    }

    @Override
    public int getItemCount() {
        return groups.size()+1;
    }

}
