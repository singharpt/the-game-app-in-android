package com.example.axs210204_asg5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<DataSchema> {
    // View lookup cache
    private static class ViewHolder {
        TextView name;
        TextView score;
        TextView date;
        TextView rank;
    }

    public CustomAdapter(Context context, ArrayList<DataSchema> users) {
        super(context, R.layout.custom_listview_layout, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DataSchema data = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_listview_layout, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.score = (TextView) convertView.findViewById(R.id.tvScore);
            viewHolder.date = (TextView) convertView.findViewById(R.id.tvDate);
            viewHolder.rank = (TextView) convertView.findViewById(R.id.tvRank);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.

        viewHolder.name.setText(data.name);
        viewHolder.score.setText(String.valueOf(data.score));
        viewHolder.date.setText(String.valueOf(data.date));
        viewHolder.rank.setText(String.valueOf(position+1));

        // Return the completed view to render on screen
        return convertView;
    }
}
