package com.example.taskmanagerment.models;

public class Task {
    private int taskId;

    private int groupId;

    private String taskName;

    private String createdAt;

    private String deadline;

    private String completedAt;

    private String description;

    private String imageUrl;


    public Task(int taskId, int groupId, String taskName, String createdAt, String deadline, String completedAt, String description, String imageUrl) {
        this.taskId = taskId;
        this.groupId = groupId;
        this.taskName = taskName;
        this.createdAt = createdAt;
        this.deadline = deadline;
        this.completedAt = completedAt;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


}
