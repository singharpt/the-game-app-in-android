package com.example.axs210204_asg5;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import android.os.Handler;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomGameView extends View {
    boolean gameRunning;
    int balloonsHit, balloonsMiss;
    Boolean istargetBallonSquare;
    String targetBallonColor;
    public ArrayList<Balloons> balloonsList;
    public ArrayList<Balloons> dumpBalloonsList;
    int canvasHeight, canvasWidth;
    Handler handler;
    TextView scoreTv, timeTv;
    Button saveScore, newGame;
    LinearLayout gameOverMenu;
    long currentTime;
    long gameTime;
    CountDownTimer timer;
    Random random = new Random();
    TextView instructionTv;

    public CustomGameView(Context context) {
        super(context);
        init(context);
    }

    public CustomGameView(Context context, @Nullable AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    public void init(Context context) {
        balloonsList = new ArrayList<Balloons>();
        dumpBalloonsList = new ArrayList<Balloons>();
        handler = new Handler();
        gameTime = 6000;
        gameRunning = false;
        balloonsHit = 0;
        balloonsMiss = 0;
    }

    public CustomGameView(Context context, Boolean isShapeSquare, String color) {
        super(context);
        this.istargetBallonSquare = isShapeSquare;
        this.targetBallonColor = color;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(gameRunning) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Balloons touchedBalloon = null;
                //Check if a balloon is touched and add it to the list
                for (Balloons b : balloonsList) {
                    if (b.balloonCorX < event.getX() && event.getX() < b.balloonCorX + b.balloonSize && b.balloonCorY > event.getY() && event.getY() > b.balloonCorY - b.balloonSize) {
                        touchedBalloon = b;
                    }
                }
                // check if touched balloon is the correct balloon
                if (touchedBalloon != null) {
                    //if(touchedBalloon.color == currentColor && ((currentShape && touchedBalloon instanceof Square) || (!currentShape && touchedBalloon instanceof Circle))){
                    if (Objects.equals(touchedBalloon.balloonColor, this.targetBallonColor) && touchedBalloon.isBallonSqaure.toString().equals(this.istargetBallonSquare.toString())) {
                        balloonsHit++;
                    } else {
                        System.out.println(touchedBalloon.balloonColor + " "+ targetBallonColor + " " + touchedBalloon.isBallonSqaure + " "+ istargetBallonSquare);
                        balloonsHit--; //decrement score if wrong balloon
                    }

                    //update score
                    scoreTv.setText(String.valueOf(balloonsHit));

                    //if 10 balloons popped the add 10s to the timer

                    balloonsList.remove(touchedBalloon);
                    if (timer != null && balloonsHit % 10 == 0 && balloonsHit != 0) {
                        timer.cancel();
                        initiateTimer(currentTime + 10000);
                        //timeTv.setText("+10s");
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        timeTv = getRootView().findViewById(R.id.timer);
        scoreTv = getRootView().findViewById(R.id.score);
        gameOverMenu = getRootView().findViewById(R.id.gameOverMenu);
        saveScore = getRootView().findViewById(R.id.saveScoreBtn);
        newGame = getRootView().findViewById(R.id.newGameBtn);
        instructionTv = getRootView().findViewById(R.id.instructionTv);
        String[] instructionTvText = instructionTv.getText().toString().split(" ");
        targetBallonColor = instructionTvText[2];
        istargetBallonSquare = ((instructionTvText[4]=="Squares") ? true : false);
        canvasHeight = getHeight();
        canvasWidth = getWidth();
        int balloonsRange = random.nextInt(7)+6;
        if (!gameRunning && balloonsList.size() == 0) {
            int i = 0;
            while (i < balloonsRange) {
                int temp = random.nextInt(9) + 9;
                if (temp % 2 == 0){
                    balloonsList.add(new Balloons(getContext(), canvasHeight, canvasWidth, true));
                }
                else {
                    balloonsList.add(new Balloons(getContext(), canvasHeight, canvasWidth, false));
                }
                i += 1;
            }
            initiateTimer(gameTime);
            gameRunning = true;
        }

        if (gameRunning) {

            //Check if balloons overlap
            for(Balloons b1 : balloonsList){
                for (Balloons b2 : balloonsList) {
                    if (b1.ROI.intersect(b2.ROI)) {
                        if(b1.balloonCorY < b2.balloonCorY){
                            b1.increaseSpeed(2);
                            b2.decreaseSpeed(2);
                        }else{
                            b2.increaseSpeed(2);
                            b1.decreaseSpeed(2);
                        }
                    }
                }
                b1.drawBalloon(canvas);
            }

            //add disappeared balloons to dump list
            for(Balloons b : balloonsList){
                if(b.balloonCorY <= 0 || b.balloonCorY > canvasHeight + b.balloonSize){
                    if(b.balloonColor == targetBallonColor && b.isBallonSqaure == istargetBallonSquare){
                        balloonsHit++;
                    }
                    //add current balloon to dump balloon list
                    dumpBalloonsList.add(b);
                }
            }

            //remove disappeared balloons from balloons list
            for(Balloons b : dumpBalloonsList){
                balloonsList.remove(b);
            }

            //create new random balloons when they are popped or disappear
            for(int i=0; i < balloonsRange - balloonsList.size() ; i++){
                int temp = random.nextInt(9) + 9;
                if (temp % 2 == 0){
                    balloonsList.add(new Balloons(getContext(), canvasHeight, canvasWidth, true));
                }
                else {
                    balloonsList.add(new Balloons(getContext(), canvasHeight, canvasWidth, false));
                }
            }
        }

        //This function keeps calling onDraw() function every 0.01 sec
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        },10);
    }

    //timer function
    public void initiateTimer(long time){
        timer = new CountDownTimer(time, 1000){
            public void onTick(long millisUntilDone){
                timeTv.setText( String.valueOf(millisUntilDone / 1000) + "s");
                currentTime=millisUntilDone;
            }
            public void onFinish() {
                timeTv.setText( "00:00");
                scoreTv.setText(String.valueOf(balloonsHit));
                gameRunning = false;
                gameOverMenu.setVisibility(VISIBLE);
                saveScore.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                newGame.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getContext(), GamePlayActivity.class);
                        getContext().startActivity(i);
                    }
                });
            }
        }.start();
    }
}
