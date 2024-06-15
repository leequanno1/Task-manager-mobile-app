package com.example.taskmanagerment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taskmanagerment.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
        databaseHelper.deleteDatabase();

    }

}
