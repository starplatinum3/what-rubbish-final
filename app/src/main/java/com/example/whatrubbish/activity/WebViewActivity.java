package com.example.whatrubbish.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.whatrubbish.R;
import com.example.whatrubbish.databinding.ActivityMainBinding;
import com.example.whatrubbish.databinding.ActivityWebViewBinding;

import lombok.SneakyThrows;
import lombok.val;

public class WebViewActivity extends AppCompatActivity {


    private ActivityWebViewBinding binding;
    public static String WEB_URL;


    //@SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        //取得启动该Activity的Intent对象
        Intent intent = getIntent();
        /*取出Intent中附加的数据*/
        String webUrl = intent.getStringExtra(WEB_URL);
        //String second = intent.getStringExtra("et2");

        binding.web.loadUrl(webUrl);


    }
}
