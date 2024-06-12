package com.example.taskmanagerment.customlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.taskmanagerment.R;
import com.example.taskmanagerment.models.Project;

import java.util.List;

public class CustomProjectAdapter extends ArrayAdapter<Project> {

    private Context context;
    private int resource;
    private List<Project> projects;

    public CustomProjectAdapter(Context context, int resource, List<Project> projects) {
        super(context, resource, projects);
        this.context = context;
        this.resource = resource;
        this.projects = projects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, null);
        }

        TextView titleTextView = view.findViewById(R.id.title_board_item_view);
        TextView idTextView = view.findViewById(R.id.id_board_item_view);
        TextView dateTextView = view.findViewById(R.id.date_board_item_view);

        Project project = projects.get(position);

        titleTextView.setText(project.getProjectName());
        idTextView.setText(String.valueOf(project.getProjectId()));
        dateTextView.setText(project.getCreatedAt());

        return view;
    }

}
