package com.urjc.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableRow;
import android.widget.TableLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.util.Log;
import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import android.os.Environment;

public class RankingActivity extends AppCompatActivity {

    String fichero = "ranking.txt";
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        text = new TextView(this);

        saveRankingFile();
        readRankingFile();

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v){
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void readRankingFile(){

        TableLayout table = findViewById(R.id.rankingTable);

        try {
            FileInputStream fin = openFileInput(fichero);
            DataInputStream dis = new DataInputStream(fin);
            text.setText(dis.readLine());
            TableRow tr = new TableRow(this);
            table.addView(tr);

            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    private void saveRankingFile(){
        try {
            FileOutputStream fos = openFileOutput(fichero, MODE_PRIVATE);
            fos.write("ijbndidcbeiwf".getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
