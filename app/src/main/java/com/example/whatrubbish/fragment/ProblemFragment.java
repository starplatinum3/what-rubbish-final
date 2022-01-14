package com.example.whatrubbish.fragment;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.whatrubbish.R;
import com.example.whatrubbish.constant.MoveConstant;
import com.example.whatrubbish.databinding.FragmentCollectRubBinding;
import com.example.whatrubbish.db.Repository;
import com.example.whatrubbish.entity.RubbishInfo;
import com.example.whatrubbish.entity.RubbishType;
import com.example.whatrubbish.util.DpPxSpTool;
import com.example.whatrubbish.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import okhttp3.FormBody;
//import okhttp3.FormBody;

//@EqualsAndHashCode(callSuper = true)
//@Data
//@Builder
public class ProblemFragment extends Fragment {
    Map<Integer, Integer> picMap=new HashMap<>();

    public Map<Integer, Integer> getPicMap() {
        return picMap;
    }

    public void setPicMap(Map<Integer, Integer> picMap) {
        this.picMap = picMap;
    }

    public ProblemFragment() {
    }

    public ProblemFragment(SendValue sendValue) {
        this.sendValue = sendValue;
    }

    SendValue sendValue;
    //WikiHolderFragment w
    //FragmentWikiBinding binding;
    FragmentCollectRubBinding binding;
    int imgId;

    //Activity activity;
    FragmentActivity activity;

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRubType();
        //binding.person.setOnClickListener(v->{
        //    //int id = v.getId();
        //    //Integer picId = picMap.get(id);
        //
        //
        //    //map.put(控件id,图片id);
        //    //Fragment fragment = new Fragment();
        //});

        //CollectRubFragment collectRubFragment = new CollectRubFragment();
        //Map<Integer, Integer>map=new HashMap<>();
        //map.put(控件id,图片id);
        //collectRubFragment.setPicMap(map);

    }


    private ViewPager mViewPager;


    Repository repository;

    void initDatabase() {
        repository = new Repository(activity);
    }


    @SuppressLint("ClickableViewAccessibility")
    void initGrid() {
        String[] from = {"img", "text"};

        int[] to = {R.id.img, R.id.text};

        initData();

//        https://www.cnblogs.com/fanglongxiang/p/11910455.html
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), dataList, R.layout.gridview_item, from, to);
        GridView gridView = binding.gridview;
        gridView.setAdapter(adapter);
        //gridView 设置 上下间隔
        //gridView.  setHorizontalSpacing(   DpPxSpTool.Dp2Px(activity,65));
        gridView.setVerticalSpacing(DpPxSpTool.Dp2Px(activity, 65));

        gridView.setOnTouchListener((v, event) -> {
            //if()
            //event.getAction()
            //MotionEvent 当触摸
            Log.d("TAG", "initGrid:setOnTouchListener ");

            switch (event.getAction()){
                case MotionEvent.ACTION_BUTTON_PRESS:
                    break;
                case MotionEvent.ACTION_DOWN:
                    //int waitMs=3000;
                    int waitMs = 2000;
                    ObjectAnimator.ofFloat(binding.person, MoveConstant.translationX, event.getRawX()).setDuration(waitMs).start();
                    ObjectAnimator.ofFloat(binding.person, MoveConstant.translationY, event.getRawY()).setDuration(waitMs).start();

                    //打开一个答题程序

                    new Handler(message -> {
                        //产生一个 碎片
                        //ActivityUtil.startActivity(getActivity(), LoginActivity.class);
                        //TextFragment textFragment = new TextFragment();
                        //new   AlertDialog.Builder(activity).setPositiveButton("确定",(dialog, which) -> {}).setNegativeButton()
                        Log.d("TAG", "initGrid: AlertDialog");
                        new AlertDialog.Builder(activity).setTitle("恭喜获得碎片").setMessage("恭喜获得碎片").
                                setPositiveButton("确定", (dialog, which) -> {
                                }).
                                setNegativeButton("取消", ((dialog, which) -> {
                                })).show();
//                        HttpUtil.post()
                        return false;
                    }).sendEmptyMessageDelayed(0, waitMs); // 延迟3秒

                    break;

            }

            return true;
        });
        //gridView.setOnTouchListener { v, event ->
        //        //在这里面拦截点击事件,并进行相应的操作
        //
        //        true
        //}

        gridView.setOnItemClickListener((adapterView, view, position, id) -> {


        });
//————————————————
//        版权声明：本文为CSDN博主「强强强子」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/sinat_25926481/article/details/70880047


//
    }

    private void showNormalDialog() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        //new   AlertDialog.Builder(activity).
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(activity);
        //normalDialog.setIcon(R.drawable.icon_dialog);
        normalDialog.setTitle("我是一个普通Dialog");
        normalDialog.setMessage("你要点击哪一个按钮呢?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }


    List<RubbishInfo> rubbishInfoLst;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        activity = getActivity();
        initDatabase();

        binding = FragmentCollectRubBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        initGrid();

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

    void initData() {

        //图标

        //图标下的文字
        String name[] = {"时钟", "信号", "宝箱", "秒钟", "大象", "FF", "记事本", "书签", "印象", "商店", "主题", "迅雷"};
        //name.length
        //int icons[] = new int[name.length]{ R.mipmap.xunzhang_6,R.mipmap.xunzhang_1,R.mipmap.xunzhang_5,R.mipmap.xunzhang_7};
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
