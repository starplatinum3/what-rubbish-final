package com.example.smlj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;


import com.example.whatrubbish.R;
import com.hrl.chaui.activity.SplashActivity;

import java.util.Timer;
import java.util.TimerTask;

public class logo extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo);
        Timer timer = new Timer();
        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                Intent intent = new Intent(logo.this, yingdao.class);
                startActivity(intent);
                logo.this.finish();
            }
        };
        timer.schedule(task, 2000);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int dpi = (int) (dm.density*160);
        Log.e("------------dpi  ", String.valueOf(dpi));


    }


}
