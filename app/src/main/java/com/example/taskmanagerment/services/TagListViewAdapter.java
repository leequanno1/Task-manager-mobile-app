package com.example.taskmanagerment.services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.taskmanagerment.R;
import com.example.taskmanagerment.models.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagListViewAdapter extends ArrayAdapter<Tag> {

    private List<Tag> selectedTags;

    public TagListViewAdapter(@NonNull Context context, List<Tag> resource, List<Tag> selectedTags) {
        super(context, R.layout.edit_tag_list_item, resource);
        this.selectedTags = selectedTags;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Tag tag = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.edit_tag_list_item, parent, false);
        }

        RelativeLayout tagContainer = convertView.findViewById(R.id.tagContainer);
        TextView tagName = convertView.findViewById(R.id.tagName);
        CheckBox chkTagSelected = convertView.findViewById(R.id.chkTagSelected);
        ImageButton tagAdjust = convertView.findViewById(R.id.tagAdjust);

        tagContainer.setBackgroundColor(tag.getTagColor());
        tagName.setText(tag.getTagName());
        boolean isTagSelected = isTagExisted(tag.getTagID());
        chkTagSelected.setChecked(isTagSelected);
        chkTagSelected.setVisibility(isTagSelected?View.VISIBLE:View.INVISIBLE);

        tagName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chkTagSelected.setChecked(!chkTagSelected.isChecked());
            }
        });
        chkTagSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                chkTagSelected.setVisibility(b?View.VISIBLE:View.INVISIBLE);
            }
        });
        return convertView;
    }

    private boolean isTagExisted(int tagID) {
        for(int i = 0; i < selectedTags.size(); i++) {
            if(selectedTags.get(i).getTagID() == tagID){
                return true;
            }
        }
        return false;
    }


}
