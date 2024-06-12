package com.example.taskmanagerment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taskmanagerment.services.ProjectService;

public class CreateNewProjectActivity extends AppCompatActivity {

    // Declare components
    private ImageView closeCreateNewProject;
    private EditText projectNameEdt;
    private Button createNewProjectButton;
    private ProjectService projectService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cretae_new_board_layout);

        // Call method to initial components
        initialComponents();

        // Close create new project view
        closeCreateNewProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Create new project and return result
        createNewProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String boardName = projectNameEdt.getText().toString();

                // Validation before creating new project
                if (boardName.isEmpty()) {
                    DialogUtils.showAlertDialog(CreateNewProjectActivity.this, "Please enter complete information!");
                    return;
                }

                if (projectService.isProjectNameExists(boardName)) {
                    DialogUtils.showAlertDialog(CreateNewProjectActivity.this, "Project name already exists!");
                    return;
                }

                // Add new project to the database
                projectService.addProject(boardName);

                // Set result to RESULT_OK to indicate successful creation
                setResult(RESULT_OK);

                // Finish the activity
                finish();
            }
        });
    }

    // Method to initial components.
    private void initialComponents() {
        closeCreateNewProject = findViewById(R.id.close_create_new_board);
        projectNameEdt = findViewById(R.id.board_name_edt);
        createNewProjectButton = findViewById(R.id.create_new_board_btn);
        projectService = new ProjectService(this);
    }

}

