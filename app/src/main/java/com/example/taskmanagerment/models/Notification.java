package com.example.taskmanagerment.models;

public class Notification {

    private int notificationId;

    private int taskId;

    private String projectName;

    private String taskName;

    private String deadlineTime;


    public Notification(int notificationId, int taskId, String projectName, String taskName, String deadlineTime) {
        this.notificationId = notificationId;
        this.taskId = taskId;
        this.projectName = projectName;
        this.taskName = taskName;
        this.deadlineTime = deadlineTime;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(String deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

}
