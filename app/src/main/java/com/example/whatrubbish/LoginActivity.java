package com.example.whatrubbish;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whatrubbish.databinding.ActivityLoginBinding;
import com.example.whatrubbish.databinding.ActivityRegisterBinding;
import com.example.whatrubbish.db.Repository;
import com.example.whatrubbish.entity.User;
import com.example.whatrubbish.repository.UserRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

import en.edu.zucc.pb.loginapplication.util.Security;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //AppAc

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

}