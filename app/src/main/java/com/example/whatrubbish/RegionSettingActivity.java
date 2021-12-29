package com.example.whatrubbish;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.smlj.login;
import com.example.whatrubbish.dao.CityDao;
import com.example.whatrubbish.databinding.ActivityRegionSettingBinding;
import com.example.whatrubbish.databinding.ActivityRegisterBinding;
import com.example.whatrubbish.db.AppDatabase;
import com.example.whatrubbish.db.Repository;
import com.example.whatrubbish.db.RoomUtil;
import com.example.whatrubbish.entity.City;
import com.example.whatrubbish.fragment.MainFragment;
import com.example.whatrubbish.ui.regionInput.RegionInputFragment;
import com.example.whatrubbish.ui.regionSettingSlide.RegionSettingSlideFragment;
import com.example.whatrubbish.util.ActivityUtil;
import com.example.whatrubbish.util.HttpUtil;
import com.example.whatrubbish.util.ThreadPoolManager;
import com.example.whatrubbish.util.ToastUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

import lombok.SneakyThrows;


public class RegionSettingActivity extends FragmentActivity {

    private static final String TAG = "RegionSettingActivity";
    private ActivityRegionSettingBinding binding;

    Repository repository;
    AppDatabase database;

    void initDatabase() {
        repository = new Repository(this);
        database = AppDatabase.getDatabase(this);
    }

    int showingRegionSettingSlideFragment = 0;
    int showingRegionInputFragment = 1;
    int showingWho = showingRegionSettingSlideFragment;
    List<City> cities;
    SendValue mSendValue;
    Fragment childFragment;

    RegionInputFragment regionInputFragment;
    RegionSettingSlideFragment regionSettingSlideFragment;

    int notSelected = -1;
    int curPos = notSelected;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //ActivityUtil.startActivity(this,RegisterActivity.class);
        ActivityUtil.startActivity(this, login.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDatabase();
        binding = ActivityRegionSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        ThreadPoolManager.getInstance().execute(new FutureTask<>(new Thread(() -> {
            try {

                //cities = RoomUtil.select(database.cityDao(), new City(), null, "limit 5");
                //cities = RoomUtil.select(database.cityDao(), new City());
                //cities = new ArrayList<>();

                //JsonObject post = HttpUtil.post(Bus.baseDbUrl + Bus.cityList, new City());
                //JsonObject post = HttpUtil.post(Bus.baseDbUrl + Bus.cityFindByPicResNotNull, new City());
                ////Log.d(TAG, "onCreate: post  "+post);
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
                //cities=new ArrayList<>();

                Log.d(TAG, "onCreate:cities "+cities);
                //mockInsert();
                //cities.forEach(o->{
                //    database.cityDao().delete(o);
                //});
                //


                //mockCities();
                //database.cityDao().insertAll(cities.toArray(new City[0]));
                if(cities.size()==0){
                    mockCities();
                    cities.forEach(city->{
                        try {
                            HttpUtil.post(Bus.baseDbUrl+Bus.citySave,city);
                        }catch (IOException e){
                            e.printStackTrace();
                        }

                    });

                    //database.cityDao().insertAll(cities.toArray(new City[0]));
                }



                //regionSettingSlideFragment = new RegionSettingSlideFragment(cities);
                regionSettingSlideFragment = new RegionSettingSlideFragment();
                //Bundle bundle=new Bundle();
                //bundle.putString("one","要传的值");
                //bundle.putString("cities",Bus.gson.toJson(cities));
                //bundle.putString(citiesKey,Bus.gson.toJson(cities.toArray(new City[0])));
                //regionSettingSlideFragment.setArguments(bundle);
                //activity 给 fragment 传递数据 是null

                //regionSettingSlideFragment.setCities(cities);
                //regionInputFragment = new RegionInputFragment(cities);
                regionInputFragment = new RegionInputFragment();
                //regionInputFragment.setCities(cities);
                //regionInputFragment.setArguments(bundle);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }), null), null);


        binding.ivSearchPopUp.setOnClickListener(v -> {
            Log.i("ivSear", "ivSearchPopUp.setOnClickListener");

            if (showingWho == showingRegionSettingSlideFragment) {

//            https://www.jianshu.com/p/5761ee2d3ea1
                getSupportFragmentManager().beginTransaction().replace(R.id.RegionSettingSlideFragment, regionInputFragment, null).commit();
                //getSupportFragmentManager().beginTransaction().replace(R.id.RegionSettingSlideFragment, regionInputFragment, null).addToBackStack(null).commit();
                showingWho = showingRegionInputFragment;
            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.RegionSettingSlideFragment, regionSettingSlideFragment, null).commit();
                //getSupportFragmentManager().beginTransaction().
                //        replace(R.id.RegionSettingSlideFragment, regionSettingSlideFragment, null).addToBackStack(null).commit();

                showingWho = showingRegionSettingSlideFragment;
            }


        });

        childFragment = getSupportFragmentManager().findFragmentById(R.id.RegionSettingSlideFragment);
        binding.btnComplete.setOnClickListener(v -> {

            Log.i("btnComplete", "btnComplete");


            Fragment regionSettingSlideFragment = getSupportFragmentManager().findFragmentById(R.id.RegionSettingSlideFragment);

            City cityNow=null;
            if (regionSettingSlideFragment instanceof RegionSettingSlideFragment) {
                RegionSettingSlideFragment regionSettingSlideFragment1 = (RegionSettingSlideFragment) regionSettingSlideFragment;
                int i = regionSettingSlideFragment1.getmCurrentPosition();
                 cityNow = cities.get(i);
                Log.d(TAG, "onCreate: city1 "+cityNow);

            } else if (regionSettingSlideFragment instanceof RegionInputFragment) {
                RegionInputFragment regionInputFragment = (RegionInputFragment) regionSettingSlideFragment;
                int curSelPos = regionInputFragment.getCurSelPos();
                 cityNow = cities.get(curSelPos);
                Log.d(TAG, "onCreate: city1  "+cityNow);

            }


            if(cityNow==null){
                ToastUtil.show(this,"还没有选择城市");
                return;
            }

            if(Bus.curUser!=null){
                Bus.curUser.setCityId(cityNow.getId());
            }
            repository.getUserRepository().update(Bus.curUser);
            ThreadPoolManager.getInstance().execute(new FutureTask<>(new Thread(()->{
                try {
                    //JsonObject post = HttpUtil.post(Bus.baseDbUrl + "/user/save", Bus.curUser);
                    JsonObject post = HttpUtil.post(Bus.baseDbUrl + "/user/updateCity", Bus.curUser);
                    //int id = post.get("id").getAsInt();
                    //int msg = post.get("msg").getAsInt();

                }catch (Exception e){
                    e.printStackTrace();
                    ToastUtil.show(this,"选择城市失败 用户名 "+Bus.curUser.getUsername());
                }

            }),null),null);

            // TODO: 2021/12/20 保存这个用户的新鲜血液 。。 信息
            ActivityUtil.startActivity(this,MainActivity.class);

        });


        mSendValue = (SendValue) childFragment;

    }

   public static String  citiesKey="cities";

    void getCitiesData() {

        Thread thread = new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                super.run();


                cities = RoomUtil.select(database.cityDao(), new City());
                if(cities.size()==0){
                    mockCities();
                    database.cityDao().insertAll(cities.toArray(new City[0]));
                }

//        cities = repository.getCityRepository().selectBy(new City());
                Log.i(TAG, "cities  " + cities);

                try {
                    mSendValue.sendCities(cities);
//                这里没有运行啊
                    Log.i(TAG, "mSendValue.sendCities(cities); end");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
//————————————————
//        版权声明：本文为CSDN博主「慕容屠苏」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/kerryqpw/article/details/64129583
        ThreadPoolManager.getInstance().execute(new FutureTask<Object>(thread, null), null);


    }

    //void mockInsert4(){
    //    City.builder().name("江西").picRes(""+R.mipmap.jiangxi);
    //    City.builder().name("浙江").picRes(""+R.mipmap.jiangxi);
    //}

    void mockCities(){

        //Long id;
        //String name;
        //String picRes;
        //String describe;
        //Integer fragFullNeed;
        //这里 res pic 设置是 sring 但是 实际上是int
        //cities.add(new City(null,"浙江",R.mipmap.jiangxi+"","浙江",1));

        //cities.add(new City(null,"江西",R.mipmap.jiangxi+"","江西",1));
        //cities.add(new City(null,"北京",R.mipmap.beijing+"","北京",1));
        //cities.add(new City(null,"重庆",R.mipmap.chongqing14+"","重庆",1));
        //cities.add(new City(null,"上海",R.mipmap.shanghai12+"","上海",1));

        //cities.add(new City(null,"北京",R.mipmap.beijing+"","北京",1));
        //cities.add(new City(null,"上海",R.mipmap.shanghai12+"","上海",1));
        //cities.add(new City(null,"重庆",R.mipmap.chongqing14+"","重庆",1));
        //cities.add(new City(null,"浙江",R.mipmap.zhejiang11+"","浙江",1));

        cities.add(new City(1L,"北京",R.mipmap.beijing+"","北京",1));
        cities.add(new City(3L,"上海",R.mipmap.shanghai12+"","上海",1));
        cities.add(new City(4L,"重庆",R.mipmap.chongqing14+"","重庆",1));
    }

    void mockInsert() {
        City city = new City();
        city.setName("浙江");
//        city.setPicRes(R.mipmap.zhejiang11+"");
        List<City> cities = new ArrayList<>();
        cities.add(city);
        cities.add(city.clone());

        City[] cities1 = cities.toArray(new City[0]);
//        City[] cities1 = cities.toArray(new City[cities.size()]);
//        database.cityDao().insertAll(cities.toArray());
        database.cityDao().insertAll(cities1);
//        这两个数据库操作 还必须并在一起啊
        Log.i(TAG, "insert end");
//        database.cityDao().getViaQuery();
//        RoomUtil.
    }

//    @Override
//    public void mySend(String s) {
//
//    }

    public interface SendValue {
        //        void mySend(String s);
        void sendCities(List<City> cities);

        void onSendComplete(Repository repository);
    }

//    @Override
//    public void getCities(List<City> cities) {
//
//    }
}