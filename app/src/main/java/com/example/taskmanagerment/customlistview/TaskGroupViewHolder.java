package com.example.taskmanagerment.customlistview;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagerment.R;

public class TaskGroupViewHolder extends RecyclerView.ViewHolder {

    ViewGroup editGroupContainer;
    ViewGroup groupNameContainer;
    ViewGroup listContentContainer;
    ImageButton editGroupCancel;
    ImageButton editGroupConfirm;
    ImageButton groupMenu;
    ImageButton addNewTaskConfirm;
    ImageButton addNewTaskCancel;
    EditText editGroupName, newTaskName;
    TextView groupName;
    ListView taskListView;
    CardView newTaskNameContainer;
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
        newTaskNameContainer = itemView.findViewById(R.id.newTaskNameContainer);
        addNewTaskConfirm = itemView.findViewById(R.id.addNewTaskConfirm);
        addNewTaskCancel = itemView.findViewById(R.id.addNewTaskCancel);
        newTaskName = itemView.findViewById(R.id.newTaskName);
    }


    public ViewGroup getEditGroupContainer() {
        return editGroupContainer;
    }

    public void setEditGroupContainer(ViewGroup editGroupContainer) {
        this.editGroupContainer = editGroupContainer;
    }

    public ViewGroup getGroupNameContainer() {
        return groupNameContainer;
    }

    public void setGroupNameContainer(ViewGroup groupNameContainer) {
        this.groupNameContainer = groupNameContainer;
    }

    public ViewGroup getListContentContainer() {
        return listContentContainer;
    }

    public void setListContentContainer(ViewGroup listContentContainer) {
        this.listContentContainer = listContentContainer;
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

    public ImageButton getAddNewTaskConfirm() {
        return addNewTaskConfirm;
    }

    public void setAddNewTaskConfirm(ImageButton addNewTaskConfirm) {
        this.addNewTaskConfirm = addNewTaskConfirm;
    }

    public ImageButton getAddNewTaskCancel() {
        return addNewTaskCancel;
    }

    public void setAddNewTaskCancel(ImageButton addNewTaskCancel) {
        this.addNewTaskCancel = addNewTaskCancel;
    }

    public EditText getEditGroupName() {
        return editGroupName;
    }

    public void setEditGroupName(EditText editGroupName) {
        this.editGroupName = editGroupName;
    }

    public EditText getNewTaskName() {
        return newTaskName;
    }

    public void setNewTaskName(EditText newTaskName) {
        this.newTaskName = newTaskName;
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

    public CardView getNewTaskNameContainer() {
        return newTaskNameContainer;
    }

    public void setNewTaskNameContainer(CardView newTaskNameContainer) {
        this.newTaskNameContainer = newTaskNameContainer;
    }

    public Button getAddNewGroup() {
        return addNewGroup;
    }

    public void setAddNewGroup(Button addNewGroup) {
        this.addNewGroup = addNewGroup;
    }

    public Button getAddNewTask() {
        return addNewTask;
    }

    public void setAddNewTask(Button addNewTask) {
        this.addNewTask = addNewTask;
    }
}
