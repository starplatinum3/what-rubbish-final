package com.example.compx202_finalproject;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.whatrubbish.R;

public class SecondFragment extends Fragment {

    TextView next;
    TextView back;
    ViewPager viewPager;

    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * This function is used to make the page turning effect through viewPagee,
     * and make the text have the click function to turn page.
     * "button" is used to turn back the previous page and "next" is used to turn to the next page.
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
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        viewPager = getActivity().findViewById(R.id.viewPager);
        next = view.findViewById(R.id.slideTwoNext);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });

        back = view.findViewById(R.id.slideTwoBack);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });

        return view;
    }
}