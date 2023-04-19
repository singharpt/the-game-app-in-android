package com.example.axs210204_asg5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class GamePlayActivity extends AppCompatActivity {

    Boolean isTargetShapeSquare;
    String targetBallonColor;
    Balloons balloonObj;
    Random random = new Random();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_game_view);
        balloonObj = new Balloons(getApplicationContext());
        CustomGameView customGameView = new CustomGameView(getApplicationContext());
        targetBallonColor = balloonObj.returnRandomColor();
        isTargetShapeSquare = random.nextInt(2) == 1;
        customGameView.setTargetVariables(isTargetShapeSquare, targetBallonColor);

        TextView instructionsTv = findViewById(R.id.instructionTv);
        String shape = ((isTargetShapeSquare) ? "Squares" : "Circles");
        String instructions = "choose only " + targetBallonColor + " colored " + shape;
        instructionsTv.setText(instructions);

        findViewById(R.id.instructionsOkayBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.whenGameStarts).setVisibility(View.VISIBLE);
                findViewById(R.id.instructions).setVisibility(View.INVISIBLE);
            }
        });
    }
}
