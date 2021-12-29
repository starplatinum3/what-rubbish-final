package com.example.whatrubbish.tab;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatrubbish.R;
import com.example.whatrubbish.gridIcons.GridIcons;
import com.example.whatrubbish.gridIcons.IconButton;

import java.util.ArrayList;
import java.util.List;

//import lombok.Data;

//import en.edu.zucc.pb.loginapplication.R;

//@Data
public class TabFragment extends Fragment {

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    int layoutId;

    @Override
    public void onResume() {
        super.onResume();
        initGridIcons();
    }

    @Override
    public void onStart() {
        super.onStart();
//        initGridIcons();
    }

    //    @Override
//    protected void onStart() {
//  super.onStart();
//    View view = this.findViewById(R.id.btnTest);
//   view.setOnClickListener(new android.view.View.OnClickListener(){
//        public void onClick(android.view.View v) {
//       //TODO...
//      }
//  });
//    }

    public TabFragment(String str) {
        Bundle b = new Bundle();
        b.putString("key", str);
        setArguments(b);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_tab, container, false);
//        这是没有 真的启动 tab 的时候 就会创建的
        View view = inflater.inflate(R.layout.fragment_pay, container, false);
        TextView textView = view.findViewById(R.id.text);
//        getArguments().getString("key");
        textView.setText(getArguments().getString("key"));
        textView.setBackgroundColor(Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
//        initGridIcons();

        return view;
    }

//    这个每次点击一个tab 就有的
//    但是不显示 只有一个显示 先是第一个显示 然后第二个
//    tab viewpager 动态显示
//    如果每个 pager 里面的东西都不一样 应该要多个 fragment
    void initGridIcons() {
        Log.i("initGridIcons()","initGridIcons");

        List<IconButton> iconButtons = new ArrayList<>();
//        IconButton
//        (Integer iconId, String text) {
        iconButtons.add(new IconButton(R.drawable.miku_fang, "miku"));
        iconButtons.add(new IconButton(R.drawable.miku_fang, "miku"));
        iconButtons.add(new IconButton(R.drawable.miku_fang, "miku"));
        iconButtons.add(new IconButton(R.drawable.miku_fang, "miku"));
        iconButtons.add(new IconButton(R.drawable.miku_fang, "miku"));

        FragmentActivity activity = getActivity();
//        GridIcons gridIcons = new GridIcons(this, iconButtons);
        GridIcons gridIcons = new GridIcons(activity, iconButtons);
//        gridIcons.setRecyclerViewId(R.id.rvGridIcons);
//        gridIcons.setContext();
        gridIcons.setItemId(R.layout.grid_icon_item);

//        gridIcons.set();
//        TextView tvShow = findViewById(R.id.tvShow);
//        RecyclerView rvGridIcons =findViewById(R.id.rvGridIcons);

//        TextView tvShow =activity. findViewById(R.id.tvShow);
        RecyclerView rvGridIcons = activity.findViewById(R.id.rvGridIcons);
//        找 fragment 里面的控件
//        https://blog.csdn.net/niuls/article/details/38488285
        Log.i("R.id.rvGridIcons", String.valueOf(R.id.rvGridIcons));
        Log.i("rvGridIcons", String.valueOf(rvGridIcons));

//        应该放在 fragment里面
//        gridIcons.setTvShow(tvShow);
        gridIcons.setRv(rvGridIcons);

//        设置点击了图标之后会显示文字的那个textview
        gridIcons.init();
    }

}
