package com.example.bumin.springball;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void onStart(View v){
        Intent mainMenu = new Intent(MainMenu.this,Stage.class);
        startActivity(mainMenu);
        overridePendingTransition(0,0);
    }
}
