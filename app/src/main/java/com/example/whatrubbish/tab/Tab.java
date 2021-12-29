package com.example.whatrubbish.tab;

import android.app.Activity;

import androidx.activity.ComponentActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.whatrubbish.tab.categoryTab.FmPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Arrays;
import java.util.List;

import lombok.Data;

@Data
public class Tab {

    List<String> titles;
    List<Fragment> fragments;
    //    void tabInit(List<String > titles, List<Fragment> fragments ){
//    Activity activity;
//     extends AppCompatActivity
//            AppCompatActivity activity;
//            Activity activity;
//    AppCompatActivity activity;
    FragmentActivity activity;
//    貌似可以了 就是这里改成了 FragmentActivity
//    ComponentActivity activity;
    //    https://blog.csdn.net/today_work/article/details/79300181
    ViewPager viewPager;
    TabLayout tabLayout;

    public void tabInit() {

        for (String title : titles) {
            fragments.add(new TabFragment(title));
        }

//        android.app.FragmentManager fragmentManager = activity.getFragmentManager();
        FragmentManager supportFragmentManager = activity.getSupportFragmentManager();

//        FmPagerAdapter pagerAdapter = new FmPagerAdapter(titles, fragments, fragmentManager);
        FmPagerAdapter pagerAdapter = new FmPagerAdapter(titles, fragments, supportFragmentManager);
        //这个是  viewPager 的  pagerAdapter


//        FragmentManager supportFragmentManager = activity.getSupportFragmentManager();
////        FmPagerAdapter pagerAdapter = new FmPagerAdapter(Arrays.asList(titles), fragments, getSupportFragmentManager());
////        FmPagerAdapter pagerAdapter = new FmPagerAdapter(titles, fragments, getSupportFragmentManager());
//        FmPagerAdapter pagerAdapter = new FmPagerAdapter(titles, fragments, supportFragmentManager);


        //FragmentManager supportFragmentManager = activity.getSupportFragmentManager();
        //FmPagerAdapter pagerAdapter = new FmPagerAdapter(titles, fragments, supportFragmentManager);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

}
