package com.example.whatrubbish.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whatrubbish.Bus;
import com.example.whatrubbish.R;
import com.example.whatrubbish.databinding.ActivityAbsAbsBinding;
import com.example.whatrubbish.databinding.ActivityAbsBinding;
import com.example.whatrubbish.databinding.ActivityGameStageBinding;
import com.example.whatrubbish.databinding.FragmentHolderBinding;
import com.example.whatrubbish.fragment.SplitDropRubFragment;
import com.example.whatrubbish.ui.home.HomeFragment;
import com.example.whatrubbish.util.ActivityUtil;
import com.suramire.androidgame25.MainActivity;

import java.util.StringJoiner;

import lombok.SneakyThrows;

public class AbsActivity extends AppCompatActivity {

    //private ActivityGameStageBinding binding;
    private ActivityAbsBinding binding;
    //private ActivityAbsAbsBinding binding;
    //private ActivityAbsAbsBinding binding;



    //@SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("AbsActivity", "onCreate: ");
        binding = ActivityAbsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Activity activity=this;
        //Bus
        binding.iv1.setOnClickListener(v -> {
            ActivityUtil.startActivity(activity,com.suramire.androidgame25.MainActivity.class);
            Log.v("btnToSuperMaria", "btnToSuperMaria");

        });

        binding.iv2.setOnClickListener(v -> {
            //记忆游戏
            ActivityUtil.startActivity(this, com.snatik.matches.MainActivity.class);
        });
        binding.iv3.setOnClickListener(v -> {
            //小鸟飞游戏
            ActivityUtil.startActivity(activity, com.quchen.flappycow.MainActivity.class);
        });

        binding.iv4.setOnClickListener(v -> {
            //Bus
            //FragmentHolderBinding
            //ActivityHo
            //HolderAc
            //天上掉垃圾
            //String url = "http://139.196.8.79:8890/";
            //SplitDropRubFragment splitDropRubFragment = new SplitDropRubFragment(url);
            ////activity budle
            //ActivityUtil.startActivity(activity, FragmentHolderActivity.class);
            ActivityUtil.startActivity(activity, SplitDropRubActivity.class);

        });

        //binding.iv5.setOnClickListener(v -> {
        //    //飞机游戏
        //    //这里出来之后会没有登录
        //    ActivityUtil.startActivity(this, com.hurteng.stormplane.MainActivity.class);
        //});
        binding.iv6.setOnClickListener(v -> {
            //垃圾识别
            //dete.
            //HomeFragment
            ActivityUtil.startActivity(activity, com.example.trash_detective.Activity.MainActivity.class);
            //ActivityUtil.startActivity(this, com.hurteng.stormplane.MainActivity.class);
        });
        binding.iv7.setOnClickListener(v -> {
            //愤怒的小鸟鸟
            ActivityUtil.startActivity(activity,         com.example.compx202_finalproject.MainActivity.class);
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