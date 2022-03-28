package com.example.whatrubbish.ui.home;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

//import com.android.soundrecorder.SoundRecorder;
import com.bn.tl.anzhi.BasketBall_Shot_Activity;
import com.example.compose.ComposeActivity;
import com.example.compose.ComposeActivityKt;
import com.example.whatrubbish.LoginActivity;
import com.example.whatrubbish.R;
import com.example.whatrubbish.RegionSettingActivity;
import com.example.whatrubbish.RegisterActivity;
import com.example.whatrubbish.achievement.AchievementActivity;
import com.example.whatrubbish.achievement.AchievementActivitySelf;
//import com.example.whatrubbish.activity.MyAppActivity;
import com.example.whatrubbish.activity.AbsActivity;
import com.example.whatrubbish.activity.GameStageActivity;
import com.example.whatrubbish.activity.NoBottomNavActivity;
import com.example.whatrubbish.activity.RecMsgActivity;
import com.example.whatrubbish.activity.WsTestActivity;
import com.example.whatrubbish.constant.MoveConstant;
import com.example.whatrubbish.databinding.FragmentHomeBinding;
import com.example.whatrubbish.fragment.CollectRubFragment;
import com.example.whatrubbish.fragment.SplitDropRubFragment;
import com.example.whatrubbish.neteasecloudmusictab.fragment.AchievementFragment;
import com.example.whatrubbish.util.ActivityUtil;
import com.example.whatrubbish.wanandroid.WanandroidActivity;
import com.hurteng.stormplane.object.GameGoods;
import com.suramire.androidgame25.MainActivity;
//import com.example.whatrubbish.tabLayout.TabLayoutActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
//import soundrecorder.SoundRecorder;
//import cn.coderdream.anim.MAnim;

//Fragment implements View.OnClickListener
public class HomeFragment extends Fragment implements View.OnClickListener {
    //    HomeViewModel 和 adapter
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    FragmentActivity activity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();

//        GameGoods 确实可以了
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //去成就
        binding.btnToAchievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAchievement();
                // TODO Auto-generated method stub
                Log.v("111", "2222");
            }
        });
//<<<<<<< HEAD

        binding.btnToRegister.setOnClickListener(v -> {
            toRegister();
            Log.v("toRegister", "toRegister");
        });

        binding.btnToRegionSetting.setOnClickListener(v -> {
            startActivity(RegionSettingActivity.class);
            Log.v("btnToRegionSetting", "btnToRegionSetting");
        });

        binding.btnToSuperMaria.setOnClickListener(v -> {
            startActivity(MainActivity.class);
            Log.v("btnToSuperMaria", "btnToSuperMaria");
        });

        binding.btnChangeImg.setOnClickListener(v -> {
//            可以的
            Log.v("setImageResource", "setImageResource");
//           binding.image.setImageResource(R.mipmap.jindu_2);
            binding.image.setImageResource(R.mipmap.yingdao_2);
//            Intent intent = new Intent(this, LoginActivity.class);
//            activity.startActivity(intent);
        });
        binding.btnToCollectRub.setOnClickListener(v -> {
            activity.getSupportFragmentManager().beginTransaction().
                    replace(R.id.nav_host_fragment_activity_main, new CollectRubFragment()).
                    addToBackStack(null).commit();
        });

        binding.btnToWxSorting.setOnClickListener(v -> {
            String url = "http://139.196.8.79:8890/";
            activity.getSupportFragmentManager().beginTransaction().
                    replace(R.id.nav_host_fragment_activity_main, new SplitDropRubFragment(url)).
                    addToBackStack(null).commit();
        });
        binding.btnToNoBottomNavActivity.setOnClickListener(v -> {
            ActivityUtil.startActivity(activity, NoBottomNavActivity.class);

        });

        binding.btnToHitBall.setOnClickListener(v -> {
            ActivityUtil.startActivity(activity, com.richard.hitball.MainActivity.class);

        });
        binding.btnToGameHall.setOnClickListener(v -> {
            ActivityUtil.startActivity(activity, com.example.androidteris.MainActivity.class);
        });
        binding.btnToBasket.setOnClickListener(v -> {
            ActivityUtil.startActivity(activity, BasketBall_Shot_Activity.class);
        });





        //平移动画:在x轴上平移
        //ObjectAnimator animator = ObjectAnimator.ofFloat(binding.btnToRegister, "translationX", 0, 200, -200,0);
        //ObjectAnimator animator = ObjectAnimator.ofFloat(binding.btnToRegister, MoveConstant.translationX, 0, 200, -200,0);
        //animator.setDuration(2000);
        //animator.start();

        //ObjectAnimator.ofFloat(binding.btnToRegister, MoveConstant.translationX, 0, 200, -200,0).;

        //可以同时 这是相对于 自己本来的位置吗
        //ObjectAnimator.ofFloat(binding.btnToRegister, MoveConstant.translationX, 0, 200, -200, 0).setDuration(2000).start();
        //ObjectAnimator.ofFloat(binding.btnToRegister, MoveConstant.translationY, 0, 200, -200, 0).setDuration(2000).start();
//————————————————
//        版权声明：本文为CSDN博主「钉某人」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/xiaochuanding/article/details/73290917
//
//        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(tv,"backgroundColor",0xfff10f0f,0xff0f94f1,0xffeaf804,0xfff92a0f);
//        objectAnimator.setDuration(2000);
//        objectAnimator.setEvaluator(new ArgbEvaluator());
//        objectAnimator.start();

        //Expected class or package
        //MAnim instance = MAnim.getInstance();
        //MAnim
        //MAnim.getInstance().with()
        //new  MAnim.getInstance().with
        // MAnim.getInstance()
        //         .with( binding.image)
        // //MAnim.with( binding.image)
        // // MAnim.with( binding.image)
        //         .scaleX(2000, 1f, 3f, 1f)
        //         .scaleY(2000, 1f, 4f, 1f)
        //         .scaleXY(2000, floatArrayOf(1f, 3f, 1f), floatArrayOf(1f, 4f, 1f))
        //         .alpha(2000, 1f, 0.1f, 1f)
        //         .rotationX(2000, 0f, 360f, 0f)
        //         .rotationY(2000, 0f, 360f, 0f)
        //         .rotationXY(2000, floatArrayOf(0f, 360f, 0f), floatArrayOf(0f, 120f, 0f))
        //         .rotation(2000, 0f, 720f, 0f)
        //         .translationX(2000, 0f, 300f, -300f, 0f)
        //         .translationY(2000, 0f, 300f, -300f, 0f)
        //         .translationXY(2000, floatArrayOf(0f, 360f, 0f), floatArrayOf(0f, 120f, 0f))
        //         .start();

        //binding.btnToGame.setOnClickListener(v -> {
        //    ActivityUtil.startActivity(activity, MyAppActivity.class);
        //});
        binding.btnToPlaneGame.setOnClickListener(v -> {
            ActivityUtil.startActivity(activity, com.hurteng.stormplane.MainActivity.class);
            //ActivityUtil.startActivity(activity,  com.hurteng.stormplane.MainActivity.class);
        });
        binding.btnToMemoGame.setOnClickListener(v -> {
            ActivityUtil.startActivity(activity, com.snatik.matches.MainActivity.class);
        });
        binding.btnToFlappy.setOnClickListener(v -> {
            ActivityUtil.startActivity(activity, com.quchen.flappycow.MainActivity.class);
        });


//      ImageView image= activity.findViewById(R.id.image);
//        image.setOnClickListener(v -> {
////            可以的
////            Log.v("setImageResource", "setImageResource");
////           binding.image.set(ImageResource(R.mipmap.jindu_2);
////            binding.image.setImageResource(R.mipmap.yingdao_2);
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//        });
//        image.setOnClickListener(v->{
//            new Intent(..)
//        });

//        startActivity();

        binding.image.setOnClickListener(v -> {
//            点击图片 改变是可行的
            Log.v("setImageResource", "setImageResource");
//            binding.image.setImageResource(R.mipmap.jindu_2);
            binding.image.setImageResource(R.mipmap.yingdao_1);
        });
//        binding.btnChangeImg
//=======
//>>>>>>> 170ca4c... !1 修改了很多颜色 Merge pull request !1 from Dearning/dev
        Button btnToAchievementSelf = (Button) root.findViewById(R.id.btnToAchievementSelf);
        btnToAchievementSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAchievementSelf();
                Log.v("111", "2222");
            }
        });

        Button btnToTabLayoutTest = (Button) root.findViewById(R.id.btnToTabLayoutTest);
        btnToTabLayoutTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toTabLayoutTest();
                Log.v("toTabLayoutTest", "toTabLayoutTest");
            }
        });

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        binding.btnStartWeb.setOnClickListener(v -> {
            startWeb();
        });
        //binding.btnToSoundRecorder.setOnClickListener(v -> {
        //    ActivityUtil.startActivity(activity, SoundRecorder.class);
        //});
        binding.btnToTrashDetect.setOnClickListener(v -> {
          ActivityUtil.startActivity(activity, com.example.trash_detective.Activity.MainActivity.class);
        });
        binding.btnToWelcomeActivityIm.setOnClickListener(v -> {
            ActivityUtil.startActivity(activity, com.sdust.im.activity.MainActivity.class);
        });
        binding.btnToLoginActivityIm.setOnClickListener(v -> {
            ActivityUtil.startActivity(activity, com.sdust.im.activity.LoginActivity.class);
        });

        binding.btnToWsTest.setOnClickListener(v -> {
            ActivityUtil.startActivity(activity, WsTestActivity.class);
        });

        binding.btnToWsLogin.setOnClickListener(v -> {
            //ActivityUtil.startActivity(activity, ComposeActivityKt.class);
            ActivityUtil.startActivity(activity, ComposeActivity.class);
        });
        binding.btnToWannaAndroid.setOnClickListener(v -> {
            ActivityUtil.startActivity(activity, WanandroidActivity.class);
        });
        binding.btnToRecMsgActivity.setOnClickListener(v -> {
            ActivityUtil.startActivity(activity, RecMsgActivity.class);
        });
        binding.btnToGameStageActivity.setOnClickListener(v -> {
            ActivityUtil.startActivity(activity, GameStageActivity.class);
        });
        binding.btnToAbsActivity.setOnClickListener(v -> {
            Log.i("AbsActivity", "onCreateView: start");
            ActivityUtil.startActivity(activity, AbsActivity.class);
        });


        return root;
    }

    void startWeb() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.baidu.com"));
        startActivity(intent);
    }

    void toTabLayoutTest() {
    }

    void startActivity(Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        startActivity(intent);
    }

    void toAchievementSelf() {
        Intent intent = new Intent(activity, AchievementActivitySelf.class);
        startActivity(intent);
    }

    //<<<<<<< HEAD
//
//=======
//>>>>>>> 170ca4c... !1 修改了很多颜色 Merge pull request !1 from Dearning/dev
    @OnClick(R.id.btnToAchievement)
    public void toAchievement(View view) {
        Button btnToAchievement = (Button) view;
        String btnToAchievementStr = btnToAchievement.getText().toString();
        Log.i("btnToAchievementStr", btnToAchievementStr);
        Intent intent = new Intent(activity, AchievementFragment.class);
        startActivity(intent);
    }

    public void toAchievement() {
        Intent intent = new Intent(activity, AchievementActivity.class);
        startActivity(intent);
    }

    public void toRegister() {
        Intent intent = new Intent(activity, RegisterActivity.class);
        startActivity(intent);
    }


    public void onViewClick() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
//        onclick 是没有 产生
        Log.i("view", view.getId() + "");
        Log.i("onClick", "onClick");
        switch (view.getId()) {
            case R.id.btnToAchievement:
                Log.i("btnToAchievement", "btnToAchievement");
                toAchievement();
                break;
        }
    }
}