package com.example.bumin.springball;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bumin on 2017-06-24.
 */

public class Ball extends AppCompatImageView{

    final int X = 0;
    final int Y = 1;

    float acc[] = new float[]{0,0};
    float velo[] = new float[]{0,0};
    float loc[] = new float[]{0,0};

    public Ball(Context context) {
        super(context);
    }

    public Ball(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private float getCenterX(){
        return loc[X] + this.getWidth()/2;
    }

    private void initQuantity(){
        acc = new float[]{0,0};
        velo = new float[]{0,0};
        loc = new float[]{0,0};
    }

    public void onTouched(View v) {
        PlayView n = (PlayView) v;
        if (isGameON) {
            velo[Y] = -5;
            velo[X] = ((getCenterX() - loc[X])/ 100);
            System.out.println("hit!");
        } else {
            initQuantity();
        }
    }
}
