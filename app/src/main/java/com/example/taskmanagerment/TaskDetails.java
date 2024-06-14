package com.example.taskmanagerment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.lights.LightsManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.taskmanagerment.models.Tag;
import com.example.taskmanagerment.models.TagList;
import com.example.taskmanagerment.models.Task;
import com.example.taskmanagerment.services.TagListAdapter;
import com.google.android.flexbox.FlexboxLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskDetails extends AppCompatActivity {

    EditText taskTile, taskDescription;
    TextView beginTime, deadlineTime, addBackgroundImage;

    FlexboxLayout tagFlexBox;

    ViewGroup notifyContainer;

    CheckBox chkIsCompleted;

    Spinner notificationAt;

    List<Tag> tags;

    TagListAdapter tagListAdapter;
    ImageButton taskDetailCancel, taskDetailConfirm;
    Intent intent;
    Task task;
    ImageView backgroundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        taskTile = findViewById(R.id.taskTitle);
        taskDescription = findViewById(R.id.taskDescription);
        beginTime = findViewById(R.id.beginTime);
        deadlineTime = findViewById(R.id.deadlineTime);
        chkIsCompleted = findViewById(R.id.chkIsCompleted);
        tagFlexBox = findViewById(R.id.tagFlexbox);
        notificationAt = findViewById(R.id.notificationAt);
        taskDetailCancel = findViewById(R.id.taskDetailCancel);
        taskDetailConfirm = findViewById(R.id.taskDetailConfirm);
        notifyContainer = findViewById(R.id.notifyContainer);
        addBackgroundImage = findViewById(R.id.addBackgroundImage);
        backgroundImage = findViewById(R.id.backgroundImage);

        intent = getIntent();
        task = (Task) intent.getSerializableExtra("task");
        taskTile.setText(task.getTaskName());
        taskDescription.setText(task.getDescription());
        if(task.getCreatedAt() != null) {
            beginTime.setText(dateFormat(task.getCreatedAt()));
        }
        if(task.getDeadline() != null) {
            deadlineTime.setText(dateFormat(task.getDeadline()));
        }
        if(!deadlineTime.getText().toString().isEmpty()) {
            notifyContainer.setVisibility(View.VISIBLE);
        }
        // hide add background textview if has URL
        if(task.getImageURL() == null || task.getImageURL().isEmpty()) {
            backgroundImage.setImageURI(Uri.parse(task.getImageURL()));
        }

        // query get selected tag list by taskID
        // ->
        tags = new ArrayList<>();
        tagListAdapter = new TagListAdapter(tagFlexBox, TaskDetails.this);
        tagListAdapter.setTags(tags);
        tagListAdapter.setChildActivity(SelectTag.class);
        tagListAdapter.render();

        SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                getResources().getTextArray(R.array.notify_at));
        notificationAt.setAdapter(spinnerAdapter);
        beginTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beginTime.getText().toString().isEmpty()){
                    beginTime.setText(dateFormat(new Date()));
                    task.setCreatedAt(new Date());
                } else {
                    task.setDeadline(openDateDialog((TextView) view));
                }
            }
        });

        deadlineTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task.setDeadline(openDateDialog((TextView) view));
            }
        });

        deadlineTime.addTextChangedListener(deadlineTextChange());

        taskDetailCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        taskDetailConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = getIntent();
                task.setTaskName(taskTile.getText().toString());
                // update task to database
                // ->
                intent.putExtra("task", task);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TagListAdapter.REQUEST_CODE && resultCode == RESULT_OK){
            TagList tagList = (TagList) data.getSerializableExtra("tags");
            tags.clear();
            tags.addAll(tagList.getTags());
            tagListAdapter.modifyDataSetChange();
        }
    }

    private Date openDateDialog(TextView textView) {
        LocalDateTime defaultDate = LocalDateTime.now();
        Date selectedDate = new Date();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                selectedDate.setYear(year-1900);
                selectedDate.setMonth(month);
                selectedDate.setDate(day);
                openTimeDialog(textView, selectedDate);
            }
        }, defaultDate.getYear(), defaultDate.getMonthValue()-1, defaultDate.getDayOfMonth());
        datePickerDialog.show();
        return selectedDate;
    }

    private void openTimeDialog(TextView textView, Date selectedDate) {
        LocalDateTime localDateTime = LocalDateTime.now();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                selectedDate.setHours(hour);
                selectedDate.setMinutes(minute);
                textView.setText(dateFormat(selectedDate));
            }
        }, localDateTime.getHour(), localDateTime.getMinute(), true);

        timePickerDialog.show();
    }

    private String dateFormat(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return dateFormat.format(date);
    }

    private TextWatcher deadlineTextChange() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                chkIsCompleted.setVisibility(deadlineTime.getText().toString().isEmpty()?View.INVISIBLE:View.VISIBLE);
                findViewById(R.id.notifyContainer).setVisibility(deadlineTime.getText().toString().isEmpty()?View.GONE:View.VISIBLE);
            }
        };
    }
}