package com.example.axs210204_asg5;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ScoreDisplay extends AppCompatActivity {
    public static final int REQ_CODE = 100;
    public static final FileIO iobj = new FileIO();
    public static final Date date = new Date();
    ListView listView;
    protected void onLoad(){
        listView = findViewById(R.id.mainlistView);
        Collections.sort(iobj.fileData, new DataComparator());
        // Get the first 20 elements of the dataArray
        List<DataSchema> firstTwenty = iobj.fileData.subList(0, Math.min(iobj.fileData.size(), 20));
        ArrayList<DataSchema> newList = new ArrayList<>(firstTwenty);
        CustomAdapter listViewAdapter = new CustomAdapter(getApplicationContext(), newList);
        listView.setAdapter(listViewAdapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_display);

        //Get data from the text file, if present
        iobj.GetFileData(this.getApplicationContext());
//        iobj.fileData.removeAll(iobj.fileData);
//        iobj.WriteFileData(this.getApplicationContext());

        //The current load function will load data in the list
        onLoad();

        //Print the data in array list of objects
        for (DataSchema k: iobj.fileData) {
            System.out.println(k.name + " " + k.score + " " + k.date);
        }
        findViewById(R.id.addBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScoreDisplay.this, ScoreEntry.class);
                startActivityForResult(intent, REQ_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            if(resultCode == RESULT_OK){
                if (data != null && data.getStringExtra(ScoreEntry.NEW_NAME) != null && data.getStringExtra(ScoreEntry.NEW_SCORE) != null && data.getStringExtra(ScoreEntry.NEW_DATETIME) != null) {
                    iobj.AddData(new String[] {data.getStringExtra(ScoreEntry.NEW_NAME), data.getStringExtra(ScoreEntry.NEW_SCORE), data.getStringExtra(ScoreEntry.NEW_DATETIME)});
                    onLoad();
                    iobj.WriteFileData(this.getApplicationContext());
                }
            }
        }
    }
}