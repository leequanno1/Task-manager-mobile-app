package com.example.taskmanagerment;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;

import com.example.taskmanagerment.models.Tag;
import com.example.taskmanagerment.services.TagListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SelectTag extends AppCompatActivity {

    ListView tagListView;
    List<Tag> tags, selectedTags;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_tag);
        tagListView = findViewById(R.id.tagListView);

        tags = new ArrayList<>();
        tags.add(new Tag(1,1,"tag1", Color.parseColor("#ccccaa")));
        tags.add(new Tag(2,1,"tag2222222222222", Color.parseColor("#ccccbb")));
        tags.add(new Tag(3,1,"tag3", Color.parseColor("#cccccc")));
        tags.add(new Tag(4,1,"tag4", Color.parseColor("#ccccdd")));
        tags.add(new Tag(5,1,"tag5", Color.parseColor("#ccccee")));
        tags.add(new Tag(6,1,"tag6", Color.parseColor("#ccccff")));
        selectedTags = new ArrayList<>();
        selectedTags.add(new Tag(1,1,"tag1", Color.parseColor("#ccccaa")));
        selectedTags.add(new Tag(2,1,"tag2222222222222", Color.parseColor("#ccccbb")));
        selectedTags.add(new Tag(3,1,"tag3", Color.parseColor("#cccccc")));

        TagListViewAdapter tagListViewAdapter = new TagListViewAdapter(this, tags, selectedTags);
        tagListView.setAdapter(tagListViewAdapter);
    }
}