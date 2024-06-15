package com.example.taskmanagerment.models;

import java.io.Serializable;

public class Project implements Serializable {

    private int projectId;

    private String projectName;

    private String createdAt;

    public Project(int projectId, String projectName, String createdAt) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.createdAt = createdAt;
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


    @Override
    public String toString() {
        return projectName;
    }
}
