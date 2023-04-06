package com.example.axs210204_asg5;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;

public class ScoreDisplay extends AppCompatActivity {

    public static final int REQ_CODE = 100;
    public static final FileIO iobj = new FileIO();
    public static final Date date = new Date();

    protected void onLoad(){

        iobj.AddData(new String[] {"a", "1", String.valueOf(new Timestamp(date.getTime()+100000))});
        iobj.AddData(new String[] {"b", "2", String.valueOf(new Timestamp(date.getTime()))});
        iobj.AddData(new String[] {"b", "3", String.valueOf(new Timestamp(date.getTime()))});
        iobj.AddData(new String[] {"a", "1", String.valueOf(new Timestamp(date.getTime()))});

        Collections.sort(iobj.fileData, new DataComparator());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_display);
        setTitle("First Activity");
        iobj.GetFileData(this.getApplicationContext());
        onLoad();
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
        iobj.WriteFileData(this.getApplicationContext());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            if(resultCode == RESULT_OK){
                if (data != null && data.getStringExtra(ScoreEntry.NEW_NAME) != null && data.getStringExtra(ScoreEntry.NEW_SCORE) != null && data.getStringExtra(ScoreEntry.NEW_DATE) != null) {
                    System.out.println(data.getStringExtra(ScoreEntry.NEW_NAME) + "  " + data.getStringExtra(ScoreEntry.NEW_SCORE) + "  " + data.getStringExtra(ScoreEntry.NEW_DATE));
                }
            }
        }
    }
}