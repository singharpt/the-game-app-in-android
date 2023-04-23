package com.example.axs210204_asg5;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

/**
 * --> Written by Arpit Singh for class CS 6326, assignment 5 - The Android App. Net Id - AXS210204 <--
 * The following class: Balloons does the following: -
 * 1. Instantiates a Hashmap of colors, and uses the hashmap & a method returnRandomColor() to get random colors.
 * 2. The parameterized constructor instantiates all the properties of a balloon such as size, coordinates, speed, type & color etc.
 * 3. The drawBalloon function draws the balloon on the game screen.
 * 4. The increment/decrement functions increase/decrease the speed of the balloon on the screen by basically changing the Y coordinates of balloon.
 */

public class Balloons {

    //Defining important class variables
    public HashMap<String, Integer> colorsMap = new HashMap<String, Integer> ();
    RectF ROI;
    String balloonType;
    int balloonSize, balloonSpeed;
    String balloonColor;
    float balloonCorX, balloonCorY;
    Paint paint = new Paint();
    Random random = new Random();

    //This function adds colors data to the colorsMap Hashmap
    public void InitiateColorsMap() {
        colorsMap.put("Red", Color.RED);
        colorsMap.put("Orange", Color.rgb(255, 165, 0));
        colorsMap.put("Yellow", Color.YELLOW);
        colorsMap.put("Green", Color.rgb(0,200,0));
        colorsMap.put("Blue", Color.BLUE);
        colorsMap.put("Purple", Color.rgb(150,0,150));
        colorsMap.put("White", Color.WHITE);
    }

    //This function selects a random color from the colorsMap and returns it
    public String returnRandomColor() {
        int index = random.nextInt(colorsMap.size());
        String key = (String) colorsMap.keySet().toArray()[index];
        return key;
    }

    public Balloons(Context context) {
        InitiateColorsMap();
    }

    //Constructor to initialize all the properties of a balloon with random colors
    public Balloons(Context context, int canvasHeight, int canvasWidth, String balloonType) {
        InitiateColorsMap();
        this.balloonSize = (int) convertToPixels(context.getApplicationContext(), random.nextInt(33) + 32);
        this.balloonType = balloonType;
        this.balloonColor = returnRandomColor();
        this.paint.setColor(colorsMap.get(balloonColor));
        this.balloonCorY = canvasHeight + balloonSize;
        this.balloonCorX = random.nextInt(canvasWidth - balloonSize);
        this.balloonSpeed = random.nextInt(12) + 5;
        this.ROI = new RectF(balloonCorX, balloonCorY-balloonSize, balloonCorX+balloonSize, balloonCorY);
    }

    //Function to draw the balloon on screen.
    void drawBalloon(Canvas canvas) {
        if (Objects.equals(this.balloonType, "Squares")) {
            canvas.drawRect(balloonCorX, balloonCorY - balloonSize, balloonCorX + balloonSize, balloonCorY, paint);
        }
        else {
            canvas.drawCircle(balloonCorX + balloonSize/2, balloonCorY - balloonSize/2, balloonSize/2, paint);
        }
        ROI = new RectF(balloonCorX, balloonCorY-balloonSize, balloonCorX+balloonSize, balloonCorY);
        balloonCorY -= balloonSpeed;
    }

    //Function to increase speed of the balloon.
    public void increaseSpeed(int speed) {
        if(balloonSpeed + speed <= 20) {
            balloonSpeed += speed;
        }
    }

    //Function to decrease speed of the balloon.
    public void decreaseSpeed(int speed) {
        if(balloonSpeed - speed >= 2) {
            balloonSpeed -= speed;
        }
    }

    //This functions converts dp to pixels.
    public float convertToPixels(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density + 0.5f;
    }
}
