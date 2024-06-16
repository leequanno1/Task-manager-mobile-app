package com.example.taskmanagerment;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taskmanagerment.customlistview.CustomNotificationAdapter;
import com.example.taskmanagerment.models.Notification;
import com.example.taskmanagerment.services.NotificationService;
import com.example.taskmanagerment.services.TaskService;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    // Declare components.
    private ImageView closeButton, markAllAsRead, allowNotify;
    private ListView notificationItemListView;
    private TextView notificationID, taskID;
    private CheckBox checkBoxFilterNotRead;
    private CustomNotificationAdapter adapter;
    private List<Notification> notifications;
    private NotificationService notificationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);

        // Method to initial components.
        initialComponents();

        notifications = notificationService.getAllNotifications();
        adapter = new CustomNotificationAdapter(this, R.layout.notification_item_listview, notifications);
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
        markAllAsRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationService.markAllAsRead();
                notifications = notificationService.getAllNotifications();

                updateNotifyListView();
            }
        });

        // Method to handle when user click to the more options button.
        allowNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    intent.setAction(android.provider.Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                    intent.putExtra(android.provider.Settings.EXTRA_APP_PACKAGE, getPackageName());
                } else {
                    intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setData(android.net.Uri.parse("package:" + getPackageName()));
                }
                startActivity(intent);
            }
        });


        checkBoxFilterNotRead.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    notifications = notificationService.getUnreadNotifications();
                } else {
                    notifications = notificationService.getAllNotifications();
                }
                updateNotifyListView();
            }
        });

        notificationItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Notification selectedNotification = (Notification) adapterView.getItemAtPosition(i);

                TaskService taskService = new TaskService(NotificationActivity.this);

                if (!notificationService.areIdsValid(selectedNotification.getId(), selectedNotification.getTaskID())) {
                    Toast.makeText(NotificationActivity.this, "Task no longer exists", Toast.LENGTH_SHORT).show();
                    return;
                }

                notificationService.markAsRead(selectedNotification.getId());
                if (checkBoxFilterNotRead.isChecked()) {
                    notifications = notificationService.getUnreadNotifications();
                } else {
                    notifications = notificationService.getAllNotifications();
                }

                updateNotifyListView();

                Intent intent = new Intent(NotificationActivity.this, TaskDetails.class);
                intent.putExtra("task", taskService.getTaskById(selectedNotification.getTaskID()));
                intent.putExtra("projectID", selectedNotification.getProjectID());

                startActivity(intent);
            }
        });
    }

    // Method to initial components.
    private void initialComponents() {
        closeButton = (ImageView) findViewById(R.id.close_button);
        markAllAsRead = (ImageView) findViewById(R.id.mark_all_as_read);
        allowNotify = (ImageView) findViewById(R.id.allow_notify);
        notificationItemListView = (ListView) findViewById(R.id.notification_item_listView);
        checkBoxFilterNotRead = (CheckBox) findViewById(R.id.check_box_filter_not_read);

        notificationID = (TextView) findViewById(R.id.notification_id);
        taskID = (TextView) findViewById(R.id.task_id);

        notifications = new ArrayList<>();
        notificationService = new NotificationService(NotificationActivity.this);
    }

    // Method to create context menu.
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_for_nofification_item, menu);
    }

    public void updateNotifyListView() {
        adapter.clear();
        adapter.addAll(notifications);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position; // Vị trí của item trong ListView
        Notification selectedNotification = (Notification) adapter.getItem(position);

        if (item.getItemId() == R.id.mark_as_unread) {
            notificationService.markAsUnRead(selectedNotification.getId());
        } else if (item.getItemId() == R.id.mark_as_read) {
            notificationService.markAsRead(selectedNotification.getId());
        } else if (item.getItemId() == R.id.delete_notify) {
            notificationService.deleteNotification(selectedNotification.getId());
        }

        if (checkBoxFilterNotRead.isChecked()) {
            notifications = notificationService.getUnreadNotifications();
        } else {
            notifications = notificationService.getAllNotifications();
        }

        updateNotifyListView();

        return true;
    }
}
