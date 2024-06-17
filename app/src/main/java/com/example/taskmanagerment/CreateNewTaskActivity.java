package com.example.taskmanagerment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.taskmanagerment.models.Project;
import com.example.taskmanagerment.models.Task;
import com.example.taskmanagerment.models.TaskGroup;
import com.example.taskmanagerment.services.ProjectService;
import com.example.taskmanagerment.services.TaskGroupService;
import com.example.taskmanagerment.services.TaskService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateNewTaskActivity extends AppCompatActivity {

    // Declare components.
    private ImageView closeCreateNewTask, confirmCreateNewTask;

    private ConstraintLayout containBoardName, containListName;

    private TextView startDateTextView, endDateTextView;

    private Spinner boardNameSpinner, listNameSpinner;

    private EditText taskNameEdt, desciptionEdt;

    private ProjectService projectService;

    private TaskGroupService taskGroupService;

    private TaskService taskService;

    private List<Project> projectList;

    private List<TaskGroup> taskGroupList;

    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_task_layout);

        // Call method to initial components.
        initialComponents();
        projectList = projectService.getAllProjects();

        ArrayAdapter<Project> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, projectList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boardNameSpinner.setAdapter(adapter);

        if (projectList.size() > 0) {
            taskGroupList = taskGroupService.getTaskGroupsByProjectId(projectList.get(0).getProjectId());
        } else {
            taskGroupList = new ArrayList<>();
        }
        ArrayAdapter<TaskGroup> groupArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, taskGroupList);
        groupArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listNameSpinner.setAdapter(groupArrayAdapter);

        if (!taskGroupList.isEmpty()) {
            task.setGroupID(taskGroupList.get(0).getGroupId());
        }

        boardNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                taskGroupList = taskGroupService.getTaskGroupsByProjectId(projectList.get(i).getProjectId());
                ArrayAdapter<TaskGroup> groupArrayAdapter = new ArrayAdapter<>(CreateNewTaskActivity.this, android.R.layout.simple_spinner_item, taskGroupList);
                groupArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                listNameSpinner.setAdapter(groupArrayAdapter);
                if (taskGroupList.size() > 0) {
                    task.setGroupID(taskGroupList.get(0).getGroupId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                adapterView.setSelection(0);
            }
        });

        listNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                task.setGroupID(taskGroupList.get(i).getGroupId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                task.setGroupID(taskGroupList.get(0).getGroupId());
            }
        });

        // Redirect to home view when user click to the close create new task button.
        closeCreateNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateNewTaskActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // Method to confirm create new task when user click to the confirm create new task button.
        confirmCreateNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task.setTaskName(taskNameEdt.getText().toString());
                task.setDescription(desciptionEdt.getText().toString());
                if (task.getGroupID() == -1) {
                    DialogUtils.showAlertDialog(CreateNewTaskActivity.this, "Please chose valid group.");
                    return;
                }
                if (taskGroupList.size() == 0) {
                    DialogUtils.showAlertDialog(CreateNewTaskActivity.this, "This project has no group.\nPlease chose another group.");
                    return;
                }
                if (taskNameEdt.getText().toString().isEmpty()) {
                    DialogUtils.showAlertDialog(CreateNewTaskActivity.this, "Please enter task name before submit.");
                    return;
                }
                taskService.addTask(task);

                setResult(RESULT_OK);

                finish();
            }
        });

        startDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((TextView) view).getText().toString().isEmpty()) {
                    task.setCreatedAt(new Date());
                    ((TextView) view).setText(dateFormat(task.getCreatedAt()));
                } else {
                    task.setCreatedAt(openDateDialog((TextView) view));
                }
            }
        });

        endDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task.setDeadline(openDateDialog((TextView) view));
            }
        });

    }

    // Method to initial components.
    private void initialComponents() {
        closeCreateNewTask = (ImageView) findViewById(R.id.close_create_new_task);
        confirmCreateNewTask = (ImageView) findViewById(R.id.confirm_create_new_task);

        containBoardName = (ConstraintLayout) findViewById(R.id.contain_board_name);
        containListName = (ConstraintLayout) findViewById(R.id.contain_list_name);

        boardNameSpinner = (Spinner) findViewById(R.id.board_name_spinner);
        listNameSpinner = (Spinner) findViewById(R.id.list_name_spinner);
        startDateTextView = (TextView) findViewById(R.id.start_date_text_view);
        endDateTextView = (TextView) findViewById(R.id.end_date_text_view);

        taskNameEdt = (EditText) findViewById(R.id.task_name_edt);
        desciptionEdt = (EditText) findViewById(R.id.description_edt);
        projectService = new ProjectService(CreateNewTaskActivity.this);
        taskGroupService = new TaskGroupService(CreateNewTaskActivity.this);
        taskService = new TaskService(CreateNewTaskActivity.this);
        task = new Task();
    }

    private Date openDateDialog(TextView textView) {
        LocalDateTime defaultDate = LocalDateTime.now();
        Date selectedDate = new Date();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                selectedDate.setYear(year - 1900);
                selectedDate.setMonth(month);
                selectedDate.setDate(day);
                openTimeDialog(textView, selectedDate);
            }
        }, defaultDate.getYear(), defaultDate.getMonthValue() - 1, defaultDate.getDayOfMonth());
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

}
