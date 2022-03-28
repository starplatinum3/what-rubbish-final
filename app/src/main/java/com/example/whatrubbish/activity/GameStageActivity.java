package com.example.whatrubbish.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import com.example.whatrubbish.R;
import com.example.whatrubbish.databinding.ActivityGameBinding;
import com.example.whatrubbish.databinding.ActivityGameStageBinding;
import com.example.whatrubbish.databinding.ActivityNoBottomNavBinding;
import com.example.whatrubbish.fragment.SelectGameFragment;

import lombok.SneakyThrows;

public class GameStageActivity extends AppCompatActivity {

    private ActivityGameStageBinding binding;



    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGameStageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }


}