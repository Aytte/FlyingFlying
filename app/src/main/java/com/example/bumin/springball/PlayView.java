package com.example.bumin.springball;

import android.content.Context;
import android.media.Image;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by bumin on 2017-06-22.
 */

public class PlayView extends AppCompatImageView {
    public float xPos;
    public float yPos;

    public PlayView(Context context) {
        super(context);
    }

    public PlayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public float getXPos(){
        return xPos;
    }

    public float getYPos(){
        return yPos;
    }

    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        super.onTouchEvent(event);
        if(event.getAction() == MotionEvent.ACTION_DOWN ){
            System.out.println(event.getX());
            System.out.println(event.getY());
            xPos = event.getX();
            yPos = event.getY();
            return true;
        }

        return false;
    }
}