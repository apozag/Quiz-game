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

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class RankingActivity extends AppCompatActivity {

    FileInputStream fos;
    private static final String TAG = "Testing: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

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

        String linea;

        TableLayout table = findViewById(R.id.rankingTable);

        try {
            FileInputStream fi = openFileInput("../../res/ranking.txt");
                    InputStreamReader miReader = new InputStreamReader(fi);
            BufferedReader miBuffer = new BufferedReader(miReader);
            while ((linea = miBuffer.readLine()) != null) {
                TableRow tr = new TableRow(this);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                TextView text = new TextView(this);
                text.setText(linea);

                tr.addView(text);

                table.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));;
            }
            fi.close();

        }catch (Exception exc){
            Log.d(TAG, "ranking file not found!");
        }




    }
}
