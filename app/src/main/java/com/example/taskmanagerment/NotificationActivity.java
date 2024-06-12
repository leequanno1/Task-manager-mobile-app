package com.example.taskmanagerment;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taskmanagerment.customlistview.CustomListAdapter;
import com.example.taskmanagerment.customlistview.ItemModel;
import com.example.taskmanagerment.customlistview.NotificationItem;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    // Declare components.
    private ImageView closeButton, checkAllNotificationButton, moreOptionsButton;

    private ListView notificationItemListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);

        // Method to initial components.
        initialComponents();

        // Set data for list.
        List<ItemModel> items = new ArrayList<>();
        items.add(new NotificationItem("Notification 1", "June 5 at 15:30"));
        items.add(new NotificationItem("Notification 2", "June 5 at 15:31"));

        // Set custom listview for list view.
        CustomListAdapter adapter = new CustomListAdapter(this, items);
        notificationItemListView.setAdapter(adapter);

        // Register context menu for notification item listview.
        registerForContextMenu(notificationItemListView);

        // Redirect to home view when user click to the close button.
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotificationActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // Method handle when user click to the "check all notification" check box.
        checkAllNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // Method to handle when user click to the more options button.
        moreOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    // Method to initial components.
    private void initialComponents() {
        closeButton = (ImageView) findViewById(R.id.close_button);
        checkAllNotificationButton = (ImageView) findViewById(R.id.check_all_notification_button);
        moreOptionsButton = (ImageView) findViewById(R.id.more_options_button);
        notificationItemListView = (ListView) findViewById(R.id.notification_item_listView);
    }

    // Method to create context menu.
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_for_nofification_item, menu);
    }

}
