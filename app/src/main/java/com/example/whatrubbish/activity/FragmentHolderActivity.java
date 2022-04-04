package com.example.whatrubbish.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.smlj.FmPagerAdapter;
import com.example.whatrubbish.R;
import com.example.whatrubbish.adapter.CardPagerAdapter;
import com.example.whatrubbish.databinding.ActivityCardBinding;
import com.example.whatrubbish.databinding.ActivityFragmentHolderBinding;
import com.example.whatrubbish.fragment.CardFragment;
import com.example.whatrubbish.fragment.SplitDropRubFragment;
import com.example.whatrubbish.fragment.WikiHolderFragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentHolderActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFragmentHolderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FragmentActivity activity=this;
        //然而具体的不知道。。
//FragmentHolderActivity
//        WikiHolderFragment
//        String url = "http://139.196.8.79:8890/";
        activity.getSupportFragmentManager().beginTransaction().
                replace(R.id.holder, fragment).
                addToBackStack(null).commit();
    }

    Fragment fragment;

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    ActivityFragmentHolderBinding binding;

}
