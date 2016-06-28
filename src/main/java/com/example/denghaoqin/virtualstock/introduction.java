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

public class introduction extends AppCompatActivity {

    private Button returnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        returnHome = (Button) findViewById(R.id.returnHome);
        returnHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(introduction.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}