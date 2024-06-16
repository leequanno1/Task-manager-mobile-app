package com.example.taskmanagerment.models;

import java.io.Serializable;
import java.util.Date;

public class Notification implements Serializable {

    private int id;

    private int taskID;

    private int projectID;

    private String content;

    private Date time;

    private boolean isRead;


    public Notification(int id, String content, Date time, boolean isRead, int taskID, int projectID) {
        this.id = id;
        this.content = content;
        this.time = time;
        this.isRead = isRead;
        this.taskID = taskID;
        this.projectID = projectID;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Date getTime() {
        return time;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }
}
