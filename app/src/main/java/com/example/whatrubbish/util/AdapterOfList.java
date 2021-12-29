package com.example.whatrubbish.util;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

import lombok.Data;

//@Data
public class AdapterOfList extends FragmentPagerAdapter {

//    FragmentPagerAdapter behavior
    List<Fragment> mFragments;
    List<String > mTitles;
//    BEHAVIOR_SET_USER_VISIBLE_HINT
    public AdapterOfList(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

//    public AdapterOfList(@NonNull FragmentManager fm, int behavior,List<Fragment> mFragments,  List<String > mTitles) {
//        super(fm, behavior);
//    }


    public AdapterOfList(@NonNull FragmentManager fm, int behavior, List<Fragment> mFragments, List<String> mTitles) {
        super(fm, behavior);
        this.mFragments = mFragments;
        this.mTitles = mTitles;
    }

    //获得每个页面的下标
    @Override
    public Fragment getItem(int position) {
        Log.i("position",""+position);
//        Fragment fragment = mFragments.get(position);
        return mFragments.get(position);
    }
    //获得List的大小
    @Override
    public int getCount() {
        return mFragments.size();
    }
    //获取title的下标
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    //创建Fragment的适配器

}
