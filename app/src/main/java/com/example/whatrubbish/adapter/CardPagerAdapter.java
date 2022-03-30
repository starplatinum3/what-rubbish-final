package com.example.whatrubbish.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.example.whatrubbish.entity.Card;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter {
    private ArrayList<View> viewLists;

    public CardPagerAdapter() {
    }
    //PagerAdapter 点击

    public CardPagerAdapter(ArrayList<View> viewLists) {
        super();
        this.viewLists = viewLists;
    }
    List<Card> cards;

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public int getCount() {
        return viewLists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public interface Listener {
        void onItemClick(int position);
    }

    Listener listener;

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = viewLists.get(position);
        view.setOnClickListener(v -> {

            if (!(listener ==null)) {
                listener.onItemClick(position);
            }

        });
        //https://blog.csdn.net/lk_123456/article/details/64469438
        //container.addView(viewLists.get(position));
        container.addView(view);
        return viewLists.get(position);
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewLists.get(position));
    }
}