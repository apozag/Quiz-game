package com.urjc.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;

import android.util.Log;

import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class ScoreActivity extends AppCompatActivity {

    TextView score;

    Button menuBtn;

    RankingHelper ranking = new RankingHelper();

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

        try {
            ranking.clear();
            FileInputStream fin = openFileInput("ranking.txt");
            DataInputStream dis = new DataInputStream(fin);

            String line = dis.readLine();
            StringBuilder sb = new StringBuilder();
            while(line != null){
                sb.append(line).append("\n");
                line = dis.readLine();
            }
            String fileAsString = sb.toString();
            Log.d("Debug", fileAsString);
            JSONObject obj = new JSONObject(fileAsString);
            JSONArray array = obj.getJSONArray("ranking");
            for(int i = 0; i < array.length(); i++){
                JSONObject jo = array.getJSONObject(i);
                ranking.addIfIsRecord(jo.getString("name"), jo.getInt("points"));
            }
            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(JSONException e){
            e.printStackTrace();
        }

        if(ranking.addIfIsRecord("pepe", Integer.parseInt(getIntent().getStringExtra("SCORE")))) {
            FileOutputStream fos;
            try {
                fos = openFileOutput("ranking.txt", MODE_PRIVATE);
                String s = ranking.toString();
                Log.d("Debug", s);
                fos.write(s.getBytes());
                fos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
