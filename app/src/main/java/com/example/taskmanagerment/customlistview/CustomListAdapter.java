package com.example.taskmanagerment.customlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.taskmanagerment.R;

import java.util.List;

public class CustomListAdapter extends ArrayAdapter<ItemModel> {

    private final Context context;

    private final List<ItemModel> items;

    public CustomListAdapter(Context context, List<ItemModel> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ItemModel item = items.get(position);
        int viewType = getItemViewType(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (viewType == ItemModel.TYPE_BOARD) {
                convertView = inflater.inflate(R.layout.board_item_litview, parent, false);
            } else if (viewType == ItemModel.TYPE_NOTIFICATION) {
                convertView = inflater.inflate(R.layout.notification_item_listview, parent, false);
            }
        }

        if (viewType == ItemModel.TYPE_BOARD) {
            TextView textView = convertView.findViewById(R.id.title_board_item_view);
            textView.setText(item.getTitle());
        } else if (viewType == ItemModel.TYPE_NOTIFICATION) {
            TextView titleView = convertView.findViewById(R.id.notification_title);
            TextView dateView = convertView.findViewById(R.id.notification_date);
            titleView.setText(item.getTitle());
            dateView.setText(item.getDate());
        }

        return convertView;
    }
}
