package com.example.whatrubbish.ui.regionSettingSlide;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.whatrubbish.Bus;
import com.example.whatrubbish.LoginActivity;
import com.example.whatrubbish.R;
import com.example.whatrubbish.RegionSettingActivity;
import com.example.whatrubbish.RegisterActivity;
import com.example.whatrubbish.achievement.AchievementActivity;
import com.example.whatrubbish.achievement.AchievementActivitySelf;
import com.example.whatrubbish.databinding.FragmentHomeBinding;
import com.example.whatrubbish.databinding.FragmentRegionSettingSlideBinding;
import com.example.whatrubbish.db.AppDatabase;
import com.example.whatrubbish.db.Repository;
import com.example.whatrubbish.db.RoomUtil;
import com.example.whatrubbish.entity.City;
import com.example.whatrubbish.entity.User;
import com.example.whatrubbish.fragment.ImageFragment;
import com.example.whatrubbish.fragment.TextFragment;
import com.example.whatrubbish.neteasecloudmusictab.fragment.AchievementFragment;
import com.example.whatrubbish.tab.Tab;
import com.example.whatrubbish.ui.home.HomeViewModel;
import com.example.whatrubbish.ui.regionInput.RegionInputFragment;
import com.example.whatrubbish.util.ActivityUtil;
import com.example.whatrubbish.util.AdapterOfList;
import com.example.whatrubbish.util.HttpUtil;
import com.example.whatrubbish.util.ThreadPoolManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.roughike.swipeselector.SwipeItem;
import com.roughike.swipeselector.SwipeSelector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

import butterknife.OnClick;
import lombok.Data;
import lombok.SneakyThrows;

//@Data
//Fragment implements View.OnClickListener
public class RegionSettingSlideFragment extends Fragment implements View.OnClickListener, RegionSettingActivity.SendValue {
    public List<City> getCities() {
        return cities;
    }

    private static final String TAG = "RegionSettingSlideFragment";

    public void setCities(List<City> cities) {
        this.cities = cities;
    }


    public int getmCurrentPosition() {
        return mCurrentPosition;
    }

    public void setmCurrentPosition(int mCurrentPosition) {
        this.mCurrentPosition = mCurrentPosition;
    }

    public RegionSettingSlideFragment() {
    }

    //onac
    public RegionSettingSlideFragment(List<City> cities) {
        this.cities = cities;
    }

    private FragmentRegionSettingSlideBinding binding;
    FragmentActivity activity;

    private MyAdapter adapter;


    List<City> cities = new ArrayList<>();
    private List<Fragment> mFragment = new ArrayList<>();
    private List<Fragment> mImgFragments = new ArrayList<>();
    private List<String> mTitle = new ArrayList<>();

    @SuppressLint("LongLogTag")
    @Override
    public void sendCities(List<City> cities) {
        Log.i(TAG, "cities  " + cities);
        this.cities = cities;
        initData();
    }

    @Override
    public void onSendComplete(Repository repository) {

        try {

            List<User> users = repository.getUserRepository().selectBy(Bus.curUser);
            if (users.size() == 0) {
                Toast.makeText(activity, "没有已经登录的用户", Toast.LENGTH_SHORT).show();
                return;
            }

            ActivityUtil.startActivity(activity, LoginActivity.class);

        } catch (Exception e) {
            Toast.makeText(activity, "没有找到这个城市的用户", Toast.LENGTH_SHORT).show();
        }
    }


    //创建Fragment的适配器
//    可是这里的 列表引用的就是这个啊
    class MyAdapter extends FragmentPagerAdapter {
        //        Adapter 都没有反应
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        //获得每个页面的下标
        @Override
        public Fragment getItem(int position) {
            return mFragment.get(position);
        }

        //获得List的大小
        @Override
        public int getCount() {
            return mFragment.size();
        }

        //获取title的下标
        @Override
        public CharSequence getPageTitle(int position) {
            Log.i("getPageTitle", "position " + position);
            return mTitle.get(position);
        }
    }

    public interface MySendValue {
        //        void mySend(String s);
        void getCities(List<City> cities);
    }

    void mockData() {

        mTitle.add("首页");
        mTitle.add("中心");
        //fragment集合

//        ImageFragment.builder().imgId(R.drawable.miku_fang).build();
        ImageFragment imageFragment = new ImageFragment();
//        imageFragment.setImgId(R.drawable.beijing);
//        java.lang.RuntimeException: Canvas: trying to draw too large(384356680bytes) bitmap.

        imageFragment.setImgId(R.mipmap.beijing);
//        mFragment.add(ImageFragment.builder().imgId(R.drawable.miku_fang).build());

        ImageFragment imageFragment2 = new ImageFragment();
        imageFragment2.setImgId(R.mipmap.beijing);
//        这里图片 要从数据库拿到了 id

        mFragment.add(new TextFragment("北京"));
        mFragment.add(new TextFragment("浙江"));
//        mFragment.add(imageFragment);
//        mFragment.add(imageFragment2);
//        mFragment.add(new FragmentDiscoverSonB());
        //在activity中使用 getSupportFragmentManager(),这里是Fragment中使用如下方法

        mImgFragments.add(imageFragment);
        mImgFragments.add(imageFragment2);

    }

    int mCurrentPosition = 0;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        initData();
//        initData();
//        if(adapter==null){
//            initAdapter();
//        }
//        setCitiesFragments();

//        这运行了 好多次啊
    }

    @Override
    public void onStart() {
        super.onStart();
//        initData();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void delNotOkLst() throws IllegalAccessException {
        cities = RoomUtil.select(database.cityDao(), new City());
        Log.i("cities", "origin  " + cities + "  ");
        cities.forEach(o -> {
            Log.i("del it ", o + "  ");
            City city = new City();
            city.setId(o.getId());
            //repository.getCityRepository().delete(City.builder().id(o.getId()).build());
            repository.getCityRepository().delete(city);
        });
        Log.i("delete", "all");
        //c
        cities = new ArrayList<>();
        mockCities();
        Log.i("mock after cities", "  " + cities + "  ");
        repository.getCityRepository().insertAll(cities.toArray(new City[0]));
        cities = RoomUtil.select(database.cityDao(), new City(), null, "limit 5");
    }

    public SendValue getSendValue() {
        return sendValue;
    }

    public void setSendValue(SendValue sendValue) {
        Log.d("set", "setSendValue: ");
        Log.d("set", sendValue + "");
        this.sendValue = sendValue;
    }

    SendValue sendValue;

    public interface SendValue {
        //void onCityClickListener(int pos);
        void onPosChange(int pos);

    }

    public interface Listener {
        //void onCityClickListener(int pos);
        void onPosChange(int pos);

    }

    //void getCitiesDataFromActivity() {
    void setCitiesFragments() {


        addList();

        if (adapter == null) {
            initAdapter();
        }


    }

    void getCitiesData() {

        Thread thread = new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("LongLogTag")
            @SneakyThrows
            @Override
            public void run() {
                super.run();


                try {
                    //cities = RoomUtil.select(database.cityDao(), new City(), null, "limit 5");
                    //JsonObject post = HttpUtil.post(Bus.baseDbUrl + Bus.cityList, new City());
                    JsonObject post = HttpUtil.post(Bus.baseDbUrl + Bus.cityFindByPicResNotNull, new City());
                    //JsonArray asJsonArray = post.getAsJsonArray();
                    JsonArray asJsonArray = post.get(Bus.contentMark).getAsJsonArray();
                    City[] citiesArr = Bus.gson.fromJson(asJsonArray, City[].class);

                    //City[] citiesArr = Bus.gson.fromJson(post, City[].class);
                    cities=  Arrays.stream(citiesArr).limit(5).collect(Collectors.toList());
                    //setAdapter();

                    Log.i(TAG, "cities  " + cities);

                    Log.i(TAG, "mSendValue.sendCities(cities); end");
                    addList();
                    activity.runOnUiThread(()->{
                        initAdapter();
                    });

                    //if (adapter == null) {
                    //    initAdapter();
                    //}

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


    void addList() {
        Log.d("cities", "addList: " + cities);
        mTitle=new ArrayList<>();
        mFragment=new ArrayList<>();
        mImgFragments=new ArrayList<>();
        if (cities != null) {
            int limit = 5;
            int i = 0;
            for (City city : cities) {

                String picRes = city.getPicRes();
                if(picRes==null){
                    continue;
                }
                String name = city.getName();
                mTitle.add(name);
                ImageFragment imageFragment = new ImageFragment();

                //String picRes = city.getPicRes();
                int picResInt = Integer.parseInt(picRes);
                imageFragment.setImgId(picResInt);
                mFragment.add(new TextFragment(name));
                mImgFragments.add(imageFragment);
                i++;
                if (i >= limit) {
                    break;
                }
            }

        }
        Log.i("mTitle", "" + mTitle);
    }

    void mockCities() {

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


        cities.add(new City(null, "北京", R.mipmap.beijing + "", "北京", 1));
        cities.add(new City(null, "上海", R.mipmap.shanghai12 + "", "上海", 1));
        cities.add(new City(null, "重庆", R.mipmap.chongqing14 + "", "重庆", 1));
        //cities.add(new City(null, "浙江", R.mipmap.zhejiang11 + "", "浙江", 1));
    }

    Repository repository;

    void initDatabase() {
        repository = new Repository(getActivity());
    }

    @SuppressLint("LongLogTag")
    void initData() {
        getCitiesData();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    AppDatabase database;


    private Handler handler = new Handler(Looper.getMainLooper());
    AdapterOfList adapterOfListImg;

//    @SuppressLint("LongLogTag")
//    void initAdapter() {
//
//
//        FragmentManager childFragmentManager = getChildFragmentManager();
//        if (adapter == null) {
//            adapter = new MyAdapter(childFragmentManager);
//        }
//
//
////        'getFragmentManager()' is deprecated
//        Log.i("setAdapter", "setAdapter");
//        activity.runOnUiThread(() -> {
////                W/System.err: java.lang.IllegalStateException: Must be called from main thread of fragment host
////
//            binding.mViewPager.setAdapter(adapter);
//
//        });
////        adapter 数据变了 怎么不显示
//
//        if (adapterOfListImg == null) {
//            //        AdapterOfList adapterOfList = new AdapterOfList(childFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//            adapterOfListImg = new AdapterOfList(childFragmentManager,
//                    FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mImgFragments, mTitle);
//            activity.runOnUiThread(() -> {
//                binding.viewPagerImage.setAdapter(adapterOfListImg);
//
//            });
////        adapterOfListImg.notifyDataSetChanged();
//        }
//
//        adapter.notifyDataSetChanged();
//        adapterOfListImg.notifyDataSetChanged();
//        Log.i(TAG, "MyThread  setAdapter end");
//    }

    @SuppressLint("LongLogTag")
    void initAdapter() {


        FragmentManager childFragmentManager = getChildFragmentManager();
        //if (adapter == null) {
        //    adapter = new MyAdapter(childFragmentManager);
        //}
        //这里不要判断 是不是null 直接null 每次都set新的 adapter ，这样子简单粗暴 不会出错。。
        adapter = new MyAdapter(childFragmentManager);

//        'getFragmentManager()' is deprecated
        Log.i("setAdapter", "setAdapter");
        binding.mViewPager.setAdapter(adapter);
//        activity.runOnUiThread(() -> {
////                W/System.err: java.lang.IllegalStateException: Must be called from main thread of fragment host
////
//            binding.mViewPager.setAdapter(adapter);
//
//        });
//        adapter 数据变了 怎么不显示
//        if (adapterOfListImg == null) {
//            adapterOfListImg = new AdapterOfList(childFragmentManager,
//                    FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mImgFragments, mTitle);
//        }
        adapterOfListImg = new AdapterOfList(childFragmentManager,
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mImgFragments, mTitle);
        binding.viewPagerImage.setAdapter(adapterOfListImg);

//        if (adapterOfListImg == null) {
//            //        AdapterOfList adapterOfList = new AdapterOfList(childFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//            adapterOfListImg = new AdapterOfList(childFragmentManager,
//                    FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mImgFragments, mTitle);
//            binding.viewPagerImage.setAdapter(adapterOfListImg);
//            //activity.runOnUiThread(() -> {
//            //    binding.viewPagerImage.setAdapter(adapterOfListImg);
//            //
//            //});
////        adapterOfListImg.notifyDataSetChanged();
//        }

        //adapter.notifyDataSetChanged();
        //adapterOfListImg.notifyDataSetChanged();
        Log.i(TAG, "MyThread  setAdapter end");
    }


    //    https://blog.csdn.net/u014738387/article/details/50494066
    //    onActivityCreated(){
//
//    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();

        binding = FragmentRegionSettingSlideBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        database = AppDatabase.getDatabase(activity);
//        initAdapter();
        initDatabase();
        //Bundle bundle=getArguments();
        //Log.d("bundle", "onCreateView: "+bundle);
        //Log.d("savedInstanceState", "onCreateView: "+savedInstanceState);
        //String string=bundle.getString(RegionSettingActivity.citiesKey);
        ////String string = savedInstanceState.getString(RegionSettingActivity.citiesKey);
        //City[] citiesArr = Bus.gson.fromJson(string, City[].class);
        //cities = Arrays.stream(citiesArr).collect(Collectors.toList());
        //Log.d("cities", "onCreateView: "+cities);
        Log.i("onCreateView", "onCreateView RegionSettingSlideFragment");
        //Log.d("sendValue", "onCreateView: sendValue  "+sendValue);

        initData();
        //setCitiesFragments();
        setListener();

        return root;
    }

    void setListener() {

        Log.i("setListener", "setListener");
//        binding.  mViewPager.get
        binding.leftButton.setOnClickListener(v -> {
//            mCurrentPosition++;
            if (mCurrentPosition <= 0) {
                return;
            }
            mCurrentPosition--;
            //Fragment fragment = mFragment.get(mCurrentPosition);
            binding.mViewPager.setCurrentItem(mCurrentPosition, true);
            binding.viewPagerImage.setCurrentItem(mCurrentPosition, true);
            Log.d("onCreateView", "onCreateView: " + sendValue);

        });

//        我一旦 点击了 打开一个新的 fragment 他的数据就没了
//        fragment 的数据保存
        binding.rightButton.setOnClickListener(v -> {
//            if(mCurrentPosition>mFragment.size())return;
            if (mCurrentPosition >= mFragment.size() - 1) {
                return;
            }
            mCurrentPosition++;
            binding.mViewPager.setCurrentItem(mCurrentPosition, true);
            binding.viewPagerImage.setCurrentItem(mCurrentPosition, true);

        });
    }


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