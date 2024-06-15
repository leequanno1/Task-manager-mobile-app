package com.example.taskmanagerment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.taskmanagerment.models.Tag;
import com.example.taskmanagerment.models.TagList;
import com.example.taskmanagerment.services.TagListViewAdapter;
import com.example.taskmanagerment.services.TagService;

import java.util.ArrayList;
import java.util.List;

public class SelectTag extends AppCompatActivity {
    public static final int ADD_NEW_TAG_REQUEST = 2;

    ListView tagListView;
    List<Tag> tags, selectedTags;
    int projectID;
    ImageButton rollBackButton;
    TagListViewAdapter tagListViewAdapter;
    Button addNewTag;

    TagService tagService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_tag);
        tagListView = findViewById(R.id.tagListView);
        rollBackButton = findViewById(R.id.rollBack);
        addNewTag = findViewById(R.id.addNewTag);
        selectedTags = new ArrayList<>(getSelectedTags());
        projectID = getProjectID();
        tagService = new TagService(SelectTag.this);
        tags = tagService.getTags(projectID);
        Toast.makeText(this, projectID+"", Toast.LENGTH_SHORT).show();

        tagListViewAdapter = new TagListViewAdapter(this, tags, selectedTags);
        tagListView.setAdapter(tagListViewAdapter);

        // roll back click
        rollBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                intent.putExtra("tags", new TagList(selectedTags));
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        addNewTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectTag.this, EditTag.class);
                SelectTag.this.startActivityForResult(intent, ADD_NEW_TAG_REQUEST);
            }
        });
    }

    private List<Tag> getSelectedTags () {
        Intent intent = getIntent();
        TagList tagList = (TagList) intent.getSerializableExtra("tags");
        return tagList.getTags();
    }

    private int getProjectID() {
        Intent intent = getIntent();
        int projectID = intent.getIntExtra("projectID", -1);
        return projectID;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if(resultCode == RESULT_CANCELED) {
                handleIntentCancel();
            }
            if(resultCode == RESULT_OK) {
                handleIntentOK(data);
            }
            if(resultCode == EditTag.RESULT_DELETE){
                handleDeleteTag(data);
            }
        }
        if(requestCode == ADD_NEW_TAG_REQUEST) {
            if(resultCode == RESULT_CANCELED) {
                handleIntentCancel();
            }
            if(resultCode == RESULT_OK){
                handleAddNewTag(data);
            }
        }
    }

    private void handleDeleteTag(Intent data) {
        int pos = data.getIntExtra("index", -1) ;
        tags.remove(pos);
        tagListViewAdapter.notifyDataSetChanged();
    }

    private void handleIntentCancel() {
    }

    private void handleIntentOK(Intent data) {
        int pos = data.getIntExtra("index", -1) ;
        Tag tag = (Tag) data.getSerializableExtra("tag");
        tagService.replace(tag);
        tags.set(pos,tag);
        findAndReplaceTag(selectedTags, tag);
        tagListViewAdapter.notifyDataSetChanged();
    }

    private void handleAddNewTag(Intent data) {
        Tag tag = (Tag) data.getSerializableExtra("tag");
        tag.setProjectID(projectID);
        Toast.makeText(this,tag.toString(),0).show();
        Toast.makeText(SelectTag.this, tagService.addNewTag(tag)+"", Toast.LENGTH_SHORT).show();
        tags.add(tag);
        tagListViewAdapter.notifyDataSetChanged();
    }

    private void findAndReplaceTag(List<Tag> tags, Tag tag) {
        for(int i = 0; i < tags.size(); i++) {
            if(tags.get(i).getTagID() == tag.getTagID()) {
                tags.set(i,tag);
                break;
            }
        }
    }
}