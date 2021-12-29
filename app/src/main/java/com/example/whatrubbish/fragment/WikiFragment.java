package com.example.whatrubbish.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.ejlchina.data.Array;
import com.ejlchina.data.Mapper;
import com.ejlchina.okhttps.GsonMsgConvertor;
import com.ejlchina.okhttps.HTTP;
//import com.ejlchina.okhttps.HttpResult;
import com.example.smlj.MyAdapter;
import com.example.whatrubbish.Bus;
import com.example.whatrubbish.R;
import com.example.whatrubbish.card.CardItem;
import com.example.whatrubbish.card.CardPagerAdapter;
import com.example.whatrubbish.card.ShadowTransformer;
import com.example.whatrubbish.circleProgressBar.CircleProgressBar;
import com.example.whatrubbish.databinding.FragmentMainBinding;
import com.example.whatrubbish.databinding.FragmentWikiBinding;
import com.example.whatrubbish.db.Repository;
import com.example.whatrubbish.dbo.RubbishInfoDbo;
import com.example.whatrubbish.dto.RubbishInfoDto;
import com.example.whatrubbish.dto.TbAdmin;
import com.example.whatrubbish.dto.TbAdminFindDto;
import com.example.whatrubbish.entity.RubbishInfo;
import com.example.whatrubbish.entity.RubbishType;
import com.example.whatrubbish.entity.User;
import com.example.whatrubbish.gridIcons.GridIcons;
import com.example.whatrubbish.gridIcons.IconButton;
import com.example.whatrubbish.gridIcons.RvAdapter;
import com.example.whatrubbish.service.RubbishInfoService;
import com.example.whatrubbish.ui.regionInput.RegionInputFragment;
import com.example.whatrubbish.util.DpPxSpTool;
import com.example.whatrubbish.util.HttpUtil;
import com.example.whatrubbish.util.PropertiesUtil;
import com.example.whatrubbish.util.ThreadPoolManager;
import com.example.whatrubbish.util.ToastUtil;
import com.google.android.material.appbar.MaterialToolbar;
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
//import com.squareup.okhttp.;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import lombok.SneakyThrows;
import okhttp3.FormBody;
//import okhttp3.FormBody;
//import okhttp3.FormBody;

//@EqualsAndHashCode(callSuper = true)
//@Data
//@Builder
public class WikiFragment extends Fragment {

    public WikiFragment() {
    }

    public WikiFragment(SendValue sendValue) {
        this.sendValue = sendValue;
    }

    SendValue sendValue;
    FragmentWikiBinding binding;

    FragmentActivity activity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRubType();
    }

    Repository repository;

    void initDatabase() {
        repository = new Repository(activity);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    void getAllDbRubbishList() throws IOException {

        String listUrl = Bus.baseDbUrl + "/rubbishInfo/list";
//        String ip = "10.0.2.2";
////        String url="http://"+ip+":8889/tbAdmin/find?id=1";
////        String url = "http://" + ip + ":8889/rubbishInfo/save";
//        String url = "http://" + ip + ":8889/rubbishInfo/list";
//        http://localhost:8889/rubbishInfo/list?pageSize=10
//        String keyRubbish = PropertiesUtil.getProperty(activity, Bus.keyRubbish);
//        String baseUrl = "http://localhost:8889/tbAdmin/find?id=1";
//        String baseUrl= "http://localhost:8889/tbAdmin";
//        String baseUrl= "http://localhost:8889/tbAdmin?id={id}";
//        String baseUrl= "http://localhost:8889/tbAdmin/find?id=1";
//        String baseUrl= "https://www.baidu.com";
//        String baseUrl= "http://api.tianapi.com/lajifenlei/index";


        Gson gson = new Gson();

        //RubbishInfo rubbishInfo = new RubbishInfo();
        RubbishInfoDbo rubbishInfoDbo = new RubbishInfoDbo();

        String jsonString = gson.toJson(rubbishInfoDbo);

        System.out.println(jsonString);
        //String post = httpUtil.post(listUrl, jsonString);
        String post = HttpUtil.post(listUrl, jsonString);
//        content
        JsonObject jsonObject = gson.fromJson(post, JsonObject.class);

//        JsonObject 嵌套怎么 转化成为 一个类

//        JsonArray 转化为 实体
        JsonElement content = jsonObject.get("content");
        RubbishInfoDbo[] rubbishInfoDbos = gson.fromJson(content, RubbishInfoDbo[].class);
//        TbAdmin[] tbAdmins = gson.fromJson(content, TbAdmin[].class);
        Log.i("rubbishInfos", Arrays.toString(rubbishInfoDbos));
//可以正常 拿到

//        字段不同 就是很麻烦
//        最好就是数据库 一套字段，他那边api 没办法 就是一套
        rubbishInfoLst = Arrays.stream(rubbishInfoDbos).map(o -> {
            //o.getExplainRub()
            RubbishInfo rubbishInfo = gson.fromJson(gson.toJson(o), RubbishInfo.class);
            rubbishInfo.setExplain(o.getExplainRub());
            rubbishInfo.setName(o.getRubName());
            return rubbishInfo;
        }).collect(Collectors.toList());

        Log.i("rubbishInfoLst", String.valueOf(rubbishInfoLst));


    }


    List<RubbishInfo> rubbishInfoLst;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //这个修改为对应子Fragment和父Fragment的布局文件
//        R.id
//        CircleProgressBar.

        //RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        ////设置布局管理器
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ////设置adapter
        //myAdapter = new MyAdapter(getActivity());
        //mRecyclerView.setAdapter(myAdapter);
        ////设置默认属性
        //mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        activity = getActivity();
        initDatabase();

        //getActivity().findViewById(R.id.username)

//        rubbishInfoLst.stream().
        binding = FragmentWikiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //这样就可以修改图片了

        //拿到提交图标的imageview,

        //SearchView
        //设置该SearchView显示搜索按钮
        //请问为什么我find 出来是null 是因为我是 android.widget的包吗
        //binding.searchView.setSubmitButtonEnabled(true);

        //这样子不行
        String[] from = {"img", "text"};

        int[] to = {R.id.img, R.id.text};

        initData();
//        点击按钮 响应  searchView的事件
//        binding.searchView.onActionViewCollapsed();


        ThreadPoolManager.getInstance().execute(new FutureTask<>(new Thread(() -> {
            try {
                Log.i("getAllDbRubbishList", "getAllDbRubbishList");
                getAllDbRubbishList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }), null), null);
        /**
         * 默认情况下是没提交搜索的按钮，所以用户必须在键盘上按下"enter"键来提交搜索.你可以同过setSubmitButtonEnabled(
         * true)来添加一个提交按钮（"submit" button)
         * 设置true后，右边会出现一个箭头按钮。如果用户没有输入，就不会触发提交（submit）事件
         */

        String ip = "10.0.2.2";
//        String url="http://"+ip+":8889/tbAdmin/find?id=1";
        String url = "http://" + ip + ":8889/tbAdmin/find";
        //binding. btnClear.setOnClickListener(v->{
        //    binding.searchView.setQuery("", false);
        //});
        //binding.searchView.setQuery(url, false);
        //binding.searchView.setQuery("桌布", false);
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            private String TAG = getClass().getSimpleName();

            /*
             * 在输入时触发的方法，当字符真正显示到searchView中才触发，像是拼音，在舒服法组词的时候不会触发
             *
             * @param queryText
             *
             * @return false if the SearchView should perform the default action
             * of showing any suggestions if available, true if the action was
             * handled by the listener.
             */
            @Override
            public boolean onQueryTextChange(String queryText) {
                Log.d(TAG, "onQueryTextChange = " + queryText);

//                String selection = RawContacts.DISPLAY_NAME_PRIMARY + " LIKE '%" + queryText + "%' " + " OR "
//                        + RawContacts.SORT_KEY_PRIMARY + " LIKE '%" + queryText + "%' ";
//                // String[] selectionArg = { queryText };
//                mCursor = getContentResolver().query(RawContacts.CONTENT_URI, PROJECTION, selection, null, null);
//                mAdapter.swapCursor(mCursor); // 交换指针，展示新的数据
                return true;
            }


            /*
             * 输入完成后，提交时触发的方法，一般情况是点击输入法中的搜索按钮才会触发。表示现在正式提交了
             *
             * @param queryText
             *
             * @return true to indicate that it has handled the submit request.
             * Otherwise return false to let the SearchView handle the
             * submission by launching any associated intent.
             */
            @SneakyThrows
            @Override
            public boolean onQueryTextSubmit(String queryText) {
                Log.d(TAG, "onQueryTextSubmit = " + queryText);
                //performSyncHttpRequest();

                ThreadPoolManager.getInstance().execute(new FutureTask<>(new Thread(() -> {
                    Log.d(TAG, "queryText = " + queryText);
                    Log.d(TAG, "start search = " + queryText);
//                    search(queryText);
//                    test2();
//                    test3();
//                    post()

                    try {
//                        这是调用了 api
//                        但是我们需要 城市的
                        search(queryText);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                }), null), null);

//                new Tread
//                Bus

                if (binding.searchView != null) {

                    // 得到输入管理对象
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        // 这将让键盘在所有的情况下都被隐藏，但是一般我们在点击搜索按钮后，输入法都会乖乖的自动隐藏的。
                        imm.hideSoftInputFromWindow(binding.searchView.getWindowToken(), 0); // 输入法如果是显示状态，那么就隐藏输入法
                    }
                    binding.searchView.clearFocus(); // 不获取焦点
                }
                return true;
            }
        });


//        MaterialToolbar

//        https://www.cnblogs.com/fanglongxiang/p/11910455.html

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), dataList, R.layout.gridview_item, from, to);
        GridView gridView = binding.gridview;
        gridView.setAdapter(adapter);
        //gridView 设置 上下间隔
        gridView.setVerticalSpacing(DpPxSpTool.Dp2Px(activity, 65));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                    long id) {

                Log.i("replace", "getSupportFragmentManager");
                if (position >= rubbishTypes.size()) {
                    Log.i("超出了 长度", "长度");
                    return;
                }
                RubbishType rubbishType = rubbishTypes.get(position);

                Log.i("sendValue", "sendValue 这里面有没有啊");
                sendValue.onItemClickListener(rubbishType);
                //getChildFragmentManager().beginTransaction().replace(R.id.holder,new WikiFragment()).addToBackStack(null).commit();
            }
        });
//————————————————
//        版权声明：本文为CSDN博主「强强强子」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/sinat_25926481/article/details/70880047


//
        return root;
    }
    //public


    public interface SendValue {
        void onItemClickListener(RubbishType rubbishType);
    }


    final static int REQUEST_SUCCESS = 200;
    final static int REQUEST_FAIL = 500;
    private Handler requestHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_SUCCESS:
//                    Toast.makeText(MainActivity.this, "SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    ToastUtil.show(activity, "REQUEST_SUCCESS");
                    break;
                case REQUEST_FAIL:
                    ToastUtil.show(activity, "REQUEST_FAIL");
//                    Toast.makeText(MainActivity.this, "request failed", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.N)
    List<RubbishInfo> searchByTianapi(String searchWord) {
        String keyRubbish = PropertiesUtil.getProperty(activity, Bus.keyRubbish);

        HTTP http = HTTP.builder()
//                .baseUrl("http://api.tianapi.com/lajifenlei/index?key="+keyRubbish+"&word=")
                .baseUrl(Bus.tianapiBaseUrl)
                .addMsgConvertor(new GsonMsgConvertor())
                .build();


        String res = http.sync("") // http://api.example.com/users
                .addPathPara(Bus.APIKEY_TOKEN, keyRubbish)
                .addPathPara(Bus.WORD_TOKEN, searchWord)
                .get()                        // GET请求
                .getBody().toString();
//        HttpUtil

        //HttpUtil
        Log.i("res", res);
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(res, JsonObject.class);
        int code = jsonObject.get("code").getAsInt();
        if (code == 200) {
//            ok
            JsonArray newslist = jsonObject.get("newslist").getAsJsonArray();
            Log.i("newslist", String.valueOf(newslist));
            RubbishInfoDto[] rubbishInfoDtos = gson.fromJson(newslist, RubbishInfoDto[].class);
//            BeanUtils
            Log.i("rubbishInfoDtos", Arrays.toString(rubbishInfoDtos));


            List<RubbishInfo> rubbishInfos = Arrays.stream(rubbishInfoDtos).map(o -> {
                String s1 = gson.toJson(o);
                RubbishInfo rubbishInfo = gson.fromJson(s1, RubbishInfo.class);
                rubbishInfo.setQueryRub(searchWord);

                return rubbishInfo;
            }).collect(Collectors.toList());

            List<RubbishInfoDbo> rubbishInfoDbos = Arrays.stream(rubbishInfoDtos).map(o -> {
                String s1 = gson.toJson(o);

                RubbishInfoDbo rubbishInfoDbo = gson.fromJson(s1, RubbishInfoDbo.class);
                //rubbishInfo.setQueryRub(searchWord);
                rubbishInfoDbo.setQueryRub(searchWord);
                rubbishInfoDbo.setRubName(o.getName());
                rubbishInfoDbo.setExplainRub(o.getExplain());
                return rubbishInfoDbo;
            }).collect(Collectors.toList());


            Log.i("rubbishInfos", String.valueOf(rubbishInfos));

            rubbishInfoDbos.forEach(o -> {
                try {
                    RubbishInfoService.save(o);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });


            rubbishInfoLst.addAll(rubbishInfos);
            return rubbishInfos;

        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
        //@SneakyThrows
        //尽量不要 使用 SneakyThrows 不然都不报错
    void search(String searchWord) {
        //Bus
//        安卓 把 key  从配置文件读出
        Log.d("start", "start search = " + searchWord);

        if (rubbishInfoLst == null) {
            Log.i("有问题", "rubbishInfoLst 是空的 可能是后台关了 没有收到数据");
            return;
        }

        List<RubbishInfo> rubbishInfoLstThisSearchWord = rubbishInfoLst.stream().
                filter(o -> {
                    String queryRub = o.getQueryRub();
                    if (queryRub == null) {
                        return false;
                    }
                    return queryRub.equals(searchWord);
                }).collect(Collectors.toList());
        Log.i("rubbishInfo", "rubbishInfoLstThisSearchWord: " + rubbishInfoLstThisSearchWord);

        if (rubbishInfoLstThisSearchWord.size() == 0) {
            rubbishInfoLstThisSearchWord = searchByTianapi(searchWord);
            //展现到 前端
        }

        Log.i("rubbishInfo", "rubbishInfoLstThisSearchWord: " + rubbishInfoLstThisSearchWord);

        if (rubbishInfoLstThisSearchWord == null) {
            Log.i("空的", "api 也是空的");
            activity.runOnUiThread(() -> {
                ToastUtil.show(activity, "没有查询到");
            });

            return;
        }
        RubbishInfo rubbishInfo = rubbishInfoLstThisSearchWord.get(0);
        activity.getSupportFragmentManager().beginTransaction().
                replace(R.id.nav_host_fragment_activity_main, new RubbishFragment(rubbishInfo), null).
                addToBackStack(null).commit();

        activity.runOnUiThread(() -> {
            ToastUtil.show(activity, "搜索完毕");
        });
    }

    List<RubbishType> rubbishTypes = new ArrayList<>();

    void initRubType() {

        rubbishTypes.add(new RubbishType(null, "可回收物", "可回收物主要包括废纸、塑料、玻璃、金属和布料五大类。", null, "https://bkimg.cdn.bcebos.com/pic/a5c27d1ed21b0ef41bd59fc3548e46da81cb39db905e?x-bce-process=image/watermark,image_d2F0ZXIvYmFpa2UxMTY=,g_7,xp_5,yp_5/format,f_auto"));
        //rubbishTypes.add( new RubbishType(null,"其他垃圾","其他垃圾（上海称干垃圾）包括除上述几类垃圾之外的砖瓦陶瓷、渣土、卫生间废纸、纸巾等难以回收的废弃物及尘土、食品袋（盒）。采取卫生填埋可有效减少对地下水、地表水、土壤及空气的污染。",null,"https://baike.baidu.com/pic/%E5%9E%83%E5%9C%BE%E5%88%86%E7%B1%BB/2904193/0/a71ea8d3fd1f41343b06c9112a1f95cad1c85ea8?fr=lemma&ct=single"));
        rubbishTypes.add(new RubbishType(null, "其他垃圾", "其他垃圾（上海称干垃圾）包括除上述几类垃圾之外的砖瓦陶瓷、渣土、卫生间废纸、纸巾等难以回收的废弃物及尘土、食品袋（盒）。采取卫生填埋可有效减少对地下水、地表水、土壤及空气的污染。", null, "https://bkimg.cdn.bcebos.com/pic/21a4462309f79052900955fd02f3d7ca7acbd5da?x-bce-process=image/watermark,image_d2F0ZXIvYmFpa2U4MA==,g_7,xp_5,yp_5/format,f_auto"));
        rubbishTypes.add(new RubbishType(null, "厨余垃圾", "厨余垃圾（上海称湿垃圾）包括剩菜剩饭、骨头、菜根菜叶、果皮等食品类废物。经生物技术就地处理堆肥，每吨可生产0.6~0.7吨有机肥料。", null, "https://bkimg.cdn.bcebos.com/pic/a71ea8d3fd1f41343b06c9112a1f95cad1c85ea8?x-bce-process=image/watermark,image_d2F0ZXIvYmFpa2UxMTY=,g_7,xp_5,yp_5/format,f_auto"));
        rubbishTypes.add(new RubbishType(null, "有害垃圾", "有害垃圾含有对人体健康有害的重金属、有毒的物质或者对环境造成现实危害或者潜在危害的废弃物。包括电池、荧光灯管、灯泡、水银温度计、油漆桶、部分家电、过期药品及其容器、过期化妆品等。这些垃圾一般使用单独回收或填埋处理。", null, "https://bkimg.cdn.bcebos.com/pic/71cf3bc79f3df8dc6ad242fec211728b461028f6?x-bce-process=image/watermark,image_d2F0ZXIvYmFpa2UxMTY=,g_7,xp_5,yp_5/format,f_auto"));
        //RubbishType rubbishType = new RubbishType(null,"可回收物","可回收物主要包括废纸、塑料、玻璃、金属和布料五大类。",null,"https://bkimg.cdn.bcebos.com/pic/a5c27d1ed21b0ef41bd59fc3548e46da81cb39db905e?x-bce-process=image/watermark,image_d2F0ZXIvYmFpa2UxMTY=,g_7,xp_5,yp_5/format,f_auto");
    }

    private List<Map<String, Object>> dataList;
    //这里的数据是一个 map的 list key是名字，val是图标

    void initData() {

        //图标

        //图标下的文字
        //数据是通过循环写入的 ，可以看一下数据是先写死的 因为我觉得勋章就那么几类，作为展示，先写死应该没问题
        //这里的name 叫什么其实无所谓，因为我name 不显示，这样才能变成一个只显示图标的样式
        String name[] = {"时钟", "信号", "宝箱", "秒钟", "大象", "FF", "记事本", "书签", "印象", "商店", "主题", "迅雷"};

        int icons[] = new int[]{R.mipmap.xunzhang_6, R.mipmap.xunzhang_1, R.mipmap.xunzhang_5, R.mipmap.xunzhang_7};
        //icons[0]=
        //for (int i = 0; i < icons.length; i++) {
        //    icons[i] = R.drawable.miku_fang;
        //}
        dataList = new ArrayList<>();
        //dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < icons.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", icons[i]);
            map.put("text", name[i]);
            dataList.add(map);
        }
    }

    //————————————————
//    版权声明：本文为CSDN博主「强强强子」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//    原文链接：https://blog.csdn.net/sinat_25926481/article/details/70880047
//
    @Override
    public void onPause() {
        super.onPause();
    }
//————————————————
//    版权声明：本文为CSDN博主「不知 不知」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//    原文链接：https://blog.csdn.net/qq_41858698/article/details/105452276
}
