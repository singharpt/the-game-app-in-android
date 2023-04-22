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
 * 3. Triggers the activity "GameScoreEntry" when the add button is clicked
 * 4. Gets the result back from the "GameScoreEntry" activity and adds it to the ArrayList of objects.
 * 5. Sorts the data in the ArrayList and display it back on the screen.
 * 5. Saves the data to the disk by using the write method in the FileIO class.
 */
public class GameScoreDisplay extends AppCompatActivity {

    //Variables Declarations
    private FileIO iobj = new FileIO();
    //private static final int REQ_CODE = 100; //This variable is used in startActivityForResult() function
//    public  GameScoreDisplay(){
//        iobj = new FileIO(); //Instantiated the object of FileIO Class
//    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, GameMainMenu.class);
        startActivity(intent);
        finish();
    }
    ListView listView; //The list view variable will be instantiated with the ListView element on GameScoreDisplay Screen

    /**
     * This function loads the data present in the ArrayList <objects> "iobj.fileData" in the listView.
     * 1. It first sorts the data using a custom class DataComparator present inside the file DataSchema.java.
     * 2. Creates a new array list with the top 20 elements.
     * 3. Displays it on the screen using a custom array adapter present inside the file CustomerAdapter.java.
     * 4. The data present in the arraylist is saved to the text file.
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

//    public void addNewScore(Context context) {
//        Intent intent = new Intent(GameScoreDisplay.this, GameScoreEntry.class);
//        startActivityForResult(intent, REQ_CODE);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_display);

//        //This if condition prevents data from being read mutilple times from the text file
//        if (FileIO.fileData.size() < 1) {
//            //The line triggers the read file method from the FileIO class
//            iobj.GetFileData(this.getApplicationContext());
//            System.out.println(FileIO.fileData.size());
//        }

        //The below two lines can clear data on an already present text file
//        iobj.fileData.removeAll(iobj.fileData);
//        iobj.WriteFileData(this.getApplicationContext());

        //The current load function will load data in the list
        onLoad();

        //The function triggered when user clicks on the add button on the display
//        findViewById(R.id.addBtn).setOnClickListener(new View.OnClickListener() {
//            //The method below instantiates the method startActivityForResult() between activities GameScoreDisplay & GameScoreEntry
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(GameScoreDisplay.this, GameScoreEntry.class);
//                startActivityForResult(intent, REQ_CODE);
//            }
//        });
    }

    //For this method: -
    //Input - Intent variable instatiated between activities scoreDisplay & scoreEntry, & int resultCode which is 100.
    //Output - The new entered data of (Name, Score & Date).
    //On receiving the output the onLoad method is called and data is saved to the text file.
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQ_CODE) {
//            if(resultCode == RESULT_OK){
//                if (data != null && data.getStringExtra(GameScoreEntry.NEW_NAME) != null && data.getStringExtra(GameScoreEntry.NEW_SCORE) != null && data.getStringExtra(GameScoreEntry.NEW_DATETIME) != null) {
//                    //Adds the new data in the ArrayList<objects> fileData using addData method of FileIO class
//                    iobj.AddData(new String[] {data.getStringExtra(GameScoreEntry.NEW_NAME), data.getStringExtra(GameScoreEntry.NEW_SCORE), data.getStringExtra(GameScoreEntry.NEW_DATETIME)});
//                    //Refreshes the screen with new data
//                    onLoad();
//                    //Saves the data to the text file
//                    iobj.WriteFileData(this.getApplicationContext());
//                }
//            }
//        }
//    }
}
