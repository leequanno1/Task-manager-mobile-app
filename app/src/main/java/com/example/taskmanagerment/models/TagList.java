package com.example.taskmanagerment.models;

import java.io.Serializable;
import java.util.List;

public class TagList implements Serializable {
    private List<Tag> tags;

    public TagList(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "TagList{" +
                "tags=" + tags +
                '}';
    }
}
