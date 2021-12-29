package com.example.smlj;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.whatrubbish.R;

public class geren  extends Fragment {
    View view;

    @Override
    public void onResume() {
        super.onResume();
        //  initGridIcons();
    }

    @Override
    public void onStart() {
        super.onStart();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.gerenfragment, container, false);

        return view;
    }
}
