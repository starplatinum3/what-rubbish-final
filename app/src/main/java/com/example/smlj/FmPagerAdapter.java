package com.example.smlj;


import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class FmPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    public List<String> strList;

    @SuppressLint("LongLogTag")
    public FmPagerAdapter(List<String> strList, List<Fragment> fragmentList, FragmentManager fm) {
        super(fm);
        this.strList = strList;
        this.fragmentList = fragmentList;
        Log.i(" this.fragmentList", String.valueOf(this.fragmentList));
        Log.i(" this.fragmentList.size()", String.valueOf(fragmentList.size()));
    }

    @Override
    public int getCount() {
        return fragmentList != null && !fragmentList.isEmpty() ? fragmentList.size() : 0;
    }

    @Override
    public Fragment getItem(int position) {

        return fragmentList.get(position);
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
//        为什么会有越界的 pos
        if(position>=strList.size()){
            return "越界";
        }
        String  pageTitle="越界";
        Log.i("strList", String.valueOf(strList));
        Log.i("position", String.valueOf(position));
        pageTitle=strList.get(position);
        return pageTitle;
//        try{
//              pageTitle=strList.get(position);
////            Log.i("pageTitle",pageTitle);
////            return pageTitle;
//        }catch (IndexOutOfBoundsException e){
//              pageTitle=strList.get(position-1);
//
////            return pageTitle;
//        }
//        Log.i("pageTitle",pageTitle);
//        return pageTitle;
//        return strList.get(position);
    }
    

}
