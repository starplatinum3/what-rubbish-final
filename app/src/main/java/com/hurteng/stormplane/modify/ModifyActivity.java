package com.hurteng.stormplane.modify;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.whatrubbish.R;

////import com.hurteng.stormplane.myplane.R; import com.example.whatrubbish.R;
//import com.hurteng.stormplane.R;
//import com.facebook.stetho.Stetho;

//import en.edu.zucc.pb.loginapplication.activity.BookkeepingActivity;

public class ModifyActivity extends Activity implements
        View.OnClickListener {

//    Button btnToHome;
//    LinearLayout main_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_modify);
        Log.e("tag", "finished");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
