//package com.example.whatrubbish.base;
//
//import android.graphics.Bitmap;
////import android.support.annotation.NonNull;
////import android.support.v7.widget.RecyclerView;
//import android.util.SparseArray;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
////import com.example.asus.detailsnew.utils.GlideApp;
//
///**
// * Created by asus on 2019/1/14.
// */
//public class BaseHolder extends RecyclerView.ViewHolder {
//    private View itemView;
//    private SparseArray<View> views;
//
//    public BaseHolder(@NonNull View itemView) {
//        super(itemView);
//        this.itemView = itemView;
//        views = new SparseArray<>();
//    }
//
//    public View getItemView() {
//        return itemView;
//    }
//
//    public <T extends View> View getViewId(int viewId) {
//        View view = views.get(viewId);
//        if (view == null) {
//            view = itemView.findViewById(viewId);
//            views.put(viewId, view);  //   控件id的int类型  控件view
//        }
//        return (T) view;
//    }
//
//    public static <T extends BaseHolder> T getHolder(int layoutId, ViewGroup parent) {
//        return (T) new BaseHolder(LayoutInflater.from(BaseApplication.getmContext()).inflate(layoutId, parent, false));
//    }
//
//    public BaseHolder setOnClickListener(int viewId, View.OnClickListener onClickListener) {
//        getViewId(viewId).setOnClickListener(onClickListener);
//        return this;
//    }
//
//    public BaseHolder setText(int viewId, String msg) {
//        ((TextView) getViewId(viewId)).setText(msg);
//        return this;
//    }
//
//    public BaseHolder setText(int viewId, int resId) {
//        ((TextView) getViewId(viewId)).setText(resId);
//        return this;
//    }
//
//    public BaseHolder setImageView(int ImageViewId, String url) {
//        GlideApp.with(BaseApplication.getmContext()).load(url).into((ImageView) getViewId(ImageViewId));
//        return this;
//    }
//
//    public BaseHolder setImageView(int imagViewId, int resId) {
//        ((ImageView) getViewId(imagViewId)).setImageResource(resId);
//        return this;
//    }
//
//
//    public BaseHolder setImageView(int imagViewId, Bitmap bitmap) {
//        ((ImageView) getViewId(imagViewId)).setImageBitmap(bitmap);
//        return this;
//    }
//}