package com.example.compx202_finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.whatrubbish.R;

public class OnboardingActivity extends AppCompatActivity {
    ViewPager viewPager;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
            Create the preferences to store the data, which is used to judge whether
            the user has browsed the introduction of app
         */
        preferences = getSharedPreferences("onboarding", MODE_PRIVATE);
        boolean isFirst = preferences.getBoolean("isFirst", false);

        /*
            The default value of isFirst is false, so when user first open the app,
            they will enter the introduction of the app.
            And when user enter the introduction page, the value of isFirst will be
            edited, and the second time user open the app, user will enter directly
            to the main page of the game.
         */
        if(isFirst) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            setContentView(R.layout.activity_main);
        }
        else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirst", true);
            editor.apply();
            setContentView(R.layout.activity_onborading);
        }

        //  These code is used to make the page full screen
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);

        viewPager = findViewById(R.id.viewPager);

        IntroAdapter adapter = new IntroAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    /**
     * This function is make the intent to the start button.
     *
     * @param view View
     */
    public void onclickStart(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}
