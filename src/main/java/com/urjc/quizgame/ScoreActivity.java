package com.urjc.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;

import android.util.Log;

import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

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

    EditText et;

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

        //Si entramos en el ranking
        if(Integer.parseInt(getIntent().getStringExtra("SCORE")) > ranking.getLower() || !ranking.isFull()) {


            //Pedimos nombre al usuario
            TableLayout table = findViewById(R.id.table);
            TableRow tr1 = new TableRow(this);
            TableRow tr2 = new TableRow(this);
            et = new EditText(this);
            TextView tex = new TextView(this);
            tex.setText("¡Nuevo récord! Introduce tus iniciales");
            tr1.addView(tex);
            tr2.addView(et);
            table.addView(tr1);
            table.addView(tr2);

            //El botón guarda los datos antes de pasar de actividad
            menuBtn.setText("Guardar");
            menuBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    ranking.addIfIsRecord(et.getText().toString(), Integer.parseInt(getIntent().getStringExtra("SCORE")));
                    ranking.sort();
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
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);
                }
            });

        }
    }
}
