package com.example.axs210204_asg5;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.Random;
import android.os.Handler;

public class CustomGameView extends View {
    int state = 0;
    Boolean istargetBallonSquare;
    String targetBallonColor;
    public ArrayList<Balloons> balloonsData;
    int canvasHeight, canvasWidth;
    Handler handler;
    Random random = new Random();

    public CustomGameView(Context context) {
        super(context);
        init(context);
    }

    public CustomGameView(Context context, @Nullable AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    public void init(Context context) {
        balloonsData = new ArrayList<Balloons>();
        handler = new Handler();
    }

    public void setTargetVariables(Boolean isShapeSquare, String color) {
        this.istargetBallonSquare = isShapeSquare;
        this.targetBallonColor = color;
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvasHeight = getHeight();
        canvasWidth = getWidth();
        int balloonsRange = random.nextInt((12 - 6) + 1)+6;
        int i = 0;
        if (state == 0) {
            while (i < balloonsRange) {
                int temp = random.nextInt(9) + 9;
                if (temp % 2 == 0){
                    balloonsData.add(new Balloons(getContext(), canvasHeight, canvasWidth, true));
                }
                else {
                    balloonsData.add(new Balloons(getContext(), canvasHeight, canvasWidth, false));
                }
                i += 1;
            }
            state++;
        }

        //Check if balloons overlap
        for(Balloons ballon1 : balloonsData){
            for (Balloons ballon2 : balloonsData) {
                if (ballon1.ROI.intersect(ballon2.ROI)) {
                    ballon1.incrementVelocity(2);
                    ballon2.decrementVelocity(2);
                }
            }
            ballon1.drawBalloon(canvas);
        }

        System.out.println(balloonsData.size());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        },10);
    }
}
