package com.example.taskmanagerment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagerment.models.Task;
import com.example.taskmanagerment.models.Project;
import com.example.taskmanagerment.models.TaskGroup;
import com.example.taskmanagerment.services.TaskGroupAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BoardActivity extends AppCompatActivity {

    private ImageView goBackButton, moreOptionsButtonOfBoard, notificationButtonOfBoard;
    private ImageView filterButtonOfBoard, confirmEditTitleName, closeEnterTitleName;
    private EditText projectTitleEdt, filterEdt;
    private RecyclerView groupHorizontalRecyclerView;
    private List<TaskGroup> groups;

    private boolean isFillerEdtVisibility = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_layout);

        initialComponents();

        String projectNameTitle = getProjectDataFromIntent().getProjectName();
        projectTitleEdt.setText(projectNameTitle.isEmpty() ? "No title name" : projectNameTitle);

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFillerEdtVisibility) {
                    filterEdt.setVisibility(View.GONE);
                    projectTitleEdt.setVisibility(View.VISIBLE);
                    filterButtonOfBoard.setVisibility(View.VISIBLE);

                    isFillerEdtVisibility = false;
                } else {
                    Intent intent = new Intent(BoardActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
        });

        projectTitleEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    projectTitleEdt.setBackgroundResource(R.drawable.under_line);

                    closeEnterTitleName.setVisibility(View.VISIBLE);
                    confirmEditTitleName.setVisibility(View.VISIBLE);

                    goBackButton.setVisibility(View.GONE);
                    filterButtonOfBoard.setVisibility(View.GONE);
                    notificationButtonOfBoard.setVisibility(View.GONE);
                    moreOptionsButtonOfBoard.setVisibility(View.GONE);

                    projectTitleEdt.post(new Runnable() {
                        @Override
                        public void run() {
                            projectTitleEdt.setSelection(projectTitleEdt.getText().length());
                        }
                    });
                }
            }
        });

        closeEnterTitleName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                projectTitleEdt.setBackgroundColor(0);

                closeEnterTitleName.setVisibility(View.GONE);
                confirmEditTitleName.setVisibility(View.GONE);

                goBackButton.setVisibility(View.VISIBLE);
                filterButtonOfBoard.setVisibility(View.VISIBLE);
                notificationButtonOfBoard.setVisibility(View.VISIBLE);
                moreOptionsButtonOfBoard.setVisibility(View.VISIBLE);

                // Đảm bảo EditText có thể lấy lại tiêu điểm
                projectTitleEdt.setFocusable(false);
                projectTitleEdt.setFocusableInTouchMode(false);

                // Đặt lại tiêu điểm khi EditText được nhấn
                projectTitleEdt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        projectTitleEdt.setFocusable(true);
                        projectTitleEdt.setFocusableInTouchMode(true);
                        projectTitleEdt.requestFocus();
                        projectTitleEdt.post(new Runnable() {
                            @Override
                            public void run() {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.showSoftInput(projectTitleEdt, InputMethodManager.SHOW_IMPLICIT);
                            }
                        });
                    }
                });
            }
        });


        confirmEditTitleName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTitle = projectTitleEdt.getText().toString();
                projectTitleEdt.setText(newTitle);
                hideKeyboard();
                projectTitleEdt.setBackgroundColor(0);

                closeEnterTitleName.setVisibility(View.GONE);
                confirmEditTitleName.setVisibility(View.GONE);

                goBackButton.setVisibility(View.VISIBLE);
                filterButtonOfBoard.setVisibility(View.VISIBLE);
                notificationButtonOfBoard.setVisibility(View.VISIBLE);
                moreOptionsButtonOfBoard.setVisibility(View.VISIBLE);

                projectTitleEdt.setFocusable(false);
            }
        });

        filterButtonOfBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFillerEdtVisibility = true;

                filterEdt.setBackgroundResource(R.drawable.under_line);
                filterEdt.setVisibility(View.VISIBLE);
                filterEdt.requestFocus();

                projectTitleEdt.setVisibility(View.GONE);
                filterButtonOfBoard.setVisibility(View.GONE);
            }
        });
    }

    private void initialComponents() {
        goBackButton = (ImageView) findViewById(R.id.go_back_button);
        projectTitleEdt = (EditText) findViewById(R.id.board_title_edt);
        moreOptionsButtonOfBoard = (ImageView) findViewById(R.id.more_options_button_of_board);
        notificationButtonOfBoard = (ImageView) findViewById(R.id.notification_button_of_board);
        filterButtonOfBoard = (ImageView) findViewById(R.id.filter_button_of_board);
        confirmEditTitleName = (ImageView) findViewById(R.id.confirm_edit_title_name);
        closeEnterTitleName = (ImageView) findViewById(R.id.close_enter_title_name);
        filterEdt = (EditText) findViewById(R.id.filter_edt);
        groupHorizontalRecyclerView = (RecyclerView) findViewById(R.id.groupHorizontalRecyclerView);

        // gáng kết quả truy vấn vào đây
        groups = new ArrayList<>();
        groupHorizontalRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        groupHorizontalRecyclerView.setAdapter(new TaskGroupAdapter(groups, BoardActivity.this));
    }

    // Method to hide the keyboard
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 ){
            if(resultCode == RESULT_CANCELED) {
                // doing nothing...
            }
            if(resultCode == RESULT_OK) {
                // find the group has id == ? and task has id = ? and replace it.
                Task task = (Task) data.getSerializableExtra("task");
                replaceSameIDTask(task);
            }
        }
    }

    private void replaceSameIDTask(Task task) {
        for(int i = 0; i < groups.size(); i++) {
            if(groups.get(i).getGroupId() == task.getGroupID()){
                for(int j = 0; j < groups.get(i).getTasks().size(); j++) {
                    if(groups.get(i).getTasks().get(j).getTaskID() == task.getTaskID()){
                        groups.get(i).getTasks().set(j,task);
                    }
                }
            }
        }
    }
    public Project getProjectDataFromIntent() {
        Bundle bundle = getIntent().getBundleExtra("bundle");

        Project project = null;

        if (bundle != null) {
            project = (Project) bundle.getSerializable("selectedProject");
        }

        return project;
    }

}
