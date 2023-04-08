package com.example.axs210204_asg5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ScoreEntry extends AppCompatActivity {

    public static final String NEW_NAME = "newName";
    public static final String NEW_SCORE = "newScore";
    public static final String NEW_DATE = "newDate";

    EditText nameToSend;
    EditText scoreToSend;
    EditText dateToSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_entry);

        nameToSend = findViewById(R.id.eTextScore);
        scoreToSend = findViewById(R.id.eTextScore);
        dateToSend = findViewById(R.id.eTextName);

        findViewById(R.id.saveBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  inputName = nameToSend.getText().toString();
                String  inputScore = scoreToSend.getText().toString();
                String  inputDate = dateToSend.getText().toString();

                Intent intent = new Intent();
                intent.putExtra(NEW_NAME, inputName);
                intent.putExtra(NEW_SCORE, inputScore);
                intent.putExtra(NEW_DATE, inputDate);
                setResult(RESULT_OK, intent);
                System.out.println(inputName+inputScore+inputDate);
                finish();
            }
        });
    }
}