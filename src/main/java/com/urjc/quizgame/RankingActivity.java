package com.urjc.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TableRow;
import android.widget.TableLayout;
import android.widget.TextView;
import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RankingActivity extends AppCompatActivity {

    String fichero = "ranking.txt";

    RankingHelper ranking = new RankingHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v){
                finish();
            }
        });

        display();
    }

   private void display(){
        TableLayout table = findViewById(R.id.rankingTable);
        ranking.clear();
       try {
           FileInputStream fin = openFileInput(fichero);
           DataInputStream dis = new DataInputStream(fin);
           String line = dis.readLine();
           StringBuilder sb = new StringBuilder();
           while(line != null){
               sb.append(line);
               line = dis.readLine();
           }
           String fileAsString = sb.toString();
           Log.d("Debug", fileAsString);
           JSONObject obj = new JSONObject(fileAsString);
           JSONArray array = obj.getJSONArray("ranking");
           for(int i = 0; i < array.length(); i++){
               JSONObject jo = array.getJSONObject(i);
               TableRow tr = new TableRow(this);
               TextView text = new TextView(this);
               text.setText(jo.getString("name") + jo.getInt("points"));
               tr.addView(text);
               table.addView(tr);
           }
           fin.close();
       } catch (IOException e) {
           e.printStackTrace();
       } catch(JSONException e){
           e.printStackTrace();
       }

/*
       for(String s : ranking.getLines()){
           TableRow tr = new TableRow(this);
           TextView text = new TextView(this);
           text.setText(s);
           tr.addView(text);
           table.addView(tr);
       }

 */
   }
}
