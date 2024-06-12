package com.example.taskmanagerment.models;

public class TaskTag {

    private int taskTagId;

    private int taskId;

    private int tagId;

    public TaskTag(int taskTagId, int taskId, int tagId) {
        this.taskTagId = taskTagId;
        this.taskId = taskId;
        this.tagId = tagId;
    }

    public int getTaskTagId() {
        return taskTagId;
    }

    public void setTaskTagId(int taskTagId) {
        this.taskTagId = taskTagId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }


}
