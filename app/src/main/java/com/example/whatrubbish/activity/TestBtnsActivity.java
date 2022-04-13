package com.example.whatrubbish.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whatrubbish.R;
import com.example.whatrubbish.databinding.ActivityAbsBinding;
import com.example.whatrubbish.databinding.ActivityTestBtnsBinding;
import com.example.whatrubbish.ui.home.HomeFragment;
import com.example.whatrubbish.util.ActivityUtil;
import com.suramire.androidgame25.MainActivity;

public class TestBtnsActivity extends AppCompatActivity {

    //private ActivityGameStageBinding binding;
    //private ActivityAbsBinding binding;
    private ActivityTestBtnsBinding binding;



    //@SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("AbsActivity", "onCreate: ");
        binding = ActivityTestBtnsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().
                replace(R.id.mainHolder, new HomeFragment())
                //.addToBackStack(null)
                .commit();


    }


}