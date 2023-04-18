package com.example.axs210204_asg5;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import java.util.HashMap;
import java.util.Random;

public class Balloons {
    public static final HashMap<String, Integer> colorsMap = new HashMap<String, Integer> ();
    RectF ROI;
    Boolean isBallonSqaure;
    int balloonSize, balloonColor, balloonSpeed;
    float balloonCorX, balloonCorY;
    Paint paint = new Paint();
    Random random = new Random();

    public void InitiateColorsMap() {
        colorsMap.put("Red", Color.RED);
        colorsMap.put("Orange", Color.rgb(255, 165, 0));
        colorsMap.put("Yellow", Color.YELLOW);
        colorsMap.put("Green", Color.GREEN);
        colorsMap.put("Blue", Color.BLUE);
        colorsMap.put("Purple", Color.rgb(128,0,128));
        colorsMap.put("White", Color.WHITE);
    }

    public int returnRandomColor() {
        int index = random.nextInt(colorsMap.size());
        String key = (String) colorsMap.keySet().toArray()[index];
        return colorsMap.get(key);
    }

    public Balloons(Context context, int canvasHeight, int canvasWidth, Boolean isballoonSquare){
        InitiateColorsMap();
        this.balloonSize = (int) convertToPixels(context.getApplicationContext(), random.nextInt((64-32)+1) + 32);
        this.isBallonSqaure = isballoonSquare;
        this.balloonColor = returnRandomColor();
        this.paint.setColor(balloonColor);
        this.balloonCorY = canvasHeight + balloonSize;
        this.balloonCorX = random.nextInt(canvasWidth - balloonSize);
        this.balloonSpeed = random.nextInt((12-6)+1) + 5;
        this.ROI = new RectF(balloonCorX, balloonCorY-balloonSize, balloonCorX+balloonSize, balloonCorY);
    }

    void drawBalloon(Canvas canvas) {
        if (this.isBallonSqaure) {
            canvas.drawRect(balloonCorX, balloonCorY - balloonSize, balloonCorX + balloonSize, balloonCorY, paint);
        }
        else{
            canvas.drawCircle(balloonCorX + balloonSize/2, balloonCorY - balloonSize/2, balloonSize/2, paint);
        }
        balloonCorY -= balloonSpeed;
    }

    public void incrementVelocity(int v){
        if(balloonSpeed + v <= 20){
            balloonSpeed += v;
        }
    }

    public void decrementVelocity(int v){
        if(balloonSpeed - v >= 2){
            balloonSpeed -= v;
        }
    }

    public float convertToPixels(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density + 0.5f;
    }
}
