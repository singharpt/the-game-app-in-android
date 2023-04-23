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

/**
 * --> Written by Arpit Singh for class CS 6326, assignment 5 - The Android App. Net Id - AXS210204 <--
 * The following class: CustomGameView does the following: -
 * 1. Instantiates all the game variables such as score, array list to hold all balloon objects, gameRunning flags, and more.
 * 2. Gets the current high score from the database.
 * 3. Contains method to process balloon popping & score counting.
 * 4. Contains the onDraw() method that draws balloons on the screen continuously every 0.01 second using a handler.
 * 5. Showcases options after game ends, and navigates to other activities as per user selection.
 */

public class CustomGameView extends View {

    //Defining important class variables.
    boolean gameRunning;
    Handler handler;
    int score, currentHighScore, balloonsHit, balloonsMiss, canvasHeight, canvasWidth;
    String targetBalloonType, targetBalloonColor;
    ArrayList<Balloons> balloonsList, dumpBalloonsList;
    TextView scoreTv, timeTv, instructionTv, gameFinishText;
    Button saveScore, newGame;
    LinearLayout gameOverMenu;
    long currentTime, gameTime;
    CountDownTimer timer;
    Random random = new Random();

    public CustomGameView(Context context) {
        super(context);
        init(context);
    }

    public CustomGameView(Context context, @Nullable AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    //Instantiates some important game variables.
    public void init(Context context) {
        balloonsList = new ArrayList<Balloons>();
        dumpBalloonsList = new ArrayList<Balloons>();
        handler = new Handler(); //Instantiates the new handler.
        gameTime = 60000; // Sets game time to 60 seconds.
        gameRunning = false; // Sets gameRunning flag to false.
        balloonsHit = 0;
        balloonsMiss = 0;
        score = 0;
        currentHighScore = 0;
    }

    //The function gets the current high score by looping over the database, and sets it to the variable currentHighScore.
    public void getHighScore() {
        for (DataSchema obj : FileIO.fileData) {
            if (obj.score > currentHighScore) {
                currentHighScore = obj.score;
            }
        }
    }

    //This function is called everytime user touches the screen.
    //First, it checks if user touched a balloon or not.
    //Second, it checks if user touched the target balloon.
    //If Yes, then it increments the score and if no, it decrements the score and displays the score on the screen.
    //Third, it keeps track of all the balloon user popped, if the user pops 10 balloons it adds 10 seconds to the timer.
    //Finally, all the popped balloons are added to the dumpBalloonList, so that they could be removed from our balloonsList.
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(gameRunning) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                for (Balloons b : balloonsList) {
                    if (b.balloonCorX < event.getX() && event.getX() < b.balloonCorX + b.balloonSize && b.balloonCorY > event.getY() && event.getY() > b.balloonCorY - b.balloonSize) {
                        if (Objects.equals(b.balloonColor, this.targetBalloonColor) && Objects.equals(b.balloonType, this.targetBalloonType)) {
                            score++;
                        } else {
                            score--;
                        }
                        balloonsHit++;
                        scoreTv.setText(String.valueOf(score));
                        dumpBalloonsList.add(b);
                        if (timer != null && balloonsHit % 10 == 0 && balloonsHit != 0) {
                            timer.cancel();
                            initiateTimer(currentTime + 10000);
                        }
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //Initiating screen elements into variables
        timeTv = getRootView().findViewById(R.id.timer);
        scoreTv = getRootView().findViewById(R.id.score);
        gameOverMenu = getRootView().findViewById(R.id.gameOverMenu);
        saveScore = getRootView().findViewById(R.id.saveScoreBtn);
        newGame = getRootView().findViewById(R.id.newGameBtn);
        instructionTv = getRootView().findViewById(R.id.instructionTv);
        gameFinishText = getRootView().findViewById(R.id.gameFinishText);

        //Getting target balloon type (Square, Circle) & color.
        String[] instructionTvText = instructionTv.getText().toString().split(" ");
        targetBalloonColor = instructionTvText[2];
        targetBalloonType = instructionTvText[4];

        //Getting height & width of the game screen
        canvasHeight = getHeight();
        canvasWidth = getWidth();

        //Balloons on the screen should be in a range of 6-12
        int balloonsRange = random.nextInt(7)+6;

        //If game is not running, add 6-12 balloons objects in the balloonsList
        //Set the gameRunning flag to true
        if (!gameRunning && balloonsList.size() == 0) {
            int i = 0;
            while (i < balloonsRange) {
                int temp = random.nextInt(9) + 9;
                if (temp % 2 == 0){
                    balloonsList.add(new Balloons(getContext(), canvasHeight, canvasWidth, "Squares"));
                }
                else {
                    balloonsList.add(new Balloons(getContext(), canvasHeight, canvasWidth, "Circles"));
                }
                i += 1;
            }
            initiateTimer(gameTime);
            gameRunning = true;
        }

        //If game is running we do 3 main operations
        //First, we check if no balloon overlap. If it happens, we change the speed of the balloons overlapping & draw them on the screen.
        //Second, as soon as balloon disappears on the screen add a new balloon to the screen. We also count the no of target balloons user missed.
        //Third, remove the disappeared or popped balloons from the balloonsList.
        if (gameRunning) {

            //First, checking if any balloon overlaps
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
                //After changing the speed of overlapping balloons, we draw all the balloons on the screen.
                b1.drawBalloon(canvas);
            }

            //Second, adding disappeared balloons to dumpBalloonsList
            for(Balloons b : balloonsList){
                if(b.balloonCorY <= 0 || b.balloonCorY > canvasHeight + b.balloonSize){
                    //Counting if user missed any target balloon that disappeared
                    if(Objects.equals(b.balloonColor, targetBalloonColor) && Objects.equals(b.balloonType, targetBalloonType)){
                        balloonsMiss++;
                    }
                    //add current balloon to dump balloon list
                    dumpBalloonsList.add(b);
                }
            }

            //Third, removing disappeared balloons from balloonsList
            for(Balloons b : dumpBalloonsList){
                balloonsList.remove(b);
            }

            //Also, creating new balloons to take place of popped & disappeared balloons
            for(int i=0; i < balloonsRange - balloonsList.size() ; i++){
                int temp = random.nextInt(9) + 9;
                if (temp % 2 == 0){
                    balloonsList.add(new Balloons(getContext(), canvasHeight, canvasWidth, "Squares"));
                }
                else {
                    balloonsList.add(new Balloons(getContext(), canvasHeight, canvasWidth, "Circles"));
                }
            }
        }

        //The handler keeps calling onDraw() function every 0.01 sec.
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        },10);
    }

    //This function initiates the timer and displays time left on the screen.
    //When the timer ends, it brings up the game over view that has 2 functions depending on user's high score.
    //If user creates a new high score it lets user save that score.
    //Otherwise, the second option allows the user to play new game.
    public void initiateTimer(long time){
        timer = new CountDownTimer(time, 1000){
            public void onTick(long millisUntilDone){
                timeTv.setText( String.valueOf(millisUntilDone / 1000) + "s");
                currentTime=millisUntilDone;
            }
            public void onFinish() {
                timeTv.setText( "00:00");
                scoreTv.setText(String.valueOf(score));
                gameRunning = false;
                gameOverMenu.setVisibility(VISIBLE);
                getHighScore();

                newGame.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), GamePlayActivity.class);
                        getContext().startActivity(intent);
                    }
                });

                if (score > currentHighScore) {
                    gameFinishText.setText(" NEW HIGH SCORE ");
                    saveScore.setVisibility(VISIBLE);
                    saveScore.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), GameScoreEntry.class);
                            intent.putExtra("Score", String.valueOf(score));
                            getContext().startActivity(intent);
                        }
                    });
                }
            }
        }.start();
    }
}
