package com.example.smlj.fragment;

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
import androidx.recyclerview.widget.RecyclerView;

import com.example.smlj.MyAdapter;
import com.example.whatrubbish.R;
import com.example.whatrubbish.databinding.MessagesBinding;

public class MessagesFragment extends Fragment {

    private MyAdapter myAdapter;
    MessagesBinding binding;
    Activity activity;
    static int flag=0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       activity=getActivity();

        binding = MessagesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initRecyclerView(activity);

//        while(true){
//            if(myAdapter.isend()){
//                //onCreate(null);
//
//                myAdapter.notifyDataSetChanged();
//                break;
//            }
//        }
        return root;

    }

    void initRecyclerView(Activity activity){

        RecyclerView mRecyclerView = binding.recyclerView;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));

//       myAdapter = new MyAdapter(activity);

       myAdapter = new MyAdapter(activity,flag);
        mRecyclerView.setAdapter(myAdapter);
        //设置默认属性

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        flag=1;

    }




}