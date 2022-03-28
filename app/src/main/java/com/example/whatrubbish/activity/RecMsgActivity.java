package com.example.whatrubbish.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.smlj.MyAdapter;
import com.example.whatrubbish.R;
import com.example.whatrubbish.adapter.RecMsgAdapter;
import com.example.whatrubbish.commonadapter.utils.CommonAdapter;
import com.example.whatrubbish.commonadapter.utils.CommonViewHolder;
import com.example.whatrubbish.databinding.ActivityRecMsgyBinding;
import com.example.whatrubbish.databinding.ActivityTestBinding;
import com.example.whatrubbish.db.App;
import com.example.whatrubbish.db.AppDatabase;
import com.example.whatrubbish.db.RoomUtil;
import com.example.whatrubbish.entity.RecMsg;
import com.example.whatrubbish.fragment.FriendFragment;
import com.example.whatrubbish.util.AdapterOfList;
import com.example.whatrubbish.util.ThreadPoolFactory;
import com.facebook.imagepipeline.memory.PoolFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.SneakyThrows;

public class RecMsgActivity extends AppCompatActivity {

    //private ActivityTestBinding binding;
    private ActivityRecMsgyBinding binding;
    RecMsgAdapter myAdapter;
    AppDatabase database;
    void initRecyclerView(Activity activity)  {

        RecyclerView mRecyclerView = binding.recyclerView;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        //RecyclerView view 点击 知道他的内容
//       myAdapter = new MyAdapter(activity);
        //List
        //ArrDa
        //AdapterOfList
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
         database= AppDatabase.getDatabase(this);
        //database.recMsgDao().
        //PoolFactory.
        ThreadPoolFactory.getExecutorService().execute(()->{
            try {
                List<RecMsg> recMsgs = RoomUtil.select(database.recMsgDao(), new RecMsg());
                myAdapter = new RecMsgAdapter(recMsgs);
                //myAdapter.setFriends(friends);
                myAdapter.setActivity(activity);
                //myAdapter.init();
                //ListAdapter

                //myAdapter.setRecyclerView(mRecyclerView);
                activity.runOnUiThread(()->{
                    mRecyclerView.setAdapter(myAdapter);
                });

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        //List<RecMsg> recMsgs = new ArrayList<>();
        //RecMsg recMsg = new RecMsg();
        //recMsg.setMsg("3131");
        //recMsg.setDate(new Date());
        //recMsgs.add(recMsg);

        //CommonAdapter<RecMsg> mAdapter = new CommonAdapter<RecMsg>(this,recMsgs, R.layout.item_rec_msg ) {
        //
        //    @Override
        //    public void setViewContent(CommonViewHolder viewHolder, RecMsg recMsg) {
        //
        //        //View convertView = viewHolder.getConvertView();
        //        //viewHolder.getView(R.id.tv_title);
        //        viewHolder.setText(R.id.tv_title,recMsg.getTitle());
        //        viewHolder.setText(R.id.tv_date,recMsg.getDate()+"");
        //        viewHolder.setText(R.id.tv_content,recMsg.getMsg());
        //    }
        //
        //    //@Override
        //    //public void convert(BaseViewHolder holder, int position) {
        //    //    holder.setText(R.id.tv_content,mDatas.get(position));
        //    //    //int number = new Random().nextInt(3);
        //    //    holder.setImageResource(R.id.iv_content,mImageRes[number]);
        //    //}
        //};


        //这个老了、、、
        //mRecyclerView.setAdapter(mAdapter);
        //mRecyclerView.setAdapter( (Adapter)mAdapter);

        //myAdapter = new MyAdapter(activity,flag);




    }

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecMsgyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //安卓 recycleView 列表
        initRecyclerView(this);
        //binding.recyclerView.
        //getSupportFragmentManager().beginTransaction().
        //        //replace(R.id.holder,new FriendFragment()).addToBackStack(null).commit();
        //                replace(R.id.holder,new FriendFragment()).commit();

    }
}
