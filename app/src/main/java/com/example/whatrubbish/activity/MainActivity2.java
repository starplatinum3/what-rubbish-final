package com.example.whatrubbish.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whatrubbish.R;

/**
 * 旋转卡片特效
 * 第1种写法
 * <p>
 * 用纯java代码来写动画特效以及 执行动画
 */
public class MainActivity2 extends AppCompatActivity {

    private FrameLayout mFlCardBack, mFlCardFront, mFlContainer;

    //https://www.cnblogs.com/hankzhouAndroid/p/9430240.html
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFlCardBack = findViewById(R.id.fl_back);
        mFlCardFront = findViewById(R.id.fl_front);
        mFlContainer = findViewById(R.id.main_fl_container);
        mFlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard();
            }
        });

        initAnimatorSet();
        setCameraDistance(); // 设置镜头距离
    }

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

}