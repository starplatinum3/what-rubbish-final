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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.whatrubbish.R;
import com.example.whatrubbish.databinding.ActivityAchievementActiSelfBinding;
import com.example.whatrubbish.databinding.ActivityMainBinding;
import com.example.whatrubbish.gridIcons.GridIcons;
import com.example.whatrubbish.gridIcons.IconButton;
import com.example.whatrubbish.neteasecloudmusictab.fragment.MainFragment;
import com.example.whatrubbish.tab.Tab;
import com.example.whatrubbish.util.DrawUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

//import davidneteasecloudmusictab.qq986945193.com.davidneteasecloudmusictab.fragment.OneFragment;
//import davidneteasecloudmusictab.qq986945193.com.davidneteasecloudmusictab.fragment.ThreeFragment;
//import davidneteasecloudmusictab.qq986945193.com.davidneteasecloudmusictab.fragment.TwoFragment;
//我们需要他有个 tab
public class AchievementActivitySelf extends AppCompatActivity
       {

    private ActivityAchievementActiSelfBinding binding;
    private List<String> stringList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();

    private TabLayout mTabLayout;
    private ViewPager mviewPager;
    //    private com.example.whatrubbish.neteasecloudmusictab.fragment.OneFragment.MyAdapter myAdapter;
    private AchievementFragment.MyAdapter myAdapter;

    private Context mContext;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
//        setContentView(R.layout.activity_achievement);
        setContentView(R.layout.activity_achievement_acti_self);
        stringList.add("勋章");
        stringList.add("好友");
        stringList.add("主播电台");
//        文档：错误 程序包android.support.design.wi...
//        链接：http://note.youdao.com/noteshare?id=e0525aebe47888e70c5fea891cb1670b&sub=CAC7FEC1D7474FABB943A4A987959933
//        for (String s : stringList) {
//            fragmentList.add(new MainFragment(s));
//        }
//        在里面会 add 外面 不要 add

        ImageView avatar = findViewById(R.id.avatar);
//        avatar
        DrawUtil.setRoundImageDrawable(avatar,R.drawable.miku_fang,getResources(),90);

        initView();

        Tab tab = new Tab();
        tab.setActivity(this);
        tab.setTabLayout(mTabLayout);
        tab.setFragments(fragmentList);
        tab.setTitles(stringList);
        tab.setViewPager(mviewPager);
        tab.tabInit();

////        应该放在 fragment里面
//        gridIcons.setTvShow(tvShow);
//        gridIcons.setRv(rvGridIcons);
//
////        设置点击了图标之后会显示文字的那个textview
//        gridIcons.init();

        super.onCreate(savedInstanceState);
    }

    void initGridIcons(){

        List<IconButton> iconButtons = new ArrayList<>();
//        IconButton
//        (Integer iconId, String text) {
        iconButtons.add(new IconButton(R.drawable.miku_fang, "miku"));
        iconButtons.add(new IconButton(R.drawable.miku_fang, "miku"));
        iconButtons.add(new IconButton(R.drawable.miku_fang, "miku"));
        iconButtons.add(new IconButton(R.drawable.miku_fang, "miku"));
        iconButtons.add(new IconButton(R.drawable.miku_fang, "miku"));

        GridIcons gridIcons = new GridIcons(this, iconButtons);
        gridIcons.setRecyclerViewId(R.id.rvGridIcons);
//        gridIcons.setContext();
        gridIcons.setItemId(R.layout.grid_icon_item);
//        gridIcons.set();
//        TextView tvShow = findViewById(R.id.tvShow);
        RecyclerView rvGridIcons = findViewById(R.id.rvGridIcons);
        Log.i("R.id.rvGridIcons", String.valueOf(R.id.rvGridIcons));
        Log.i("rvGridIcons", String.valueOf(rvGridIcons));

//        应该放在 fragment里面
//        gridIcons.setTvShow(tvShow);
        gridIcons.setRv(rvGridIcons);
//        gridIcons.setin

//        设置点击了图标之后会显示文字的那个textview
        gridIcons.init();


    }


    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mviewPager = (ViewPager) findViewById(R.id.mViewPager);
    }

    private void initView(View rootView) {
        mTabLayout = (TabLayout) rootView.findViewById(R.id.mTabLayout);
        mviewPager = (ViewPager) rootView.findViewById(R.id.mViewPager);
    }




//        NavigationImageView = (ImageView) findViewById(R.id.NavigationImageView);
////        navi 的 左上角的 三

}
