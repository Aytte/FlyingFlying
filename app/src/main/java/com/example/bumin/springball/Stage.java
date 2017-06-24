package com.example.bumin.springball;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Vector;

public class Stage extends AppCompatActivity {
    ArrayList<Ball> springs = new ArrayList<Ball>();

    final float GRAVITY = (float) 0.03;
    final int X = 0;
    final int Y = 1;

    float getCenterX(View v) {
        return v.getWidth() / 2;
    }

/*
    float getCenterY(View v) {
        return v.getHeight() / 2;
    }
*/

    Thread phyMaker = new Thread(new Physics());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);
        springs.add(new Ball(this));
        /*
        PlayView play = new PlayView(this);
        setContentView(play);
        */

        FrameLayout fram = (FrameLayout)findViewById(R.id.Frames);
        fram.addView(springs.get(0));
    }

    boolean isGameON = false;

    public void deb(View v) {
        isGameON = false;
        springs.get(0).setY(30);
        springs.get(0).setX(50);
        isGameON = true;
        phyMaker.start();
        System.out.println("button called");
    }

    class Physics implements Runnable {
        float gravity = GRAVITY;

        void setGravityAcc() {
            for (Ball ball : springs) {
                ball.setAcc(0, gravity);
            }
        }

        void addVelo() {
 /*           velo.set(X, velo.get(X) + acc.get(X));
            velo.set(Y, velo.get(Y) + acc.get(Y));
            //System.out.println(velo.get(Y));*/

            for (Ball ball : springs) {
                ball.addVelo(ball.getAccX(), ball.getAccY());
            }
        }

        void locateBall() {
/*            loc.set(X, loc.get(X) + velo.get(X));
            loc.set(Y, loc.get(Y) + velo.get(Y));*/

            for (Ball ball : springs) {
                ball.setLoc(ball.getX() + ball.getVeloX(), ball.getY() + ball.getVeloY());
                ball.setX(ball.getLocX());
                ball.setY(ball.getLocY());
                if (ball.getX() < 0) {
                    ball.setVelo(-(ball.getVeloX()), ball.getVeloY());
                }
                if (ball.getY() < 0) {
                    ball.setVelo(ball.getVeloX(), -(ball.getVeloY()));
                }
            }

/*            ball.setX(loc.get(X));
            ball.setY(loc.get(Y));
            if (loc.get(X) < 0) {
                velo.set(X, -(velo.get(X)));
            }
            if (loc.get(Y) < 0) {
                velo.set(Y, -(velo.get(Y)));
            }*/
        }

        void updatePos() {
            setGravityAcc();
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

