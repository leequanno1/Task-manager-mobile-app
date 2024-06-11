package com.example.taskmanagerment.models;

public class TaskGroup {

    private int groupId;

    private int projectId;

    private String groupName;

    private String createdAt;

    private int prevGroupId;

    private int nextGroupId;

    public TaskGroup(int groupId, int projectId, String groupName, String createdAt, int prevGroupId, int nextGroupId) {
        this.groupId = groupId;
        this.projectId = projectId;
        this.groupName = groupName;
        this.createdAt = createdAt;
        this.prevGroupId = prevGroupId;
        this.nextGroupId = nextGroupId;
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

}
