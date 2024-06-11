package com.example.taskmanagerment;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class CreateNewTaskActivity extends AppCompatActivity {

    // Declare components.
    private ImageView closeCreateNewTask, confirmCreateNewTask;

    private ConstraintLayout containBoardName, containListName;

    private TextView boardNameTextView, listNameTextView, startDateTextView, endDateTextView;

    private EditText taskNameEdt, desciptionEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_task_layout);

        // Call method to initial components.
        initialComponents();

        // Register context menu for containBoardName and containListName View (Constraint layout).
        registerForContextMenu(containBoardName);
        registerForContextMenu(containListName);

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
                if (!isValidForm()) {
                    DialogUtils.showAlertDialog(CreateNewTaskActivity.this, "Please enter complete ìnformation");
                    return;
                }
            }
        });

        // Show context menu for contain board nam view
        containBoardName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContextMenu(containBoardName);
            }
        });

        // Show context menu for contain list name.
        containListName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContextMenu(containListName);
            }
        });

    }

    // Method to initial components.
    private void initialComponents() {
        closeCreateNewTask = (ImageView) findViewById(R.id.close_create_new_task);
        confirmCreateNewTask = (ImageView) findViewById(R.id.confirm_create_new_task);

        containBoardName = (ConstraintLayout) findViewById(R.id.contain_board_name);
        containListName = (ConstraintLayout) findViewById(R.id.contain_list_name);

        boardNameTextView = (TextView) findViewById(R.id.board_name_text_view);
        listNameTextView = (TextView) findViewById(R.id.list_name_text_view);
        startDateTextView = (TextView) findViewById(R.id.start_date_text_view);
        endDateTextView = (TextView) findViewById(R.id.end_date_text_view);

        taskNameEdt = (EditText) findViewById(R.id.task_name_edt);
        desciptionEdt = (EditText) findViewById(R.id.description_edt);
    }

    // Method to valid form (view) before form is submitted.
    private boolean isValidForm() {
        boolean valid = true;

        if (boardNameTextView.getText().toString().isEmpty()) {
            valid = false;
        }
        if (listNameTextView.getText().toString().isEmpty()) {
            valid = false;
        }
        if (startDateTextView.getText().toString().isEmpty()) {
            valid = false;
        }
        if (endDateTextView.getText().toString().isEmpty()) {
            valid = false;
        }
        if (taskNameEdt.getText().toString().isEmpty()) {
            valid = false;
        }
        if (desciptionEdt.getText().toString().isEmpty()) {
            valid = false;
        }

        return valid;
    }

    //  Method to create context menu.
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_for_task_view, menu);
    }
}
