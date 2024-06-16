package com.example.taskmanagerment;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import android.widget.Toast;

import com.example.taskmanagerment.broadcastreceivers.NotificationReceiver;
import com.example.taskmanagerment.models.Tag;
import com.example.taskmanagerment.models.TagList;
import com.example.taskmanagerment.models.Task;
import com.example.taskmanagerment.services.ImageUtils;
import com.example.taskmanagerment.services.NotificationService;
import com.example.taskmanagerment.services.ProjectService;
import com.example.taskmanagerment.services.TagListAdapter;
import com.example.taskmanagerment.services.TagService;
import com.example.taskmanagerment.services.TaskService;
import com.example.taskmanagerment.services.TaskTagService;
import com.google.android.flexbox.FlexboxLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.taskmanagerment.models.NotifyWhen;
import android.Manifest;

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

    private ActivityResultLauncher<Intent> pickImageLauncher;

    private static final int REQUEST_PERMISSION_CODE = 100;

    private TaskService taskService;

    private TagService tagService;

    private TaskTagService taskTagService;

    private int projectID = -1;

    private int positionOfNotificationType = 0;

    private static int requestCodeNotification = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        initializeComponent();

        if (ContextCompat.checkSelfPermission(TaskDetails.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION_CODE);
            }
        } else {
            Toast.makeText(TaskDetails.this, "Granted", Toast.LENGTH_SHORT).show();
        }


    }

    // New for notify
    // Method to set the notification
    private void setNotification(Date deadline, int notifyWhen, String taskName, String projectName, int taskID, int projectID) {
        NotificationService notificationService = new NotificationService(TaskDetails.this);

        String notificationContent = taskName + " in " + projectName + " has expired.";
        long notificationID = notificationService.addNotification(notificationContent, deadline, taskID, projectID);

        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("taskName", taskName);
        intent.putExtra("projectName", projectName);
        intent.putExtra("deadlineTimeFormatted", dateFormat(deadline));

        intent.putExtra("taskID", taskID);
        intent.putExtra("projectID", projectID);
        intent.putExtra("notificationID", notificationID);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                requestCodeNotification++, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (alarmManager != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(deadline);

            switch (notifyWhen) {
                case NotifyWhen.BEFORE_ONE_DATE:
                    calendar.add(Calendar.DAY_OF_YEAR, -1);
                    break;
                case NotifyWhen.BEFORE_ONE_HOUR:
                    calendar.add(Calendar.HOUR_OF_DAY, -1);
                    break;
                case NotifyWhen.BEFORE_THIRTY_MINUTES:
                    calendar.add(Calendar.MINUTE, -30);
                    break;
                case NotifyWhen.BEFORE_FIFTY_MINUTES:
                    calendar.add(Calendar.MINUTE, -15);
                    break;
                case NotifyWhen.BEFORE_TEN_MINUTES:
                    calendar.add(Calendar.MINUTE, -10);
                    break;
                case NotifyWhen.BEFORE_FIVE_MINUTES:
                    calendar.add(Calendar.MINUTE, -5);
                    break;
                case NotifyWhen.AT_DEADLINE:
                    break;
                default:
                    return;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }

            Toast.makeText(this, "Notification set for " + dateFormat(calendar.getTime()), Toast.LENGTH_SHORT).show();
        }
    }


    private void cancelNotification() {
        // Create Intent for BroadcastReceiver
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                requestCodeNotification, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            // Cancel alarm
            alarmManager.cancel(pendingIntent);

            // Inform user
            Toast.makeText(this, "Notification cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TagListAdapter.REQUEST_CODE && resultCode == RESULT_OK) {
            TagList tagList = (TagList) data.getSerializableExtra("tags");
            taskTagService.addOrDelete(task.getTaskID(), tagList.getTags());
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
                chkIsCompleted.setVisibility(deadlineTime.getText().toString().isEmpty() ? View.INVISIBLE : View.VISIBLE);
                findViewById(R.id.notifyContainer).setVisibility(deadlineTime.getText().toString().isEmpty() ? View.GONE : View.VISIBLE);
            }
        };
    }

    private void registerResult() {
        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                Uri selectedImageUri = data.getData();
                                if (selectedImageUri != null) {
                                    // Save the image to external storage
                                    Bitmap bitmap = ImageUtils.getBitmapFromUri(TaskDetails.this, selectedImageUri);
                                    Uri replaceUri = ImageUtils.saveBitmapToExternalStorage(TaskDetails.this, bitmap);
                                    task.setImageURL(replaceUri.toString());
                                    backgroundImage.setImageURI(Uri.parse(task.getImageURL()));
                                    backgroundImage.setVisibility(View.VISIBLE);
                                    addBackgroundImage.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                });
    }

    private void pickImage() {
//        Intent pickImage = new Intent(MediaStore.ACTION_PICK_IMAGES);
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(pickImage);
    }

    private void initializeComponent() {
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
        registerResult();
        intent = getIntent();
        task = (Task) intent.getSerializableExtra("task");
        projectID = intent.getIntExtra("projectID", -1);
        // query get selected tag list by taskID
        // ->
        tagService = new TagService(TaskDetails.this);
        tags = tagService.getTags(projectID, task.getTaskID());
        taskService = new TaskService(TaskDetails.this);
        taskTagService = new TaskTagService(TaskDetails.this);

        taskTile.setText(task.getTaskName());
        taskDescription.setText(task.getDescription());
        if (task.getCreatedAt() != null) {
            beginTime.setText(dateFormat(task.getCreatedAt()));
        }
        if (task.getDeadline() != null) {
            deadlineTime.setText(dateFormat(task.getDeadline()));
        }
        if (!deadlineTime.getText().toString().isEmpty()) {
            notifyContainer.setVisibility(View.VISIBLE);
        }

        if (task.getImageURL() == null || task.getImageURL().isEmpty()) {
            backgroundImage.setVisibility(View.GONE);
        } else {
            backgroundImage.setImageURI(Uri.parse(task.getImageURL()));
            backgroundImage.setVisibility(View.VISIBLE);
            addBackgroundImage.setVisibility(View.GONE);
        }

        if (!deadlineTime.getText().toString().isEmpty()) {
            chkIsCompleted.setVisibility(View.VISIBLE);
            chkIsCompleted.setChecked(task.getCompletedAt() != null);
        }

        notificationAt.post(new Runnable() {
            @Override
            public void run() {
                notificationAt.setSelection(task.getNotifyWhen());
            }
        });

        tagListAdapter = new TagListAdapter(tagFlexBox, TaskDetails.this);
        tagListAdapter.setTags(tags);
        tagListAdapter.setProjectID(projectID);
        tagListAdapter.setChildActivity(SelectTag.class);
        tagListAdapter.render();

        SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                getResources().getTextArray(R.array.notify_at));

        notificationAt.setAdapter(spinnerAdapter);


        notificationAt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                positionOfNotificationType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        beginTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (beginTime.getText().toString().isEmpty()) {
                    beginTime.setText(dateFormat(new Date()));
                    task.setCreatedAt(new Date());
                } else {
                    task.setCreatedAt(openDateDialog((TextView) view));
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

        chkIsCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task.setCompletedAt(chkIsCompleted.isChecked() ? new Date() : null);
                taskService.setCompleteTime(task);

            }
        });

        taskDetailCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                finish();
                Intent intent = new Intent(TaskDetails.this, BoardActivity.class);
                Bundle bundle = new Bundle();
                ProjectService projectService = new ProjectService(TaskDetails.this);
                bundle.putSerializable("selectedProject", projectService.getProjectByID(projectID));

                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });

        taskDetailConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = getIntent();
                task.setTaskName(taskTile.getText().toString());
                task.setDescription(taskDescription.getText().toString());
                task.setNotifyWhen(notificationAt.getSelectedItemPosition());
                // update task to database
                // ->
                taskService.modifyTask(task);
                intent.putExtra("task", task);
                setResult(RESULT_OK, intent);

                // Setup notification
                if (chkIsCompleted.isChecked() && deadlineTime.getText().toString().isEmpty()) {
                    return;
                }

                ProjectService projectService = new ProjectService(TaskDetails.this);
                String projectName = projectService.getProjectNameById(projectID);


                if (positionOfNotificationType == 0) {
                    cancelNotification();
                } else {
                    setNotification(task.getDeadline(), positionOfNotificationType, task.getTaskName(), projectName, task.getTaskID(), projectID);
                }

//                finish();
                Intent intent = new Intent(TaskDetails.this, BoardActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("selectedProject", projectService.getProjectByID(projectID));

                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });

        addBackgroundImage.setOnClickListener(view -> pickImage());
        backgroundImage.setOnClickListener(view -> pickImage());
    }
}