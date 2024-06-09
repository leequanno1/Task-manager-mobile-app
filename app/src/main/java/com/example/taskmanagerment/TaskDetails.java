package com.example.taskmanagerment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.hardware.lights.LightsManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.taskmanagerment.models.Tag;
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
    TextView beginTime, deadlineTime;

    FlexboxLayout tagFlexBox;

    CheckBox chkIsCompleted;

    Spinner notificationAt;

    List<Tag> tags;

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
        tags = new ArrayList<>();
        tags.add(new Tag(1,1,"tag1", Color.parseColor("#ccccaa")));
        tags.add(new Tag(2,1,"tag2222222222222", Color.parseColor("#ccccbb")));
        tags.add(new Tag(3,1,"tag3", Color.parseColor("#cccccc")));

        TagListAdapter tagListAdapter = new TagListAdapter(tagFlexBox, this);
        tagListAdapter.setTags(tags);
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
                } else {
                    openDateDialog((TextView) view);
                }
            }
        });

        deadlineTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDateDialog((TextView) view);
            }
        });

        deadlineTime.addTextChangedListener(deadlineTextChange());
    }

    private void openDateDialog(TextView textView) {
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