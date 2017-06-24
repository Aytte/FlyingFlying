package com.example.bumin.springball;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Vector;

public class Stage extends AppCompatActivity {
    final float GRAVITY = (float) 0.03;
    final int X = 0;
    final int Y = 1;

    Vector<Float> acc = new Vector<>();
    Vector<Float> velo = new Vector<>();
    Vector<Float> loc = new Vector<>();
    Vector<Float> mousePos = new Vector<>();

    ImageView ball;


    void initQuantity() {
        try {
            acc.set(X, (float) 0);
            acc.set(Y, GRAVITY);
            velo.set(X, (float) 0);
            velo.set(Y, (float) 0);
            loc.set(X, ball.getX());
            loc.set(Y, ball.getY());
        } catch (IndexOutOfBoundsException e) {
            acc.add((float) 0);
            acc.add(GRAVITY);
            velo.add((float) 0);
            velo.add((float) 0);
            loc.add(ball.getX());
            loc.add(ball.getY());
            mousePos.add((float) 0);
            mousePos.add((float) 0);
        }
    }

    float getCenterX(View v) {
        return v.getWidth() / 2;
    }

/*
    float getCenterY(View v) {
        return v.getHeight() / 2;
    }
*/

    Thread physicer = new Thread(new GravityMaker());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);
        ball = (ImageView) findViewById(R.id.ball);

        /*
        PlayView play = new PlayView(this);
        setContentView(play);
        */
    }

    boolean isGameON = false;

    public void deb(View v) {
        isGameON = false;
        ball.setY(30);
        ball.setX(50);
    }

    class GravityMaker implements Runnable {
        void adjustAcc() {
            if (acc.get(Y) < GRAVITY) {
                acc.set(Y, acc.get(Y) + (float) 0.005);
            }
            //System.out.println(acc.get(Y));
        }

        void addVelo() {
            velo.set(X, velo.get(X) + acc.get(X));
            velo.set(Y, velo.get(Y) + acc.get(Y));
            //System.out.println(velo.get(Y));
        }

        void locateBall() {
            loc.set(X, loc.get(X) + velo.get(X));
            loc.set(Y, loc.get(Y) + velo.get(Y));
            ball.setX(loc.get(X));
            ball.setY(loc.get(Y));
            if (loc.get(X) < 0) {
                velo.set(X, -(velo.get(X)));
            }
            if (loc.get(Y) < 0) {
                velo.set(Y, -(velo.get(Y)));
            }
        }

        void updatePos() {
            adjustAcc();
            addVelo();
            locateBall();
            //System.out.println("Complete");
        }

        @Override
        public void run() {
            while (isGameON) {
                updatePos();
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    e.getStackTrace();
                }
            }
        }
    }
}
