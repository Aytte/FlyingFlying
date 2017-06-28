package com.example.bumin.springball;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class End extends AppCompatActivity {

    TextView score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent it = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        score=(TextView)findViewById(R.id.scr);
        score.setText(String.valueOf(it.getIntExtra("score",0)));
    }
}
