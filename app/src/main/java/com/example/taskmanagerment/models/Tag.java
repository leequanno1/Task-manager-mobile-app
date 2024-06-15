package com.example.taskmanagerment.models;

import java.io.Serializable;
import java.util.Objects;

public class Tag implements Serializable {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return tagID == tag.tagID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagID, projectID, tagName, tagColor);
    }

    private int tagID;
    private int projectID;
    private String tagName;
    private String tagColor;

    public Tag() {
        setTagID(0);
        setProjectID(0);
        setTagName("");
        setTagColor("#ffffff");
    }

    public Tag(int tagID, int projectID, String tagName, String tagColor) {
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

    public String getTagColor() {
        return tagColor;
    }

    public void setTagColor(String tagColor) {
        this.tagColor = tagColor;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagID=" + tagID +
                ", projectID=" + projectID +
                ", tagName='" + tagName + '\'' +
                ", tagColor='" + tagColor + '\'' +
                '}';
    }


}
