package com.example.whatrubbish.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whatrubbish.R;
import com.example.whatrubbish.databinding.ActivityTestBinding;
import com.example.whatrubbish.fragment.FriendFragment;
import com.example.whatrubbish.test.TestHolderFragment;

import lombok.SneakyThrows;

public class SearchFriendActivity extends AppCompatActivity {

    private ActivityTestBinding binding;

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //binding.
        getSupportFragmentManager().beginTransaction().
                //replace(R.id.holder,new FriendFragment()).addToBackStack(null).commit();
                replace(R.id.holder,new FriendFragment()).commit();

    }
}
