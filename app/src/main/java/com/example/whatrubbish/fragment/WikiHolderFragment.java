package com.example.whatrubbish.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.ejlchina.okhttps.GsonMsgConvertor;
import com.ejlchina.okhttps.HTTP;
import com.example.whatrubbish.Bus;
import com.example.whatrubbish.R;
import com.example.whatrubbish.databinding.FragmentWikiBinding;
import com.example.whatrubbish.databinding.FragmentWikiHolderBinding;
import com.example.whatrubbish.db.Repository;
import com.example.whatrubbish.dbo.RubbishInfoDbo;
import com.example.whatrubbish.dto.RubbishInfoDto;
import com.example.whatrubbish.dto.TbAdmin;
import com.example.whatrubbish.entity.RubbishInfo;
import com.example.whatrubbish.entity.RubbishType;
import com.example.whatrubbish.gridIcons.GridIcons;
import com.example.whatrubbish.gridIcons.IconButton;
import com.example.whatrubbish.service.RubbishInfoService;
import com.example.whatrubbish.util.HttpUtil;
import com.example.whatrubbish.util.PropertiesUtil;
import com.example.whatrubbish.util.ThreadPoolManager;
import com.example.whatrubbish.util.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

import lombok.SneakyThrows;
//import okhttp3.FormBody;
//import okhttp3.FormBody;

//@EqualsAndHashCode(callSuper = true)
//@Data
//@Builder
public class WikiHolderFragment extends Fragment {

    FragmentWikiHolderBinding binding;

    FragmentActivity activity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //这个修改为对应子Fragment和父Fragment的布局文件
//        R.id
//        CircleProgressBar.
        activity = getActivity();

//        rubbishInfoLst.stream().
        binding = FragmentWikiHolderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        WikiFragment.SendValue sendValue= rubbishType -> getChildFragmentManager()
                .beginTransaction().
                replace(R.id.holder,new RubbishTypeFragment(rubbishType)).
                        addToBackStack(null).commit();

        Log.i("sendValue",""+sendValue);
        getChildFragmentManager().beginTransaction().
                replace(R.id.holder,new WikiFragment(sendValue)).addToBackStack(null).commit();

        return root;
    }



}
