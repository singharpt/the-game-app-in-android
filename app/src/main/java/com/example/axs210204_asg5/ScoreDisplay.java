package com.example.axs210204_asg5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

public class ScoreDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_display);
        FileIO iobj = new FileIO();
        iobj.GetFileData(this.getApplicationContext());
        System.out.println("Before Adding Data /n");
        for (int i = 0; i < iobj.fileData.size(); i++) {
            System.out.println(iobj.fileData.get(i).name + "  " + iobj.fileData.get(i).score + "  " + iobj.fileData.get(i).date);
        }
        iobj.AddData(new String[] {"f", "6", new Timestamp.from(Instant.now())});
        iobj.AddData(new String[] {"g", "7", new Timestamp.from(Instant.now())});
        iobj.AddData(new String[] {"h", "8", new Timestamp.from(Instant.now())});
        iobj.AddData(new String[] {"i", "9", new Timestamp.from(Instant.now())});
        iobj.AddData(new String[] {"j", "10", new Timestamp.from(Instant.now())});
        System.out.println("/n/nAfter Adding Data /n");
        for (int i = 0; i < iobj.fileData.size(); i++) {
            System.out.println(iobj.fileData.get(i).name + "  " + iobj.fileData.get(i).score + "  " + iobj.fileData.get(i).date);
        }
        iobj.WriteFileData(this.getApplicationContext());
    }
}