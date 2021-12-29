package com.example.compx202_finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whatrubbish.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class HighScoreActivity extends AppCompatActivity {

    SharedPreferences preferences;
    Set<String> ScoreSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        ScoreSet = new HashSet<>();
        preferences = getSharedPreferences("score_info", MODE_PRIVATE);
        boolean isExist = preferences.getBoolean("isExist", false);

        /*
            Use preferences store the data of score, and use isExist to judge if it
            is the first time for palying the game.
         */
        SharedPreferences.Editor editor = preferences.edit();

        // If it is not the first time, get the original data, update it and apply.
        if (isExist) {
            ScoreSet = preferences.getStringSet("ScoreSet", null);
            ScoreSet.add(String.valueOf(score));
            editor.remove("ScoreSet");
            editor.apply();
        }
        // If it is the first time user play the game, add the score to the hashSet and change the value of isExist
        else {
            ScoreSet.add(String.valueOf(score));
            editor.putBoolean("isExist", true);
        }
        editor.putStringSet("ScoreSet", ScoreSet);
        editor.apply();

        // Create listView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.my_adapter, processTopFive(ScoreSet));
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        // Set the function to the restart button
        Button restart = (Button) findViewById(R.id.restartButton);
        Intent restartIntent = new Intent(this, GameActivity.class);

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(restartIntent);
            }
        });

        // Make the game full screen and use sticky immersive mode
        int uiOptions = View. SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View. SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);
    }

    /**
     * Arrange the data in the collection in flashback and return the data
     *
     * @param set Set<String>
     * @return String[]
     */
    public String[] processTopFive(Set<String> set) {
        String[] array = set.toArray(new String[set.size()]);
        ArrayList<String> result = new ArrayList<>();
        if(array.length >= 5) {
            for(int i = array.length - 1; i >= array.length - 5; i--) {
                result.add(array[i] + " points");
            }
        }
        else {
            for(int i = array.length - 1; i >= 0; i--) {
                result.add(array[i] + " points");
            }
        }
        return result.toArray(new String[result.size()]);
    }
}
