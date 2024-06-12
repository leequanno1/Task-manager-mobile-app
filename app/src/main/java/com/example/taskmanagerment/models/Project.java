package com.example.taskmanagerment.models;

public class Project {

    private int projectId;

    private String projectName;

    private String createdAt;

    private int firstGroupId;


    public Project(int projectId, String projectName, String createdAt, int firstGroupId) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.createdAt = createdAt;
        this.firstGroupId = firstGroupId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getFirstGroupId() {
        return firstGroupId;
    }

    public void setFirstGroupId(int firstGroupId) {
        this.firstGroupId = firstGroupId;
    }

}
