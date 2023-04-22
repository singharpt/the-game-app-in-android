package com.example.axs210204_asg5;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class GamePlayActivity extends AppCompatActivity {

    Boolean isTargetShapeSquare;
    String targetBallonColor;
    Balloons balloonObj;
    Random random = new Random();

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
        balloonObj = new Balloons(getApplicationContext());
        CustomGameView customGameView = new CustomGameView(getApplicationContext());
        targetBallonColor = balloonObj.returnRandomColor();
        isTargetShapeSquare = random.nextInt(2) == 1;
        //customGameView.setInstructionVariables(isTargetShapeSquare, targetBallonColor);

        TextView instructionsTv = findViewById(R.id.instructionTv);
        ConstraintLayout balloonGame = findViewById(R.id.balloonGame);
        String shape = ((isTargetShapeSquare) ? "Squares" : "Circles");
        String instructions = "choose only " + targetBallonColor + " colored " + shape;
        instructionsTv.setText(instructions);
        int intTargetBallonColor = balloonObj.colorsMap.get(targetBallonColor);
        instructionsTv.setBackgroundColor(intTargetBallonColor);
        balloonGame.setBackgroundColor(intTargetBallonColor);
        if (targetBallonColor == "White" || targetBallonColor == "Yellow") {
            instructionsTv.setTextColor(Color.rgb(0,0,0));
        }
        else {
            instructionsTv.setTextColor(Color.WHITE);
        }

        findViewById(R.id.instructionsOkayBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.instructions).setVisibility(View.INVISIBLE);
                findViewById(R.id.mainGame).setVisibility(View.VISIBLE);
            }
        });
    }
}
