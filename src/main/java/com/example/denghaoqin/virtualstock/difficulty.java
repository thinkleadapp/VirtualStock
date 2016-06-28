package com.example.denghaoqin.virtualstock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class difficulty extends AppCompatActivity {

    Button easy;
    Button medium;
    Button hard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);
        easy=(Button)findViewById(R.id.easy);
        medium=(Button)findViewById(R.id.medium);
        hard=(Button)findViewById(R.id.hard);

                easy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent();
                        intent.setClass(difficulty.this, selectstock.class);
                        intent.putExtra("difficulty",1);
                        intent.putExtra("loadfromarchive",0);
                        startActivity(intent);
                    }});

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(difficulty.this, selectstock.class);
                intent.putExtra("difficulty",2);
                intent.putExtra("loadfromarchive",0);
                startActivity(intent);
            }});


        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(difficulty.this, selectstock.class);
                intent.putExtra("difficulty",3);
                intent.putExtra("loadfromarchive",0);
                startActivity(intent);
            }});

    }
}
