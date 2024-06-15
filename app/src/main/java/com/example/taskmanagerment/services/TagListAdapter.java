package com.example.taskmanagerment.services;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanagerment.R;
import com.example.taskmanagerment.TaskDetails;
import com.example.taskmanagerment.models.Tag;
import com.example.taskmanagerment.models.TagList;
//import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

public class TagListAdapter {

    private ViewGroup parent;

    private TextView defaultChild;

    private List<Tag> tags;

    private Context context;

    private TaskDetails taskDetails;

    private Class childActivity;
    public static final int REQUEST_CODE = 1;

    private ViewGroup.MarginLayoutParams layoutParams;

    private int projectID = -1;

    public TagListAdapter(ViewGroup parent, TaskDetails context) {
        this.parent = parent;
        tags = new ArrayList<>();
        this.context = context;
        taskDetails = context;
        defaultChild = generateTagPlaceholder("Add tags...");
    }

    public TagListAdapter(Context context) {
        parent = null;
        tags = new ArrayList<>();
        this.context = context;
        defaultChild = generateTagPlaceholder("Add tags...");
    }

    public ViewGroup getParent() {
        return parent;
    }

    public void setParent(ViewGroup parent) {
        this.parent = parent;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public View getDefaultChild() {
        return defaultChild;
    }

    public void setDefaultChild(TextView defaultChild) {
        this.defaultChild = defaultChild;
    }

    public void render() {
        if (tags.isEmpty()) {
            parent.addView(defaultChild);
        }else {
            // render tags
            for (TextView textView : generateTextViewTagList(tags)) {
                parent.addView(textView);
            }
        }
    }

    public void modifyDataSetChange() {
        removeAll();
        render();
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    private void removeAll () {
        // remove all child
        parent.removeAllViews();
    }

    private TextView generateTextViewTag (Tag tag) {
        TextView textView = new TextView(context);
        textView.setId(tag.getTagID());
        textView.setText(tag.getTagName());
        textView.setPadding(20,20,20,20);
        textView.setTextSize(18f);
        textView.setBackground(context.getResources().getDrawable(R.drawable.tag_background));
        GradientDrawable drawable = (GradientDrawable) textView.getBackground();
        drawable.setColor(Color.parseColor(tag.getTagColor()));
        textView.setLayoutParams(getLayoutParams(16));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // change activity
                Intent intent = new Intent(context, childActivity);
                intent.putExtra("tags", new TagList(tags));
                intent.putExtra("projectID", projectID);
                taskDetails.startActivityForResult(intent, REQUEST_CODE);
            }
        });
        return textView;
    }

    private List<TextView> generateTextViewTagList (List<Tag> tags) {
        List<TextView> res = new ArrayList<>();
        for(Tag tag : tags) {
            res.add(generateTextViewTag(tag));
        }
        return res;
    }

    private ViewGroup.LayoutParams getLayoutParams(int margin) {
        if(layoutParams != null) {
            return layoutParams;
        }
        else {
            layoutParams = new ViewGroup.MarginLayoutParams(
                    ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                    ViewGroup.MarginLayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0,0,16,16);
            return layoutParams;
        }
    }

    private void removeTagByID(int tagID) {
        for(int i = 0; i < tags.size(); i++) {
            if (tags.get(i).getTagID() == tagID) {
                tags.remove(i);
                break;
            }
        }
    }

    private void deleteTag(View deletedView) {
        removeTagByID(deletedView.getId());
        parent.removeView(deletedView);
        Toast toast = new Toast(context);
        toast.setText(tags.size() + "");
        toast.show();
    }

    private TextView generateTagPlaceholder(String placeholder) {
        TextView textView = new TextView(context);
        textView.setText(placeholder);
        textView.setPadding(20,20,20,20);
        textView.setTextSize(18f);
        textView.setLayoutParams(getLayoutParams(16));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // change activity
                Intent intent = new Intent(context, childActivity);
                intent.putExtra("tags", new TagList(tags));
                intent.putExtra("projectID", projectID);
                taskDetails.startActivityForResult(intent, REQUEST_CODE);
            }
        });
        return textView;
    }


    public void setChildActivity(Class selectTagClass) {
        childActivity = selectTagClass;
    }
}
