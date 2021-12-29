package com.example.whatrubbish.achievement;

//import android.app.Fragment;
//import android.app.FragmentManager;
//import android.app.FragmentPagerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
//import android.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentPagerAdapter;
//import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.whatrubbish.R;
import com.example.whatrubbish.neteasecloudmusictab.fragment.MainFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

//package com.example.whatrubbish.neteasecloudmusictab.fragment;
//package davidneteasecloudmusictab.qq986945193.com.davidneteasecloudmusictab.fragment;

//import android.content.Context;
//import android.os.Bundle;
////import android.support.annotation.Nullable;
////import android.support.design.widget.TabLayout;
////import android.support.v4.app.Fragment;
//android.support.v4.app.Fragment 迁移到 androidx Fragment
////import android.support.v4.app.FragmentManager;
////import android.support.v4.app.FragmentPagerAdapter;
////import android.support.v4.view.ViewPager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentPagerAdapter;
//import androidx.viewpager.widget.ViewPager;
//
//import com.example.whatrubbish.R;
//import com.google.android.material.tabs.TabLayout;
//
//import java.util.ArrayList;
//import java.util.List;

//import davidneteasecloudmusictab.qq986945193.com.davidneteasecloudmusictab.R;

/**
 * @author ：程序员小冰
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 * @CSDN博客: http://blog.csdn.net/qq_21376985
 */
public class AchievementFragment extends Fragment {
    private List<String> stringList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();

    private TabLayout mTabLayout;
    private ViewPager mviewPager;
    //    private com.example.whatrubbish.neteasecloudmusictab.fragment.OneFragment.MyAdapter myAdapter;
    private MyAdapter myAdapter;

    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        stringList.add("个性推荐");
        stringList.add("歌单");
        stringList.add("主播电台");
        stringList.add("排行榜");
        for (String s : stringList) {
            fragmentList.add(new MainFragment(s));
        }
//        fragmentList.add(new MainFragment("个性推荐"));
//        fragmentList.add(new MainFragment("歌单"));
//        fragmentList.add(new MainFragment("主播电台"));
//        fragmentList.add(new MainFragment("排行榜"));
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_one, container, false);
        View rootView = inflater.inflate(R.layout.fragment_achievement, container, false);
        if (getActivity() != null) {
            mContext = getActivity();
        }
        initView(rootView);

//        myAdapter = new com.example.whatrubbish.neteasecloudmusictab.fragment.OneFragment.MyAdapter(getChildFragmentManager());
        myAdapter = new MyAdapter(getChildFragmentManager());
//        FragmentManager childFragmentManager = getChildFragmentManager();
        mviewPager.setAdapter(myAdapter);

        mTabLayout.setupWithViewPager(mviewPager);

        mTabLayout.setTabsFromPagerAdapter(myAdapter);
        return rootView;
    }

    private void initView(View rootView) {
        mTabLayout = (TabLayout) rootView.findViewById(R.id.mTabLayout);
        mviewPager = (ViewPager) rootView.findViewById(R.id.mViewPager);
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return stringList.get(position);
        }
    }
}
