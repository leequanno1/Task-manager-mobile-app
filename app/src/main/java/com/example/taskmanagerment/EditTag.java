package com.example.taskmanagerment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.taskmanagerment.models.Tag;
import com.example.taskmanagerment.models.TagBuilder;
import com.example.taskmanagerment.adapter.ColorListViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditTag extends AppCompatActivity {

    public static final int RESULT_DELETE = 4;
    private EditText tagName;
    private ListView colorListView;
    private ImageButton rollBack, confirm , delete;
    private Tag tag;
    private Intent intent;
    private int index, requestCode;
    private ColorListViewAdapter colorListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tag);
        tagName = (EditText) findViewById(R.id.tagName);
        colorListView = (ListView) findViewById(R.id.colorListView);
        rollBack = (ImageButton) findViewById(R.id.rollBack);
        confirm = (ImageButton) findViewById(R.id.confirm);
        delete = (ImageButton) findViewById(R.id.delete);
        intent = getIntent();
        tag = (Tag) intent.getSerializableExtra("tag");
        if(tag == null) {
            int projectID = intent.getIntExtra("projectID", -1);
            tag = new TagBuilder()
                    .setProjectID(projectID)
                    .setTagColor("#aaaaaa")
                    .build();
            requestCode = 2;
            delete.setVisibility(View.GONE);
        } else {
            index = intent.getIntExtra("index", 0);
            requestCode = 1;
        }
        tagName.setText(tag.getTagName());
        List<String> colors = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.color_list)));

        colorListViewAdapter = new ColorListViewAdapter(this, colors, tag.getTagColor());
        colorListView.setAdapter(colorListViewAdapter);

        rollBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tag.setTagName(tagName.getText().toString());
                tag.setTagColor(colorListViewAdapter.getSelectedColor());
                intent.putExtra("tag", tag);
                if(tag.getTagID() != 0) {
                    intent.putExtra("index", index);

                    // update here
                } else {
                    // add new here
                }
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show alert
                // delete on database
                intent.putExtra("index", index);
                setResult(RESULT_DELETE, intent);
                finish();
            }
        });
    }
}