//package com.example.whatrubbish.base;
//
//import com.example.asus.detailsnew.base.BaseAdapter;
//import com.example.asus.detailsnew.base.BaseHolder;
//
//import java.util.List;
//
///**
// * Created by asus on 2019/1/14.
// */
//public abstract class MutiLayoutAdapter<T> extends BaseAdapter<T> {
//    public MutiLayoutAdapter(List<T> datas, int[] layoutIds) {
//        super(datas, layoutIds);
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return getItemType(position);
//    }
//
//    protected abstract int getItemType(int position);  //区分多个item的
//
//    @Override
//    protected void onBinDatas(BaseHolder holder, T t, int position) {
//        onBind(holder,t,position,getItemType(position));
//    }
//
//    protected abstract void onBind(BaseHolder holder, T t, int position,int itemType);
//}