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
import android.widget.ImageView;
import android.widget.TimePicker;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

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
    Calendar cal = Calendar.getInstance();
    private void saveBtnVisibility() {
        if (nameLayout.getHelperText() == "Satisfied" && scoreLayout.getHelperText() == "Satisfied") {
            findViewById(R.id.saveBtn).setVisibility(View.VISIBLE);
        }
        else { findViewById(R.id.saveBtn).setVisibility(View.INVISIBLE); }
    }
    private String checkDigit(int number) { return number <= 9 ? "0" + number : String.valueOf(number); }
    private void openDateDialog() {
        DatePickerDialog dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth)  {
                String text = checkDigit(year) + "/" + checkDigit(month+1) + "/" + checkDigit(dayOfMonth);
                dateToSend.setText(text);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        dateDialog.getDatePicker().setMaxDate(cal.getInstance().getTimeInMillis());
        dateDialog.show();
    }
    private void openTimeDialog() {
        TimePickerDialog timeDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {
                String text = checkDigit(hour)+ ":" + checkDigit(min);
                timeToSend.setText(text);
            }
        }, cal.get(Calendar.HOUR), cal.get(Calendar.HOUR), true);
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
        dateToSend.setText(checkDigit(cal.get(Calendar.YEAR))+"/"+checkDigit(cal.get(Calendar.MONTH)+1)+"/"+checkDigit(cal.get(Calendar.DAY_OF_MONTH)));
        timeToSend.setText(checkDigit(cal.get(Calendar.HOUR))+":"+checkDigit(cal.get(Calendar.MINUTE)));
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy/M/d H:m");
        DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("H:m");
        findViewById(R.id.saveBtn).setVisibility(View.INVISIBLE);
        nameToSend.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String name = nameToSend.getText().toString();
                if (!b) {
                    if (name.isEmpty()) {
                        nameLayout.setHelperText("");
                        nameLayout.setError("Name field can't be left empty!");
                    }
                    else { nameLayout.setHelperText("Satisfied");}
                }
                saveBtnVisibility();
            }
        });
        nameToSend.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty()) {
                    nameLayout.setHelperText("");
                    nameLayout.setError("Name field can't be left empty!");
                }
                else { nameLayout.setHelperText("Satisfied");}
                saveBtnVisibility();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        scoreToSend.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String score = charSequence.toString();
                boolean checkFlag = false;
                int scoreValue = 0;
                try {
                    scoreValue = Integer.parseInt(score);
                    checkFlag = true;
                } catch (NumberFormatException e) { checkFlag = false; }
                if (checkFlag && scoreValue > 0) { scoreLayout.setHelperText("Satisfied");}
                else {
                    scoreLayout.setHelperText("");
                    scoreLayout.setError("Enter only digits greater than 0");
                }
                saveBtnVisibility();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        dateToSend.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                //has focus
                if (b) { openDateDialog(); }
            }
        });
        dateToSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openDateDialog(); }
        });
        timeToSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openTimeDialog(); }
        });
        timeToSend.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                //has focus
                if (b) { openTimeDialog(); }
            }
        });
        findViewById(R.id.saveBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  inputName = nameToSend.getText().toString();
                String  inputScore = scoreToSend.getText().toString();
                String  inputDateTime = dateToSend.getText().toString() + " " + timeToSend.getText().toString();
                if (!inputName.isEmpty() && !inputScore.isEmpty()) {
                    Intent intent = new Intent();
                    intent.putExtra(NEW_NAME, inputName);
                    intent.putExtra(NEW_SCORE, inputScore);
                    intent.putExtra(NEW_DATETIME, inputDateTime);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
