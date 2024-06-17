package com.example.taskmanagerment.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.taskmanagerment.R;

import java.util.List;

public class ColorListViewAdapter extends ArrayAdapter<String> {

    private String selectedColor;
    private int selectedIndex;
    private CheckBox selectedCheckBox;

    public ColorListViewAdapter(@NonNull Context context, List<String> colors, String selectedColor) {
        super(context, R.layout.color_list_item, colors);
        this.selectedColor = selectedColor;
        selectedCheckBox = null;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String color = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.color_list_item, parent, false);
        }
        View colorBox = convertView.findViewById(R.id.colorBox);
        TextView colorName = convertView.findViewById(R.id.colorName);
        CheckBox chkColorSelected = convertView.findViewById(R.id.chkColorSelected);
        colorBox.setBackgroundColor(Color.parseColor(color));
        colorName.setText(color);
        boolean isColorSelected = color.equals(selectedColor);
        chkColorSelected.setChecked(isColorSelected);
        chkColorSelected.setVisibility(isColorSelected?View.VISIBLE:View.INVISIBLE);
        if(isColorSelected) {
            selectedCheckBox = chkColorSelected;
        }
        // text view click listener
        colorName.setOnClickListener(colorNameOnClickListener(chkColorSelected));
        return convertView;
    }

    private View.OnClickListener colorNameOnClickListener(CheckBox chkColorSelected) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if selected checkbox != current check box
                if(chkColorSelected != selectedCheckBox) {
                    // true -> change selected check box and visible current checkbox
                    selectedCheckBox.setChecked(false);
                    selectedCheckBox.setVisibility(View.INVISIBLE);
                    chkColorSelected.setChecked(true);
                    chkColorSelected.setVisibility(View.VISIBLE);
                    selectedCheckBox = chkColorSelected;
                    selectedColor = ((TextView) view).getText().toString();
                } else {
                    selectedCheckBox.setChecked(!selectedCheckBox.isChecked());
                    selectedCheckBox.setVisibility(selectedCheckBox.isChecked()?View.VISIBLE:View.INVISIBLE);
                }
            }
        };
    }

    public String getSelectedColor() {
        return selectedColor;
    }

}
