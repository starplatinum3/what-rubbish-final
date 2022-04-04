package com.example.whatrubbish.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.whatrubbish.R;
import com.example.whatrubbish.databinding.ActivityFragmentHolderBinding;
import com.example.whatrubbish.databinding.ActivityTestBinding;
import com.example.whatrubbish.fragment.SplitDropRubFragment;
import com.example.whatrubbish.test.TestHolderFragment;

import lombok.SneakyThrows;

public class SplitDropRubActivity extends AppCompatActivity {

    ActivityFragmentHolderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFragmentHolderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //holder

        FragmentActivity activity=this;
        //这个有问题 是没有登录的问题吗
        String url = "http://139.196.8.79:8890/";
        SplitDropRubFragment splitDropRubFragment = new SplitDropRubFragment(url);
        activity.getSupportFragmentManager().beginTransaction().
                replace(R.id.holder, splitDropRubFragment).
                addToBackStack(null).commit();
    }
}
