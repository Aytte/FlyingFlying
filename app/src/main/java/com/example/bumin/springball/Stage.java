package com.example.bumin.springball;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class Stage extends AppCompatActivity {
    ArrayList<Ball> springs = new ArrayList<>();

    final float GRAVITY = (float) 0.02;
    final float SPRING_POWER = 5;

    Thread phyMaker = new Thread(new Physics());
    FrameLayout fram;

    TextView scr;
    int score = 0;
    boolean canAddBall = false;
    boolean addBall = false;
    int ballColor;

    final Handler handler = new Handler();
    Thread scrAdder = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                if (isGameON) {
                    score++;
//                        scr.setText(String.valueOf(score));
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            scr.setText(String.valueOf(score));
                            if(score % 15 == 0){
                                addBall = true;
                            }
                            if(addBall && canAddBall){
                                createBall();
                                addBall = false;
                            }
                        }
                    });
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    });

    void endGame() {
        isGameON = false;
        scrAdder.interrupt();
        phyMaker.interrupt();
        Intent it = new Intent(Stage.this, End.class);
        it.putExtra("score",score);
        startActivity(it);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);

        fram = (FrameLayout) findViewById(R.id.Frames);
        scr = (TextView) findViewById(R.id.score);
        scrAdder.start();
        synchronized (springs) {
            createBall();
        }
    }

    @Override
    public void onBackPressed() {
        System.out.println("back ");
        pu();
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
                        ball.setVelo((ball.getWidth() / 2 - motionEvent.getX()) / 60, -SPRING_POWER);
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

    public void pu() {
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
        boolean gameOver = false;

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
                    try {
                        switch (ball.hit) {
                            case 0:
                                ball.hit++;
                                ball.setImageResource(R.drawable.ball_green);
                                break;
                            case 1:
                                ball.hit++;
                                ball.setImageResource(R.drawable.ball_red);
                                break;
                            case 2:
                                gameOver = true;
                                break;
                        }
                    } catch (Exception e) {
                    }

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
                    synchronized (springs) {
                        canAddBall = false;
                        updatePos();
                        canAddBall = true;
                    }
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
                if (gameOver) {
                    endGame();
                    break;
                }
            }
        }
    }

/*    class Timer implements Runnable{
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        if(isGameON) {
                            score++;
                            scr.setText(String.valueOf(score));
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        }*/
    // }
}

