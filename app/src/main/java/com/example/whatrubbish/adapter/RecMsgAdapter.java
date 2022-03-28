package com.example.whatrubbish.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.whatrubbish.Bus;
import com.example.whatrubbish.R;
import com.example.whatrubbish.entity.Friendship;
import com.example.whatrubbish.entity.RecMsg;
import com.example.whatrubbish.entity.User;
import com.example.whatrubbish.util.HttpUtil;
import com.example.whatrubbish.util.ThreadPoolManager;
import com.example.whatrubbish.util.ToastUtil;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

// ① 创建Adapter
//@Data
public class RecMsgAdapter extends RecyclerView.Adapter<RecMsgAdapter.VH>{
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
        //public final TextView tvUsername;
        //public final TextView tvNickName;
        //public final TextView tvUsername;
        //public final TextView tvNickName;
        public final TextView tv_title;
        public final TextView tv_content;
        public final TextView tv_date;
        public VH(View v) {
            super(v);

            //title = (TextView) v.findViewById(R.id.title);
            //tvUsername = (TextView) v.findViewById(R.id.tvUsername);
            //tvNickName = (TextView) v.findViewById(R.id.tvNickName);
            tv_title =  v.findViewById(R.id.tv_title);
            tv_content = (TextView) v.findViewById(R.id.tv_content);
            tv_date = (TextView) v.findViewById(R.id.tv_date);
        }
    }

    //private List<String> mDatas;
    //private List<User> mDatas;
    private List<RecMsg> mDatas;
    //public FriendsAdapter(List<String> data) {
    //    this.mDatas = data;
    //}
    public RecMsgAdapter(List<RecMsg> data) {
        this.mDatas = data;
    }

    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(VH holder, int position) {
        //holder.title.setText(mDatas.get(position));
        //User user = mDatas.get(position);
        RecMsg user = mDatas.get(position);
        String msg = user.getMsg();
        holder.tv_content.setText(msg);
        holder.tv_title.setText(user.getTitle());
        holder.tv_date.setText( user.getDate()+"");

        //holder.itemView.setOnClickListener(v -> {
        //    //item 点击事件
        //    //添加好友 请求
        //    //User user = mDatas.get(position);
        //    Log.d("TAG", "onBindViewHolder: 添加好友  "+user);
        //
        //    //String   msg="添加他吗? "+ finalNickname;
        //    new AlertDialog.Builder(activity).setTitle("确认添加好友").setMessage("添加他吗? "+ finalNickname).
        //            setPositiveButton("确定", (dialog, which) -> {
        //                Thread thread = new Thread(() -> {
        //                    try {
        //                        addFriend(user);
        //                    //} catch (IOException e) {
        //                    } catch (Exception e) {
        //                        e.printStackTrace();
        //                    }
        //                });
        //                ThreadPoolManager.run(thread);
        //
        //            }).
        //            setNegativeButton("取消", (dialog, which) -> {
        //            }).show();
        //});
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_1, parent, false);
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rec_msg, parent, false);
        return new VH(v);
    }
}