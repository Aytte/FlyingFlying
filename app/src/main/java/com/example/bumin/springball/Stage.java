package com.example.bumin.springball;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

public class Stage extends AppCompatActivity {
    ArrayList<Ball> springs = new ArrayList<>();

    final float GRAVITY = (float) 0.03;

    Thread phyMaker = new Thread(new Physics());
    FrameLayout fram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);

        fram = (FrameLayout) findViewById(R.id.Frames);
        createBall();
    }

    @Override
    public void onBackPressed() {
        System.out.println("back ");
        isGameON = false;
        phyMaker.interrupt();
        super.onBackPressed();
    }

    void createBall() {
        springs.add(new Ball(this));
        fram.addView(springs.get(springs.size() - 1));
        springs.get(springs.size() - 1).setOnTouchListener(new onClickBounder());
    }

    class onClickBounder implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            Ball ball = (Ball) view;
            if (isGameON) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ball.setVelo((ball.getWidth() / 2 - motionEvent.getX()) / 60, -7);
                        break;
                }
            }
            return false;
        }
    }

    boolean isGameON = false;

    public void deb(View v) {
        if (!isGameON) {
            springs.get(0).setY(30);
            springs.get(0).setX(50);
            isGameON = true;
            phyMaker.start();
        } else {
            createBall();
        }
    }

    public void pu(View v) {
        isGameON = !isGameON;
        if (isGameON) {
            synchronized (phyMaker) {
                phyMaker.notify();
            }
        }
        System.out.println(isGameON);
    }

    class Physics implements Runnable {
        float gravity = GRAVITY;

        void setGravityAcc() {
            for (Ball ball : springs) {
                ball.setAcc(0, gravity);
            }
        }

        void addVelo() {
            for (Ball ball : springs) {
                ball.addVelo(ball.getAccX(), ball.getAccY());
            }
        }

        void locateBall() {
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
                if (ball.getX() + ball.getWidth() > fram.getWidth()) {
                    ball.setVelo(-(ball.getVeloX()), ball.getVeloY());
                }
                if (ball.getY() + ball.getHeight() > fram.getHeight()) {
                    ball.setVelo(ball.getVeloX(), -(ball.getVeloY()));
                }
            }
        }

        void updatePos() {
            setGravityAcc();
            addVelo();
            locateBall();
        }

        @Override
        public void run() {
            while (true) {
                if (isGameON) {
                    updatePos();
                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException e) {
                        e.getStackTrace();
                    }
                } else {
                    synchronized (phyMaker) {
                        try {
                            phyMaker.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}

