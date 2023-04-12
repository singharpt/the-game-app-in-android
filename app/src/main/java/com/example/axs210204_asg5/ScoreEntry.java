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

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
/**
 * --> Written by Arpit Singh for class CS 6326, assignment 5 - The Android App. Net Id - AXS210204 <--
 * The following class: ScoreEntry does the following: -
 * 1. Sets current date & time in the date & time layout input fields on the display.
 * 2. Allow the user to input data in the nameLayout, scoreLayout and modify the data in dataLayout & timeLayout
 * 3. Checks for erros in the name & score data entered by user
 * 4. Upon validation of input data, makes the save button visible and allows the user to save the data entered.
 * 5. Sends the data back to ScoreDisplay activity.
 */
public class ScoreEntry extends AppCompatActivity {
    //Important variable declarations
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

    //This method makes the save button visible if user entered correct data for name & score.
    private void saveBtnVisibility() {
        if (nameLayout.getHelperText() == "Satisfied" && scoreLayout.getHelperText() == "Satisfied") {
            findViewById(R.id.saveBtn).setVisibility(View.VISIBLE);
        }
        else { findViewById(R.id.saveBtn).setVisibility(View.INVISIBLE); }
    }

    //This method converts the 24 hour format time to 12 hour format
    //Input - Takes in two int values Hours & Mins.
    //Ouput - Writtens a string values with format --> HH:MM AM/PM
    private String getTime(int hr,int min) {
        Time tme = new Time(hr,min,0);//seconds by default set to zero
        Format formatter;
        formatter = new SimpleDateFormat("hh:mm a");
        return formatter.format(tme);
    }

    //This methods converts the time format of H:M into HH:MM (Adds preceding zeroes)
    private String checkDigit(int number) { return number <= 9 ? "0" + number : String.valueOf(number); }

    //This method takes in an integer number and return AM if number is 0 otherwise it returns PM
    private String checkAMPM(int number) { return number == 0 ? "AM" : "PM"; }

    //This method instantiates the DataPickerDialog class which opens up the data picker dialog box on screen.
    private void openDateDialog() {
        DatePickerDialog dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth)  {
                String text = checkDigit(month+1) + "/" + checkDigit(dayOfMonth) + "/" + checkDigit(year);
                //The date selected by the user is displayed in the input field for date on screen.
                dateToSend.setText(text);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        //This lines sets the max date as the current date.
        dateDialog.getDatePicker().setMaxDate(cal.getInstance().getTimeInMillis());
        dateDialog.show();
    }

    //This method instantiates the TimePickerDialog class which opens up the time picker dialog box on screen.
    private void openTimeDialog() {
        TimePickerDialog timeDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {
                //The time selected by the user is first converted into 12 hour format from 24 hour format.
                //The 12 hours format is then displayed on the input field for time on screen.
                String text = getTime(hour, min);
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

        //The save button is made invisible so that use can't click it before entering valid data.
        findViewById(R.id.saveBtn).setVisibility(View.INVISIBLE);

        //Instantiating all the elements on the screen.
        nameToSend = findViewById(R.id.eTextName);
        scoreToSend = findViewById(R.id.eTextScore);
        dateToSend = findViewById(R.id.eTextDate);
        timeToSend = findViewById(R.id.eTextTime);
        nameLayout = findViewById(R.id.layoutName);
        scoreLayout = findViewById(R.id.layoutScore);
        dateLayout = findViewById(R.id.layoutDate);
        timeLayout = findViewById(R.id.layoutTime);

        //Gets the current date in the format mm/dd/yyyy and displays it on the date input field on screen.
        String fixDateText = checkDigit(cal.get(Calendar.MONTH)+1)+"/"+checkDigit(cal.get(Calendar.DAY_OF_MONTH))+"/"+checkDigit(cal.get(Calendar.YEAR));
        dateToSend.setText(fixDateText);

        //Gets the current date in the format mm/dd/yyyy and displays it on the date input field on screen.
        String fixTimeText = checkDigit(cal.get(Calendar.HOUR))+":"+checkDigit(cal.get(Calendar.MINUTE))+" "+checkAMPM(cal.get(Calendar.AM_PM));
        timeToSend.setText(fixTimeText);

        //Checks for data validation in the name input field field whenever the foccus enters it.
        //If user leaves the input field without entering anything, it changes the color to red and sets an error message.
//        nameToSend.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                String name = nameToSend.getText().toString();
//                if (!b) {
//                    if (name.isEmpty()) {
//                        nameLayout.setHelperText("");
//                        nameLayout.setError("Name field can't be left empty!");
//                    }
//                    else { nameLayout.setHelperText("Satisfied");}
//                }
//                //Calls this function to make save button visible if data is validated successfully.
//                saveBtnVisibility();
//            }
//        });

        //Checks for data validation in the name input field field whenever user makes text changes.
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
                //Calls this function to make save button visible if data is validated successfully.
                saveBtnVisibility();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        //Checks for data validation in the score input field field whenever user makes text changes
        //If user enters anything other than integer with values greater than 0
        //it changes the color of the input field to red and sets an error message.
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
                //Calls this function to make save button visible if data is validated successfully.
                saveBtnVisibility();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        //When the focus enters the date input field, this method opens up the date picker dialog on screen
        dateToSend.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                //has focus
                if (b) { openDateDialog(); }
            }
        });

        //When the user clicks on the date input field, this method opens up the date picker dialog on screen
        dateToSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openDateDialog(); }
        });

        //When the user clicks on the time input field, this method opens up the time picker dialog on screen
        timeToSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openTimeDialog(); }
        });

        //When the focus enters the time input field, this method opens up the time picker dialog on screen
        timeToSend.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                //has focus
                if (b) { openTimeDialog(); }
            }
        });

        //When the save button is visible and the user clicks on it.
        //First, there is one if condition which again checks if all there is no null data.
        //If data is valid it sends the data to the ScoreDisplay activity
        findViewById(R.id.saveBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Reads the data present in all the input fields on the screeen.
                String  inputName = nameToSend.getText().toString();
                String  inputScore = scoreToSend.getText().toString();
                String  inputDateTime = dateToSend.getText().toString() + " " + timeToSend.getText().toString();

                //Checks if data is not null null
                if (!inputName.isEmpty() && !inputScore.isEmpty()) {
                    Intent intent = new Intent();
                    intent.putExtra(NEW_NAME, inputName);
                    intent.putExtra(NEW_SCORE, inputScore);
                    intent.putExtra(NEW_DATETIME, inputDateTime);
                    //Sends the data to scoreDisplay activity
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
