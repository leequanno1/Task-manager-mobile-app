package com.example.taskmanagerment;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.taskmanagerment.models.Tag;
import com.example.taskmanagerment.models.TagList;
import com.example.taskmanagerment.services.TagListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SelectTag extends AppCompatActivity {
    public static final int ADD_NEW_TAG_REQUEST = 2;

    ListView tagListView;
    List<Tag> tags, selectedTags;
    ImageButton rollBackButton;
    TagListViewAdapter tagListViewAdapter;
    Button addNewTag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_tag);
        tagListView = findViewById(R.id.tagListView);
        rollBackButton = findViewById(R.id.rollBack);
        addNewTag = findViewById(R.id.addNewTag);
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
        tags.set(pos,tag);
        tagListViewAdapter.notifyDataSetChanged();
    }

    private void handleAddNewTag(Intent data) {
        Tag tag = (Tag) data.getSerializableExtra("tag");
        Toast toast = new Toast(this);
        toast.setText(tag.getTagName());
        toast.show();
        tags.add(tag);
        tagListViewAdapter.notifyDataSetChanged();
    }
}