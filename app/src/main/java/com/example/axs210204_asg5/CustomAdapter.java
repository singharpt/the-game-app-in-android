package com.example.axs210204_asg5;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * --> Written by Arpit Singh for class CS 6326, assignment 5 - The Android App. Net Id - AXS210204 <--
 * The following class: CustomeAdapter does the following: -
 * 1. It gets the ArrayList of objects fileData as an argument when object of this class is instantiated.
 * 2. It gets the text view and image view elements present on the score display screen.
 * 3. It sets the texts and images to the respective text views and image view using appropriate data.
 * 4. It renders the whole view on the screen.
 */
public class CustomAdapter extends ArrayAdapter<DataSchema> {

    private static class ViewHolder {
        TextView name;
        TextView score;
        TextView date;
        ImageView rank;
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
            viewHolder.rank = (ImageView) convertView.findViewById(R.id.tvRank);
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

        // The switch case displays the respective image icon in the rank column on the screen.
        switch (position) {
            case 0 : viewHolder.rank.setImageResource(R.drawable.medal_gold_winner_2_svgrepo_com);
                     break;
            case 1 : viewHolder.rank.setImageResource(R.drawable.medal_silver_badge_svgrepo_com);
                     break;
            case 2 : viewHolder.rank.setImageResource(R.drawable.medal_bronze_prize_svgrepo_com);
                break;
            case 3 : viewHolder.rank.setImageResource(R.drawable.ic_launcher_4_foreground);
                break;
            case 4 : viewHolder.rank.setImageResource(R.drawable.ic_launcher_5_foreground);
                break;
            case 5 : viewHolder.rank.setImageResource(R.drawable.ic_launcher_6_foreground);
                break;
            case 6 : viewHolder.rank.setImageResource(R.drawable.ic_launcher_7_foreground);
                break;
            case 7 : viewHolder.rank.setImageResource(R.drawable.ic_launcher_8_foreground);
                break;
            case 8 : viewHolder.rank.setImageResource(R.drawable.ic_launcher_9_foreground);
                break;
            case 9 : viewHolder.rank.setImageResource(R.drawable.ic_launcher_foreground_10);
                break;
            case 10 : viewHolder.rank.setImageResource(R.drawable.ic_launcher_foreground_11);
                break;
            case 11 : viewHolder.rank.setImageResource(R.drawable.ic_launcher_12_foreground);
                break;
            case 12 : viewHolder.rank.setImageResource(R.drawable.ic_launcher_13_foreground);
                break;
            case 13 : viewHolder.rank.setImageResource(R.drawable.ic_launcher_14_foreground);
                break;
            case 14 : viewHolder.rank.setImageResource(R.drawable.ic_launcher_15_foreground);
                break;
            case 15 : viewHolder.rank.setImageResource(R.drawable.ic_launcher_16_foreground);
                break;
            case 16 : viewHolder.rank.setImageResource(R.drawable.ic_launcher_17_foreground);
                break;
            case 17 : viewHolder.rank.setImageResource(R.drawable.ic_launcher_18_foreground);
                break;
            case 18 : viewHolder.rank.setImageResource(R.drawable.ic_launcher_19_foreground);
                break;
            case 19 : viewHolder.rank.setImageResource(R.drawable.ic_launcher_20_foreground);
                break;
            default : break;
        }
        // Return the completed view to render on screen
        return convertView;
    }
}
