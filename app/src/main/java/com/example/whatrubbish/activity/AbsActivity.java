package com.example.whatrubbish.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whatrubbish.Bus;
import com.example.whatrubbish.databinding.ActivityAbsBinding;
import com.example.whatrubbish.databinding.ActivityGameStageBinding;
import com.example.whatrubbish.util.ActivityUtil;

import java.util.StringJoiner;

import lombok.SneakyThrows;

public class AbsActivity extends AppCompatActivity {

    //private ActivityGameStageBinding binding;
    private ActivityAbsBinding binding;



    //@SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("AbsActivity", "onCreate: ");
        binding = ActivityAbsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //Bus
        binding.iv1.setOnClickListener(v -> {
            //飞机游戏
            ActivityUtil.startActivity(this, com.hurteng.stormplane.MainActivity.class);
        });


        //Context context= this;
        //for (int i = 0; i < 14; i++) {
        //    try{
        //        //每个游戏不一样的
        //        int id = context.getResources().getIdentifier("iv"+(i+1),"id",context.getPackageName());
        //        ImageView imageView = findViewById(id);
        //        if(imageView==null){
        //            continue;
        //        }
        //        int finalI = i;
        //        imageView.setOnClickListener(v->{
        //            Log.i("im onclick", "onCreate: "+ finalI);
        //        });
        //    }catch (Exception e){
        //        e.printStackTrace();
        //    }
        //}

    }


}