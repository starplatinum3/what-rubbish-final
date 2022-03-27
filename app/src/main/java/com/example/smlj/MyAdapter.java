package com.example.smlj;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatrubbish.Bus;
import com.example.whatrubbish.MainActivity;
import com.example.whatrubbish.R;
import com.example.whatrubbish.db.AppDatabase;
import com.example.whatrubbish.db.RoomUtil;
import com.example.whatrubbish.entity.City;
import com.example.whatrubbish.entity.User;
import com.example.whatrubbish.im.Chat;
import com.example.whatrubbish.util.ActivityUtil;
import com.example.whatrubbish.util.DrawUtil;
import com.example.whatrubbish.util.HttpUtil;
import com.example.whatrubbish.util.JsonJavaUtil;
import com.example.whatrubbish.util.JsonUtil;
import com.example.whatrubbish.util.ThreadPoolFactory;
import com.example.whatrubbish.util.ThreadPoolManager;
import com.example.whatrubbish.util.ToastUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hrl.chaui.activity.SplashActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HEADER_VIEW = 0;
    final int i = 1;
    private static final int NORMAL_VIEW = 1;
    //   ArrayList<String> list = new ArrayList();
    private Context context;
    AppDatabase appDatabase;

    Activity activity;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    public void setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    List<String> namelist = new ArrayList<>();
    List<String> contentlist = new ArrayList<>();
    List<String> timelist = new ArrayList<>();
    List<Integer> touxianglist = new ArrayList<>();
    List<String> avatarList = new ArrayList<>();

    int flag = 0;
    RecyclerView recyclerView;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public MyAdapter(Context context, int flag) {
        //context.ru
        this.context = context;
        if (flag == 1)
            this.flag = flag;
        //init();

    }
//    public MyAdapter(Context context) {
//        this.context = context;
//
//
//        init();
//
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER_VIEW) {

            View headerView = LayoutInflater.from(context).inflate(R.layout.itemhead, parent, false);
            HeaderViewHolder Headerholder = new HeaderViewHolder(headerView);
            Headerholder.weidu = (TextView) headerView.findViewById(R.id.weidu);
            return Headerholder;
        }
        View normView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        NormalViewHolder holder = new NormalViewHolder(normView);
        holder.content = (TextView) normView.findViewById(R.id.textView4);
        holder.name = (TextView) normView.findViewById(R.id.name);
        holder.time = (TextView) normView.findViewById(R.id.time);
        holder.touxiang = (ImageView) normView.findViewById(R.id.imageView5);
        holder.mark = (ImageView) normView.findViewById(R.id.imageView6);
        holder.weiduback = (ImageView) normView.findViewById(R.id.weiduback);
        holder.weidunum = (TextView) normView.findViewById(R.id.weidunum);
        //未读的个数

        return holder;

    }

    public class NormalViewHolder extends RecyclerView.ViewHolder {
        public NormalViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Do whatever you want on clicking the normal items
                }
            });
        }

        private TextView name;
        private TextView content;
        private ImageView touxiang;
        private TextView time;
        private ImageView mark;
        private ImageView weiduback;
        private TextView weidunum;


    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Do whatever you want on clicking the item
                }
            });

        }

        private TextView weidu;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_VIEW;
        }

        return NORMAL_VIEW;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof NormalViewHolder) {
            NormalViewHolder holder = (NormalViewHolder) viewHolder;


            User friend = friends[position - 1];
            //这个类型的friend 也没事吧 反正只要都用这个就行？
            //friend
            Log.i("namelist", "onBindViewHolder: "+namelist);
            Log.i("position", "onBindViewHolder: position "+position);
            holder.name.setText(namelist.get(position - 1));
            //数组有一个 那么他是头 但是不是头啊
            holder.content.setText(contentlist.get(position - 1));
            holder.time.setText(timelist.get(position - 1));
            //holder.touxiang.setImageResource(touxianglist.get(position - 1));
            String avatarUrl = avatarList.get(position - 1);
            DrawUtil.loadImage(activity,avatarUrl, holder.touxiang);
            if (position == 1) {
                holder.mark.setImageResource(R.mipmap.mark19);//置顶
                if (flag == 0) {
                    holder.weidunum.setVisibility(View.VISIBLE);
                    holder.weiduback.setVisibility(View.VISIBLE);

                } else {
                    holder.content.setText("表情");
                    holder.weidunum.setVisibility(View.GONE);
                    holder.weiduback.setVisibility(View.GONE);
                }
                //第一个消息

            }
            holder.touxiang.setOnClickListener(v->{

                ThreadPoolFactory.getExecutorService().execute(()->{
                    //Chat chat=new Chat();
                    ////chat.setAvatar();
                    //chat.setUserId(Bus.curUser.getId()+"");
                    //try {
                    //    List<Chat> select = RoomUtil.select(appDatabase.chatDao(), chat);
                    //} catch (IllegalAccessException e) {
                    //    e.printStackTrace();
                    //}
                    //activity.runOnUiThread(()->{
                    //    ActivityUtil.startActivity(activity, SplashActivity.class);
                    //});
                    Bus.nowFriendId=friend.getId()+"";
                    Bus.curFriend=friend;
                    Log.i("Bus.nowFriendId", "onBindViewHolder: "+ Bus.nowFriendId);

                    //怎么传递呢 通过statis 吗 还是 budle ？ static一般都说不好？ 但是不就是eventBus 吗
                    ActivityUtil.startActivityOnUiThread(activity,SplashActivity.class);
                    //todo 需要传递 userid

                });

            });
            //holder.
        } else if (viewHolder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
            if (flag == 1) {
                //第二次回来是未读的
                headerViewHolder.weidu.setText("(未读0)");
            }
        }


    }

    User[] friends;

    public User[] getFriends() {
        return friends;
    }

    public void setFriends(User[] friends) {
        this.friends = friends;
    }

    void initFromHttp(){


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
                for (User user : friends) {
                    namelist.add(user.getNickname());
                    //还有一个上次的聊天
                    //contentlist.add(user.getNickname());
                    //timelist.add(user.getNickname());
                    contentlist.add("");
                    timelist.add("");
                    //touxianglist.add(user.getAvatarUrl());
                    avatarList.add(user.getAvatarUrl());
                    //我觉得放在一个接口反而不妥？
                    //有limit的话 可能limit 可以少的吧
                }
                //for (JsonElement jsonElement : asJsonArray) {
                //    JsonObject asJsonObject = jsonElement.getAsJsonObject();
                //    //asJsonArray.get("")
                //
                //    //JsonUtil.
                //    //City[] citiesArr = Bus.gson.fromJson(asJsonArray, City[].class);
                //}
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void init() {
        //ThreadPoolExecutor.
        //ThreadPoolManager.getInstance().execute(()->{
        //    JsonObject post = HttpUtil.post(Bus.baseDbUrl + "/friendship/listFriend", Bus.curUser);
        //});
        if(friends==null){
            Log.i("friends", "init: null");
            mockData();
            return;
        }

        for (User user : friends) {
            namelist.add(user.getNickname());
            //还有一个上次的聊天
            //contentlist.add(user.getNickname());
            //timelist.add(user.getNickname());
            contentlist.add("");
            timelist.add("");
            //touxianglist.add(user.getAvatarUrl());
            avatarList.add(user.getAvatarUrl());
            //我觉得放在一个接口反而不妥？
            //有limit的话 可能limit 可以少的吧
        }

        //ThreadPoolManager.run(()->{
        //
        //});

        //namelist.add("李薇薇");
        //namelist.add("邓佳美");
        //namelist.add("王一");
        //namelist.add("张航");
        //namelist.add("陈立飞");
        //contentlist.add("不过我刚刚在游戏中看到了你想要的。");
        //contentlist.add("我的排名快超过你啦！");
        //contentlist.add("好的，我知道了。");
        //contentlist.add("我觉得那个很好玩的。");
        //contentlist.add("我昨天收集到了想要的碎片。");
        //timelist.add("9:24");
        //timelist.add("8:35");
        //timelist.add("昨天21:56");
        //timelist.add("昨天16:30");
        //timelist.add("周一20:45");
        //touxianglist.add(R.mipmap.tou1);
        //touxianglist.add(R.mipmap.tou2);
        //touxianglist.add(R.mipmap.tou3);
        //touxianglist.add(R.mipmap.tou4);
        //touxianglist.add(R.mipmap.tou5);
    }

    void mockData(){
        namelist.add("李薇薇");
        namelist.add("邓佳美");
        namelist.add("王一");
        namelist.add("张航");
        namelist.add("陈立飞");
        contentlist.add("不过我刚刚在游戏中看到了你想要的。");
        contentlist.add("我的排名快超过你啦！");
        contentlist.add("好的，我知道了。");
        contentlist.add("我觉得那个很好玩的。");
        contentlist.add("我昨天收集到了想要的碎片。");
        timelist.add("9:24");
        timelist.add("8:35");
        timelist.add("昨天21:56");
        timelist.add("昨天16:30");
        timelist.add("周一20:45");
        touxianglist.add(R.mipmap.tou1);
        touxianglist.add(R.mipmap.tou2);
        touxianglist.add(R.mipmap.tou3);
        touxianglist.add(R.mipmap.tou4);
        touxianglist.add(R.mipmap.tou5);
    }

    @Override
    public int getItemCount() {

        if(friends==null){
            return 1;
        }
        //return 6;
        return friends.length+1;
    }
//        if (list == null) {
//            return 0;
//        }
//
//        if (list.size() == 0) {
//            //header count include
//            return 1;
//        }
//
//        //header count include
//        return list.size() + 1;
//    }
}