package com.example.whatrubbish.util;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smlj.MyAdapter;
import com.example.whatrubbish.R;
import com.example.whatrubbish.gridIcons.DividerGridItemDecoration;

public class RecyclerViewUtil {
    @SuppressLint("WrongConstant")
    public  static void  init(RecyclerView recyclerView, Context context){
        //RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //设置布局管理器
        //recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ////设置adapter
        //MyAdapter myAdapter = new MyAdapter(context);
        //recyclerView.setAdapter(myAdapter);
        ////设置默认属性
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        //

        //recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(context );
////设置布局管理器
//        recyclerView.setLayoutManager(layoutManager);
////设置为垂直布局，这也是默认的
//        layoutManager.setOrientation(OrientationHelper. VERTICAL);
////设置Adapter
//        recyclerView.setAdapter(recycleAdapter);
//        //设置分隔线
//        recyclerView.addItemDecoration( new DividerGridItemDecoration(context ));
////设置增加或删除条目的动画
//        recyclerView.setItemAnimator( new DefaultItemAnimator());
    }
}
