package com.example.compx202_finalproject;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.whatrubbish.R;

public class FirstFragment extends Fragment {

    TextView next;
    ViewPager viewPager;

    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * This function is used to make the page turning effect through viewPagee,
     * and make the text have the click function to turn page.
     * *此功能用于通过viewPagee实现翻页效果，
     * *并使文本具有点击翻页功能。
     *
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //R.id
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        viewPager = getActivity().findViewById(R.id.viewPager);
        next = view.findViewById(R.id.slideOneNext);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               viewPager.setCurrentItem(1);
            }
        });

        return view;
    }
}