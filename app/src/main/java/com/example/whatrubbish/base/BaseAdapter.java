//package com.example.whatrubbish.base;
//
////import android.support.annotation.NonNull;
////import android.support.v7.widget.RecyclerView;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
///**
// * Created by asus on 2019/1/14.
// */
//public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseHolder> {
//    private List<T> datas;
//    private int [] layoutIds;
//
//    //多种item
//    public BaseAdapter(List<T> datas, int[] layoutIds) {
//        this.datas = datas;
//        this.layoutIds = layoutIds;
//    }
//
//    //单个item
//    public BaseAdapter(List<T> datas,int layoutId){
//        this.datas=datas;
//        this.layoutIds=new int[]{layoutId};
//    }
//
//    @NonNull
//    @Override
//    public BaseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        return BaseHolder.getHolder(layoutIds[i],viewGroup);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
//                onBinDatas(holder,datas.get(position),position);
//    }
//
//    protected abstract void onBinDatas(BaseHolder holder, T t, int position);
//
//    @Override
//    public int getItemCount() {
//        return datas==null?0:datas.size();
//    }
//}