package com.example.whatrubbish.card;

//import android.support.v4.view.PagerAdapter;
//import android.support.v7.widget.CardView;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.whatrubbish.Bus;
import com.example.whatrubbish.MainActivity;
import com.example.whatrubbish.R;
import com.example.whatrubbish.commonadapter.utils.CommonViewHolder;
import com.example.whatrubbish.fragment.MainFragment;
import com.example.whatrubbish.util.DateUtil;
import com.example.whatrubbish.util.DrawUtil;
import com.example.whatrubbish.util.GlideUtil;
import com.example.whatrubbish.util.HttpUtil;
import com.example.whatrubbish.util.ThreadPoolManager;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.FutureTask;

import lombok.Data;

//@Data
//, MainFragment.MySendValue
public class CardPagerAdapter extends PagerAdapter implements CardAdapter{
    //  Adapter ViewHoder
    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;

    public CardPagerAdapter() {
//        viewHolder=new ViewHolder();
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(CardItem item) {
        mViews.add(null);
        //View view = LayoutInflater.from(container.getContext())
        //        .inflate(R.layout.card_adapter, container, false);
        mData.add(item);
    }

    public MySendValue getMySendValue() {
        return mySendValue;
    }

    public void setMySendValue(MySendValue mySendValue) {
        this.mySendValue = mySendValue;
    }

    ViewHolder viewHolder;

    @Override
    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    MySendValue mySendValue;

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.card_adapter, container, false);
//       cards_adapter
//        R.
        //得到一个ViewHolder
//        CommonViewHolder viewHolder = CommonViewHolder.get(container.getContext(), convertView, container, R.layout.card_adapter, position);

        //Context context = view.getContext();
        //context.run
        //view.getAccessibilityClassName()
        //container.getChildAt()

        //Log.i("position", "" + position);
        //这个会来两次
        container.addView(view);
        //bind(mData.get(position),  getCardViewAt(position));
        //bind(mData.get(position), view);
        CardItem cardItem = mData.get(position);
        bindNotHolder(cardItem,view);

        //Log.i("cardItem", "" + cardItem);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        //当 adapter 里面的东西收到 点击了 activity 反应
        cardView.setOnClickListener(v->{
            if(mySendValue==null) {
                return;
            }
//            传送了 url 让他去 显示
//            去显示数据 爬虫吗
            mySendValue.  onCardViewClicked( cardItem.getUrl());
            //MySendValue
            Log.d("Bus.curUser", "instantiateItem: "+Bus.curUser);

            //ThreadPoolManager.getInstance().execute(new FutureTask<>(new Thread(()->{
            //    //给他积分
            //    Bus.curUser.addPoint(1);
            //    try {
            //        Log.d("Bus.curUser", "setAdapter: "+Bus.curUser);
            //        JsonObject post = HttpUtil.post(Bus.baseDbUrl + Bus.userSave, Bus.curUser);
            //        Log.d("post", "setAdapter: "+post);
            //    } catch (IOException e) {
            //        e.printStackTrace();
            //    }
            //}),null),null);
        });
        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        Log.i("mViews", "" + mViews);
        return view;
    }

    //    https://blog.csdn.net/chenzheng8975/article/details/76976122
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bindNotHolder(CardItem item, View view) {
        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        TextView contentTextView = (TextView) view.findViewById(R.id.contentTextView);
        TextView tvTime = (TextView) view.findViewById(R.id.tvTime);
        ImageView headImg = view.findViewById(R.id.headImg);
        String title = item.getTitle();
        titleTextView.setText(title);

        String description = item.getDescription();
        //可以直接set Str 所以没事吧
        contentTextView.setText(description);

        Date time = item.getTime();
        String dateStr = DateUtil.dateToString(time, DateUtil.ymdZh);
        //viewHolder. tvTime .setText(dateStr);
        if (time != null) {
            //viewHolder. tvTime .setText(time);
            tvTime.setText(dateStr);

        }
        String picUrl = item.getPicUrl();


        //DrawUtil
        //DrawUtil.loadImage(view.getContext(), picUrl, headImg);
        //() -> onCardViewClicked
        //GlideUtil.glideWithPlaceHolder(view.getContext(),picUrl).into(headImg);
        GlideUtil.drawTopTwoRound(view.getContext(),picUrl).into(headImg);
    }

    public interface MySendValue {
        //        void mySend(String s);
        void onCardViewClicked(String  url);
    }



    //因为我之前改成了用 viewHolder ，但是这个CardPagerAdapter里面是有多个数据的
    //所以他不能一个holder hold 住，所以还是回归之前那样，每次都find出当前的页面
    private void bind(CardItem item, View view) {


        //不能holder 因为不一样
        Log.i("item bind", item + "");
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            viewHolder.titleTextView = (TextView) view.findViewById(R.id.titleTextView);
            viewHolder.contentTextView = (TextView) view.findViewById(R.id.contentTextView);
            viewHolder.tvTime = (TextView) view.findViewById(R.id.tvTime);
            viewHolder.headImg = view.findViewById(R.id.headImg);
        }
        //int titleRes = item.getTitle();
        String title = item.getTitle();
        viewHolder.titleTextView.setText(title);
        //if(titleRes!=0){
        //    viewHolder.titleTextView .setText(titleRes);
        //}
//        viewHolder.titleTextView .setText(item.getTitle());
//        viewHolder.titleTextView.setText();
//        int textRes = item.getText();
        String description = item.getDescription();
        //可以直接set Str 所以没事吧
        viewHolder.contentTextView.setText(description);
        //if(textRes!=0){
        //    //viewHolder. contentTextView .setText(textRes);
        //    viewHolder. contentTextView .setText(description);
        //
        //}
        Date time = item.getTime();
        String dateStr = DateUtil.dateToString(time, DateUtil.ymdZh);
        //viewHolder. tvTime .setText(dateStr);
        if (time != null) {
            //viewHolder. tvTime .setText(time);
            viewHolder.tvTime.setText(dateStr);

        }
        //int headImgRes = item.getHeadImgRes();
        String picUrl = item.getPicUrl();
        //if(headImgRes!=0){
        //    viewHolder. headImg.setImageResource(headImgRes);
        //}

        DrawUtil.loadImage(view.getContext(), picUrl, viewHolder.headImg);

//        titleTextView.setText(item.getTitle());
//        contentTextView.setText(item.getText());
    }

    //@Override
    //public void onCardViewClicked(String url) {
    //
    //}
//
//    private void bind(CardItem item, View view) {
////        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
////        TextView contentTextView = (TextView) view.findViewById(R.id.contentTextView);
////        TextView tvTime = (TextView) view.findViewById(R.id.tvTime);
////       ImageView headImg=  view.findViewById(R.id.headImg);
//
//        if(viewHolder==null){
//            viewHolder=new ViewHolder();
//            viewHolder.titleTextView = (TextView) view.findViewById(R.id.titleTextView);
//            viewHolder. contentTextView = (TextView) view.findViewById(R.id.contentTextView);
//            viewHolder. tvTime = (TextView) view.findViewById(R.id.tvTime);
//            viewHolder. headImg=  view.findViewById(R.id.headImg);
//        }
//        int titleRes = item.getTitle();
//        if(titleRes!=0){
//            viewHolder.titleTextView .setText(titleRes);
//        }
////        viewHolder.titleTextView .setText(item.getTitle());
////        viewHolder.titleTextView.setText();
//        int textRes = item.getText();
//        //可以直接set Str 所以没事吧
//        if(textRes!=0){
//            viewHolder. contentTextView .setText(textRes);
//
//        }
//        String time = item.getTime();
//        if(time!=null){
//            viewHolder. tvTime .setText(time);
//
//        }
//        int headImgRes = item.getHeadImgRes();
//        if(headImgRes!=0){
//            viewHolder. headImg.setImageResource(headImgRes);
//        }
//
////        titleTextView.setText(item.getTitle());
////        contentTextView.setText(item.getText());
//    }
//

    private class ViewHolder {
        TextView titleTextView;
        TextView contentTextView;
        TextView tvTime;
        ImageView headImg;
    }

}
