package com.example.taskmanagerment.services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagerment.R;
import com.example.taskmanagerment.customlistview.TaskGroupViewHolder;
import com.example.taskmanagerment.models.Task;
import com.example.taskmanagerment.models.TaskGroup;

import java.util.ArrayList;
import java.util.List;

public class TaskGroupAdapter extends RecyclerView.Adapter<TaskGroupViewHolder> {

    private List<TaskGroup> groups;

    private Context context;

    private AppCompatActivity activity;

    private int projectID;

    private int selectedTaskID = -1;

    private int selectedGroupID = -1;


    public TaskGroupAdapter(List<TaskGroup> groups, AppCompatActivity activity) {
        this.groups = groups;
        this.context = activity;
        this.activity = activity;
    }

    public TaskGroupAdapter(List<TaskGroup> groups, AppCompatActivity activity, int projectID) {
        this.groups = groups;
        this.context = activity;
        this.activity = activity;
        this.projectID = projectID;
    }

    @NonNull
    @Override
    public TaskGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_group, parent, false);
        return new TaskGroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskGroupViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (position == groups.size()) {
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
                        TaskGroupService taskGroupService = new TaskGroupService(context);
                        String groupName = holder.getEditGroupName().getText().toString();
                        long newGroupId = taskGroupService.addTaskGroup(projectID, groupName);

                        if (newGroupId != -1) {
                            groups.add(new TaskGroup((int) newGroupId, projectID, groupName, null, new ArrayList<>()));

                            holder.getGroupNameContainer().setVisibility(View.VISIBLE);
                            holder.getListContentContainer().setVisibility(View.VISIBLE);
                            holder.getGroupName().setText(groupName);

                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Failed to create Task Group", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });

        } else {
            holder.getGroupName().setText(groups.get(position).getGroupName());
            TaskAdapter taskAdapter = new TaskAdapter(activity, groups.get(position).getTasks());
            taskAdapter.setProjectID(projectID);
            holder.getTaskListView().setAdapter(taskAdapter);

            activity.registerForContextMenu(holder.getTaskListView());

            holder.getTaskListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedTaskID = groups.get(position).getTasks().get(i).getTaskID();
                    selectedGroupID = groups.get(position).getGroupId();

                    return false;
                }
            });

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
                holder.getNewTaskName().setText("");
            }
        });

        // Thêm new Task
        holder.getAddNewTaskConfirm().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!holder.getNewTaskName().getText().toString().isEmpty()) {
                    holder.getNewTaskNameContainer().setVisibility(View.GONE);
                    holder.getAddNewTask().setVisibility(View.VISIBLE);
                    int groupID = groups.get(position).getGroupId();
                    // Thêm vào datebase và gán vào đây

                    TaskService taskService = new TaskService(context);
                    long taskID = taskService.addTask(groupID, holder.getNewTaskName().getText().toString(), null, null, null);

                    groups.get(position).getTasks().add(new Task((int) taskID, groupID, holder.getNewTaskName().getText().toString(), null, null, null, "", ""));
                    holder.getNewTaskName().setText("");
                    notifyDataSetChanged();
                }
            }
        });

        holder.getGroupMenu().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(activity, view);
                popupMenu.getMenuInflater().inflate(R.menu.context_menu_for_delete_group_item, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.delete_group_item) {
                            TaskGroupService taskGroupService = new TaskGroupService(context);
                            taskGroupService.deleteTaskGroup(groups.get(position).getGroupId());

//                            List<TaskGroup> groupsClone = new ArrayList<>(groups);
//                            groupsClone.remove(position);
//                            groups.clear();
//                            groups.addAll(groupsClone);

                            groups.remove(position);

                            notifyDataSetChanged();
                        }
                        return true;
                    }
                });

                popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return getGroupSize() + 1;
    }

    public int getSelectedTaskID() {
        return selectedTaskID;
    }

    public int getSelectedGroupID() {
        return selectedGroupID;
    }

    private int getGroupSize() {
        return groups.size();
    }


}
