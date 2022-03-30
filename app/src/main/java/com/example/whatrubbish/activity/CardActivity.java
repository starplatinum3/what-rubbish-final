package com.example.whatrubbish.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.smlj.FmPagerAdapter;
import com.example.smlj.MyAdapter;
import com.example.smlj.TabFragment;
import com.example.smlj.geren;
import com.example.smlj.paiming;
import com.example.whatrubbish.R;
import com.example.whatrubbish.adapter.CardPagerAdapter;
import com.example.whatrubbish.databinding.ActivityCardBinding;
import com.example.whatrubbish.databinding.ActivityProblemBinding;
import com.example.whatrubbish.fragment.CardFragment;

import java.util.ArrayList;
import java.util.List;

public class CardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setFlipCard();

        flipCard();
    }

    //void setUpViewPagerCards(){
    //
    //    //tabInit();
    //
    //    //https://www.runoob.com/w3cnote/android-tutorial-viewpager.html
    //    aList = new ArrayList<View>();
    //    LayoutInflater li = getLayoutInflater();
    //    //这不行的 布局有问题
    //    //这块东西的大小也要定好
    //    aList.add(li.inflate(R.layout.item_card,null,false));
    //    aList.add(li.inflate(R.layout.item_card,null,false));
    //    aList.add(li.inflate(R.layout.item_card,null,false));
    //    //aList.add(li.inflate(R.layout.view_one,null,false));
    //    //aList.add(li.inflate(R.layout.view_two,null,false));
    //    //aList.add(li.inflate(R.layout.view_three,null,false));
    //    mAdapter = new CardPagerAdapter(aList);
    //    vpager_one=binding.mViewPager;
    //    vpager_one.setAdapter(mAdapter);
    //
    //}

    private FrameLayout mFlCardBack, mFlCardFront, mFlContainer;


    // 改变视角距离, 贴近屏幕,这个必须设置，因为如果不这么做，沿着Y轴旋转的过程中有可能产生超出屏幕的3D效果。
    private void setCameraDistance() {
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mFlCardFront.setCameraDistance(scale);
        mFlCardBack.setCameraDistance(scale);
    }

    private boolean mIsShowBack = false;

    // 翻转卡片
    public void flipCard() {
        // 正面朝上
        if (!mIsShowBack) {
            inSet.setTarget(mFlCardBack);
            outSet.setTarget(mFlCardFront);
            mIsShowBack = true;
        } else { // 背面朝上
            inSet.setTarget(mFlCardFront);
            outSet.setTarget(mFlCardBack);
            mIsShowBack = false;
        }
        inSet.start();
        outSet.start();
    }

    AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(Animator animation) {
            super.onAnimationStart(animation);
            mFlContainer.setClickable(false);//在动画执行过程中，不许允许接收点击事件
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            mFlContainer.setClickable(true);//在动画执行过程中，不许允许接收点击事件
        }
    };

    private AnimatorSet inSet, outSet;

    private void initAnimatorSet() {
        inSet = new AnimatorSet();
        ObjectAnimator animator = ObjectAnimator.ofFloat(null, "rotationY", -180f, 0f);
        animator.setDuration(500);
        animator.setStartDelay(0);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(null, "alpha", 0.0f, 1f);
        animator2.setStartDelay(250);
        animator2.setDuration(0);
        inSet.playTogether(animator, animator2);
        inSet.addListener(animatorListenerAdapter);

        outSet = new AnimatorSet();
        ObjectAnimator animator_ = ObjectAnimator.ofFloat(null, "rotationY", 0, 180f);
        animator_.setDuration(500);
        animator_.setStartDelay(0);
        ObjectAnimator animator2_ = ObjectAnimator.ofFloat(null, "alpha", 1f, 0f);
        animator2_.setStartDelay(250);
        animator2_.setDuration(0);
        outSet.playTogether(animator_, animator2_);
        outSet.addListener(animatorListenerAdapter);
    }


    void setFlipCard(){

        mFlCardBack = findViewById(R.id.fl_back);
        mFlCardFront = findViewById(R.id.fl_front);
        mFlContainer = findViewById(R.id.main_fl_container);
        //mFlCardBack.setScaleX(0.5f);
        //mFlCardBack.setScaleY(0.5f);
        //mFlCardFront.setScaleX(0.5f);
        //mFlCardFront.setScaleY(0.5f);
        //整体缩小了 但是有些还是不显示

        mFlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard();
            }
        });

        initAnimatorSet();
        setCameraDistance(); // 设置镜头距离


    }

   private ViewPager vpager_one;
    private ArrayList<View> aList;
    private CardPagerAdapter mAdapter ;



    ActivityCardBinding binding;

    List<String> titles = new ArrayList<>();
    List<Fragment> fragments = new ArrayList<>();

//    void initRecyclerView(Activity activity){
//
//        RecyclerView mRecyclerView = binding.recyclerView;
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
//        //RecyclerView view 点击 知道他的内容
////       myAdapter = new MyAdapter(activity);
//
//        myAdapter = new MyAdapter(activity,flag);
//        myAdapter.setFriends(friends);
//        myAdapter.setActivity(activity);
//        myAdapter.init();
//
//        //myAdapter.setRecyclerView(mRecyclerView);
//        mRecyclerView.setAdapter(myAdapter);
//        //设置默认属性
//
//        //myAdapter.setOnItemClickListerner(object :FruitAdapter.OnItemClickListerner{
//        //    override fun onItemClick(position: Int) {
//        //        Toast.makeText(this@MainActivity, "你点击了${frustList.get(position)}", Toast.LENGTH_SHORT).show()
//        //    }
//        //})
////————————————————
////        版权声明：本文为CSDN博主「强强爱学习」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
////        原文链接：https://blog.csdn.net/weixin_42708161/article/details/107919566
//
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        flag=1;
//
//    }

    public void tabInit() {
        titles.add("   勋章   ");
        titles.add("   好友   ");
        titles.add("   个人   ");

//        for (String title : titles) {
//            fragments.add(new TabFragment());
//
//
//        }
//        fragments.add(new TabFragment());
        fragments.add(new CardFragment());
        fragments.add(new CardFragment());
        fragments.add(new CardFragment());
        //fragments.add(new paiming());
        //fragments.add(new geren());


        //FragmentManager supportFragmentManager = this.getSupportFragmentManager();
        //Fragment fragment;
        //FragmentManager supportFragmentManager = getChildFragmentManager();
        FragmentManager supportFragmentManager = getSupportFragmentManager();

        FmPagerAdapter pagerAdapter = new FmPagerAdapter(titles, fragments, supportFragmentManager);

        //binding.mViewPager.setAdapter(pagerAdapter);

//        viewPager.setAdapter(pagerAdapter);

        //tabLayout.setupWithViewPager(viewPager);

    }
}
