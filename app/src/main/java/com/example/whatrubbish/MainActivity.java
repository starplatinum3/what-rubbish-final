package com.example.whatrubbish;

import android.app.Activity;
//import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

//import com.chengww.circleprogressdemo.CircleImageView;
//import com.example.whatrubbish.card.CardPagerAdapter;
//import com.example.whatrubbish.db.Repository;
//import com.example.whatrubbish.entity.User;
//import com.example.whatrubbish.fragment.NavFragment;
//import com.example.whatrubbish.fragment.WebFragment;
//import com.example.whatrubbish.util.ActivityUtil;
//import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.FragmentContainerView;
//import androidx.navigation.NavController;
//import androidx.navigation.Navigation;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.whatrubbish.databinding.ActivityMainBinding;

import java.util.List;

import lombok.SneakyThrows;
import lombok.val;

//implements CardPagerAdapter.MySendValue
public class MainActivity extends AppCompatActivity {

    //    ImageView weiduback;
//    TextView weidunum;

    public void liaotian(View view){


        Intent intent = new Intent( MainActivity.this,com.hrl.chaui.activity.SplashActivity.class);
        startActivity(intent);


    }
    public void tuichu(View view){

        //  flag=1;
        Intent intent = new Intent( MainActivity.this,com.example.smlj.login.class);
        startActivity(intent);


    }
//    public int getFlag(){
//        return flag;
//    }

    private ActivityMainBinding binding;


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
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        //AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
        //        R.id.navigation_home,
        //        R.id. navigation_main,
        //        R.id.navigation_dashboard,
        //        R.id.navigation_notifications,R.id.navigation_personal)
        //        .build();

        //FragmentContainerView fragmentContainerView;
        //fragmentContainerView.hos
        //NavHostFragment navHostFragment= binding.navHostFragmentActivityMain;
        //binding.navHostFragmentActivityMain.
        //NavController navController =
        //        Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);

        //获取Navigation的导航控制器 NavController

//        NavHostFragment navHostFragment =(NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main) ;

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        //占位
        //怎么传入 appdatabse
        //val navController = navHostFragment.getNavController();
       NavController  navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.navView, navController);

        getWH();
//        getSupportFragmentManager().beginTransaction().replace(  R.id.nav,new NavFragment(this)).commit();

        //ActivityUtil.startActivity(this,  com.hurteng.stormplane.MainActivity.class);



        //把BottomNavigationView与NavController绑定。
        //binding.bottomNavView.setupWithNavController(navController);
//————————————————
//        版权声明：本文为CSDN博主「喝烧酒的兔子」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/weixin_38858037/article/details/114893497
//
//        initDatabase();
//        User user = new User();
//        user.setId(1L);
//        List<User> users = repository.getUserRepository().selectBy(user);
//        repository.getUserRepository().delete();
//        repository.getUserRepository().update();
//        repository.getUserRepository().();

    }

//    private static final String  TAG   = "MainActivity";
//    private static final String  TAG   = "Main";
    private static final String  TAG   = "M";

    void getWH(){

        // 获取屏幕密度（方法1）
        int screenWidth = getWindowManager().getDefaultDisplay().getWidth(); // 屏幕宽（像素，如：480px）
        int screenHeight = getWindowManager().getDefaultDisplay().getHeight(); // 屏幕高（像素，如：800p）
        Log.e(TAG + " getDefaultDisplay", "screenWidth=" + screenWidth + "; screenHeight=" + screenHeight);
// 获取屏幕密度（方法2）
        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        float density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
        int densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
        float xdpi = dm.xdpi;
        float ydpi = dm.ydpi;
        Log.e(TAG + " DisplayMetrics", "xdpi=" + xdpi + "; ydpi=" + ydpi);
        Log.e(TAG + " DisplayMetrics", "density=" + density + "; densityDPI=" + densityDPI);
        screenWidth = dm.widthPixels; // 屏幕宽（像素，如：480px）
        screenHeight = dm.heightPixels; // 屏幕高（像素，如：800px）
        Log.e(TAG + " DisplayMetrics(111)", "screenWidth=" + screenWidth + "; screenHeight=" + screenHeight);
// 获取屏幕密度（方法3）
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
        densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
        xdpi = dm.xdpi;
        ydpi = dm.ydpi;
        Log.e(TAG + " DisplayMetrics", "xdpi=" + xdpi + "; ydpi=" + ydpi);
        Log.e(TAG + " DisplayMetrics", "density=" + density + "; densityDPI=" + densityDPI);
        int screenWidthDip = dm.widthPixels; // 屏幕宽（dip，如：320dip）
        int screenHeightDip = dm.heightPixels; // 屏幕宽（dip，如：533dip）
        Log.e(TAG + " DisplayMetrics(222)", "screenWidthDip=" + screenWidthDip + "; screenHeightDip=" + screenHeightDip);
        screenWidth = (int)(dm.widthPixels * density + 0.5f); // 屏幕宽（px，如：480px）
        screenHeight = (int)(dm.heightPixels * density + 0.5f); // 屏幕高（px，如：800px）
        Log.e(TAG + " DisplayMetrics(222)", "screenWidth=" + screenWidth + "; screenHeight=" + screenHeight);

    }
    //@Override
    //public void onCardViewClicked(String url) {
    //    getSupportFragmentManager().beginTransaction().
    //            replace(R.id.nav_host_fragment_activity_main,new WebFragment(url))
    //            .addToBackStack(null).commit();
    //}
}