package com.example.taskmanagerment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.taskmanagerment.models.Tag;
import com.example.taskmanagerment.models.TagList;
import com.example.taskmanagerment.services.TagListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SelectTag extends AppCompatActivity {

    ListView tagListView;
    List<Tag> tags, selectedTags;
    ImageButton rollBackButton;
    TagListViewAdapter tagListViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_tag);
        tagListView = findViewById(R.id.tagListView);
        rollBackButton = findViewById(R.id.rollBack);
        selectedTags = new ArrayList<>(getSelectedTags());


        tags = new ArrayList<>();
        tags.add(new Tag(1,1,"tag1", "#aaaaaa"));
        tags.add(new Tag(2,1,"tag2222222222222", "#aaaabb"));
        tags.add(new Tag(3,1,"tag3", "#aaaacc"));
        tags.add(new Tag(4,1,"tag4", "#aaaadd"));
        tags.add(new Tag(5,1,"tag5", "#aaaaee"));
        tags.add(new Tag(6,1,"tag6", "#aaaaff"));

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
    }

    private List<Tag> getSelectedTags () {
        Intent intent = getIntent();
        TagList tagList = (TagList) intent.getSerializableExtra("tags");
        return tagList.getTags();
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
        }
    }

    private void handleIntentCancel() {
    }

    private void handleIntentOK(Intent data) {
        int pos = data.getIntExtra("index", -1) ;
        Tag tag = (Tag) data.getSerializableExtra("tag");
        tags.set(pos,tag);
        tagListViewAdapter.notifyDataSetChanged();
    }
}