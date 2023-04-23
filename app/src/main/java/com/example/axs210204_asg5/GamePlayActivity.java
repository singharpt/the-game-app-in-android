package com.example.axs210204_asg5;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;
import java.util.Random;

/**
 * --> Written by Arpit Singh for class CS 6326, assignment 5 - The Android App. Net Id - AXS210204 <--
 * The following class: GameScoreDisplay does the following: -
 * 1. Initiates the custom game view.
 * 2. Sets instructions regarding the target color and target balloon type to be popped.
 * 3. On clicking the okay button it makes the game visible and instruction page invisible.
 */

public class GamePlayActivity extends AppCompatActivity {

    Boolean isTargetShapeSquare;
    String targetBallonColor;
    Balloons balloonObj;
    TextView instructionsTv;
    ConstraintLayout balloonGame;
    Random random = new Random();

    //Whenever back is pressed, take the flow towards main menu screen.
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, GameMainMenu.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_game_view);

        //Creating an object of Balloon class to access its methods
        balloonObj = new Balloons(getApplicationContext());

        //Instantiating layout elements
        instructionsTv = findViewById(R.id.instructionTv);
        balloonGame = findViewById(R.id.balloonGame);

        //Generate target color and shape for the game
        targetBallonColor = balloonObj.returnRandomColor();
        int intTargetBallonColor = balloonObj.colorsMap.get(targetBallonColor);
        isTargetShapeSquare = random.nextInt(2) == 1;
        String shape = ((isTargetShapeSquare) ? "Squares" : "Circles");

        //Set the target color and shape as display on the instruction page.
        String instructions = "choose only " + targetBallonColor + " colored " + shape;
        instructionsTv.setText(instructions);

        //Set the color of instructions textview as the target color
        //Only when the background color is white or yellow make the text color as black for proper visibility otherwise white.
        instructionsTv.setBackgroundColor(intTargetBallonColor);
        if (Objects.equals(targetBallonColor, "White") || Objects.equals(targetBallonColor, "Yellow")) { instructionsTv.setTextColor(Color.rgb(0,0,0)); }
        else { instructionsTv.setTextColor(Color.WHITE); }

        //Set the color of game border as the target color
        balloonGame.setBackgroundColor(intTargetBallonColor);

        //When the user clicks the okay button make the game visible and make instructions invisible
        findViewById(R.id.instructionsOkayBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.instructions).setVisibility(View.INVISIBLE);
                findViewById(R.id.mainGame).setVisibility(View.VISIBLE);
            }
        });
    }
}
