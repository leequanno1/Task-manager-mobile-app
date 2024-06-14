package com.example.taskmanagerment.models;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {
    private int taskID;
    private int groupID;
    private String taskName;
    private Date createdAt;
    private Date deadline;
    private Date completedAt;
    private String description;
    private String imageURL;
//    private int notifyWhen;

    public Task(int taskID, int groupID, String taskName, Date createdAt, Date deadline, Date completedAt, String description, String imageURL) {
        this.taskID = taskID;
        this.groupID = groupID;
        this.taskName = taskName;
        this.createdAt = createdAt;
        this.deadline = deadline;
        this.completedAt = completedAt;
        this.description = description;
        this.imageURL = imageURL;
    }

    public Task() {
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
