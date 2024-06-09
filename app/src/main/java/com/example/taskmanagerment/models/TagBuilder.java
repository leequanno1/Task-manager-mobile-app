package com.example.taskmanagerment.models;

public class TagBuilder {
    private Tag tag;
    private final String ERR_TAG_ID_NOT_SET = "Tag id doesn't set!!";
    private final String ERR_PROJECT_ID_NOT_SET = "Project id doesn't set!!";

    public TagBuilder() {
        this.tag = new Tag();
    }

    public TagBuilder setTagID(int tagID) {
        this.tag.setTagID(tagID);
        return this;
    }

    public TagBuilder setProjectID(int projectID) {
        this.tag.setProjectID(projectID);
        return this;
    }

    public TagBuilder setTagName(String tagName) {
        this.tag.setTagName(tagName);
        return this;
    }

    public TagBuilder setTagColor(int tagColor) {
        this.tag.setTagColor(tagColor);
        return this;
    }

    public Tag buildSecured() throws ExceptionInInitializerError {
        if (tag.getProjectID() == 0) {
            throw new ExceptionInInitializerError(ERR_PROJECT_ID_NOT_SET);
        }
        if (tag.getTagID() == 0) {
            throw new ExceptionInInitializerError(ERR_TAG_ID_NOT_SET);
        }
        return tag;
    }

    public Tag build(){
        return tag;
    }
}
