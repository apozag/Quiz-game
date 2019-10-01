package com.urjc.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class GameActivity extends AppCompatActivity {

    private int score;
    private ArrayList<Question> questions = new ArrayList<>();
    private RadioGroup radioGroup;
    private RadioButton radioBtn1;
    private RadioButton radioBtn2;
    private RadioButton radioBtn3;
    private RadioButton radioBtn4;
    private TextView question;
    private TextView questionCount;
    private TextView scoreText;
    private Button chooseBtn;

    private Question currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Configuración de las preguntas

        Question q1, q2, q3, q4, q5;
        q1 = q2 = q3 = q4 = q5 = new Question();

        switch(getIntent().getStringExtra("MODE")) {
            case "Música":
                String a1m[] = {"a", "b", "c", "d"};
                q1 = new Question(0, "Pregunta 1 m", new ArrayList<>(Arrays.asList(a1m)), 1);

                String a2m[] = {"e", "f", "g", "h"};
                q2 = new Question(1, "Pregunta 2 m", new ArrayList<>(Arrays.asList(a2m)), 1);

                String a3m[] = {"i", "j", "k", "l"};
                q3 = new Question(2, "Pregunta 3 m", new ArrayList<>(Arrays.asList(a3m)), 1);

                String a4m[] = {"m", "n", "ñ", "o"};
                q4 = new Question(3, "Pregunta 4 m", new ArrayList<>(Arrays.asList(a4m)), 1);

                String a5m[] = {"o", "p", "q", "r"};
                q5 = new Question(4, "Pregunta 5 m", new ArrayList<>(Arrays.asList(a5m)), 1);
            break;
            case"Cine":
                String a1c[] = {"a", "b", "c", "d"};
                q1 = new Question(0, "Pregunta 1 c", new ArrayList<>(Arrays.asList(a1c)), 1);

                String a2c[] = {"e", "f", "g", "h"};
                q2 = new Question(1, "Pregunta 2 c", new ArrayList<>(Arrays.asList(a2c)), 1);

                String a3c[] = {"i", "j", "k", "l"};
                q3 = new Question(2, "Pregunta 3 c", new ArrayList<>(Arrays.asList(a3c)), 1);

                String a4c[] = {"m", "n", "ñ", "o"};
                q4 = new Question(3, "Pregunta 4 c", new ArrayList<>(Arrays.asList(a4c)), 1);

                String a5c[] = {"o", "p", "q", "r"};
                q5 = new Question(4, "Pregunta 5 c", new ArrayList<>(Arrays.asList(a5c)), 1);
                break;
            case "Historia":
                String a1h[] = {"a", "b", "c", "d"};
                q1 = new Question(0, "Pregunta 1 h", new ArrayList<>(Arrays.asList(a1h)), 1);

                String a2h[] = {"e", "f", "g", "h"};
                q2 = new Question(1, "Pregunta 2 h", new ArrayList<>(Arrays.asList(a2h)), 1);

                String a3h[] = {"i", "j", "k", "l"};
                q3 = new Question(2, "Pregunta 3 h", new ArrayList<>(Arrays.asList(a3h)), 1);

                String a4h[] = {"m", "n", "ñ", "o"};
                q4 = new Question(3, "Pregunta 4 h", new ArrayList<>(Arrays.asList(a4h)), 1);

                String a5h[] = {"o", "p", "q", "r"};
                q5 = new Question(4, "Pregunta 5 h", new ArrayList<>(Arrays.asList(a5h)), 1);

        }

        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        questions.add(q4);
        questions.add(q5);



        //COnfiguración RadioButtons

        radioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }

        });

        radioBtn1 = (RadioButton) findViewById(R.id.radioBtn1);
        radioBtn2 = (RadioButton) findViewById(R.id.radioBtn2);
        radioBtn3 = (RadioButton) findViewById(R.id.radioBtn3);
        radioBtn4 = (RadioButton) findViewById(R.id.radioBtn4);
        question = (TextView) findViewById(R.id.question);
        questionCount = (TextView) findViewById(R.id.textQuestionCount);
        scoreText= (TextView)findViewById(R.id.textScore);
        score = 0;

        chooseBtn = (Button)findViewById(R.id.chooseBtn);

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
                    if(currentQuestion.getId() <= questions.size()-2)
                        changeQuestion(currentQuestion.getId() + 1);
                    else{
                        Intent intent = new Intent(v.getContext(), ScoreActivity.class);
                        intent.putExtra("SCORE", score+"");
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

        radioBtn1.setText(currentQuestion.getOption(0));
        radioBtn2.setText(currentQuestion.getOption(1));
        radioBtn3.setText(currentQuestion.getOption(2));
        radioBtn4.setText(currentQuestion.getOption(3));

        radioBtn1.setBackgroundColor(0x00000000);
        radioBtn2.setBackgroundColor(0x00000000);
        radioBtn3.setBackgroundColor(0x00000000);
        radioBtn4.setBackgroundColor(0x00000000);
    }


}
