package com.example.taskmanagerment.customlistview;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagerment.R;

public class TaskGroupViewHolder extends RecyclerView.ViewHolder {

    RelativeLayout editGroupContainer;
    RelativeLayout groupNameContainer;
    RelativeLayout listContentContainer;
    ImageButton editGroupCancel, editGroupConfirm, groupMenu;
    EditText editGroupName;
    TextView groupName;
    ListView taskListView;

    Button addNewGroup, addNewTask;
    public TaskGroupViewHolder(@NonNull View itemView) {
        super(itemView);
        addNewGroup = itemView.findViewById(R.id.addNewGroup);
        editGroupContainer = itemView.findViewById(R.id.editGroupContainer);
        editGroupCancel = itemView.findViewById(R.id.editGroupCancel);
        editGroupConfirm = itemView.findViewById(R.id.editGroupConfirm);
        editGroupName = itemView.findViewById(R.id.editGroupName);
        groupNameContainer = itemView.findViewById(R.id.groupNameContainer);
        groupName = itemView.findViewById(R.id.groupName);
        groupMenu = itemView.findViewById(R.id.groupMenu);
        listContentContainer = itemView.findViewById(R.id.listContentContainer);
        taskListView = itemView.findViewById(R.id.taskListView);
        addNewTask = itemView.findViewById(R.id.addNewTask);
    }

    public RelativeLayout getEditGroupContainer() {
        return editGroupContainer;
    }

    public Button getAddNewGroup() {
        return addNewGroup;
    }

    public void setAddNewGroup(Button addNewGroup) {
        this.addNewGroup = addNewGroup;
    }

    public void setEditGroupContainer(RelativeLayout editGroupContainer) {
        this.editGroupContainer = editGroupContainer;
    }

    public RelativeLayout getGroupNameContainer() {
        return groupNameContainer;
    }

    public void setGroupNameContainer(RelativeLayout groupNameContainer) {
        this.groupNameContainer = groupNameContainer;
    }

    public ImageButton getEditGroupCancel() {
        return editGroupCancel;
    }

    public void setEditGroupCancel(ImageButton editGroupCancel) {
        this.editGroupCancel = editGroupCancel;
    }

    public ImageButton getEditGroupConfirm() {
        return editGroupConfirm;
    }

    public void setEditGroupConfirm(ImageButton editGroupConfirm) {
        this.editGroupConfirm = editGroupConfirm;
    }

    public ImageButton getGroupMenu() {
        return groupMenu;
    }

    public void setGroupMenu(ImageButton groupMenu) {
        this.groupMenu = groupMenu;
    }

    public EditText getEditGroupName() {
        return editGroupName;
    }

    public void setEditGroupName(EditText editGroupName) {
        this.editGroupName = editGroupName;
    }

    public TextView getGroupName() {
        return groupName;
    }

    public void setGroupName(TextView groupName) {
        this.groupName = groupName;
    }

    public ListView getTaskListView() {
        return taskListView;
    }

    public void setTaskListView(ListView taskListView) {
        this.taskListView = taskListView;
    }

    public Button getAddNewTask() {
        return addNewTask;
    }

    public void setAddNewTask(Button addNewTask) {
        this.addNewTask = addNewTask;
    }

    public RelativeLayout getListContentContainer() {
        return listContentContainer;
    }

    public void setListContentContainer(RelativeLayout listContentContainer) {
        this.listContentContainer = listContentContainer;
    }
}
