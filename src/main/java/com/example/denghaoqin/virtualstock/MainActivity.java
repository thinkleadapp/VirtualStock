package com.example.denghaoqin.virtualstock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

import android.view.Menu;
import android.view.View.OnClickListener;



public class MainActivity extends AppCompatActivity {

    private Button buttonIntroduction;
    private Button startMission;
    private Button loadMission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonIntroduction = (Button) findViewById(R.id.buttonIntroduction);
        startMission = (Button) findViewById(R.id.startMission);
        loadMission=(Button)findViewById(R.id.loadMission);

        buttonIntroduction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, introduction.class);
                startActivity(intent);
            }
        });
        startMission.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, difficulty.class);
                startActivity(intent);
            }
        });
        loadMission.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, archive.class);
                startActivity(intent);
            }
        });


    }

}
