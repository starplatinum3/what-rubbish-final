package com.example.whatrubbish.ui.regionInput;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.whatrubbish.Bus;
import com.example.whatrubbish.R;
import com.example.whatrubbish.RegisterActivity;
import com.example.whatrubbish.achievement.AchievementActivity;
import com.example.whatrubbish.achievement.AchievementActivitySelf;
import com.example.whatrubbish.databinding.FragmentRegionInputBinding;
import com.example.whatrubbish.databinding.FragmentRegionSettingSlideBinding;
import com.example.whatrubbish.db.AppDatabase;
import com.example.whatrubbish.db.Repository;
import com.example.whatrubbish.db.RoomUtil;
import com.example.whatrubbish.entity.City;
import com.example.whatrubbish.ui.home.HomeViewModel;
import com.example.whatrubbish.util.HttpUtil;
import com.example.whatrubbish.util.ThreadPoolManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

//Fragment implements View.OnClickListener
public class RegionInputFragment extends Fragment implements View.OnClickListener {

    private FragmentRegionInputBinding binding;
    FragmentActivity activity;
    String curQuery;


    public RegionInputFragment(List<City> cities) {
        this.cities = cities;
        //curSelPos
    }

    public RegionInputFragment() {
    }


    public String getCurQuery() {
        return curQuery;
    }

    public void setCurQuery(String curQuery) {
        this.curQuery = curQuery;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    //    SearchView searchView;
    private String[] mStrs = {"aaa", "bbb", "ccc", "airsaid"};
    private String[] candidateCities = {"aaa", "bbb", "ccc", "airsaid"};
    //    private SearchView mSearchView;
//    private ListView mListView;
//Candidate cities
    List<City> cities;
    private static final String TAG = "RegionInputFragment";
    AppDatabase database;

    ArrayAdapter<String> stringArrayAdapter;

    public int getCurSelPos() {
        return curSelPos;
    }

    public void setCurSelPos(int curSelPos) {
        this.curSelPos = curSelPos;
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRegionInputBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        database = AppDatabase.getDatabase(activity);

        activity = getActivity();

        //Log.d(TAG, "onCreateView: sendValue  "+sendValue);

        ThreadPoolManager.run(new Thread(() -> {
            try {
                //cities = RoomUtil.select(database.cityDao(), new City(),null,"limit 5");
                //JsonObject post = HttpUtil.post(Bus.baseDbUrl + Bus.cityList, new City());
                //JsonObject post = HttpUtil.post(Bus.baseDbUrl + Bus.cityFindByPicResNotNull, new City());
                ////JsonArray asJsonArray = post.getAsJsonArray();
                //////JsonArray asJsonArray = post.get(Bus.contentMark).getAsJsonArray();
                ////City[] citiesArr = Bus.gson.fromJson(asJsonArray, City[].class);
                //City[] citiesArr = Bus.gson.fromJson(post, City[].class);
                //cities=  Arrays.stream(citiesArr).collect(Collectors.toList());

                JsonObject post = HttpUtil.post(Bus.baseDbUrl + Bus.cityFindByPicResNotNull, new City());
                //JsonArray asJsonArray = post.getAsJsonArray();
                JsonArray asJsonArray = post.get(Bus.contentMark).getAsJsonArray();
                City[] citiesArr = Bus.gson.fromJson(asJsonArray, City[].class);

                //City[] citiesArr = Bus.gson.fromJson(post, City[].class);
                cities=  Arrays.stream(citiesArr).limit(5).collect(Collectors.toList());

                Log.d(TAG, "onCreateView: cities  "+cities);

                activity.runOnUiThread(()->{
                    setAdapter();
                });

            } catch ( Exception e) {
                e.printStackTrace();
                //IOException
            }

        }));

//        ThreadPoolManager.getInstance().execute(new FutureTask<>(new Thread(() -> {
//            try {
//                //cities = RoomUtil.select(database.cityDao(), new City());
//                cities = RoomUtil.select(database.cityDao(), new City(),null,"limit 5");
//
//                //他是 new 出来的
////        cities = repository.getCityRepository().selectBy(new City());
//                Log.i(TAG, "cities  " + cities);
//
////                    mSendValue.sendCities(cities);
////                这里没有运行啊
//                Log.i(TAG, "mSendValue.sendCities(cities); end");
//
//                candidateCities = cities.stream().map(City::getName).toArray(String[]::new);
//
//                stringArrayAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, candidateCities);
//                binding.listView.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, candidateCities));
////        mListView.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, mStrs));
//                binding.listView.setTextFilterEnabled(true);
//                binding.listView.setOnItemClickListener((parent, view, position, id) -> {
//
//                    view.setOnClickListener(v -> {
////                                Object_Item = your_Adapter.getItem(position);
//                        String clickCity = stringArrayAdapter.getItem(position);
//                        curSelPos=position;
//                        //if(sendValue!=null){
//                        //    sendValue.setOnCityClickListener(position);
//                        //
//                        //}
////                        https://blog.csdn.net/BUCTOJ/article/details/95955484
//                        binding.searchView.setQuery(clickCity,false);
//                    });
////————————————————
////                    版权声明：本文为CSDN博主「One-Direction」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
////                    原文链接：https://blog.csdn.net/BUCTOJ/article/details/95955484
//                });
//
//
////————————————————
////        版权声明：本文为CSDN博主「Airsaid」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
////        原文链接：https://blog.csdn.net/airsaid/article/details/51087226
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//
//        }), null), null);

        //binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        //    // 当点击搜索按钮时触发该方法
        //    @Override
        //    public boolean onQueryTextSubmit(String query) {
        //        return false;
        //    }
        //
        //    // 当搜索内容改变时触发该方法
        //    @Override
        //    public boolean onQueryTextChange(String newText) {
        //        curQuery = newText;
        //        if (!TextUtils.isEmpty(newText)) {
        //            binding.listView.setFilterText(newText);
        //        } else {
        //            binding.listView.clearTextFilter();
        //        }
        //        return false;
        //    }
        //});


        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void setAdapter(){

        //他是 new 出来的
//        cities = repository.getCityRepository().selectBy(new City());
        Log.i(TAG, "cities  " + cities);

//                    mSendValue.sendCities(cities);
//                这里没有运行啊
//        Log.i(TAG, "mSendValue.sendCities(cities); end");

        candidateCities = cities.stream().map(City::getName).toArray(String[]::new);

        Log.d(TAG, "setAdapter: candidateCities  "+ Arrays.toString(candidateCities));
        stringArrayAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, candidateCities);
        //stringArrayAdapter 设置了 但是 空的
        Log.d(TAG, "setAdapter: stringArrayAdapter  "+stringArrayAdapter);
        String item = stringArrayAdapter.getItem(0);
        Log.d(TAG, "setAdapter: item  "+item);
        //binding.listView.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, candidateCities));
        binding.listView.setAdapter(stringArrayAdapter);
//        mListView.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, mStrs));
        binding.listView.setTextFilterEnabled(true);
        binding.listView.setOnItemClickListener((parent, view, position, id) -> {

            view.setOnClickListener(v -> {
//                                Object_Item = your_Adapter.getItem(position);
                Log.d(TAG, "setAdapter: position  "+position);
                Log.d(TAG, "setAdapter: stringArrayAdapter "+stringArrayAdapter);
                String clickCity = stringArrayAdapter.getItem(position);
                curSelPos=position;
                //if(sendValue!=null){
                //    sendValue.setOnCityClickListener(position);
                //
                //}
//                        https://blog.csdn.net/BUCTOJ/article/details/95955484
                binding.searchView.setQuery(clickCity,false);
            });
//————————————————
//                    版权声明：本文为CSDN博主「One-Direction」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//                    原文链接：https://blog.csdn.net/BUCTOJ/article/details/95955484
        });


        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                curQuery = newText;
                if (!TextUtils.isEmpty(newText)) {
                    binding.listView.setFilterText(newText);
                } else {
                    binding.listView.clearTextFilter();
                }
                return false;
            }
        });


//————————————————
//        版权声明：本文为CSDN博主「Airsaid」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/airsaid/article/details/51087226
    }
    public SendValue getSendValue() {
        return sendValue;
    }

    public void setSendValue(SendValue sendValue) {
        Log.d(TAG, "setSendValue: "+sendValue);
        this.sendValue = sendValue;
    }

    SendValue sendValue;

    public interface SendValue {
        void setOnCityClickListener(int pos);

    }


    int notSelected=-1;

    int curSelPos=notSelected;

    void toTabLayoutTest() {
    }

    void toAchievementSelf() {
        Intent intent = new Intent(activity, AchievementActivitySelf.class);
        startActivity(intent);
    }


    public void toAchievement() {
        Intent intent = new Intent(activity, AchievementActivity.class);
        startActivity(intent);
    }

    public void toRegister() {
        Intent intent = new Intent(activity, RegisterActivity.class);
        startActivity(intent);
    }


    public void onViewClick() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
//        onclick 是没有 产生
        Log.i("view", view.getId() + "");
        Log.i("onClick", "onClick");
        switch (view.getId()) {
            case R.id.btnToAchievement:
                Log.i("btnToAchievement", "btnToAchievement");
                toAchievement();
                break;
        }
    }
}