package com.example.axs210204_asg5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.sql.Timestamp;
import java.util.Date;

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
        Date date = new Date();
        System.out.println(String.valueOf(new Timestamp(date.getTime())));
        iobj.AddData(new String[] {"a", "1", String.valueOf(new Timestamp(date.getTime()))});
        iobj.AddData(new String[] {"b", "2", String.valueOf(new Timestamp(date.getTime()))});
        iobj.AddData(new String[] {"c", "3", String.valueOf(new Timestamp(date.getTime()))});
        iobj.AddData(new String[] {"d", "4", String.valueOf(new Timestamp(date.getTime()))});
        iobj.AddData(new String[] {"e", "5", String.valueOf(new Timestamp(date.getTime()))});
        System.out.println("/n/nAfter Adding Data /n");
        for (int i = 0; i < iobj.fileData.size(); i++) {
            System.out.println(iobj.fileData.get(i).name + "  " + iobj.fileData.get(i).score + "  " + iobj.fileData.get(i).date);
        }
        iobj.WriteFileData(this.getApplicationContext());
    }
}