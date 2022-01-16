package com.example.whatrubbish.fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.whatrubbish.Bus;
import com.example.whatrubbish.R;
import com.example.whatrubbish.databinding.FragmentProblemBinding;
import com.example.whatrubbish.db.Repository;
import com.example.whatrubbish.dto.Problem;
import com.example.whatrubbish.entity.RubbishType;
import com.example.whatrubbish.util.HttpUtil;
import com.example.whatrubbish.util.ThreadPoolManager;
import com.example.whatrubbish.util.ToastUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import ViewGroup.LayoutParams

import lombok.SneakyThrows;
//import okhttp3.FormBody;
//import okhttp3.FormBody;

//@EqualsAndHashCode(callSuper = true)
//@Data
//@Builder
public class ProblemFragment extends Fragment {

    public ProblemFragment() {
    }

    Problem problem;

    Listener listener;

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    //WikiHolderFragment w
    //FragmentWikiBinding binding;
    //FragmentCollectRubBinding binding;
    FragmentProblemBinding binding;
    int imgId;

    //Activity activity;
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

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    void initProblems() throws IOException {
        JsonObject post = HttpUtil.post(Bus.baseDbUrl + "/problem/list", new Problem());
        int code = post.get("code").getAsInt();
        if(code==200){
            //post.get("data").getAsJsonObject().get("content")
            //post.get("data").getAsJsonObject().get(Bus.contentMark)
            //JsonObject asJsonObject = post.get(Bus.dataMark).getAsJsonObject().get(Bus.contentMark).getAsJsonObject();
            JsonArray asJsonArray = post.get(Bus.dataMark).getAsJsonObject().get(Bus.contentMark).getAsJsonArray();
            //Problem problem = Bus.gson.fromJson(asJsonObject, Problem.class);
            Problem[] problems = Bus.gson.fromJson(asJsonArray, Problem[].class);
            Log.d("problems", "wasteTimeInit: "+ Arrays.toString(problems));
        }
    }

    void wasteTimeInit() throws IOException {
        Thread thread = new Thread(() -> {
            try {
                initProblems();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ThreadPoolManager.run(thread);
    }
    @SneakyThrows
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        activity = getActivity();
        initDatabase();

        binding = FragmentProblemBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //binding.
        //wasteTimeInit();
        setUpData();
        return root;
    }
    //public
    List<RadioButton>radioButtons=new ArrayList<>();


    void setUpData(){

        List<String> candidates = problem.getCandidates();
        int i=0;
        for (int j = 0; j < 4; j++) {
            radioButtons.add(new RadioButton(activity));
        }
        //for (int j = 0; j <radioButtons.size() ; j++) {
        //
        //}

        LayoutParams layoutParams = new LayoutParams(400,LayoutParams.WRAP_CONTENT);

        for (RadioButton radioButton : radioButtons) {
            radioButton.setText(candidates.get(i++));
            binding.radiogroup1.addView(radioButton,layoutParams);
        }

        //radioButtons.add(new RadioButton(activity));
        //radioButtons.add(new RadioButton(activity));
        //radioButtons.add(new RadioButton(activity));
        //java 代码 生成 view
        //LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        //binding.radiogroup1.addView(new RadioButton(activity), ViewGroup.LayoutParams.MATCH_PARENT);
        //int

        //binding.radiogroup1
        //binding.radiobutton1.setText(candidates.get(i++));
        //binding.radiobutton2.setText(candidates.get(i++));
        //binding.radiobutton3.setText(candidates.get(i++));
        //binding.radiobutton4.setText(candidates.get(i++));
        //多个 radiobutton4    循环
        binding.tvTitle.setText(problem.getTitle());
        binding.btnConfirm.setOnClickListener(v->{
            //binding.radiobutton1.isChecked()
            //binding.radiogroup1.getCheckedRadioButtonId()
            //for

            List<Integer> ansNums = problem.getAnsNums();
            Integer rightAnsNum = ansNums.get(0);

            int idx=0;
            boolean isRight=false;
            for (RadioButton radioButton : radioButtons) {
                if(radioButton.isChecked()){

                    if(rightAnsNum.equals(idx)){
                        //ToastUtil.show(activity,"答对了");


                        isRight=true;
                        if(listener!=null){
                            listener.onAnsRightListener();
                        }
                        break;
                    }
                    //String  text = radioButton.getText().toString();
                }
                idx++;
            }
            if(isRight==false){
                ToastUtil.show(activity,"答错了");
            }
            //int checkedRadioButtonId = binding.radiogroup1.getCheckedRadioButtonId();
            //binding.radiogroup1.getCheckedRadioButtonId()
        });
    }

    public interface Listener {
        //void onItemClickListener(RubbishType rubbishType);
        void onAnsRightListener();
    }


    //final static int REQUEST_SUCCESS = 200;
    //final static int REQUEST_FAIL = 500;
//    private Handler requestHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case REQUEST_SUCCESS:
////                    Toast.makeText(MainActivity.this, "SUCCESSFUL", Toast.LENGTH_SHORT).show();
//                    ToastUtil.show(activity, "REQUEST_SUCCESS");
//                    break;
//                case REQUEST_FAIL:
//                    ToastUtil.show(activity, "REQUEST_FAIL");
////                    Toast.makeText(MainActivity.this, "request failed", Toast.LENGTH_SHORT).show();
//                    break;
//                default:
//                    super.handleMessage(msg);
//            }
//        }
//    };




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
