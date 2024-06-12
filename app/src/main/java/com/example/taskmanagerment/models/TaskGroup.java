package com.example.taskmanagerment.models;

import java.util.List;

public class TaskGroup {

    private int groupId;

    private int projectId;

    private String groupName;

    private String createdAt;

    private int prevGroupId;

    private int nextGroupId;

    private List<Task> tasks;


    public TaskGroup(int groupId, int projectId, String groupName, String createdAt, int prevGroupId, int nextGroupId, List<Task> tasks) {
        this.groupId = groupId;
        this.projectId = projectId;
        this.groupName = groupName;
        this.createdAt = createdAt;
        this.prevGroupId = prevGroupId;
        this.nextGroupId = nextGroupId;
        this.tasks = tasks;
    }

    public TaskGroup() {
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getPrevGroupId() {
        return prevGroupId;
    }

    public void setPrevGroupId(int prevGroupId) {
        this.prevGroupId = prevGroupId;
    }

    public int getNextGroupId() {
        return nextGroupId;
    }

    public void setNextGroupId(int nextGroupId) {
        this.nextGroupId = nextGroupId;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
