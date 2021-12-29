package com.example.whatrubbish.achievement;
//package davidneteasecloudmusictab.qq986945193.com.davidneteasecloudmusictab;
//package davidneteasecloudmusictab.qq986945193.com.davidneteasecloudmusictab;

//import android.support.design.widget.NavigationView;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.whatrubbish.R;
import com.example.whatrubbish.neteasecloudmusictab.fragment.MainFragment;
//import com.example.whatrubbish.neteasecloudmusictab.fragment.OneFragment;
//import com.example.whatrubbish.neteasecloudmusictab.fragment.ThreeFragment;
//import com.example.whatrubbish.neteasecloudmusictab.fragment.TwoFragment;
//import com.google.android.material.navigation.NavigationView;
import com.example.whatrubbish.tab.Tab;
//import com.example.whatrubbish.tab.TabFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import davidneteasecloudmusictab.qq986945193.com.davidneteasecloudmusictab.fragment.OneFragment;
//import davidneteasecloudmusictab.qq986945193.com.davidneteasecloudmusictab.fragment.ThreeFragment;
//import davidneteasecloudmusictab.qq986945193.com.davidneteasecloudmusictab.fragment.TwoFragment;
//我们需要他有个 tab
//https://blog.csdn.net/songyachao/article/details/51261748
public class AchievementActivity extends FragmentActivity
        implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private List<String> stringList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();

    private TabLayout mTabLayout;
    private ViewPager mviewPager;
    //    private com.example.whatrubbish.neteasecloudmusictab.fragment.OneFragment.MyAdapter myAdapter;
    private AchievementFragment.MyAdapter myAdapter;

    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

//        文档：EAndroidRuntime FATAL EXCEPTION main...
//        链接：http://note.youdao.com/noteshare?id=983bfd750edfd40f4a9dede4db87957d&sub=AFEFA6B1EA814AB0AF4D1BF378560244
//        E/AndroidRuntime: FATAL EXCEPTION: main
//        Process: com.example.whatrubbish, PID: 27236
//        java.lang.RuntimeException: Unable to start activity ComponentInfo{com.example.whatrubbish/com.example.whatrubbish.achievement.AchievementActivity}: android.view.InflateException: Binary XML file line #14 in com.example.whatrubbish:layout/activity_achievement: Binary XML file line #14 in com.example.whatrubbish:layout/activity_achievement: Error inflating class androidx.fragment.app.FragmentContainerView
        setContentView(R.layout.activity_achievement);
        stringList.add("勋章");
        stringList.add("好友");
        stringList.add("主播电台");
//        stringList.add("排行榜");

//        stringList.add("勋章");
//        stringList.add("歌单");
//        stringList.add("主播电台");
//        stringList.add("排行榜");
        Tab tab = new Tab();
//        tab.getClass()
//        tab.se
//        tab.se

        for (String s : stringList) {
            fragmentList.add(new MainFragment(s));
        }
//        initView();
//        fragmentList.add(new MainFragment("个性推荐"));
//        fragmentList.add(new MainFragment("歌单"));
//        fragmentList.add(new MainFragment("主播电台"));
//        fragmentList.add(new MainFragment("排行榜"));



        super.onCreate(savedInstanceState);
    }

//    void tabInit(List<String >titles,List<Fragment> fragments ){
//
//        for (String title : titles) {
//            fragments.add(new TabFragment(title));
//        }
//
//        FmPagerAdapter pagerAdapter = new FmPagerAdapter(Arrays.asList(titles), fragments, getSupportFragmentManager());
//        viewPager.setAdapter(pagerAdapter);
//
//        tabLayout.setupWithViewPager(viewPager);
//
//    }

    void init(){
//        if (getActivity() != null) {
//            mContext = getActivity();
//        }
        initView();
//        initView(rootView);

        android.app.FragmentManager fragmentManager = getFragmentManager();
//        这个 怎么用啊。。。 哭了
//        getCallingActivity()
//        myAdapter = new com.example.whatrubbish.neteasecloudmusictab.fragment.OneFragment.MyAdapter(getChildFragmentManager());
//        myAdapter = new AchievementFragment.MyAdapter(getChildFragmentManager());
//        myAdapter = new AchievementFragment.MyAdapter(fragmentManager);

        mviewPager.setAdapter(myAdapter);

        mTabLayout.setupWithViewPager(mviewPager);

        mTabLayout.setTabsFromPagerAdapter(myAdapter);
//        return rootView;
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
////        View rootView = inflater.inflate(R.layout.fragment_one, container, false);
//        View rootView = inflater.inflate(R.layout.fragment_achievement, container, false);
//        if (getActivity() != null) {
//            mContext = getActivity();
//        }
//        initView(rootView);
//
////        myAdapter = new com.example.whatrubbish.neteasecloudmusictab.fragment.OneFragment.MyAdapter(getChildFragmentManager());
//        myAdapter = new AchievementFragment.MyAdapter(getChildFragmentManager());
//
//        mviewPager.setAdapter(myAdapter);
//
//        mTabLayout.setupWithViewPager(mviewPager);
//
//        mTabLayout.setTabsFromPagerAdapter(myAdapter);
//        return rootView;
//    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mviewPager = (ViewPager) findViewById(R.id.mViewPager);
    }

    private void initView(View rootView) {
        mTabLayout = (TabLayout) rootView.findViewById(R.id.mTabLayout);
        mviewPager = (ViewPager) rootView.findViewById(R.id.mViewPager);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

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

//    private DrawerLayout mDrawerLayout;
//    private ViewPager main_viewpager;
//    private NavigationView navigation_view;
//    private ImageView ImageView1;
//    private ImageView ImageView2;
//    private ImageView ImageView3;
//    private List<Fragment> fragments = new ArrayList<>();
//    private MainAdapter mAdapter;
//    private ImageView NavigationImageView;
//
//
//    private OneFragment oneFragment;
////    private davidneteasecloudmusictab.qq986945193.com.davidneteasecloudmusictab.fragment.TwoFragment twoFragment;
//    private TwoFragment twoFragment;
//    private ThreeFragment threeFragment;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
////        setContentView(R.layout.activity_main);
//        initView();
//        initData();
//
//    }
//
//    private void initData() {
//        NavigationImageView.setOnClickListener(this);
//        /**
//         * 利用menu设置监听事件
//         */
//        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(MenuItem item) {
//                int item_id = item.getItemId();
//                switch (item_id) {
////                    case R.id.item_green:
////                        Toast.makeText(AchievementActivity.this, "我的信息", Toast.LENGTH_SHORT).show();
////                        break;
////                    case R.id.item_blue:
////                        Toast.makeText(AchievementActivity.this, "积分商城", Toast.LENGTH_SHORT).show();
////                        break;
////                    case R.id.item_pink:
////                        Toast.makeText(AchievementActivity.this, "付费音乐包", Toast.LENGTH_SHORT).show();
////                        break;
////                    case R.id.subitem_01:
////                        Toast.makeText(AchievementActivity.this, "听歌识曲", Toast.LENGTH_SHORT).show();
////                        break;
////                    case R.id.subitem_02:
////                        Toast.makeText(AchievementActivity.this, "主题换肤", Toast.LENGTH_SHORT).show();
////                        break;
////                    default:
////                        Toast.makeText(AchievementActivity.this, "。。。其他。。。。", Toast.LENGTH_SHORT).show();
////                        break;
//                }
//                return true;
//            }
//        });
//    }
//
//    private void initView() {
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.mDrawerLayout);
//        main_viewpager = (ViewPager) findViewById(R.id.main_viewpager);
//        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
//        ImageView1 = (ImageView) findViewById(R.id.ImageView1);
//        ImageView2 = (ImageView) findViewById(R.id.ImageView2);
//        ImageView3 = (ImageView) findViewById(R.id.ImageView3);
//        NavigationImageView = (ImageView) findViewById(R.id.NavigationImageView);
////        navi 的 左上角的 三
//
//        oneFragment = new OneFragment();
//        twoFragment = new TwoFragment();
//        threeFragment = new ThreeFragment();
//
//        fragments.add(oneFragment);
//        fragments.add(twoFragment);
//        fragments.add(threeFragment);
//
//        ImageView1.setSelected(true);
//
//        mAdapter = new MainAdapter(getSupportFragmentManager());
//        main_viewpager.setAdapter(mAdapter);
//
//        main_viewpager.addOnPageChangeListener(this);
//    }
//
//    /**
//     * NavigationImageView的监听事件（Imageview）
//     *
//     * @param v
//     */
//    @Override
//    public void onClick(View v) {
//        mDrawerLayout.openDrawer(navigation_view);
//
//    }
//
//    /**
//     * 滑动
//     *
//     * @param position
//     * @param positionOffset
//     * @param positionOffsetPixels
//     */
//    @Override
//    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//    }
//
//    /**
//     * 滑动选中
//     *
//     * @param position
//     */
//    @Override
//    public void onPageSelected(int position) {
//        switch (position) {
//            case 0:
//                ImageView1.setSelected(true);
//                ImageView2.setSelected(false);
//                ImageView3.setSelected(false);
//                break;
//            case 1:
//                ImageView1.setSelected(false);
//                ImageView2.setSelected(true);
//                ImageView3.setSelected(false);
//                break;
//            case 2:
//                ImageView1.setSelected(false);
//                ImageView2.setSelected(false);
//                ImageView3.setSelected(true);
//                break;
//        }
//    }
//
//    @Override
//    public void onPageScrollStateChanged(int state) {
//
//    }
//
//    class MainAdapter extends FragmentPagerAdapter {
//
//        public MainAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return fragments.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return fragments.size();
//        }
//    }
//
//    public void one_iv(View view) {
//
//        main_viewpager.setCurrentItem(0);
//
//
//        ImageView1.setSelected(true);
//        ImageView2.setSelected(false);
//        ImageView3.setSelected(false);
//    }
//
//    public void two_iv(View view) {
//        main_viewpager.setCurrentItem(1);
//
//
//        ImageView1.setSelected(false);
//        ImageView2.setSelected(true);
//        ImageView3.setSelected(false);
//    }
//
//    public void three_iv(View view) {
//        main_viewpager.setCurrentItem(2);
//
//
//        ImageView1.setSelected(false);
//        ImageView2.setSelected(false);
//        ImageView3.setSelected(true);
//    }
}
