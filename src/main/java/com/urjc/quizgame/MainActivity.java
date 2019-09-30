package com.urjc.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static int score = 3;
    private int highScore;
    private boolean newGame = true;
    private TextView HighscoreText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (newGame){
            this.highScore = 0;
            this.newGame = false;
        } else if (highScore < score){
            this.highScore = score;
        }
        HighscoreText = findViewById(R.id.textHighscore);
        HighscoreText.setText("Highscore: " + highScore);
        Button startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(v.getContext(), GameActivity.class);
                startActivity(intent);
            }
        });
    }
}
