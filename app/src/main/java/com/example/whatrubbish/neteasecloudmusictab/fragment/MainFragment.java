package com.example.whatrubbish.neteasecloudmusictab.fragment;//package davidneteasecloudmusictab.qq986945193.com.davidneteasecloudmusictab.fragment;

import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

//import davidneteasecloudmusictab.qq986945193.com.davidneteasecloudmusictab.R;
import com.example.whatrubbish.R;
/**
 * @author ：程序员小冰
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 * @CSDN博客: http://blog.csdn.net/qq_21376985
 */
public class MainFragment extends Fragment {
    private String title = "无数据填充";

    public MainFragment() {
    }

    public MainFragment(String title) {
        this.title = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_two, container, false);

        TextView viewById = (TextView) inflate.findViewById(R.id.tv);
        viewById.setText(title);


        return inflate;
    }
}
