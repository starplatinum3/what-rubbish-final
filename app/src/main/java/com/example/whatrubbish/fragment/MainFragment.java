package com.example.whatrubbish.fragment;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.whatrubbish.Bus;
import com.example.whatrubbish.LoginActivity;
import com.example.whatrubbish.R;
import com.example.whatrubbish.activity.TestActivity;
import com.example.whatrubbish.card.CardItem;
import com.example.whatrubbish.card.CardPagerAdapter;
import com.example.whatrubbish.card.ShadowTransformer;

import com.example.whatrubbish.databinding.FragmentHolderBinding;
import com.example.whatrubbish.databinding.FragmentMainBinding;
import com.example.whatrubbish.db.BaseDao;
import com.example.whatrubbish.db.Repository;
import com.example.whatrubbish.dbo.RubbishInfoDbo;
import com.example.whatrubbish.dto.RubClsfyNewsDto;
import com.example.whatrubbish.dto.RubbishInfoDto;
import com.example.whatrubbish.entity.City;
import com.example.whatrubbish.entity.RubClsfyNews;
import com.example.whatrubbish.entity.RubbishInfo;
import com.example.whatrubbish.jo.RubClsfyNewsJo;
import com.example.whatrubbish.service.RubbishInfoService;
import com.example.whatrubbish.ui.regionSettingSlide.RegionSettingSlideFragment;
import com.example.whatrubbish.util.ActivityUtil;
import com.example.whatrubbish.util.DateUtil;
import com.example.whatrubbish.util.DpPxSpTool;
import com.example.whatrubbish.util.GlideUtil;
import com.example.whatrubbish.util.HttpUtil;
import com.example.whatrubbish.util.PropertiesUtil;
import com.example.whatrubbish.util.ThreadPoolManager;
import com.example.whatrubbish.util.TimeUtil;
import com.example.whatrubbish.util.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

import okhttp3.HttpUrl;

//@EqualsAndHashCode(callSuper = true)
//@Data
//@Builder
public class MainFragment extends Fragment implements CardPagerAdapter.MySendValue {

    FragmentMainBinding binding;
    //FragmentHolderBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    Repository repository;

    List<CardItem> cardItems;


    @RequiresApi(api = Build.VERSION_CODES.N)
    void getNewsFromTianApi() {
//这样子 多余了 应该后台直接请求 但是现在写好了 也不想去优化
        String keyRubbish = PropertiesUtil.getProperty(activity, Bus.keyRubbish);
        String res = HttpUtil.getHttp(Bus.tianApiNewsUrl).sync("").
                addPathPara(Bus.NEWS_NUM_TOKEN, 10).
                addPathPara(Bus.APIKEY_TOKEN, keyRubbish).get().getBody().toString();
        Log.i("res", res + "");
        JsonObject jsonObject = HttpUtil.strToJson(res);

        Log.i("jsonObject", jsonObject + "");
        int code = jsonObject.get("code").getAsInt();
        Log.i("code", code + "");
        if (code == 200) {
            JsonArray newslist = jsonObject.get("newslist").getAsJsonArray();
            Log.i("newslist", newslist + "");
            List<RubClsfyNewsDto> rubClsfyNewsDtoLst = new ArrayList<>();
            try {
                //Gson gson = new Gson();
                //这里出错了 估计是
                newslist.forEach(o -> {

                    JsonObject asJsonObject = o.getAsJsonObject();
                    String id1 = asJsonObject.get("id").getAsString();
                    String ctime = asJsonObject.get("ctime").getAsString();
                    String title = asJsonObject.get("title").getAsString();
                    String description = asJsonObject.get("description").getAsString();
                    String source = asJsonObject.get("source").getAsString();
                    String picUrl = asJsonObject.get("picUrl").getAsString();
                    String url = asJsonObject.get("url").getAsString();
                    //2021-12-18 11:00
                    Date date = DateUtil.stringToDate(ctime, DateUtil.ymdhm);
                    rubClsfyNewsDtoLst.add(new RubClsfyNewsDto(id1, date, title, description, source, picUrl, url));
                });


                //Log.i("rubClsfyNewsDtos", Arrays.toString(rubClsfyNewsDtos) +"");
                Log.i("rubClsfyNewsDtoLst", rubClsfyNewsDtoLst + "");
                rubClsfyNewsDtoLst.forEach(o -> {
                    //有bug W/System.err: com.google.gson.JsonSyntaxException: java.lang.NumberFormatException: For input string: "932f8c678821e4e22e5df3c8d161ba7e"
                    RubClsfyNews rubClsfyNews = RubClsfyNews.toMe(o);

                    try {
                        JsonObject resSave = HttpUtil.post(Bus.baseDbUrl + "/lajifenleiNews/save", rubClsfyNews);
                        Log.i("resSave", resSave + "");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    RubClsfyNews[] geRubClsfyNewsArr() throws IOException {

        Gson gson = new Gson();

        //RubClsfyNews rubClsfyNews = new RubClsfyNews();
        //rubClsfyNews.setId_str("string1");
        ////rubClsfyNews.setIdStr("string1");
        //Log.i("rubClsfyNews", String.valueOf(rubClsfyNews));
        //Log.i("getAllDbCardItems", "getAllDbCardItems");
        //JsonObject res = HttpUtil.post(Bus.baseDbUrl + "/lajifenleiNews/list", rubClsfyNews);
        //这里是封装了一个HttpUtil 基于okhttp，给后台发post请求，拿出我们需要的数据，我们先new出一个RubClsfyNews 作为表单
        //里面没有数据就是说明是全部要搜出来的，返回出来一个json对象，我们拿到里面的content
        RubClsfyNewsJo rubClsfyNewsReq = new RubClsfyNewsJo();
        //rubClsfyNewsReq.setIdStr("string1");
        Log.d("rubClsfyNewsReq", "geRubClsfyNewsArr: " + rubClsfyNewsReq);
        JsonObject res = HttpUtil.post(Bus.baseDbUrl + "/lajifenleiNews/list", rubClsfyNewsReq);

        Log.i("res", String.valueOf(res));

        JsonElement content = res.get("content");

        try {
            RubClsfyNewsJo[] rubClsfyNewsResJoLst = gson.fromJson(content, RubClsfyNewsJo[].class);
            List<RubClsfyNews> collect = Arrays.stream(rubClsfyNewsResJoLst).map(RubClsfyNewsJo::toRubClsfyNews).collect(Collectors.toList());
            Log.i("collect", "" + collect);
            RubClsfyNews[] rubClsfyNews1 = collect.toArray(new RubClsfyNews[0]);
            return rubClsfyNews1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

    void mockCardItems() {
        cardItems = new ArrayList<>();
        Log.i("mockCardItems", "mockCardItems");
        cardItems.add(new CardItem("hada", "dada", new Date(), "https://n.sinaimg.cn/sinakd20110/200/w600h400/20210729/d47d-15af7328ab3cd66b00775dc7821f433c.jpg"));
        cardItems.add(new CardItem("31412", "1241", new Date(), "https://n.sinaimg.cn/sinakd20110/200/w600h400/20210729/d47d-15af7328ab3cd66b00775dc7821f433c.jpg"));
        //for (int i = 0; i <4 ; i++) {
        //    cardItems.add(new CardItem("hada","dada",new Date(),"https://n.sinaimg.cn/sinakd20110/200/w600h400/20210729/d47d-15af7328ab3cd66b00775dc7821f433c.jpg"));
        //}
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void getAllDbCardItems() throws IOException {
        cardItems = new ArrayList<>();
        RubClsfyNews[] rubClsfyNewsResLst = geRubClsfyNewsArr();
        if (rubClsfyNewsResLst == null) {
            Log.i("出错", "设置默认的");
            for (int i = 0; i < 4; i++) {
                cardItems.add(new CardItem());
            }
            return;
        }
        if (rubClsfyNewsResLst.length == 0) {

            Log.i("没有数据", "设置默认的");
            for (int i = 0; i < 4; i++) {
                cardItems.add(new CardItem());
            }
            return;

        }
        Log.i("rubClsfyNewsResLst", Arrays.toString(rubClsfyNewsResLst));
//可以正常 拿到
        List<RubClsfyNews> rubClsfyNewsResLstLimit = Arrays.stream(rubClsfyNewsResLst)
                .limit(4).collect(Collectors.toList());


        cardItems = rubClsfyNewsResLstLimit.stream().map(o -> {
            //o.getExplainRub()
            CardItem cardItem = new CardItem();
            cardItem.setDescription(o.getDescription());
            cardItem.setTime(o.getCtime());
            cardItem.setTitle(o.getTitle());
            //cardItem.setDescription(o.getDescription());
            cardItem.setPicUrl(o.getPicUrl());
            cardItem.setUrl(o.getUrl());

            return cardItem;

        }).collect(Collectors.toList());

        Log.i("cardItems", String.valueOf(cardItems));


    }

    CardPagerAdapter mockCardPagerAdapter() {
        CardPagerAdapter mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem());
        mCardAdapter.addCardItem(new CardItem());
        mCardAdapter.addCardItem(new CardItem());
        mCardAdapter.addCardItem(new CardItem());
        return mCardAdapter;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    void setAdapter(CardPagerAdapter.MySendValue mySendValue) {

        CardPagerAdapter mCardAdapter = new CardPagerAdapter();
        if (mySendValue != null) {
//            这个url 是干嘛的
            mCardAdapter.setMySendValue(mySendValue);

        }

        if (cardItems == null || cardItems.size() == 0) {
            mockCardItems();
        }
        cardItems.forEach(mCardAdapter::addCardItem);
        Log.i("cardItems", cardItems + "");
        Log.i("cardItems size()", cardItems.size() + "");
        Log.i("mCardAdapter getCount()", mCardAdapter.getCount() + "");

        ShadowTransformer mCardShadowTransformer = new ShadowTransformer(binding.viewPager, mCardAdapter);
        mCardShadowTransformer.enableScaling(true);

        Configuration config = getResources().getConfiguration();
        //config.smallestScreenWidthDp;
        Log.d("smallestScreenWidthDp", "setAdapter: smallestScreenWidthDp" + config.smallestScreenWidthDp);
        int leftRightPadding = 100;
        //int leftRightPadding=80;
        //int bottomPadding=150;
        //int bottomPadding=180;
        int bottomPadding = 200;
        //int topPadding=100;
        if (config.smallestScreenWidthDp <= 360) {
            //binding.viewPager.setPadding(80,80,50,20);
            binding.viewPager.setPadding(leftRightPadding, 80, leftRightPadding, bottomPadding);
        } else {
            //binding.viewPager.setPadding(leftRightPadding,80,leftRightPadding,50);
            binding.viewPager.setPadding(leftRightPadding, 80, leftRightPadding, 90);

        }

        binding.viewPager.setAdapter(mCardAdapter);
        binding.viewPager.setPageTransformer(false, mCardShadowTransformer);
        binding.viewPager.setOffscreenPageLimit(3);
        //int padding = DpPxSpTool.Dp2Px(activity, 80);
        //binding.viewPager.setPadding(padding,padding,padding,padding);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    void setAdapter() {


//为什么回退不了 replace getSupportFragmentManager
//        监听一个 接口 里面跑线程 没有跑起来
        CardPagerAdapter.MySendValue mySendValue = url -> {

            Log.d("跳转", "setAdapter: 跳转");
            //这里的线程 为什么没有反应

            Log.d("Bus.curUser", "setAdapter: " + Bus.curUser);
            ThreadPoolManager.getInstance().execute(new FutureTask<>(new Thread(() -> {
                //给他积分

                //Log.d("Bus.curUser", "setAdapter: "+Bus.curUser);
                //activity.runOnUiThread(()->{
                //    System.out.println("Bus.curUser"+"setAdapter: "+Bus.curUser);
                //});
                //

                try {
                    Bus.curUser.addPoint(1);
                    Log.d("Bus.curUser", "setAdapter: " + Bus.curUser);
                    JsonObject post = HttpUtil.post(Bus.baseDbUrl + Bus.userSave, Bus.curUser);
                    Log.d("post", "setAdapter: " + post);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }), null), null);


            //activity.getSupportFragmentManager().beginTransaction().
            //        replace(R.id.nav_host_fragment_activity_main, new WebFragment(url))
            //        .addToBackStack(null).commit();
            //FragmentManager childFragmentManager = getChildFragmentManager();
            //childFragmentManager.beginTransaction().
            //        replace(R.id.mainHolder, new WebFragment(url))
            //        .addToBackStack(null).commit();

            getParentFragmentManager().beginTransaction().
                    //replace(R.id.nav_host_fragment_activity_main, new FriendFragment())
                            replace(R.id.mainHolder,new WebFragment(url))
                    .addToBackStack(null).commit();

            //ThreadPoolManager.getInstance().execute(new FutureTask<>(new Thread(()->{
            //    mockInsert();
            //}),null),null);


            //ThreadPoolManager.run(new Thread(()->{
            //    //给他积分
            //    Bus.curUser.addPoint(1);
            //    try {
            //        Log.d("Bus.curUser", "setAdapter: "+Bus.curUser);
            //        JsonObject post = HttpUtil.post(Bus.baseDbUrl + Bus.userSave, Bus.curUser);
            //        Log.d("post", "setAdapter: "+post);
            //    } catch (IOException e) {
            //        e.printStackTrace();
            //    }
            //}));


        };

        //这里是可以的啊 为啥别的不行呢

        setAdapter(mySendValue);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void wasteTimeInit() {
        ThreadPoolManager.getInstance().execute(new FutureTask<>(new Thread(() -> {

            try {
                Log.i("getAllDbCardItems", "getAllDbCardItems");
                getAllDbCardItems();
                //mockCardItems();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //线程里的东西 为什么不报错
            Log.i("mockCardItems ", "mockCardItems end");
            activity.runOnUiThread(() -> {
                try {
                    Log.i("setAdapter", "setAdapter");
                    setAdapter();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });


        }), null), null);

    }

    void initDatabase() {
        repository = new Repository(getActivity());
    }

    FragmentActivity activity;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //这个修改为对应子Fragment和父Fragment的布局文件

        binding = FragmentMainBinding.inflate(inflater, container, false);
        //binding = FragmentHolderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initDatabase();
        activity = getActivity();


        //setProgress();
        setProgressByPointCnt();
//mockCardPagerAdapter();
        mockCardItems();
        setAdapter(null);

        wasteTimeInit();
        //Bus


//        版权声明：本文为CSDN博主「风云正」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/chenzheng8975/article/details/76976122
//

        Log.d("listener", "onCreateView: "+listener);
        //这里 set的 listener 是空的
        //testSet();
        binding.iconTool.setOnClickListener(v->{
            ActivityUtil.startActivity(activity, TestActivity.class);
        });
        binding.ivAvatar.setOnClickListener(v->{
            ActivityUtil.startActivity(activity, TestActivity.class);
        });
        return root;
    }

    void toLoginActivity() {

//            int waitMs=3000;
        int waitMs = 2000;
        new Handler(message -> {
//                ActivityUtil.startActivity(getActivity(), LoginActivity.class);
            ActivityUtil.startActivity(activity, LoginActivity.class);
            return false;
        }).sendEmptyMessageDelayed(0, waitMs); // 延迟3秒
    }

    void testSet() {
        //设置的listener 是 null
        //binding.btnToFriend.setOnClickListener(v -> {
        //    Log.d("btnToFriend", "testSet: btnToFriend");
        //    //Log.d("listener", "testSet: listener  "+listener);
        //    //if(listener!=null){
        //    //    listener.onBtnToFriendClick();
        //    //
        //    //}
        //    //getParentFragment().getParentFragmentManager().beginTransaction().
        //    getParentFragmentManager().beginTransaction().
        //            //replace(R.id.nav_host_fragment_activity_main, new FriendFragment())
        //                    replace(R.id.holder, new FriendFragment())
        //            .addToBackStack(null).commit();
        //    //getParentFragment().getParentFragmentManager().beginTransaction().
        //    //        //replace(R.id.nav_host_fragment_activity_main, new FriendFragment())
        //    //                replace(R.id.holder, new FriendFragment())
        //    //        .addToBackStack(null).commit();
        //    //activity.getSupportFragmentManager().beginTransaction().
        //    //        //replace(R.id.nav_host_fragment_activity_main, new FriendFragment())
        //    //        replace(R.id.holder, new FriendFragment())
        //    //        .addToBackStack(null).commit();
        //});
    }
    Listener listener;

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;

        //Log.d("listener set", "setListener: ");
        //binding.btnToFriend.setOnClickListener(v -> {
        //    Log.d("btnToFriend", "testSet: btnToFriend");
        //    Log.d("listener", "testSet: listener  "+listener);
        //    if(listener!=null){
        //        listener.onBtnToFriendClick();
        //
        //    }
        //    //activity.getSupportFragmentManager().beginTransaction().
        //    //        //replace(R.id.nav_host_fragment_activity_main, new FriendFragment())
        //    //                replace(R.id.holder, new FriendFragment())
        //    //        .addToBackStack(null).commit();
        //});
    }

    public interface Listener {
        void onBtnToFriendClick();
    }

    void setProgress() {
        if (Bus.curUser == null) {
            //Bus.httpDb
            ToastUtil.show(getActivity(), "当前没有用户登录 请登录");


            return;
        }
        Integer stageId;
        stageId = Bus.curUser.getStageId();
        //stageId = Bus.curUser.getPointCnt();
        Integer pointCnt = Bus.curUser.getPointCnt();

        if (stageId == null) {
            stageId = 0;
        }
        double progress = stageId * 100.0 / Bus.stagesLen;
        binding.donutProgress.setProgress((int) progress);
    }

    void setProgressByPointCnt() {
        if (Bus.curUser == null) {
            //Bus.httpDb
            ToastUtil.show(getActivity(), "当前没有用户登录 请登录");


            return;
        }
        //Integer stageId;
        //stageId = Bus.curUser.getStageId();
        //stageId = Bus.curUser.getPointCnt();
        Integer pointCnt = Bus.curUser.getPointCnt();

        if (pointCnt == null) {
            pointCnt = 0;
        }
        //if (stageId == null) {
        //    stageId = 0;
        //}
        //if (pointCnt > 5) {
        //    pointCnt = 5;
        //}

        if (pointCnt > Bus.maxPoint) {
            pointCnt =  Bus.maxPoint;
        }

        //double progress = pointCnt * 100.0 / Bus.maxPoint;
        double progress = pointCnt * 100.0 / Bus.maxPoint;
        binding.donutProgress.setProgress((int) progress);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onCardViewClicked(String url) {
        //activity.ge
    }
//————————————————
//    版权声明：本文为CSDN博主「不知 不知」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//    原文链接：https://blog.csdn.net/qq_41858698/article/details/105452276
}
