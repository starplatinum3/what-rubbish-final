package com.example.whatrubbish.activity;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bullfrog.particle.IParticleManager;
import com.bullfrog.particle.Particles;
import com.bullfrog.particle.animation.ParticleAnimation;
import com.bullfrog.particle.particle.configuration.Rotation;
import com.bullfrog.particle.particle.configuration.RotationDirection;
import com.bullfrog.particle.particle.configuration.Shape;
import com.bullfrog.particle.path.IPathGenerator;
import com.bullfrog.particle.path.LinearPathGenerator;
import com.example.whatrubbish.MainActivity;
import com.example.whatrubbish.R;
import com.example.whatrubbish.databinding.ActivityCardComposeBinding;
import com.example.whatrubbish.databinding.ActivityRotate3dBinding;
import com.example.whatrubbish.flipCard.Rotate3dAnimation;
import com.example.whatrubbish.util.ActivityUtil;
import com.example.whatrubbish.util.ParticleUtil;
import com.plattysoft.leonids.ParticleSystem;

import org.jetbrains.annotations.NotNull;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import lombok.val;

//https://blog.csdn.net/u010302327/article/details/83379099
public class Rotate3dActivity extends AppCompatActivity {
    private ImageView[] imageViews = new ImageView[2];
    private int index = 0;
//    private ObjectAnimator frontAnim;
//    private ObjectAnimator backAnim;
    private Animation frontAnim;
    private Animation backAnim;

    IParticleManager particleManager;

    boolean clicked=false;

    ActivityRotate3dBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_rotate_3d);

        binding = ActivityRotate3dBinding.inflate(getLayoutInflater());
        //安卓 按钮点击没有反应  因为这里setContentView的是layout拿出来的， binding的是另外一套

        setContentView(binding.getRoot());

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        imageViews[0] = findViewById(R.id.front_view);
        //imageViews[0].setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.ic_jn,options));
        imageViews[0].setImageBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.tuisong_1,options));
        imageViews[1] = findViewById(R.id.back_view);
        //imageViews[1].setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.ic_m,options));
        imageViews[1].setImageBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.tuisong_2,options));

        imageViews[0].setOnClickListener(v->{
            blow(imageViews[0]);
            //MainActivity
            //int waitMs=3000;
            //new Handler(message -> {
            //    ActivityUtil.startActivity(this,MainActivity.class);
            //    return false;
            //}).sendEmptyMessageDelayed(0, waitMs); // 延迟3秒
            delayStart();
        });
        imageViews[1].setOnClickListener(v->{
            blow(imageViews[1]);
           delayStart();
           //if(clicked==false){
           //    delayStart();
           //}
        });
        //startAnimation();
        //这个时候启动有问题啊 只有一个边在转动

//        frontAnim = new ObjectAnimator();
//        frontAnim.setFloatValues(0,90f);
//        frontAnim.setDuration(1000);
//        frontAnim.setInterpolator(new LinearInterpolator());
//        frontAnim.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                imageViews[index].setVisibility(View.INVISIBLE);
//                index = (index+1)%2;
//                imageViews[index].setVisibility(View.VISIBLE);
//                backAnim.start();
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
//        ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float value = (Float) animation.getAnimatedValue();
//                imageViews[index].setRotationY(value);
//            }
//        };
//        frontAnim.addUpdateListener(updateListener);
//
//        backAnim = new ObjectAnimator();
//        backAnim.setFloatValues(270f,360f);
//        backAnim.setDuration(1000);
//        backAnim.setInterpolator(new LinearInterpolator());
//        backAnim.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                isAnim = false;
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//            }
//        });
//        backAnim.addUpdateListener(updateListener);

        binding.button.setOnClickListener(v -> {
            //blow(v);
            Log.i("button", "onCreate: ");
            drawParticle();
            //ActivityUtil.startActivity(this,ParticleExamActivity.class);
        });

        binding.btnToParticle.setOnClickListener(v -> {

            ActivityUtil.startActivity(this,ParticleActivity.class);
        });
    }

    void delayStart(){
        if(clicked){
            return;
        }
        clicked=true;
        int waitMs=3000;
        new Handler(message -> {
            ActivityUtil.startActivity(this,MainActivity.class);
            clicked=false;
            return false;
        }).sendEmptyMessageDelayed(0, waitMs); // 延迟3秒
    }

    void blow(View view){


        Log.i("blow", "blow: ");
       View container=view;
       Context context=this;
       View anchor=view;
        ConstraintLayout root = binding.getRoot();
        //binding.container;
        //Particles.Companion.with()
        //Particles.Companion.with(context, container) // container 是粒子动画的宿主父 ViewGroup
        //Particles.Companion.with(context, root) // container 是粒子动画的宿主父 ViewGroup
        Particles.Companion.with(context, binding.container) // container 是粒子动画的宿主父 ViewGroup
                .colorFromView(anchor,50)// 从 button 中采样颜色
                .particleNum(200)// 一共 200 个粒子
                .anchor(anchor)// 把 button 作为动画的锚点
                .shape(Shape.CIRCLE)// 粒子形状是圆形
                .radius(2, 6)// 粒子随机半径 2~6
                .anim(ParticleAnimation.Companion.getEXPLOSION())// 使用爆炸动画
                .start();


    }

    //kotlin 翻译java 、
    //private fun createAnimator(): ValueAnimator {
    //    val animator = ValueAnimator.ofInt(0, 1)
    //    animator.repeatCount = -1
    //    animator.repeatMode = ValueAnimator.REVERSE
    //    animator.duration = 4000L
    //    return animator
    //}

    private final ValueAnimator createAnimator() {
        ValueAnimator animator = ValueAnimator.ofInt(new int[]{0, 1});
        Intrinsics.checkNotNullExpressionValue(animator, "animator");
        animator.setRepeatCount(-1);
        //animator.setRepeatMode(2);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setDuration(4000L);
        return animator;
    }

    void blowOldType(View view){


        //int res=  R.mipmap.browse;
        //int res=  R.drawable.level_complete_star;
        int res=  R.drawable.animated_confetti;
        //不太好看
        //彩带
        //int res=  R.drawable.star_white_border;
        //int res=  R.drawable.star_white;
        new ParticleSystem(this, 1000, res, 3000)
                .setSpeedModuleAndAngleRange(0.05f, 0.2f, 0, 360)
                .setRotationSpeed(30)
                .setAcceleration(0, 90)
                //.oneShot(binding.btnBlow, 200);
                .oneShot(view, 200);
    }

    void drawParticle(){

        particleManager = Particles.Companion.with(this, binding.container);

        ParticleUtil.Companion. drawParticle(particleManager,binding.button);
        //button.visibility = View.GONE
        binding.button.setVisibility(View.GONE);
        //particleManager.colorFromView(binding.button,50)
        //        .particleNum(500)
        //        .anchor(binding.button)
        //        .shape(Shape.CIRCLE, Shape.HOLLOW_RECTANGLE)
        //        .radius(8, 14)
        //        .strokeWidth(8f)
        //        .size(40, 40)
        //        .rotation(
        //                new Rotation(600, RotationDirection.ClockWise)
        //        )
        //        .bitmap(R.drawable.ic_thumbs_up)
        //        .anim(ParticleAnimation.Companion.with(
        //                new Function0<ValueAnimator>() {
        //                    @Override
        //                    public ValueAnimator invoke() {
        //                        return null;
        //                    }
        //                }, new Function0<ValueAnimator>() {
        //                    @Override
        //                    public ValueAnimator invoke() {
        //                        return null;
        //                    }
        //                }
        //        ));
        //particleManager.start();
    }
    @Override
    protected void onStart() {
        super.onStart();
        //startAnimation();
        //这也是不行滴

        //我觉得需要延时 确实 延时就好了
        int waitMs=200;
        new Handler(message -> {
            //ActivityUtil.startActivity(getActivity(), AbsActivity.class);
            startAnimation();
            return false;
        }).sendEmptyMessageDelayed(0, waitMs); // 延迟3秒

    }

    private final IPathGenerator createPathGenerator() {
        return (IPathGenerator) (new LinearPathGenerator() {
            private final double cos;
            private final double sin;

            public final double getCos() {
                return this.cos;
            }

            public final double getSin() {
                return this.sin;
            }

            public void getCurrentCoord(float progress, long duration, @NotNull int[] outCoord) {
                Intrinsics.checkNotNullParameter(outCoord, "outCoord");
                float originalX = (float) this.getDistance() * progress;
                float var10000 = (float) 100;
                float var7 = originalX / (float) 50;
                boolean var8 = false;
                float originalY = var10000 * (float) Math.sin((double) var7);
                double x = (double) originalX * this.cos - (double) originalY * this.sin;
                double y = (double) originalX * this.sin + (double) originalY * this.cos;
                outCoord[0] = (int) (0.01D * x * (double) originalY);
                byte var13 = 2;
                boolean var14 = false;
                outCoord[1] = -((int) (1.0E-4D * Math.pow(y, (double) var13) * (double) originalX));
            }

            {
                this.cos = Random.Default.nextDouble(-1.0D, 1.0D);
                this.sin = Random.Default.nextDouble(-1.0D, 1.0D);
            }
        });
    }

    private boolean isAnim = false;
    public void startAnimation(View view){

        startAnimation();
 
//        frontAnim.start();
    }

    public void startAnimation(){
        if(isAnim){
            return;
        }
        isAnim = true;
        if(frontAnim == null){
            final float centerX = imageViews[0].getWidth() / 2.0f;
            final float centerY = imageViews[0].getHeight() / 2.0f;
            frontAnim= new Rotate3dAnimation(this,0, 90, centerX, centerY, 0f, true,0);
            frontAnim.setDuration(1000);                         //设置动画时长
            frontAnim.setFillAfter(true);                        //保持旋转后效果
            frontAnim.setInterpolator(new LinearInterpolator());   //设置插值器

            backAnim = new Rotate3dAnimation(this,-90, 0, centerX, centerY, 0f, true,0);
            backAnim.setDuration(1000);
            backAnim.setFillAfter(true);
            backAnim.setInterpolator(new LinearInterpolator());

            frontAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    imageViews[index].setVisibility(View.INVISIBLE);
                    imageViews[index].clearAnimation();
                    index = (index+1)%2;
                    //第二张图片 不能看了 然后开始了转动回来
                    imageViews[index].setVisibility(View.VISIBLE);
                    imageViews[index].startAnimation(backAnim);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            backAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    isAnim = false;
                    //imageViews[index].setVisibility(View.INVISIBLE);
                    //imageViews[index].setVisibility(View.VISIBLE);
                    imageViews[index].clearAnimation();
                    //imageViews[index].clearAnimation();
                    //index = (index+1)%2;
                    ////imageViews[index].setVisibility(View.VISIBLE);
                    ////imageViews[index].setVisibility(View.INVISIBLE);
                    //imageViews[index].startAnimation(frontAnim);
                    //animation.start();
                    imageViews[index].startAnimation(frontAnim);
                    //写上这句 就会一直转了
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        imageViews[index].startAnimation(frontAnim);


//        frontAnim.start();
    }
}