package com.example.compx202_finalproject;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.whatrubbish.R;

public class ThirdFragment extends Fragment {

    TextView done;
    TextView back;
    ViewPager viewPager;

    public ThirdFragment() {
        // Required empty public constructor
    }

    /**
     * This function is used to make the page turning effect through viewPage,
     * and make the text have the click function to turn bake to the previous page.
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
        View view = inflater.inflate(R.layout.fragment_third, container, false);

        viewPager = getActivity().findViewById(R.id.viewPager);
        done = view.findViewById(R.id.slideThreeDone);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        back = view.findViewById(R.id.slideThreeBack);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });

        return view;
    }
}