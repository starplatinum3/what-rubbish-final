package com.example.whatrubbish;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.util.StringUtil;

import com.example.smlj.login;
import com.example.whatrubbish.databinding.ActivityMainBinding;
import com.example.whatrubbish.databinding.ActivityRegisterBinding;
import com.example.whatrubbish.db.App;
import com.example.whatrubbish.db.Repository;
import com.example.whatrubbish.entity.User;
import com.example.whatrubbish.repository.UserRepository;
import com.example.whatrubbish.span.CustomUrlSpan;
import com.example.whatrubbish.util.HttpUtil;
import com.example.whatrubbish.util.ThreadPoolManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import en.edu.zucc.pb.loginapplication.util.Security;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    //    Repository repository;
//
    UserRepository userRepository;
    Repository repository;

    void initDatabase() {
        repository = new Repository(this);
        userRepository = repository.getUserRepository();
//        userRepository = App.getRepository().getUserRepository();
    }

    void btnRegisterBind() {

    }

    /**
     * 拦截超链接
     *
     * @param tv
     */
    private void interceptHyperLink(TextView tv) {
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        CharSequence text = tv.getText();
        if (text instanceof Spannable) {
            int end = text.length();
            Spannable spannable = (Spannable) tv.getText();
            URLSpan[] urlSpans = spannable.getSpans(0, end, URLSpan.class);
            if (urlSpans.length == 0) {
                return;
            }

            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
            // 循环遍历并拦截 所有http://开头的链接
            //String markStr="点此登录";
            String markStr = "返回登录";
            for (URLSpan uri : urlSpans) {
                String url = uri.getURL();
                //if (url.indexOf("http://") == 0) {
                if (url.indexOf(markStr) == 0) {
                    CustomUrlSpan customUrlSpan = new CustomUrlSpan(this, url);
                    spannableStringBuilder.setSpan(customUrlSpan, spannable.getSpanStart(uri),
                            spannable.getSpanEnd(uri), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                }
            }
            tv.setText(spannableStringBuilder);
        }
    }

    //void checkRegister
    void  registerHttp(){


//            注册
        String phoneStr = binding.etPhone.getText().toString();
//            if(StringUtil.)
        if (phoneStr.equals("")) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;


        }
        String passwordStr = binding.etPassword.getText().toString();
        if (passwordStr.equals("")) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        String etPasswordAgainStr = binding.etPasswordAgain.getText().toString();
        if (etPasswordAgainStr.equals("")) {
            Toast.makeText(this, "请再次输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!etPasswordAgainStr.equals(passwordStr)) {
            Toast.makeText(this, "两次输入密码不同 请检查", Toast.LENGTH_SHORT).show();
            return;
        }

        ThreadPoolManager.getInstance().execute(new FutureTask<>(new Thread(() -> {


            JsonObject post = null;
            try {

                User user = new User();
                user.setPhone(phoneStr);

                JsonObject listRes = HttpUtil.post(Bus.baseDbUrl + "/user/list",user);
                //JsonObject listRes = HttpUtil.post(Bus.baseDbUrl + "/user/list", User.builder().phone(phoneStr).build());
                JsonArray asJsonArray = listRes.get(Bus.contentMark).getAsJsonArray();
                if(asJsonArray.size() >= 1){
                    runOnUiThread(()->{
                        Toast.makeText(this, "这个手机已经被使用了 " + phoneStr, Toast.LENGTH_SHORT).show();
                    });
                    return;
                }


                User build = new User();
                //user1.setPhone();
                build.setPassword(Security.md5(passwordStr));
                build.setUsername(phoneStr);
                build.setPhone(phoneStr);
                //User build = User.builder().
                //        password(Security.md5(passwordStr)).username(phoneStr).phone(phoneStr).build();
                Log.d("build", "registerHttp: build"+build);

                post = HttpUtil.post(Bus.baseDbUrl + "/user/save", build);
                int id = post.get("id").getAsInt();



                runOnUiThread(()->{
                    Toast.makeText(this, "注册成功 账号： " + phoneStr + "  将跳转到登录页", Toast.LENGTH_SHORT).show();

                    startActivity(login.class);
                    //这个要放在 ui 线程
//                    int waitMs = 3000;
////        https://blog.csdn.net/qq_26460841/article/details/106833887
//                    // 这里会回调它本身的一个handleMessage方法
//                    new Handler(message -> {
//                        // 3秒后将调用这个handleMessage函数
//                        startActivity(login.class);
////            登录玩了 去 选择城市的界面
////            登录那边 判断 是不是有城市 如果有的话 就不用选择了 就去首页 就是main activity
//                        return false;
//                    }).sendEmptyMessageDelayed(0, waitMs); // 延迟3秒
                });



            } catch (IOException e) {
                e.printStackTrace();
                //线程里的异常 怎么   throw 到ui线程 安卓
                Log.d("注册失败", "register: 注册失败");
                runOnUiThread(()->{
                    Toast.makeText(this, "注册失败 账号： " + phoneStr+"  错误信息  "+e.getMessage() , Toast.LENGTH_SHORT).show();
                });
                throw new RuntimeException("注册失败");
            }


        }), null), null);




    }
//————————————————
//        版权声明：本文为CSDN博主「zhangjinhuang」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/zhangjinhuang/article/details/52416608

    void register() throws ExecutionException, InterruptedException, IOException {

//            注册
        String phoneStr = binding.etPhone.getText().toString();
//            if(StringUtil.)
        if (phoneStr.equals("")) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;


        }
        String passwordStr = binding.etPassword.getText().toString();
        if (passwordStr.equals("")) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        String etPasswordAgainStr = binding.etPasswordAgain.getText().toString();
        if (etPasswordAgainStr.equals("")) {
            Toast.makeText(this, "请再次输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!etPasswordAgainStr.equals(passwordStr)) {
            Toast.makeText(this, "两次输入密码不同 请检查", Toast.LENGTH_SHORT).show();
            return;
        }
//            UserRepository userRepository = App.getRepository().getUserRepository();

        User userFormSqlThisPhone = new User();
        userFormSqlThisPhone.setPhone(phoneStr);



        List<User> users = userRepository.selectBy(userFormSqlThisPhone);
        if (users.size() >= 1) {
            Toast.makeText(this, "这个手机已经被使用了 " + phoneStr, Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User();

        user.setPassword(Security.md5(passwordStr));
        user.setUsername(phoneStr);
        user.setPhone(phoneStr);
        userRepository.insert(user);
        //HttpUtil.post





//        App.getRepository().getUserRepository().insert(user);
        Toast.makeText(this, "注册成功 账号： " + phoneStr + "  将跳转到登录页", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(LoginActivity.class);

        int waitMs = 3000;
//        https://blog.csdn.net/qq_26460841/article/details/106833887
        // 这里会回调它本身的一个handleMessage方法
        new Handler(message -> {
            // 3秒后将调用这个handleMessage函数
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//            startActivity(LoginActivity.class);
            startActivity(login.class);
//            登录玩了 去 选择城市的界面
//            登录那边 判断 是不是有城市 如果有的话 就不用选择了 就去首页 就是main activity
            return false;
        }).sendEmptyMessageDelayed(0, waitMs); // 延迟3秒


    }

    void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initDatabase();

        //interceptHyperLink(binding.allReadyHave);


        binding.allReadyHave.setOnClickListener(v -> {
//            startActivity(LoginActivity.class);
            startActivity(login.class);
        });
        binding.btnRegister.setOnClickListener(v -> {
            //try {
            //    register();
            //    registerHttp();
            //} catch (ExecutionException | InterruptedException | IOException e) {
            //    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            //    e.printStackTrace();
            //}

            registerHttp();
        });
//        App.getRepository().getUserRepository().de
//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard,
//                R.id.navigation_notifications,R.id.navigation_personal)
//                .build();
//        NavController navController =
//                Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}