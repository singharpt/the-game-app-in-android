package com.example.axs210204_asg5;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.sql.Timestamp;
import java.util.Date;

public class ScoreDisplay extends AppCompatActivity {

    public static final int REQ_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_display);
        setTitle("First Activity");

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
                if (data != null && data.getStringExtra(ScoreEntry.NEW_NAME) != null && data.getStringExtra(ScoreEntry.NEW_SCORE) != null && data.getStringExtra(ScoreEntry.NEW_DATE) != null) {
                    System.out.println(data.getStringExtra(ScoreEntry.NEW_NAME) + "  " + data.getStringExtra(ScoreEntry.NEW_SCORE) + "  " + data.getStringExtra(ScoreEntry.NEW_DATE));
                }
            }
        }
    }
}