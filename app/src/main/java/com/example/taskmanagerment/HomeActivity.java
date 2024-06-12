package com.example.taskmanagerment;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taskmanagerment.customlistview.BoardItem;
import com.example.taskmanagerment.customlistview.CustomListAdapter;
import com.example.taskmanagerment.customlistview.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    // Declare components.
    private ImageView searchHomeButton, notificationHomeButton, createNewBoardOrTaskButton;

    private ImageView homeTask, homeBoard;

    private View overlayView;

    private TextView tvForHomeBoard, tvForHomeTask;

    private ListView boardListview;

    private boolean isShowOptionForCreateNewBoard = false;


    // Called when activity is created, this is used to set up user interface and necessary initialization.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        // Call method to initial components.
        initialComponents();

        // Add data to list
        List<ItemModel> items = new ArrayList<>();
        items.add(new BoardItem("Board 1"));
        items.add(new BoardItem("Board 2"));
        items.add(new BoardItem("Board 3"));

        // Set custom listview for list view.
        CustomListAdapter adapter = new CustomListAdapter(this, items);
        boardListview.setAdapter(adapter);

        // Create intent to received data from create new board view.
        Intent intent = getIntent();
        String newBoardName = intent.getStringExtra("boardName");
        if (newBoardName != null && !newBoardName.isEmpty()) {
            items.add(new BoardItem(newBoardName));
            adapter.notifyDataSetChanged();
        }

        // Register context menu for board list view.
        registerForContextMenu(boardListview);

        // Handle when item in board list view is clicked.
        boardListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HomeActivity.this, BoardActivity.class);
                startActivity(intent);
            }
        });

        // Redirect to search view when user click to the search button.
        searchHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
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
                    overlayView.setVisibility(View.GONE);
                    homeTask.setVisibility(View.GONE);
                    homeBoard.setVisibility(View.GONE);
                    tvForHomeBoard.setVisibility(View.GONE);
                    tvForHomeTask.setVisibility(View.GONE);
                    createNewBoardOrTaskButton.setImageResource(R.drawable.baseline_add_24);

                    isShowOptionForCreateNewBoard = false;
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
                Intent intent = new Intent(HomeActivity.this, CreateNewBoardActivity.class);
                startActivity(intent);
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
    }

    // Method to create and show context menu when user on long click to any view.
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_for_board_item, menu);
    }

}
