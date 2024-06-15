package com.example.taskmanagerment.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TaskGroup implements Serializable {

    private int groupId;

    private int projectId;

    private String groupName;

    private Date createdAt;

    private List<Task> tasks;

    public TaskGroup() {
    }

    public TaskGroup(int groupId, int projectId, String groupName, Date createdAt, List<Task> tasks) {
        this.groupId = groupId;
        this.projectId = projectId;
        this.groupName = groupName;
        this.createdAt = createdAt;
        this.tasks = tasks;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return groupName;
    }
}
