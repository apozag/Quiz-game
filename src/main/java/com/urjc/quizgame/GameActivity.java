package com.urjc.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Debug;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;


public class GameActivity extends AppCompatActivity {

    private int score;
    private int MAX_QUESTIONS;
    private final static int MAX_VOLUME = 100;
    private float currentVolume;
    private SharedPreferences preferences;
    private ArrayList<Question> questions = new ArrayList<>();
    private RadioGroup radioGroup;
    private RadioButton radioBtn1;
    private RadioButton radioBtn2;
    private RadioButton radioBtn3;
    private RadioButton radioBtn4;
    private TextView question;
    private TextView questionCount;
    private TextView scoreText;
    private ImageView imageView;
    private Button chooseBtn;
    private Chronometer chronometer;
    private MediaPlayer player;

    private Question currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Music volume
        preferences = getSharedPreferences("MyPrefsFile", 0);
        MAX_QUESTIONS = (preferences.getInt("difficulty", 1) +1)* 6;
        currentVolume = (float) (1 - (Math.log(MAX_VOLUME - preferences.getInt("volume", 0)) / Math.log(MAX_VOLUME)));
        //Cronometro
        chronometer = findViewById(R.id.chronometer);
        startChronometer();

        readQuestions(getIntent().getStringExtra("MODE"));

        //COnfiguración RadioButtons

        radioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }

        });

        radioBtn1 = findViewById(R.id.radioBtn1);
        radioBtn2 = findViewById(R.id.radioBtn2);
        radioBtn3 = findViewById(R.id.radioBtn3);
        radioBtn4 = findViewById(R.id.radioBtn4);
        question = findViewById(R.id.question);
        questionCount = findViewById(R.id.textQuestionCount);
        scoreText= findViewById(R.id.textScore);
        score = 0;

        chooseBtn = findViewById(R.id.chooseBtn);
        imageView = findViewById(R.id.imageView);

        chooseBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(radioGroup.isEnabled()) {

                    int radioButtonID = radioGroup.getCheckedRadioButtonId();
                    View radioButton = radioGroup.findViewById(radioButtonID);
                    int selectedId = radioGroup.indexOfChild(radioButton);

                    System.out.println(selectedId);

                    if (selectedId != currentQuestion.getAnswer()) {
                        //fallo
                        switch (selectedId) {
                            case 0:
                                radioBtn1.setBackgroundColor(Color.RED);
                                break;
                            case 1:
                                radioBtn2.setBackgroundColor(Color.RED);
                                break;
                            case 2:
                                radioBtn3.setBackgroundColor(Color.RED);
                                break;
                            case 3:
                                radioBtn4.setBackgroundColor(Color.RED);
                                break;
                        }
                    } else {
                        //acierto
                        switch (selectedId) {
                            case 0:
                                radioBtn1.setBackgroundColor(Color.GREEN);
                                break;
                            case 1:
                                radioBtn2.setBackgroundColor(Color.GREEN);
                                break;
                            case 2:
                                radioBtn3.setBackgroundColor(Color.GREEN);
                                break;
                            case 3:
                                radioBtn4.setBackgroundColor(Color.GREEN);
                                break;
                        }
                        score += 1;
                        scoreText.setText("Score: " + score);
                    }

                    radioGroup.setEnabled(false);

                    chooseBtn.setText("Next");
                }
                else{
                    //Siguiente pregunta
                    if(currentQuestion.getId() <= questions.size()-2 && currentQuestion.getId() <= MAX_QUESTIONS)
                        changeQuestion (currentQuestion.getId() + 1);
                    else{
                        Intent intent = new Intent(v.getContext(), ScoreActivity.class);
                        intent.putExtra("SCORE", score+"");
                        intent.putExtra("TIME", chronometer.getText());
                        startActivity(intent);
                    }
                }

            }
        });


        changeQuestion(0);
    }

    private void changeQuestion(int pos){

        radioGroup.setEnabled(true);

        chooseBtn.setText("Choose");

        currentQuestion = questions.get(pos);

        radioGroup.clearCheck();

        question.setText(currentQuestion.getQuestion());

        questionCount.setText("Question " + (currentQuestion.getId() + 1) + "/" + questions.size());

        Log.d("Debug", "imageId: " + currentQuestion.getImageId());
        Log.d("Debug", "audioId: " + currentQuestion.getMusicId());

        if(!currentQuestion.getImageId().equals("null") && currentQuestion.getImageId()!= null) {
            int img = getResources().getIdentifier("@drawable/" + currentQuestion.getImageId(), null, this.getPackageName());
            imageView.setImageResource(img);
        }else{
            imageView.setImageResource(0);
        }

        //Detiene la reproducción de audio de la pregunta anterior
        stop();
        //Reproduce el nuevo audio en caso de que exista
        if (!currentQuestion.getMusicId().equals("null") && currentQuestion.getMusicId() != null){
            play();
        }

        radioBtn1.setText(currentQuestion.getOption(0));
        radioBtn2.setText(currentQuestion.getOption(1));
        radioBtn3.setText(currentQuestion.getOption(2));
        radioBtn4.setText(currentQuestion.getOption(3));

        radioBtn1.setBackgroundColor(0x00000000);
        radioBtn2.setBackgroundColor(0x00000000);
        radioBtn3.setBackgroundColor(0x00000000);
        radioBtn4.setBackgroundColor(0x00000000);
    }

    //Crea un nuevo reproductor en caso de que no exista y lo reproduce
    private void play(){
        if (player == null){
            int music = getResources().getIdentifier("@raw/"+currentQuestion.getMusicId(),null, this.getPackageName());
            player = MediaPlayer.create(this, music);
            player.setVolume(currentVolume, currentVolume);
        }
        player.start();
    }

    //Detiene la reproducción y libera los recursos
    private void stop (){
        if (player != null){
            player.release();
            player = null;
        }
    }

    private void startChronometer(){
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    private void stopChronometer(){
        chronometer.stop();
    }

    private void readQuestions(String type){
        try {

            InputStream recursoRaw;

            //TODO Cambiar a switch case
            if(type.equals("Música"))
                recursoRaw = getResources().openRawResource(R.raw.musica);
            else
                recursoRaw = getResources().openRawResource(R.raw.videojuegos);


            byte[] b = new byte[recursoRaw.available()];
            recursoRaw.read(b);

            String s = new String(b);
            JSONObject obj = new JSONObject(new String(b));

            JSONArray array = obj.getJSONArray("music");

            for(int i = 0 ; i < array.length() ; i++){

                Question question = new Question();
                JSONObject q = array.getJSONObject(i);
                question.setQuestion(q.getString("question"));
                JSONArray answers = q.getJSONArray("answers");
                ArrayList<String> list = new ArrayList<>();

                for(int j = 0; j < answers.length(); j++){
                    list.add(answers.get(j).toString());
                }

                question.setOptions(list);
                question.setAnswer(q.getInt("correct"));
                question.setId(i);
                question.setImageId(q.getString("image"));
                question.setMusicId(q.getString("audio"));

                questions.add(question);
            }

        }
        catch(IOException exc){
            Log.d("Error", exc.toString());
        }
        catch(JSONException exc){
            Log.d("Error", exc.toString());
        }
    }
}
