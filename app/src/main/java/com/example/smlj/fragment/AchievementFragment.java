package com.example.smlj.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.smlj.FmPagerAdapter;
import com.example.smlj.TabFragment;
import com.example.smlj.geren;
import com.example.smlj.paiming;
import com.example.whatrubbish.Bus;
import com.example.whatrubbish.R;
import com.example.whatrubbish.activity.AbsActivity;
import com.example.whatrubbish.activity.PersonalSettingActivity;
import com.example.whatrubbish.databinding.ActivityAchievementBinding;
import com.example.whatrubbish.databinding.FragmentCollectRubBinding;
import com.example.whatrubbish.databinding.FragmentTextBinding;
import com.example.whatrubbish.databinding.PaimingitemBinding;
import com.example.whatrubbish.util.ActivityUtil;
import com.example.whatrubbish.util.DrawUtil;
import com.example.whatrubbish.util.ThreadPoolManager;
import com.example.whatrubbish.util.ToastUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AchievementFragment extends Fragment {
    TabLayout tabLayout;
    ActivityAchievementBinding binding;
    PaimingitemBinding bi;

    //@Override
    //protected void onCreate(Bundle savedInstanceState) {
    //    super.onCreate(savedInstanceState);
    //    setContentView(R.layout.activity_achievement);
    //    tabLayout = (TabLayout) findViewById(R.id.tablayout);
    //    viewPager = (ViewPager) findViewById(R.id.mViewPager);
    //    tabInit();
    //
    //}

    @SuppressLint("SetTextI18n")
    void setPointCnt() {
        if (Bus.curUser == null) {
            //Bus.httpDb
            getActivity().runOnUiThread(()->{
                ToastUtil.show(getActivity(), "当前没有用户登录 请登录");
            });


            return;
        }
        //Integer stageId;
        //stageId = Bus.curUser.getStageId();
        //stageId = Bus.curUser.getPointCnt();
        Integer pointCnt = Bus.curUser.getPointCnt();

        if (pointCnt == null) {
            pointCnt = 0;
        }
        //if (stageId == null) {
        //    stageId = 0;
        //}
        //if (pointCnt > 5) {
        //    pointCnt = 5;
        //}
        //double progress = pointCnt * 100.0 / Bus.maxPoint;
        //double progress = pointCnt * 100.0 / Bus.maxPoint;
        binding.tvPoint.setText(pointCnt+"");
        //bi.textView8.setText(pointCnt+"");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = ActivityAchievementBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        tabLayout = binding.tablayout;
        viewPager = binding.mViewPager;
        //binding. avatar.set
        if(Bus.curUser!=null){
            String avatarUrl = Bus.curUser.getAvatarUrl();
            if(avatarUrl!=null){
                //String imgUrl="https://cdn2.jianshu.io/assets/default_avatar/7-0993d41a595d6ab6ef17b19496eb2f21.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/240/h/240";
                //String imgUrlFailed="http://img5.duitang.com/uploads/item/201506/07/20150607110911_kY5cP.jpeg";
                Glide.with(this)
                        .load(avatarUrl)
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(binding.avatar);
            }
            String nickname = Bus.curUser.getNickname();
            if(nickname!=null){
                binding. tvNickName.setText(nickname);
            }

        }

        binding.  setting.setOnClickListener(v->{
            //SettingAct
            //PersonalSettingActivity
            ActivityUtil.startActivity(getActivity(), PersonalSettingActivity.class);
        });
        //可以
        //String imgErCi="http://starplatinumora.top/images/0d301eb2419d479db005a5980d4d165c.jpg";
        //Glide.with(this)
        //        .load(imgErCi)
        //        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
        //        .into(binding.avatar);

        //方形的
        //DrawUtil.loadImage(getActivity(),"https://cdn2.jianshu.io/assets/default_avatar/7-0993d41a595d6ab6ef17b19496eb2f21.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/240/h/240",binding.avatar);
        tabInit();
        //

        //tvPoint
        Thread thread = new Thread(() -> {
            try{
                setPointCnt();
            }catch (Exception e){
                e.printStackTrace();
            }

        });
        ThreadPoolManager.run(thread);

        binding.avatar.setOnClickListener(v -> {
            //开始游戏
            ActivityUtil.startActivity(getActivity(), AbsActivity.class);
        });
        return root;
    }

    //@Override
    //public View onCreateView(LayoutInflater inflater, ViewGroup container,
    //                         Bundle savedInstanceState) {
    //
    //    view = inflater.inflate(R.layout.achievementfrag, container, false);
    //
    //    return view;
    //}


    List<String> titles = new ArrayList<>();
    List<Fragment> fragments = new ArrayList<>();

    //  FragmentActivity activity;

    ViewPager viewPager;


    public void tabInit() {
        titles.add("   勋章   ");
        titles.add("   好友   ");
        titles.add("   个人   ");

//        for (String title : titles) {
//            fragments.add(new TabFragment());
//
//
//        }
        fragments.add(new TabFragment());
        fragments.add(new paiming());
        fragments.add(new geren());


        //FragmentManager supportFragmentManager = this.getSupportFragmentManager();
        //Fragment fragment;
        FragmentManager supportFragmentManager = getChildFragmentManager();

        FmPagerAdapter pagerAdapter = new FmPagerAdapter(titles, fragments, supportFragmentManager);

        viewPager.setAdapter(pagerAdapter);
//        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

    }
}
