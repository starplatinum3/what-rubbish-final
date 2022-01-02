package com.example.whatrubbish.activity;

//import android.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.whatrubbish.R;
import com.example.whatrubbish.databinding.ActivityMainBinding;
import com.example.whatrubbish.databinding.ActivityTestBinding;
import com.example.whatrubbish.databinding.FragmentTestHolderBinding;
import com.example.whatrubbish.test.TestHolderFragment;

import lombok.SneakyThrows;
import lombok.val;

//implements CardPagerAdapter.MySendValue
public class TestActivity extends AppCompatActivity {

    //    ImageView weiduback;
//    TextView weidunum;

    public void liaotian(View view){


        Intent intent = new Intent( TestActivity.this,com.hrl.chaui.activity.SplashActivity.class);
        startActivity(intent);


    }
    public void tuichu(View view){

        //  flag=1;
        Intent intent = new Intent( TestActivity.this,com.example.smlj.login.class);
        startActivity(intent);


    }
//    public int getFlag(){
//        return flag;
//    }

    //private ActivityMainBinding binding;
    private ActivityTestBinding binding;


    //    Repository repository;
//    void initDatabase() {
//        repository=new Repository(this);
//    }
    NavHostFragment navHostFragment;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
//拦截返回键
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK){
//判断触摸UP事件才会进行返回事件处理
            if (event.getAction() == KeyEvent.ACTION_UP) {
                onBackPressed();
            }
//只要是返回事件，直接返回true，表示消费掉
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onBackPressed() {
// do something…
//        FragmentManager fm = getFragmentManager();
        FragmentManager fm = getSupportFragmentManager();
        Log.d("onBackPressed", "onBackPressed: fm.getBackStackEntryCount() "+fm.getBackStackEntryCount() );
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
        Log.d("onBackPressed", "onBackPressed: ");
    }



    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        WindowManager windowManager = getWindowManager();
//        Circlei
//        CircleImageView
        binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //holder

        getSupportFragmentManager().beginTransaction().
                replace(R.id.holder,new TestHolderFragment()).addToBackStack(null).commit();

    }

    //@Override
    //public void onCardViewClicked(String url) {
    //    getSupportFragmentManager().beginTransaction().
    //            replace(R.id.nav_host_fragment_activity_main,new WebFragment(url))
    //            .addToBackStack(null).commit();
    //}
}