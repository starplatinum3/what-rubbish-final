package com.sdust.im.fragment;

import com.example.whatrubbish.R;
import com.example.whatrubbish.databinding.FragmentHomeBinding;
import com.example.whatrubbish.databinding.FragmentUserinfoBinding;
import com.example.whatrubbish.util.ActivityUtil;
import com.sdust.im.activity.LoginActivity;
import com.sdust.im.view.TitleBarView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
//import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class UserInfoFragment extends Fragment {
	private Context mContext;
	private View mBaseView;
	private TitleBarView mTitleBarView;
	FragmentUserinfoBinding binding;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		binding = FragmentUserinfoBinding.inflate(inflater, container, false);
		mBaseView = binding.getRoot();

		mContext = getActivity();

		binding.toLogin.setOnClickListener(v->{
			ActivityUtil.startActivity(getActivity(), LoginActivity.class);
		});
		//mBaseView = inflater.inflate(R.layout.fragment_userinfo, null);
		findView();
		init();
		return mBaseView;
	}
	private void findView(){
		mTitleBarView=(TitleBarView) mBaseView.findViewById(R.id.title_bar);
	}
	
	private void init(){
		mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE);
		mTitleBarView.setTitleText("个人信息");
	}
}

