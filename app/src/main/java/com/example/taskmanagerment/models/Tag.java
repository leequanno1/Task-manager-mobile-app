package com.example.taskmanagerment.models;

public class Tag {
    private int tagID;
    private int projectID;
    private String tagName;
    private int tagColor;

    public Tag() {
        setTagID(0);
        setProjectID(0);
        setTagName("");
        setTagColor(1);
    }

    public Tag(int tagID, int projectID, String tagName, int tagColor) {
        this.tagID = tagID;
        this.projectID = projectID;
        this.tagName = tagName;
        this.tagColor = tagColor;
    }

    public int getTagID() {
        return tagID;
    }

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getTagColor() {
        return tagColor;
    }

    public void setTagColor(int tagColor) {
        this.tagColor = tagColor;
    }
}
