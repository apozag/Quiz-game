package com.urjc.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;

public class SettingsActivity extends AppCompatActivity {

    //private Spinner languageSpinner;

    SharedPreferences settings;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settings = getSharedPreferences("MyPrefsFile", 0);
        editor = settings.edit();

        //languageSpinner = (Spinner) findViewById(R.id.languageSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.language_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //languageSpinner.setAdapter(adapter);

        SeekBar difficulty = findViewById(R.id.difficultyBar);
        difficulty.setProgress(settings.getInt("difficulty", 0));
        difficulty.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                editor.putInt("difficulty", i);
                editor.commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar volume = findViewById(R.id.volumeBar);
        volume.setProgress(settings.getInt("volume", 5));
        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                editor.putInt("volume", i);
                editor.commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        /*Spinner language = findViewById(R.id.languageSpinner);
        language.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:
                        editor.putString("language", "en_");
                        break;
                    case 1:
                        editor.putString("language", "es_");
                        break;
                }

                editor.commit();
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }

        });*/
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v){
                finish();
            }
        });
    }
}
