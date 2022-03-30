package com.example.whatrubbish.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatrubbish.R;
import com.example.whatrubbish.card.CardPagerAdapter;
import com.example.whatrubbish.entity.RecMsg;

import java.util.List;

// ① 创建Adapter
//@Data
public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.VH>{
    //② 创建ViewHolder
    Activity activity;

    //如果不是adpter的话 是不是布局就乱掉了

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
        //public final TextView tv_title;
        //public final TextView tv_content;
        //public final TextView tv_date;
        public final TextView tv_title;
        //public final TextView tv_content;
        public final ImageView tv_content;
        public final TextView tv_descrip;

        public VH(View v) {
            super(v);

            //title = (TextView) v.findViewById(R.id.title);
            //tvUsername = (TextView) v.findViewById(R.id.tvUsername);
            //tvNickName = (TextView) v.findViewById(R.id.tvNickName);
            tv_title =  v.findViewById(R.id.tv_title);
            tv_content =v.findViewById(R.id.tv_content);
            tv_descrip = (TextView) v.findViewById(R.id.tv_descrip);
        }
    }

    //private List<String> mDatas;
    //private List<User> mDatas;
    private List<RecMsg> mDatas;
    //public FriendsAdapter(List<String> data) {
    //    this.mDatas = data;
    //}
    public CardListAdapter(List<RecMsg> data) {
        this.mDatas = data;
    }

    //③ 在Adapter中实现3个方法
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(VH holder, int position) {
        //holder.title.setText(mDatas.get(position));
        //User user = mDatas.get(position);
        RecMsg user = mDatas.get(position);
        String msg = user.getMsg();
        //holder.tv_content.setText(msg);
        holder.tv_content.setImageResource(R.drawable.miku_fang);
        holder.tv_title.setText(user.getTitle());
        holder.tv_descrip.setText( user.getDate()+"");
        //dapter  他的相对布局就正常了吧

        //CardPagerAdapter
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

    @NonNull
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_1, parent, false);
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rec_msg, parent, false);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new VH(v);
    }
}