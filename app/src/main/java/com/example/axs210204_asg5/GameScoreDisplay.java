package com.example.axs210204_asg5;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * --> Written by Arpit Singh for class CS 6326, assignment 5 - The Android App. Net Id - AXS210204 <--
 * The following class: GameScoreDisplay does the following: -
 * 1. Initialises the FileIO class the has a data member ArrayList <Objects> and methods for read/write.
 * 2. Loads the data in the list view on application start
 * 5. Sorts the data in the ArrayList and display it back on the screen.
 * 5. Saves the data to the disk by using the write method in the FileIO class.
 */
public class GameScoreDisplay extends AppCompatActivity {

    //Variables Declarations
    private FileIO iobj = new FileIO();
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, GameMainMenu.class);
        startActivity(intent);
        finish();
    }
    ListView listView; //The list view variable will be instantiated with the ListView element on GameScoreDisplay Screen

    /**
     * 1. This function loads the data present in the ArrayList <objects> "iobj.fileData" in the listView.
     * 2. It first sorts the data using a custom class DataComparator present inside the file DataSchema.java.
     * 3. Creates a new array list with the top 20 elements.
     * 4. Displays it on the screen using a custom array adapter present inside the file CustomerAdapter.java.
     * 5. The data present in the arraylist is saved to the text file.
     */
    protected void onLoad(){
        System.out.println(FileIO.fileData.size());
        listView = findViewById(R.id.mainlistView);
        Collections.sort(FileIO.fileData, new DataComparator());
        List<DataSchema> firstTwenty = FileIO.fileData.subList(0, Math.min(FileIO.fileData.size(), 20));
        ArrayList<DataSchema> newList = new ArrayList<>(firstTwenty);
        CustomAdapter listViewAdapter = new CustomAdapter(getApplicationContext(), newList);
        listView.setAdapter(listViewAdapter);
        iobj.WriteFileData(this.getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_display);

        //The below line can clear data on an already present text file
        //FileIO.fileData.removeAll(FileIO.fileData);

        //The current load function will load data in the list
        onLoad();
    }
}
