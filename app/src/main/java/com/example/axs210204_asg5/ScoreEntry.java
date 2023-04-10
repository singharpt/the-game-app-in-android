package com.example.axs210204_asg5;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class ScoreEntry extends AppCompatActivity {
    public static final String NEW_NAME = "name";
    public static final String NEW_SCORE = "score";
    public static final String NEW_DATETIME = "datetime";
    EditText nameToSend;
    EditText scoreToSend;
    EditText dateToSend;
    EditText timeToSend;
    TextInputLayout nameLayout;
    TextInputLayout scoreLayout;
    TextInputLayout dateLayout;
    TextInputLayout timeLayout;
    private void openDateDialog() {
        DatePickerDialog dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth)  {
                String text = String.valueOf(year) + "/" + String.valueOf(month+1) + "/" + String.valueOf(dayOfMonth);
                dateToSend.setText(text);
            }
        }, 2023, 0, 1);
        dateDialog.show();
    }
    private void openTimeDialog() {
        TimePickerDialog timeDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {
                String text = String.valueOf(hour)+ ":" + String.valueOf(min);
                timeToSend.setText(text);
            }
        }, 0, 0, true);
        timeDialog.show();
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_entry);
        nameToSend = findViewById(R.id.eTextName);
        scoreToSend = findViewById(R.id.eTextScore);
        dateToSend = findViewById(R.id.eTextDate);
        timeToSend = findViewById(R.id.eTextTime);
        nameLayout = findViewById(R.id.layoutName);
        scoreLayout = findViewById(R.id.layoutScore);
        dateLayout = findViewById(R.id.layoutDate);
        timeLayout = findViewById(R.id.layoutTime);
        scoreToSend.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String score = charSequence.toString();
                boolean checkFlag = false;
                int scoreValue = 0;
                try {
                    scoreValue = Integer.parseInt(score);
                    checkFlag = true;
                } catch (NumberFormatException e) {
                    checkFlag = false;
                }
                if (checkFlag && scoreValue > 0) {
                    scoreLayout.setError("");
                    scoreLayout.setHelperText("");
                }
                else {
                    scoreLayout.setError("Enter only digits greater than 0");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        dateToSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDateDialog();
            }
        });
        timeToSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimeDialog();
            }
        });
        //findViewById(R.id.saveBtn).setVisibility(View.INVISIBLE);
        findViewById(R.id.saveBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  inputName = nameToSend.getText().toString();
                String  inputScore = scoreToSend.getText().toString();
                String  inputDateTime = dateToSend.getText().toString() + " " + timeToSend.getText().toString();
                Intent intent = new Intent();
                intent.putExtra(NEW_NAME, inputName);
                intent.putExtra(NEW_SCORE, inputScore);
                intent.putExtra(NEW_DATETIME, inputDateTime);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
