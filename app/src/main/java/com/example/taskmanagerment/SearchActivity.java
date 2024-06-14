package com.example.taskmanagerment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taskmanagerment.customlistview.CustomProjectAdapter;
import com.example.taskmanagerment.models.Project;
import com.example.taskmanagerment.services.ProjectService;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    // Declare components.
    private ImageView goBackButton;

    private EditText searchEditText;

    private ListView resultSearchesListview;

    private CustomProjectAdapter adapter;

    private List<Project> projects;

    private ProjectService projectService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        // Call method to initial components.
        initialComponents();

        adapter = new CustomProjectAdapter(this, R.layout.board_item_litview, projects);
        resultSearchesListview.setAdapter(adapter);

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

        // Set up TextWatcher for search EditText
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not used in this implementation
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Trigger search on text change
                performSearch(); // Perform search without passing searchTerm
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Not used in this implementation
            }
        });

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });

        resultSearchesListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Project selectedProject = projects.get(position);

                Intent intent = new Intent(SearchActivity.this, BoardActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("selectedProject", selectedProject);

                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });

    }

    // Method to initial components.
    private void initialComponents() {
        goBackButton = (ImageView) findViewById(R.id.go_back_button);
        searchEditText = (EditText) findViewById(R.id.search_edit_text);
        resultSearchesListview = (ListView) findViewById(R.id.result_search_listview);

        projects = new ArrayList<>();
        projectService = new ProjectService(this);
    }

    private void performSearch() {
        String searchTerm = searchEditText.getText().toString();
        if (!searchTerm.isEmpty()) {
            List<Project> searchResults = projectService.getProjectsByName(searchTerm);
            adapter.clear();
            adapter.addAll(searchResults);
            adapter.notifyDataSetChanged();
        }
    }

}
