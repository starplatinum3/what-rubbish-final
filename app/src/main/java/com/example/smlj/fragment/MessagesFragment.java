package com.example.smlj.fragment;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smlj.MyAdapter;
import com.example.whatrubbish.Bus;
import com.example.whatrubbish.R;
import com.example.whatrubbish.activity.SearchFriendActivity;
//import com.example.whatrubbish.databinding.CommonTitlebarBinding;
import com.example.whatrubbish.databinding.MessagesBinding;
import com.example.whatrubbish.db.App;
import com.example.whatrubbish.db.AppDatabase;
import com.example.whatrubbish.entity.User;
import com.example.whatrubbish.fragment.FriendFragment;
import com.example.whatrubbish.fragment.WikiFragment;
import com.example.whatrubbish.util.ActivityUtil;
import com.example.whatrubbish.util.HttpUtil;
import com.example.whatrubbish.util.JsonJavaUtil;
import com.example.whatrubbish.util.ThreadPoolFactory;
import com.example.whatrubbish.util.ToastUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.Arrays;

public class MessagesFragment extends Fragment {

    private MyAdapter myAdapter;
    MessagesBinding binding;
    Activity activity;
    static int flag=0;
    AppDatabase appDatabase;

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    //CommonTitlebarBinding commonTitlebarBinding;
    public void setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    User[] friends;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       activity=getActivity();

        binding = MessagesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //commonTitlebarBinding = CommonTitlebarBinding.bind(root);
        initData();
        //initRecyclerView(activity);


        appDatabase= AppDatabase.getDatabase(activity);
        //Fragment friendFragment = new FriendFragment();
        binding.btnToSearchFriend.setOnClickListener(v->{
            //activity.getF
            //getSu
            //这个会挡住
            //getActivity().getSupportFragmentManager().beginTransaction()
            //        .replace(R.id.nav_host_fragment_activity_main,friendFragment).
            //        addToBackStack(null).commit();
            ActivityUtil.startActivity(getActivity(), SearchFriendActivity.class);
            //activity.getFragmentManager().beginTransaction()
            //        .replace(R.id.nav_host_fragment_activity_main,friendFragment).
            //        addToBackStack(null).commit();
            //getfR
        });
        //这个用户的所有朋友 要交朋友 首先要查找朋友


//        while(true){
//            if(myAdapter.isend()){
//                //onCreate(null);
//
//                myAdapter.notifyDataSetChanged();
//                break;
//            }
//        }
        return root;

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    void initData(){
        if(Bus.curUser==null){
            Log.i("没有登录", "initData: 没有登录");
            initRecyclerView(activity);
            return;
        }
        ThreadPoolFactory.getExecutorService().execute(()->{
            try {
                JsonObject post = HttpUtil.post(Bus.baseDbUrl + "/friendship/listFriend", Bus.curUser);
                if (post.get("code").getAsInt()== Bus.codeError) {
                    //activity.runOnUiThread(()->{
                    //    ToastUtil.show(activity,"");
                    //});
                    ToastUtil.showOnUiThread(activity,"失败  "+post.get("msg"));
                    return;
                }
                JsonArray asJsonArray = post.get(Bus.dataMark).getAsJsonArray();
                //Us
                friends = JsonJavaUtil.jsonArrToObjArr(asJsonArray, User[].class);
                Log.i("friends", "onCreateView: "+ Arrays.toString(friends));
                activity.runOnUiThread(()->{
                    initRecyclerView(activity);
                });


                //for (User user : friends) {
                //    namelist.add(user.getNickname());
                //    //还有一个上次的聊天
                //    //contentlist.add(user.getNickname());
                //    //timelist.add(user.getNickname());
                //    contentlist.add("");
                //    timelist.add("");
                //    //touxianglist.add(user.getAvatarUrl());
                //    avatarList.add(user.getAvatarUrl());
                //    //我觉得放在一个接口反而不妥？
                //    //有limit的话 可能limit 可以少的吧
                //}

                //for (JsonElement jsonElement : asJsonArray) {
                //    JsonObject asJsonObject = jsonElement.getAsJsonObject();
                //    //asJsonArray.get("")
                //
                //    //JsonUtil.
                //    //City[] citiesArr = Bus.gson.fromJson(asJsonArray, City[].class);
                //}
            } catch (IOException e) {
                e.printStackTrace();
                //ToastUtil.show(getActivity(),"404了 "+e.getMessage());
                ToastUtil.showOnUiThread(getActivity(),"404了 "+e.getMessage());
            }
        });
    }
    void initRecyclerView(Activity activity){

        RecyclerView mRecyclerView = binding.recyclerView;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        //RecyclerView view 点击 知道他的内容
//       myAdapter = new MyAdapter(activity);

       myAdapter = new MyAdapter(activity,flag);
        myAdapter.setFriends(friends);
        myAdapter.setActivity(activity);
        myAdapter.init();

        //myAdapter.setRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(myAdapter);
        //设置默认属性

        //myAdapter.setOnItemClickListerner(object :FruitAdapter.OnItemClickListerner{
        //    override fun onItemClick(position: Int) {
        //        Toast.makeText(this@MainActivity, "你点击了${frustList.get(position)}", Toast.LENGTH_SHORT).show()
        //    }
        //})
//————————————————
//        版权声明：本文为CSDN博主「强强爱学习」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/weixin_42708161/article/details/107919566

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        flag=1;

    }




}