package com.example.taskmanagerment;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taskmanagerment.customlistview.CustomProjectAdapter;
import com.example.taskmanagerment.database.DatabaseHelper;
import com.example.taskmanagerment.models.Project;
import com.example.taskmanagerment.services.ProjectService;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final int CREATE_PROJECT_REQUEST_CODE = 1;

    // Declare components.
    private ImageView searchHomeButton, notificationHomeButton, createNewBoardOrTaskButton;

    private ImageView homeTask, homeBoard;

    private View overlayView;

    private TextView tvForHomeBoard, tvForHomeTask;

    private ListView boardListview;

    private boolean isShowOptionForCreateNewBoard = false;

    private DatabaseHelper databaseHelper;

    private CustomProjectAdapter adapter;

    private List<Project> projects;

    private ProjectService projectService;


    // Called when activity is created, this is used to set up user interface and necessary initialization.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        // Call method to initial components.
        initialComponents();

        projects = projectService.getAllProjects();
        adapter = new CustomProjectAdapter(this, R.layout.board_item_litview, projects);
        boardListview.setAdapter(adapter);

        // Register context menu for board list view.
        registerForContextMenu(boardListview);

        // Handle when item in board list view is clicked.
        boardListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Project selectedProject = projects.get(position);

                Intent intent = new Intent(HomeActivity.this, BoardActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("selectedProject", selectedProject);

                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });

        // Redirect to search view when user click to the search button.
        searchHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivityForResult(intent, CREATE_PROJECT_REQUEST_CODE);
            }

        });

        // Redirect to notification view when user click to the notification button.
        notificationHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });

        // Display create new board or task button when user click to the creteNewBoarOrTask button.
        createNewBoardOrTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowOptionForCreateNewBoard) {
                    refreshHomeView();
                } else {
                    overlayView.setVisibility(View.VISIBLE);
                    homeTask.setVisibility(View.VISIBLE);
                    homeBoard.setVisibility(View.VISIBLE);
                    tvForHomeBoard.setVisibility(View.VISIBLE);
                    tvForHomeTask.setVisibility(View.VISIBLE);
                    createNewBoardOrTaskButton.setImageResource(R.drawable.close_24dp_fill0_wght400_grad0_opsz24);

                    isShowOptionForCreateNewBoard = true;
                }
            }
        });

        // Redirect to create new task view when user click to the home task button.
        homeTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CreateNewTaskActivity.class);
                startActivity(intent);
            }
        });

        // Redirect to create new board view when user click to the home board button.
        homeBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CreateNewProjectActivity.class);
                startActivityForResult(intent, CREATE_PROJECT_REQUEST_CODE);
            }
        });


    }

    // Method to initial component.
    private void initialComponents() {
        overlayView = (View) findViewById(R.id.overlay_view);

        searchHomeButton = (ImageView) findViewById(R.id.search_home_button);
        notificationHomeButton = (ImageView) findViewById(R.id.notification_home_button);
        createNewBoardOrTaskButton = (ImageView) findViewById(R.id.create_new_board_or_task_button);
        homeTask = (ImageView) findViewById(R.id.home_task);
        homeBoard = (ImageView) findViewById(R.id.home_board);

        tvForHomeBoard = (TextView) findViewById(R.id.tv_for_home_board);
        tvForHomeTask = (TextView) findViewById(R.id.tv_for_home_task);
        boardListview = (ListView) findViewById(R.id.board_listview);

        databaseHelper = new DatabaseHelper(this);
        projects = new ArrayList<>();
        projectService = new ProjectService(this);
    }

    // Method to refresh home view
    public void refreshHomeView() {
        overlayView.setVisibility(View.GONE);
        homeTask.setVisibility(View.GONE);
        tvForHomeBoard.setVisibility(View.GONE);
        homeBoard.setVisibility(View.GONE);
        tvForHomeTask.setVisibility(View.GONE);
        createNewBoardOrTaskButton.setImageResource(R.drawable.baseline_add_24);

        isShowOptionForCreateNewBoard = false;
    }

    // Method to create and show context menu when user on long click to any view.
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_for_board_item, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        assert info != null;
        int position = info.position;
        Project selectedProject = projects.get(position);

        if (item.getItemId() == R.id.delete_project) {
            boolean isDeleted = projectService.deleteProject(selectedProject.getProjectId());
            if (isDeleted) {
                Toast.makeText(this, "Project deleted", Toast.LENGTH_SHORT).show();
                updateProjectData();
            } else {
                Toast.makeText(this, "Failed to delete project", Toast.LENGTH_SHORT).show();
            }

            return true;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_PROJECT_REQUEST_CODE && resultCode == RESULT_OK) {
            refreshHomeView();
            updateProjectData();
        }
    }

    private void updateProjectData() {
        projects = projectService.getAllProjects();
        adapter.clear();
        adapter.addAll(projects);
        adapter.notifyDataSetChanged();
    }

}
