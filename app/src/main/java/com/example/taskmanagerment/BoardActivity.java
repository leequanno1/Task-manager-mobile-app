package com.example.taskmanagerment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagerment.models.Task;
import com.example.taskmanagerment.models.Project;
import com.example.taskmanagerment.models.TaskGroup;
import com.example.taskmanagerment.services.ProjectService;
import com.example.taskmanagerment.services.TaskAdapter;
import com.example.taskmanagerment.services.TaskGroupAdapter;
import com.example.taskmanagerment.services.TaskGroupService;
import com.example.taskmanagerment.services.TaskService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BoardActivity extends AppCompatActivity {

    public static final int MODIFY_TASK_REQUEST = 1;
    private ImageView goBackButton, moreOptionsButtonOfBoard, notificationButtonOfBoard;

    private ImageView filterButtonOfBoard, confirmEditTitleName, closeEnterTitleName;

    private EditText projectTitleEdt, filterEdt;

    private RecyclerView groupHorizontalRecyclerView;

    private List<TaskGroup> groups;

    private boolean isFillerEdtVisibility = false;

    private ProjectService projectService;

    private TaskGroupService taskGroupService;

    private TaskService taskService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_layout);

        initialComponents();

        String projectNameTitle = getProjectDataFromIntent().getProjectName();
        projectTitleEdt.setText(projectNameTitle.isEmpty() ? "No title name" : projectNameTitle);

        int projectID = getProjectDataFromIntent().getProjectId();
        // gáng kết quả truy vấn vào đây
        groups = taskGroupService.getTaskGroupsByProjectId(projectID);
        groupHorizontalRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        groupHorizontalRecyclerView.setAdapter(new TaskGroupAdapter(groups, BoardActivity.this, projectID));


        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFillerEdtVisibility) {
                    filterEdt.setVisibility(View.GONE);
                    projectTitleEdt.setVisibility(View.VISIBLE);
                    filterButtonOfBoard.setVisibility(View.VISIBLE);

                    filterEdt.setText("");
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

                if (!newTitle.isEmpty()) {
                    if (projectService.updateProjectNameById(projectID, newTitle)) {
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
                }

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

        filterEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().isEmpty()) {
                    groups.clear();
                    groups.addAll(taskGroupService.filterByTaskName(projectID, charSequence.toString()));
                    groupHorizontalRecyclerView.setAdapter(new TaskGroupAdapter(groups, BoardActivity.this, projectID));
                } else {
                    groups.clear();
                    groups.addAll(taskGroupService.getTaskGroupsByProjectId(projectID));
                    groupHorizontalRecyclerView.setAdapter(new TaskGroupAdapter(groups, BoardActivity.this, projectID));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initialComponents() {
        goBackButton = (ImageView) findViewById(R.id.go_back_button);
        moreOptionsButtonOfBoard = (ImageView) findViewById(R.id.more_options_button_of_board);
        notificationButtonOfBoard = (ImageView) findViewById(R.id.notification_button_of_board);
        filterButtonOfBoard = (ImageView) findViewById(R.id.filter_button_of_board);
        confirmEditTitleName = (ImageView) findViewById(R.id.confirm_edit_title_name);
        closeEnterTitleName = (ImageView) findViewById(R.id.close_enter_title_name);

        projectTitleEdt = (EditText) findViewById(R.id.board_title_edt);
        filterEdt = (EditText) findViewById(R.id.filter_edt);

        groupHorizontalRecyclerView = (RecyclerView) findViewById(R.id.groupHorizontalRecyclerView);

        projectService = new ProjectService(BoardActivity.this);
        taskGroupService = new TaskGroupService(BoardActivity.this);
        taskGroupService = new TaskGroupService(BoardActivity.this);
        taskService = new TaskService(BoardActivity.this);
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
        if(requestCode == MODIFY_TASK_REQUEST ){
            if(resultCode == RESULT_CANCELED) {

                // doing nothing...
            }
            if (resultCode == RESULT_OK) {
                // find the group has id == ? and task has id = ? and replace it.
                Task task = (Task) data.getSerializableExtra("task");
                replaceSameIDTask(task);
                groupHorizontalRecyclerView.getAdapter().notifyDataSetChanged();
            }
        }
    }

    private void replaceSameIDTask(Task task) {
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).getGroupId() == task.getGroupID()) {
                for (int j = 0; j < groups.get(i).getTasks().size(); j++) {
                    if (groups.get(i).getTasks().get(j).getTaskID() == task.getTaskID()) {
                        groups.get(i).getTasks().set(j, task);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        if (v.getId() == R.id.taskItem) {
            inflater.inflate(R.menu.context_menu_for_delete_task_item, menu);
        }

    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_task_item) {
            TaskGroupAdapter taskGroupAdapter = (TaskGroupAdapter) groupHorizontalRecyclerView.getAdapter();
            Toast.makeText(BoardActivity.this, taskGroupAdapter.getSelectedTaskID() + " ", Toast.LENGTH_SHORT).show();
            taskService.deleteTask(taskGroupAdapter.getSelectedTaskID());

            for (int i = 0; i < groups.size(); i++) {
                if (groups.get(i).getGroupId() == taskGroupAdapter.getSelectedGroupID()) {
                    for (int j = 0; j < groups.get(i).getTasks().size(); j++) {
                        if (groups.get(i).getTasks().get(j).getTaskID() == taskGroupAdapter.getSelectedTaskID()) {
                            groups.get(i).getTasks().remove(j);
                            break;
                        }
                    }
                    break;
                }
            }

            taskGroupAdapter.notifyDataSetChanged();
        }

        return true;
    }


}
