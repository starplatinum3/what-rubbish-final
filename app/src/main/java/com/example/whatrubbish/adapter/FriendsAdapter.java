package com.example.whatrubbish.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.whatrubbish.Bus;
import com.example.whatrubbish.R;
import com.example.whatrubbish.entity.Friendship;
import com.example.whatrubbish.entity.User;
import com.example.whatrubbish.util.HttpUtil;
import com.example.whatrubbish.util.ThreadPoolManager;
import com.example.whatrubbish.util.ToastUtil;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import lombok.Data;

// ① 创建Adapter
//@Data
public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.VH>{
    //② 创建ViewHolder
    Activity activity;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public static class VH extends RecyclerView.ViewHolder{
        //public final TextView title;
        public final TextView tvUsername;
        public final TextView tvNickName;
        public VH(View v) {
            super(v);
            //title = (TextView) v.findViewById(R.id.title);
            tvUsername = (TextView) v.findViewById(R.id.tvUsername);
            tvNickName = (TextView) v.findViewById(R.id.tvNickName);
        }
    }

    //private List<String> mDatas;
    private List<User> mDatas;
    //public FriendsAdapter(List<String> data) {
    //    this.mDatas = data;
    //}
    public FriendsAdapter(List<User> data) {
        this.mDatas = data;
    }

    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(VH holder, int position) {
        //holder.title.setText(mDatas.get(position));
        User user = mDatas.get(position);
        String nickname = user.getNickname();
        if(nickname==null){
            nickname="没有昵称";
        }
        //holder.tvNickName.setText(user.getNickname());
        holder.tvNickName.setText(nickname);
        holder.tvUsername.setText(user.getUsername());
        String finalNickname = nickname;
        holder.itemView.setOnClickListener(v -> {
            //item 点击事件
            //添加好友 请求
            //User user = mDatas.get(position);
            Log.d("TAG", "onBindViewHolder: 添加好友  "+user);

            //String   msg="添加他吗? "+ finalNickname;
            new AlertDialog.Builder(activity).setTitle("确认添加好友").setMessage("添加他吗? "+ finalNickname).
                    setPositiveButton("确定", (dialog, which) -> {
                        Thread thread = new Thread(() -> {
                            try {
                                addFriend(user);
                            //} catch (IOException e) {
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                        ThreadPoolManager.run(thread);

                    }).
                    setNegativeButton("取消", (dialog, which) -> {
                    }).show();
        });
    }

    void addFriend(User user) throws IOException {
        //new Friendship()
        Friendship build = new Friendship();
        if(Bus.curUser==null){
            ToastUtil.show(activity,"用户没有登录");
            return;
        }
        build.setSomeOneId(Bus.curUser.getId());
        build.setFriendId(user.getId());
        //Friendship build = Friendship.builder().someOneId(Bus.curUser.getId()).friendId(user.getId()).build();
        //JsonObject post = HttpUtil.post(Bus.baseDbUrl + "/friendship/list", build);
        //JsonObject post = HttpUtil.post(Bus.baseDbUrl + "/friendship/save", build);
        JsonObject post = HttpUtil.post(Bus.baseDbUrl + "/friendship/addFriend", build);
        Log.d("post", "addFriend: "+post);
        //int code = post.get("code").getAsInt();
        String  msg = post.get("msg").getAsString();
        getActivity().runOnUiThread(()->{
            ToastUtil.show(activity,msg);
        });

        //if(code==200){
        //    ToastUtil.show(activity,"添加成功");
        //}else{
        //    ToastUtil.show(activity,msg);
        //}
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_1, parent, false);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new VH(v);
    }
}