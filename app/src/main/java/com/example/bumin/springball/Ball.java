package com.example.bumin.springball;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by bumin on 2017-06-24.
 */

public class Ball extends AppCompatImageView{

    final int X = 0;
    final int Y = 1;

    private float acc[] = new float[]{0,0};
    public float velo[] = new float[]{0,0};
    public float loc[] = new float[]{0,0};

    public Ball(Context context) {
        super(context);
        setImageResource(R.drawable.ball);
        setX(0);
        setY(0);
        setLayoutParams(new FrameLayout.LayoutParams(240,240));
        System.out.println("Called by context");
    }

    public Ball(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private float getCenterX(){
        return loc[X] + this.getWidth()/2;
    }

    public void setAcc(float x, float y){
        this.acc[X] = x;
        this.acc[Y] = y;
    }

    public void addAcc(float x, float y){
        this.acc[X] += x;
        this.acc[Y] += y;
    }

    public float getAccX(){
        return this.acc[X];
    }

    public float getAccY(){
        return this.acc[Y];
    }

    public void setVelo(float x, float y){
        this.velo[X] = x;
        this.velo[Y] = y;
    }

    public void addVelo(float x, float y){
        this.velo[X] += x;
        this.velo[Y] += y;
    }

    public float getVeloX(){
        return this.velo[X];
    }

    public float getVeloY(){
        return this.velo[Y];
    }

    public void setLoc(float x, float y){
        this.loc[X] = x;
        this.loc[Y] = y;
    }

    public void addLoc(float x, float y){
        this.loc[X] += x;
        this.loc[Y] += y;
    }

    public float getLocX(){
        return this.loc[X];
    }

    public float getLocY(){
        return this.loc[Y];
    }

    private void initQuantity(){
        acc = new float[]{0,0};
        velo = new float[]{0,0};
        loc = new float[]{0,0};
    }

    /*
    public void onTouched(View v) {
        PlayView n = (PlayView) v;
        if (isGameON) {
            velo[Y] = -5;
            velo[X] = ((getCenterX() - loc[X])/ 100);
            System.out.println("hit!");
        } else {
            initQuantity();
        }
    }*/
}
