package com.example.whatrubbish.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smlj.login;
import com.example.whatrubbish.Bus;
import com.example.whatrubbish.databinding.ActivityPersonalSettingBinding;
import com.example.whatrubbish.databinding.ActivityRegisterBinding;
import com.example.whatrubbish.db.Repository;
import com.example.whatrubbish.entity.User;
import com.example.whatrubbish.repository.UserRepository;
import com.example.whatrubbish.span.CustomUrlSpan;
import com.example.whatrubbish.util.ActivityUtil;
import com.example.whatrubbish.util.HttpUtil;
import com.example.whatrubbish.util.ThreadPoolManager;
import com.example.whatrubbish.util.ToastUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import en.edu.zucc.pb.loginapplication.util.Security;

public class PersonalSettingActivity extends AppCompatActivity {

    //private ActivityRegisterBinding binding;
    private ActivityPersonalSettingBinding binding;

    //    Repository repository;
//
    UserRepository userRepository;
    Repository repository;

    void initDatabase() {
        repository = new Repository(this);
        userRepository = repository.getUserRepository();
//        userRepository = App.getRepository().getUserRepository();
    }



    void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPersonalSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //initDatabase();
        if(Bus.curUser==null){
            ToastUtil.show(this,"没有登录");
            return;
        }
        binding.edNickName.setText(Bus.curUser.getNickname());
        binding.etAddr.setText(Bus.curUser.getAddress());

        binding.btnUpdate.setOnClickListener(v->{
            String  edNickNameStr = binding.edNickName.getText().toString();
            if (!TextUtils.isEmpty(edNickNameStr)) {
                Bus.curUser.setNickname(edNickNameStr);
            }
            String  etAddrStr = binding.etAddr.getText().toString();
            if (!TextUtils.isEmpty(etAddrStr)) {
                Bus.curUser.setAddress(etAddrStr);
            }

            Thread thread = new Thread(() -> {
                updateUser();
            });
            ThreadPoolManager.run(thread);

        });

        binding.btnGetRank.setOnClickListener(v->{
            //AbsActivity


            Thread thread = new Thread(() -> {
               getRank();
            });
            ThreadPoolManager.run(thread);

        });
        binding. allReadyHave.setOnClickListener(v->{

            ActivityUtil.startActivity(this,login.class);
        });

    }

    void updateUser(){
        try {
            JsonObject post = HttpUtil.post(Bus.baseDbUrl + "/user/update", Bus.curUser);
            Log.d("post", "updateUser: "+post);
            if (post.get("code").getAsInt()== Bus.codeError) {
                this.runOnUiThread(()->{
                    ToastUtil.show(this,"修改失败 "+post.get("msg"));
                });
                return;
            }
            this.runOnUiThread(()->{
                ToastUtil.show(this,"修改成功 "+post.get("msg"));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void getRank(){
        try {
            JsonObject post = HttpUtil.post(Bus.baseDbUrl + "/user/rank", Bus.curUser);
            Log.d("post", "updateUser: "+post);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}