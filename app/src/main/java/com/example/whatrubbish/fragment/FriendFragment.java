package com.example.whatrubbish.fragment;

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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smlj.MyAdapter;
import com.example.whatrubbish.Bus;
import com.example.whatrubbish.adapter.FriendsAdapter;
import com.example.whatrubbish.adapter.NormalAdapter;
import com.example.whatrubbish.databinding.FragmentFriendBinding;
import com.example.whatrubbish.entity.User;
import com.example.whatrubbish.gridIcons.DividerGridItemDecoration;
import com.example.whatrubbish.util.HttpUtil;
import com.example.whatrubbish.util.ThreadPoolManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FriendFragment extends Fragment {

    private MyAdapter myAdapter;
    FragmentFriendBinding binding;
    Activity activity;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        activity = getActivity();

        binding = FragmentFriendBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ThreadPoolManager.run(new Thread(()->{
            try {
                addFriend("1");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        return root;
    }

  Listener listener;

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onBtnToFriendClick();
        void onBtnToMainClick();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("WrongConstant")
    void addFriend(String username) throws IOException {
        //HttpUtil.post(Bus.baseDbUrl+Bus.userList, User.builder().username(username))
        //User build = User.builder().username(username).build();
        User build = new User();
        build.setUsername(username);
        JsonObject post = HttpUtil.post(Bus.baseDbUrl + "/user/findByUsernameLike", build);
        JsonArray dataArr = post.get("data").getAsJsonArray();

        //JsonObject post = HttpUtil.post(Bus.baseDbUrl + Bus.cityFindByPicResNotNull, new City());
        //JsonArray asJsonArray = post.get(Bus.contentMark).getAsJsonArray();
        User[] users = Bus.gson.fromJson(dataArr, User[].class);
        activity.runOnUiThread(() -> {
            setStrAdapter(users);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("WrongConstant")
    void setStrAdapter(User[] users) {
        //cities=  Arrays.stream(citiesArr).limit(5).collect(Collectors.toList());
        RecyclerView recyclerView = binding.recyclerView;
        //recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        //List<String> userNames = Arrays.stream(users).map(o -> {
        //    return o.getUsername();
        //}).collect(Collectors.toList());
        List<User> collect = Arrays.stream(users).collect(Collectors.toList());
        //NormalAdapter normalAdapter = new NormalAdapter(userNames);
        FriendsAdapter friendsAdapter = new FriendsAdapter(collect);
        friendsAdapter.setActivity(activity);
        recyclerView.setAdapter(friendsAdapter);
        //设置分隔线
        recyclerView.addItemDecoration(new DividerGridItemDecoration(activity));
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //RecyclerViewUtil.init(  binding.recyclerView,activity);
    }
}