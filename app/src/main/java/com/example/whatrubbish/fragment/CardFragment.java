package com.example.whatrubbish.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smlj.MyAdapter;
import com.example.whatrubbish.Bus;
import com.example.whatrubbish.adapter.FriendsAdapter;
import com.example.whatrubbish.databinding.FragmentFriendBinding;
import com.example.whatrubbish.databinding.ItemCardBinding;
import com.example.whatrubbish.entity.Card;
import com.example.whatrubbish.entity.User;
import com.example.whatrubbish.gridIcons.DividerGridItemDecoration;
import com.example.whatrubbish.util.DrawUtil;
import com.example.whatrubbish.util.HttpUtil;
import com.example.whatrubbish.util.ThreadPoolFactory;
import com.example.whatrubbish.util.ThreadPoolManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CardFragment extends Fragment {


    //FragmentFriendBinding binding;
    ItemCardBinding binding;
    Activity activity;

    int index;

    Card card;

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public interface Listener {
        void onCardClicked(int pos);

    }

    Listener listener;

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    boolean defaultStyle = true;

    public boolean isDefaultStyle() {
        return defaultStyle;
    }

    public void setDefaultStyle(boolean defaultStyle) {
        this.defaultStyle = defaultStyle;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        activity = getActivity();

        binding = ItemCardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        putStyle();
        //不知道是哪个啊
        //binding.
        root.setOnClickListener(v -> {
            if (listener == null) {
                return;
            }
            listener.onCardClicked(index);
        });

        return root;
    }

    void putStyle() {
        if (defaultStyle) {
            return;
        }
        binding.tvTitle.setText(card.getName());
        binding.tvDescrip.setText(card.getDescribe());
        //binding.tvContent.setImageResource(card.getName());
        DrawUtil.loadImageChoose(getActivity(), card.getImgUrl(), binding.tvContent);
    }


}
