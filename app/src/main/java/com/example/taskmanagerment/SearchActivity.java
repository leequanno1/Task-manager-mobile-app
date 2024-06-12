package com.example.taskmanagerment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {

    // Declare components.
    private ImageView goBackButton;

    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        // Call method to initial components.
        initialComponents();

        // Focus to the search edit text when view is loaded.
        searchEditText.requestFocus();

        // Redirect to the home view when user click to the go back button.
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }

    // Method to initial components.
    private void initialComponents() {
        goBackButton = (ImageView) findViewById(R.id.go_back_button);
        searchEditText = (EditText) findViewById(R.id.search_edit_text);
    }

}
