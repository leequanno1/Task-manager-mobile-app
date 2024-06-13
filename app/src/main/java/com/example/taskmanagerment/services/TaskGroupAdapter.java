package com.example.taskmanagerment.services;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.taskmanagerment.R;
import com.example.taskmanagerment.customlistview.TaskGroupViewHolder;
import com.example.taskmanagerment.models.Task;
import com.example.taskmanagerment.models.TaskGroup;

import java.util.ArrayList;
import java.util.Arrays;
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
            holder.getAddNewGroup().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.getAddNewGroup().setVisibility(View.GONE);
                    holder.getEditGroupContainer().setVisibility(View.VISIBLE);
                }
            });

            holder.getEditGroupCancel().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.getEditGroupContainer().setVisibility(View.GONE);
                    holder.getAddNewGroup().setVisibility(View.VISIBLE);
                }
            });

            // Thêm Group
            holder.getEditGroupConfirm().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!holder.getEditGroupName().getText().toString().isEmpty()) {
                        holder.getEditGroupContainer().setVisibility(View.GONE);
                        // add new group to database here
                        // ->
                        int groupID = 0;
                        // thêm taskgroup vào datebase rồi gán vào đây
                        groups.add(new TaskGroup(groupID,1,holder.getEditGroupName().getText().toString(),null, new ArrayList<>()));

                        holder.getGroupNameContainer().setVisibility(View.VISIBLE);
                        holder.getListContentContainer().setVisibility(View.VISIBLE);
                        holder.getGroupName().setText(holder.getEditGroupName().getText().toString());
                        notifyDataSetChanged();
                    }
                }
            });
        }else {
            holder.getGroupName().setText(groups.get(position).getGroupName());
            holder.getTaskListView().setAdapter(new TaskAdapter(activity, groups.get(position).getTasks()));
        }

        holder.getAddNewTask().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.getNewTaskNameContainer().setVisibility(View.VISIBLE);
                holder.getAddNewTask().setVisibility(View.GONE);
            }
        });

        holder.getAddNewTaskCancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.getNewTaskNameContainer().setVisibility(View.GONE);
                holder.getAddNewTask().setVisibility(View.VISIBLE);
            }
        });

        // Thêm new Task
        holder.getAddNewTaskConfirm().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!holder.getNewTaskName().getText().toString().isEmpty()) {
                    holder.getNewTaskNameContainer().setVisibility(View.GONE);
                    holder.getAddNewTask().setVisibility(View.VISIBLE);
                    int groupID = groups.get(position).getGroupId();
                    // Thêm vào datebase và gán vào đây
                    // ->
                    groups.get(position).getTasks().add(new Task(100,groupID, holder.getNewTaskName().getText().toString(), null, null, null, "", ""));
                    notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return groups.size()+1;
    }


}
