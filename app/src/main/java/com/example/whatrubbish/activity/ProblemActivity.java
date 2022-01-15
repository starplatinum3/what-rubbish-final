package com.example.whatrubbish.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.whatrubbish.Bus;
import com.example.whatrubbish.R;
import com.example.whatrubbish.databinding.ActivityPersonalSettingBinding;
import com.example.whatrubbish.databinding.ActivityProblemBinding;
import com.example.whatrubbish.databinding.FragmentProblemBinding;
import com.example.whatrubbish.db.Repository;
import com.example.whatrubbish.dto.Problem;
import com.example.whatrubbish.entity.RubbishType;
import com.example.whatrubbish.entity.User;
import com.example.whatrubbish.fragment.CollectRubFragment;
import com.example.whatrubbish.fragment.ProblemFragment;
import com.example.whatrubbish.fragment.WikiFragment;
import com.example.whatrubbish.repository.UserRepository;
import com.example.whatrubbish.util.ActivityUtil;
import com.example.whatrubbish.util.HttpUtil;
import com.example.whatrubbish.util.ThreadPoolFactory;
import com.example.whatrubbish.util.ThreadPoolManager;
import com.example.whatrubbish.util.ToastUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;

import lombok.SneakyThrows;

public class ProblemActivity extends AppCompatActivity {

    //private ActivityRegisterBinding binding;
    //private ActivityPersonalSettingBinding binding;
    //private FragmentProblemBinding binding;
    private ActivityProblemBinding binding;
    //ProblemA

    //    Repository repository;
//

    int curProblemIdx = 0;

    void putProblem(){

    }

    public interface Listener {
        //void onItemClickListener(RubbishType rubbishType);
        void onAnsRightListener();
    }
    Listener listener;

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }



    void initProblems() throws IOException {
        //JsonObject post = HttpUtil.post(Bus.baseDbUrl + "/problem/list", new Problem());
        //int code = post.get("code").getAsInt();

        JsonObject post = HttpUtil.post(Bus.baseDbUrl + "/problem/randomProblem", new Problem());
        int code = post.get("code").getAsInt();
        if (code == 200) {
            //post.get("data").getAsJsonObject().get("content")
            //post.get("data").getAsJsonObject().get(Bus.contentMark)
            //JsonObject asJsonObject = post.get(Bus.dataMark).getAsJsonObject().get(Bus.contentMark).getAsJsonObject();
            //JsonArray asJsonArray = post.get(Bus.dataMark).getAsJsonObject().get(Bus.contentMark).getAsJsonArray();
            JsonObject asJsonObject = post.get(Bus.dataMark).getAsJsonObject();
            Problem problem = Bus.gson.fromJson(asJsonObject, Problem.class);
            //随机出一题 吗
            //Problem problem = Bus.gson.fromJson(asJsonObject, Problem.class);
            //Problem[] problems = Bus.gson.fromJson(asJsonArray, Problem[].class);
            //Log.d("problems", "wasteTimeInit: " + Arrays.toString(problems));
            ProblemFragment problemFragment = new ProblemFragment();
            //problemFragment.setProblem(problems[curProblemIdx]);
            problemFragment.setProblem(problem);


            ProblemFragment.Listener listener = new ProblemFragment.Listener() {

                //安卓 线程 异步返回
                @Override
                public void onAnsRightListener() {

                    if(   Bus.curUser==null){
                        ToastUtil.show(ProblemActivity.this,"没有登录 请登录");
                        return;
                    }
                    try {

                        //CollectRubFragment.MHandler mHandler = new CollectRubFragment.MHandler(Looper.getMainLooper());
                        ExecutorService service = ThreadPoolFactory.getExecutorService();
                        service.execute(() -> {
                            try {
                                //Bus.curUser.addPoint(1);
                                User cloneUser = Bus.curUser.clone();
                                cloneUser.addPoint(1);
                                //JsonObject post = HttpUtil.post(Bus.baseDbUrl + Bus.userSave, Bus.curUser);
                                JsonObject post = HttpUtil.post(Bus.baseDbUrl + Bus.userSave,cloneUser);
                                Log.d("post", "onAnsRightListener: " + post);

                                //if(listener!=null){
                                //    listener.onAnsRightListener();
                                //}
                                ProblemActivity.this.runOnUiThread(()->{
                                    ToastUtil.show(ProblemActivity.this,"答对了 恭喜增加一个积分");


                                    //String   msg="添加他吗? "+ finalNickname;
                                    //new AlertDialog.Builder(   ProblemActivity.this).setTitle("恭喜增加一个积分")
                                    //        .setMessage("恭喜增加一个积分").
                                    //        setPositiveButton("确定", (dialog, which) -> {
                                    //
                                    //        }).
                                    //        setNegativeButton("取消", (dialog, which) -> {
                                    //        }).show();
                                });

                                Bus.curUser.addPoint(1);
                            } catch (Exception e) {
                                e.printStackTrace();
                                ProblemActivity.this.runOnUiThread(()->{
                                    ToastUtil.show(ProblemActivity.this,"答对了 但是增加积分失败 "+e.getMessage());
                                    //new AlertDialog.Builder(   ProblemActivity.this).setTitle("增加积分失败")
                                    //        .setMessage(e.getMessage()).
                                    //        setPositiveButton("确定", (dialog, which) -> {
                                    //        }).
                                    //        setNegativeButton("取消", (dialog, which) -> {
                                    //        }).show();
                                });




                            }
                        });
                        //for (int i = 0; i < 50; i++) {
                        //
                        //}

                        //Thread thread = new Thread(() -> {
                        //
                        //    try {
                        //        JsonObject post = HttpUtil.post(Bus.baseDbUrl + Bus.userSave, Bus.curUser);
                        //        Log.d("post", "onAnsRightListener: " + post);
                        //    } catch (Exception e) {
                        //        e.printStackTrace();
                        //    }
                        //
                        //});
                        //ThreadPoolManager.run(thread);
                        //ToastUtil.show(ProblemActivity.this,"没有登录 请登录");
                        //finishActivity(1);
                        finish();
                        //ActivityUtil.startActivity(this, CollectRubFragment);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
            problemFragment.setListener(listener);
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            //FragmentManager fragmentManager = getFragmentManager();
            //Fragment fragment;
            //FragmentManager supportFragmentManager = fragment.getChildFragmentManager()();
            this.runOnUiThread(() -> {
                Log.d("supportFragmentManager", "initProblems: " + supportFragmentManager);
                supportFragmentManager.beginTransaction().
                        replace(R.id.holder, problemFragment).addToBackStack(null).commit();
            });

        }
    }

    void wasteTimeInit() throws IOException {
        Thread thread = new Thread(() -> {
            try {
                initProblems();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ThreadPoolManager.run(thread);
    }

    UserRepository userRepository;
    Repository repository;

    void initDatabase() {
        repository = new Repository(this);
        userRepository = repository.getUserRepository();
//        userRepository = App.getRepository().getUserRepository();
    }


    void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }


    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //binding = FragmentProblemBinding.inflate(getLayoutInflater());
        binding = ActivityProblemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        wasteTimeInit();
    }

    void updateUser() {
        try {
            JsonObject post = HttpUtil.post(Bus.baseDbUrl + "/user/update", Bus.curUser);
            Log.d("post", "updateUser: " + post);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void getRank() {
        try {
            JsonObject post = HttpUtil.post(Bus.baseDbUrl + "/user/rank", Bus.curUser);
            Log.d("post", "updateUser: " + post);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}