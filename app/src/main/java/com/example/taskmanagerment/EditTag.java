package com.example.taskmanagerment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanagerment.models.Tag;
import com.example.taskmanagerment.services.ColorListViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditTag extends AppCompatActivity {

    private EditText tagName;
    private ListView colorListView;
    private ImageButton rollBack, confirm;
    private Tag tag;
    private Intent intent;
    private int index;
    private ColorListViewAdapter colorListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tag);
        tagName = (EditText) findViewById(R.id.tagName);
        colorListView = (ListView) findViewById(R.id.colorListView);
        rollBack = (ImageButton) findViewById(R.id.rollBack);
        confirm = (ImageButton) findViewById(R.id.confirm);
        intent = getIntent();
        tag = (Tag) intent.getSerializableExtra("tag");
        index = intent.getIntExtra("index", -1);
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
                intent.putExtra("index", index);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}