package com.example.axs210204_asg5;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class Balloons {
    public HashMap<String, Integer> colorsMap = new HashMap<String, Integer> ();
    RectF ROI;
    String balloonType;
    int balloonSize, balloonSpeed;
    String balloonColor;
    float balloonCorX, balloonCorY;
    Paint paint = new Paint();
    Random random = new Random();

    public void InitiateColorsMap() {
        colorsMap.put("Red", Color.RED);
        colorsMap.put("Orange", Color.rgb(255, 165, 0));
        colorsMap.put("Yellow", Color.YELLOW);
        colorsMap.put("Green", Color.rgb(0,200,0));
        colorsMap.put("Blue", Color.BLUE);
        colorsMap.put("Purple", Color.rgb(150,0,150));
        colorsMap.put("White", Color.WHITE);
    }

    public String returnRandomColor() {
        int index = random.nextInt(colorsMap.size());
        String key = (String) colorsMap.keySet().toArray()[index];
        return key;
    }

    public Balloons(Context context){
        InitiateColorsMap();
    }

    public Balloons(Context context, int canvasHeight, int canvasWidth, String balloonType){
        InitiateColorsMap();
        this.balloonSize = (int) convertToPixels(context.getApplicationContext(), random.nextInt(33) + 32);
        this.balloonType = balloonType;
        this.balloonColor = returnRandomColor();
        this.paint.setColor(colorsMap.get(balloonColor));
        this.balloonCorY = canvasHeight + balloonSize;
        this.balloonCorX = random.nextInt(canvasWidth - balloonSize);
        this.balloonSpeed = random.nextInt(10) + 5;
        this.ROI = new RectF(balloonCorX, balloonCorY-balloonSize, balloonCorX+balloonSize, balloonCorY);
    }

    void drawBalloon(Canvas canvas) {
        if (Objects.equals(this.balloonType, "Squares")) {
            canvas.drawRect(balloonCorX, balloonCorY - balloonSize, balloonCorX + balloonSize, balloonCorY, paint);
        }
        else{
            canvas.drawCircle(balloonCorX + balloonSize/2, balloonCorY - balloonSize/2, balloonSize/2, paint);
        }
        ROI = new RectF(balloonCorX, balloonCorY-balloonSize, balloonCorX+balloonSize, balloonCorY);
        balloonCorY -= balloonSpeed;
    }

    public void increaseSpeed(int speed){
        if(balloonSpeed + speed <= 20){
            balloonSpeed += speed;
        }
    }

    public void decreaseSpeed(int speed){
        if(balloonSpeed - speed >= 2){
            balloonSpeed -= speed;
        }
    }

    public float convertToPixels(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density + 0.5f;
    }
}
