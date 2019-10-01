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
                String a1m[] = {"5 líneas y 4 espacios", "5 líneas y 5 espacios", "5 líneas y 6 espacios", "4 líneas y 5 espacios"};
                q1 = new Question(0, "¿Cuántas líneas y espacios tiene un pentagrama?", new ArrayList<>(Arrays.asList(a1m)), 0);

                String a2m[] = {"Nueva York", "Philadelphia", "Chicago", "Nueva Orleans"};
                q2 = new Question(1, "¿En qué ciudad se originó el Jazz?", new ArrayList<>(Arrays.asList(a2m)), 3);

                String a3m[] = {"4", "2", "8", "3"};
                q3 = new Question(2, "¿A cuántas semicorcheas equivale una blanca?", new ArrayList<>(Arrays.asList(a3m)), 2);

                String a4m[] = {"4", "2", "8", "6"};
                q4 = new Question(3, "¿A cuántas negras equivale una redonda?", new ArrayList<>(Arrays.asList(a4m)), 0);

                String a5m[] = {"saxofón", "trompeta", "flauta travesera", "fagot"};
                q5 = new Question(4, "¿Cuál de estos instrumentos no es de viento-madera?", new ArrayList<>(Arrays.asList(a5m)), 1);
                break;
            case"Cine":
                String a1c[] = {"Champiñón", "Calavera", "Caimán", "Prohibida"};
                q1 = new Question(0, "¿Cómo se llama la isla en la que vive King Kong?", new ArrayList<>(Arrays.asList(a1c)), 1);

                String a2c[] = {"coche", "barco", "tren", "avión"};
                q2 = new Question(1, "¿Qué transporte es popular por ser filmado por los hermanos Lumière?", new ArrayList<>(Arrays.asList(a2c)), 2);

                String a3c[] = {"Shrek", "Monsters Inc.", "Toy Story", "El Viaje de Chihiro"};
                q3 = new Question(2, "¿Cual de estas ganó el primer Óscar a mejor película de animación?", new ArrayList<>(Arrays.asList(a3c)), 0);

                String a4c[] = {"Teresa", "Amalia", "Ofelia", "Yolanda"};
                q4 = new Question(3, "¿Cual era el nombre de la protagonista del Laberinto del Fauno?", new ArrayList<>(Arrays.asList(a4c)), 2);

                String a5c[] = {"Paprika", "Perfect Blue", "Tokyo Godfathers", "La Tumba de las Luciérnagas"};
                q5 = new Question(4, "¿Cual de estas películas no pertenece a Satoshi Kon?", new ArrayList<>(Arrays.asList(a5c)), 3);
                break;
            case "Historia":
                String a1h[] = {"1512", "1502", "1482", "1492"};
                q1 = new Question(0, "En qué año descubrió Colón América", new ArrayList<>(Arrays.asList(a1h)), 3);

                String a2h[] = {"Champiñón", "Cerdeña", "Córcega", "Santa Elena"};
                q2 = new Question(1, "¿En qué Isla murió Napoleón?", new ArrayList<>(Arrays.asList(a2h)), 3);

                String a3h[] = {"Quito", "Cuzco", "Machu Pichu", "Lima"};
                q3 = new Question(2, "¿Cuál era la capital del imperio Inca?", new ArrayList<>(Arrays.asList(a3h)), 1);

                String a4h[] = {"1812", "1820", "1814", "1802"};
                q4 = new Question(3, "De qué año es la primera Constitución española?", new ArrayList<>(Arrays.asList(a4h)), 0);

                String a5h[] = {"griegos y espartacos", "romanos y cartagineses", "griegos y persas", "griegos y egipcios"};
                q5 = new Question(4, "¿Quiénes lucharon en la batalla de la maraton?", new ArrayList<>(Arrays.asList(a5h)), 2);
                break;
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
