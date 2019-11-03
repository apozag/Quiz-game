package com.urjc.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;

import android.view.View;
import android.widget.TextView;
import android.widget.Button;



public class ScoreActivity extends AppCompatActivity {

    TextView score;

    Button menuBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        setContentView(R.layout.activity_score);

        ((TextView)findViewById(R.id.score)).setText(getIntent().getStringExtra("SCORE"));
        ((TextView)findViewById(R.id.time)).setText(getIntent().getStringExtra("TIME"));


        menuBtn = findViewById(R.id.menuBtn);

        menuBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), MainActivity.class);

                startActivity(intent);
            }
        });
    }
}
