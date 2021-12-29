package com.example.whatrubbish.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smlj.MyAdapter;
import com.example.whatrubbish.Bus;
import com.example.whatrubbish.R;
import com.example.whatrubbish.databinding.FragmentFriendBinding;
import com.example.whatrubbish.databinding.FragmentNavBinding;
import com.example.whatrubbish.entity.User;
import com.example.whatrubbish.gridIcons.DividerGridItemDecoration;
import com.example.whatrubbish.util.HttpUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;

public class NavFragment extends Fragment {

    private FragmentNavBinding binding;

    FragmentActivity activity;

    public NavFragment() {
    }

    public NavFragment(FragmentActivity activity) {
        this.activity = activity;
    }

    String url;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNavBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.nav1.setOnClickListener(v->{
            Log.d("onCreateView", "onCreateView: nav to WikiFragment");
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main,new WikiFragment()).addToBackStack(null).commit();
            //getChildFragmentManager().beginTransaction().
            //如果他已经是这个 地方了 就不要再启动一个
            //binding.nav1Icon.setImageResource(R.mipmap.group_1);
            binding.nav1Icon.setImageResource(R.mipmap.group_12);
            //这个变了
            //binding.nav1Img.setBackground(R.drawable.none);
            binding.nav1Img.setBackgroundResource(R.drawable.none);
            //setBackground 安卓
        });
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}