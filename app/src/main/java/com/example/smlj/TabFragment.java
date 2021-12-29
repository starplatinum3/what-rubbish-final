package com.example.smlj;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatrubbish.R;


public class TabFragment extends Fragment {



    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }
    private  MyAdapter myAdapter;
    int layoutId;
    private int i;
    private Context context;
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
//
//    public TabFragment(int i,Context context) {
//       this.i=i;
//
//    }

//    public TabFragment(String str) {
//        Bundle b = new Bundle();
//        b.putString("key", str);
//        setArguments(b);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            view = inflater.inflate(R.layout.achievementfrag, container, false);

        return view;
    }



}
