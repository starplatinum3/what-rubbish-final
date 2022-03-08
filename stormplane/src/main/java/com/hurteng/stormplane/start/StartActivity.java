package com.hurteng.stormplane.start;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.hurteng.stormplane.MainActivity;
import com.hurteng.stormplane.modify.ModifyActivity;
//import com.hurteng.stormplane.myplane.R; import com.example.whatrubbish.R;


public class StartActivity extends Activity implements
        View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        setContentView(R.layout.activity_modify);
        setContentView(R.layout.activity_start);
        Log.e("tag", "finished");
//        btnStart
        findViewById(R.id.btnStart).setOnClickListener(this);
        findViewById(R.id.btnModify).setOnClickListener(this);

    }

    void start() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    void toModify() {
        //new Intent 怎么回退
        Intent intent = new Intent(this, ModifyActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        //R.id.btnStart==1
        int id = v.getId();// 点击小睡
        if (id == R.id.btnStart) {
            start();
        } else if (id == R.id.btnModify) {
            toModify();
        }
    }
}
