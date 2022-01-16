package com.example.whatrubbish.test;

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

import com.example.compose.ComposeActivity;
import com.example.compx202_finalproject.MainActivity;
import com.example.whatrubbish.R;
import com.example.whatrubbish.databinding.FragmentMainHolderBinding;
import com.example.whatrubbish.databinding.FragmentTestHolderBinding;
import com.example.whatrubbish.databinding.FragmentTextBinding;
import com.example.whatrubbish.fragment.CollectRubFragment;
import com.example.whatrubbish.fragment.FriendFragment;
import com.example.whatrubbish.fragment.MainFragment;
import com.example.whatrubbish.fragment.WebFragment;
import com.example.whatrubbish.util.ActivityUtil;
//import okhttp3.FormBody;
//import okhttp3.FormBody;

//@EqualsAndHashCode(callSuper = true)
//@Data
//@Builder
public class TestHolderFragment extends Fragment {

    //FragmentWikiHolderBinding binding;
    //FragmentHolderBinding binding;
    //FragmentMainHolderBinding binding;
    FragmentTestHolderBinding binding;

    FragmentActivity activity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    MainFragment mainFragment;
    FriendFragment friendFragment;

    void setListener(){


        friendFragment = new FriendFragment();
        friendFragment.setListener(new FriendFragment.Listener() {

            @Override
            public void onBtnToFriendClick() {

            }

            @Override
            public void onBtnToMainClick() {
                getChildFragmentManager().beginTransaction().
                        replace(R.id.holder, mainFragment).addToBackStack(null).commit();
            }
        });

        mainFragment = new MainFragment();

        mainFragment.setListener(new MainFragment.Listener() {
            @Override
            public void onBtnToFriendClick() {

                Log.d("TAG", "onBtnToFriendClick: ");
                getChildFragmentManager().beginTransaction().
                        replace(R.id.holder, friendFragment).addToBackStack(null).commit();

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //这个修改为对应子Fragment和父Fragment的布局文件
//        R.id
//        CircleProgressBar.
        activity = getActivity();

//        rubbishInfoLst.stream().
        binding = FragmentTestHolderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //setListener();
        //getChildFragmentManager().beginTransaction().
        //        replace(R.id.holder,new MainFragment()).addToBackStack(null).commit();
        //getChildFragmentManager().beginTransaction().
        //        replace(R.id.holder, mainFragment).addToBackStack(null).commit();
        //getChildFragmentManager().beginTransaction().
        //        replace(R.id.mainHolder, mainFragment).addToBackStack(null).commit();
        //getChildFragmentManager().beginTransaction().
        //        replace(R.id.mainHolder, new MainFragment()).addToBackStack(null).commit();
        //getFragmentManager()
        getChildFragmentManager().beginTransaction().
                replace(R.id.mainHolder, new WebFragment("https://www.baidu.com")).addToBackStack(null).commit();
        binding.btnToFriend.setOnClickListener(v->{
            Log.d("btnToFriend", "onCreateView: btnToFriend");
            getChildFragmentManager().beginTransaction().
                    replace(R.id.mainHolder, new FriendFragment()).addToBackStack(null).commit();
        });
        binding.btnToCompose.setOnClickListener(v->{
            Log.d("btnToCompose", "onCreateView: ComposeActivity");
            ActivityUtil.startActivity(activity, ComposeActivity.class);
            //getChildFragmentManager().beginTransaction().
            //        replace(R.id.mainHolder, new FriendFragment()).addToBackStack(null).commit();
        });
        binding.btnToComposeHolder.setOnClickListener(v->{
            Log.d("btnToCompose", "onCreateView: ComposeActivity");
            //ActivityUtil.startActivity(activity, ComposeActivity.class);
            getChildFragmentManager().beginTransaction().
                    replace(R.id.mainHolder, new ComposeHolderFragment()).addToBackStack(null).commit();
        });

        binding.btnToBird.setOnClickListener(v->{
            //ActivityUtil.startActivity(activity, MainActivity.class);
            ActivityUtil.startActivity(activity, com.example.compx202_finalproject.MainActivity.class);
        });

        //replace getSupportFragmentManager 下面还有之前的
        binding.btnToCollectRub.setOnClickListener(v -> {
            getChildFragmentManager().beginTransaction().
                    replace(R.id.mainHolder,new CollectRubFragment()).addToBackStack(null).commit();
            //会重叠 因为 这里是用了activity.getSupportFragmentManager()，而前面用的是getChildFragmentManager()
            //是不一样的fragment 管理器
            //activity.getSupportFragmentManager().beginTransaction().
            //        replace(R.id.mainHolder, new CollectRubFragment()).
            //        addToBackStack(null).commit();
        });


        //MainFragment.Listener
        return root;
    }


}
