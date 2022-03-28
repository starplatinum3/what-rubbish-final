package com.example.smlj;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

//import com.example.whatrubbish.LoginActivity;
import com.example.whatrubbish.Bus;
import com.example.whatrubbish.MainActivity;
import com.example.whatrubbish.R;
import com.example.whatrubbish.RegionSettingActivity;
import com.example.whatrubbish.RegisterActivity;
import com.example.whatrubbish.db.AppDatabase;
import com.example.whatrubbish.db.Repository;
import com.example.whatrubbish.db.RoomUtil;
import com.example.whatrubbish.entity.ImUser;
import com.example.whatrubbish.entity.User;
import com.example.whatrubbish.service.UserService;
import com.example.whatrubbish.service.UserServiceKt;
import com.example.whatrubbish.util.ActivityUtil;
import com.example.whatrubbish.util.HttpUtil;
import com.example.whatrubbish.util.JsonJavaUtil;
import com.example.whatrubbish.util.JsonUtil;
import com.example.whatrubbish.util.ThreadPoolManager;
import com.example.whatrubbish.util.ToastUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sdust.im.util.MyWebSocketClient;

import org.java_websocket.client.WebSocketClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import en.edu.zucc.pb.loginapplication.util.Security;
import lombok.SneakyThrows;
import lombok.val;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class login extends Activity {

    TextView btnRegisterNow;
    TextView tvUsername;
    TextView tvPassword;
    View view;
    View bottom;


    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initDatabase();
        btnRegisterNow = findViewById(R.id.btnRegisterNow);
        btnRegisterNow.setOnClickListener(v -> {
            ActivityUtil.startActivity(this, RegisterActivity.class);
        });
        tvPassword = findViewById(R.id.userpst);
        tvUsername = findViewById(R.id.username);
        bottom = findViewById(R.id.bottom);
        view = findViewById(R.id.btn_login);
//        tvUsername.setOnClickListener(v -> {
//            addLayoutListener(view,btnRegisterNow);
//        });
        addLayoutListener(view, bottom);
        findViewById(R.id.button).setOnClickListener(v -> {
            //登录
            loginHttp();

        });


    }

    void loginUserPost(String username, String password) {
        ThreadPoolManager.getInstance().execute(new FutureTask<>(new Thread(() -> {
            try {
                User userForm = new User();
                userForm.setUsername(username);
                //userForm.setPassword(Security.md5(password));
                userForm.setPassword(password);
                //User userForm= User.builder().
                //        username(username).password(Security.md5(password)).build();
                //所有的user 找到的是一个
                JsonObject listRes = HttpUtil.post(Bus.baseDbUrl + "/user/login", userForm);
                Log.d("listRes", "loginHttp: listRes " + listRes);
                int code = listRes.get("code").getAsInt();
                if (code != Bus.codeSuccess) {
                    //ToastUtil.show(login.this,"登录失败 后端出错");
                    ToastUtil.showOnUiThread(login.this, "登录失败 后端出错");
                    return;
                }
                JsonObject asJsonObject1 = listRes.get(Bus.dataMark).getAsJsonObject();
                //JsonObject asJsonObject = listRes.get(Bus.dataMark).getAsJsonObject();
                ////JsonArray asJsonArray = listRes.get(Bus.contentMark).getAsJsonArray();
                //JsonArray asJsonArray = asJsonObject.get(Bus.contentMark).getAsJsonArray();
                //Log.d("asJsonArray", "loginHttp: asJsonArray "+asJsonArray);
                runOnUiThread(() -> {
                    //ToastUtil.show(this,"登录错误 请检查账号密码");
                    //if(asJsonArray.size()==0){
                    //    ToastUtil.show(this,"登录错误 请检查账号密码");
                    //    return;
                    //}
                    //if(asJsonArray.size()!=1){
                    //    ToastUtil.show(this,"登录错误(有多个这样的账号) 请联系管理员 您的账号 : "+username);
                    //    return;
                    //}
                    //User user = Bus.gson.fromJson(asJsonArray.get(0), User.class);
                    User user = Bus.gson.fromJson(asJsonObject1, User.class);

                    //User user = users.get(0);
                    Bus.curUser = user;
                    Log.d("user", "login: user  " + user);
                    if (user.getCityId() == null) {
                        ActivityUtil.startActivity(this, RegionSettingActivity.class);

                    } else {
                        ActivityUtil.startActivity(this, MainActivity.class);
                    }


                });


            } catch (Exception e) {
                e.printStackTrace();
                login.this.runOnUiThread(() -> {
                    ToastUtil.show(login.this, "登录失败 " + e.getMessage());

                });
            }
        }), null), null);
    }


    void loginUser(String username, String password) {
        ThreadPoolManager.getInstance().execute(new FutureTask<>(new Thread(() -> {
            try {
                User userForm = new User();
                userForm.setUsername(username);
                userForm.setPassword(Security.md5(password));
                //User userForm= User.builder().
                //        username(username).password(Security.md5(password)).build();
                //所有的user 找到的是一个
                JsonObject listRes = HttpUtil.post(Bus.baseDbUrl + "/user/list", userForm);
                Log.d("listRes", "loginHttp: listRes " + listRes);
                int code = listRes.get("code").getAsInt();
                if (code != Bus.codeSuccess) {
                    ToastUtil.show(login.this, "登录失败 后端出错");
                    return;
                }
                JsonObject asJsonObject = listRes.get(Bus.dataMark).getAsJsonObject();
                //JsonArray asJsonArray = listRes.get(Bus.contentMark).getAsJsonArray();
                JsonArray asJsonArray = asJsonObject.get(Bus.contentMark).getAsJsonArray();
                Log.d("asJsonArray", "loginHttp: asJsonArray " + asJsonArray);
                runOnUiThread(() -> {
                    //ToastUtil.show(this,"登录错误 请检查账号密码");
                    if (asJsonArray.size() == 0) {
                        ToastUtil.show(this, "登录错误 请检查账号密码");
                        return;
                    }
                    if (asJsonArray.size() != 1) {
                        ToastUtil.show(this, "登录错误(有多个这样的账号) 请联系管理员 您的账号 : " + username);
                        return;
                    }
                    User user = Bus.gson.fromJson(asJsonArray.get(0), User.class);

                    //User user = users.get(0);
                    Bus.curUser = user;
                    Log.d("user", "login: user  " + user);
                    if (user.getCityId() == null) {
                        ActivityUtil.startActivity(this, RegionSettingActivity.class);

                    } else {
                        ActivityUtil.startActivity(this, MainActivity.class);
                    }


                });


            } catch (Exception e) {
                e.printStackTrace();
                login.this.runOnUiThread(() -> {
                    ToastUtil.show(login.this, "登录失败 " + e.getMessage());

                });
            }
        }), null), null);
    }

    Repository repository;
    AppDatabase database;

    void initDatabase() {
        repository = new Repository(this);
        database = AppDatabase.getDatabase(this);
    }

    void loginHttp() {
        String username = tvUsername.getText().toString();
        //database.userDao().insert(User.builder().username("111").password(Security.md5("111")))
        if (username.equals("")) {
            ToastUtil.show(this, "请填写用户名");
            return;
        }
        String password = tvPassword.getText().toString();
        if (password.equals("")) {
            ToastUtil.show(this, "请填写密码");
            return;
        }

        //ThreadPoolManager.getInstance().execute(new FutureTask<>(new Thread(()->{
        //    try {
        //        User userForm = new User();
        //        userForm.setUsername(username);
        //        userForm.setPassword(Security.md5(password));
        //        //User userForm= User.builder().
        //        //        username(username).password(Security.md5(password)).build();
        //        //所有的user 找到的是一个
        //        JsonObject listRes = HttpUtil.post(Bus.baseDbUrl + "/user/list",userForm);
        //        Log.d("listRes", "loginHttp: listRes "+listRes);
        //        int code = listRes.get("code").getAsInt();
        //        if(code!=Bus.codeSuccess){
        //            ToastUtil.show(login.this,"登录失败 后端出错");
        //            return;
        //        }
        //        JsonObject asJsonObject = listRes.get(Bus.dataMark).getAsJsonObject();
        //        //JsonArray asJsonArray = listRes.get(Bus.contentMark).getAsJsonArray();
        //        JsonArray asJsonArray = asJsonObject.get(Bus.contentMark).getAsJsonArray();
        //        Log.d("asJsonArray", "loginHttp: asJsonArray "+asJsonArray);
        //        runOnUiThread(()->{
        //            //ToastUtil.show(this,"登录错误 请检查账号密码");
        //            if(asJsonArray.size()==0){
        //                ToastUtil.show(this,"登录错误 请检查账号密码");
        //                return;
        //            }
        //            if(asJsonArray.size()!=1){
        //                ToastUtil.show(this,"登录错误(有多个这样的账号) 请联系管理员 您的账号 : "+username);
        //                return;
        //            }
        //            User user = Bus.gson.fromJson(asJsonArray.get(0), User.class);
        //
        //            //User user = users.get(0);
        //            Bus.curUser=user;
        //            Log.d("user", "login: user  "+user);
        //            if(user.getCityId()==null){
        //                ActivityUtil.startActivity(this, RegionSettingActivity.class);
        //
        //            }else{
        //                ActivityUtil.startActivity(this, MainActivity.class);
        //            }
        //
        //
        //
        //        });
        //
        //
        //
        //    } catch (Exception e) {
        //        e.printStackTrace();
        //        login.this.runOnUiThread(()->{
        //            ToastUtil.show(login.this,"登录失败 "+e.getMessage());
        //
        //        });
        //    }
        //}),null),null);
        Bus.loginForm = new User();
        Bus.loginForm.setUsername(username);
        Bus.loginForm.setPassword(password);

        Callback userInitCb = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JsonObject jsonObject = JsonJavaUtil.responseToJsonObject(response);
                JsonObject me = jsonObject.get("me").getAsJsonObject();
                JsonArray friends = jsonObject.get("friends").getAsJsonArray();
                JsonArray groups = jsonObject.get("groups").getAsJsonArray();
                JsonElement userRub1 = jsonObject.get("userRub");
                if (userRub1==null) {
                    ToastUtil.showOnUiThread(login.this,"userRub 是空的 用户没有注册成功");
                    return;
                }

                //JsonObject userRub = jsonObject.get("userRub").getAsJsonObject();
                JsonObject userRub =userRub1.getAsJsonObject();
                //JsonUtil.
                ImUser imUser = JsonJavaUtil.jsonObjToObj(me, ImUser.class);
                User user = JsonJavaUtil.jsonObjToObj(userRub, User.class);
                //但是他没有city
                //注册的时候 同时注册了 user
                //User user = new User();
                //user.setCityId();
                //user.seimus
                //imUser.get
                Log.i("imUser", "onResponse: " + imUser);
                Log.i("user", "onResponse: " + user);
                Bus.curImUser = imUser;
                Bus.curUser = user;
                Bus.friends = friends;
                Bus.groups = groups;
                //ActivityUtil.startActivityOnUiThread(login.this,);
                if (user.getCityId() == null) {
                    ActivityUtil.startActivity(login.this, RegionSettingActivity.class);

                } else {
                    ActivityUtil.startActivity(login.this, MainActivity.class);
                }
                Bus.myWebSocketClient= MyWebSocketClient.getInstance();
                Bus.myWebSocketClient.setContext(login.this);

            }
        };

        //这里为啥要放呢 主要是为了im的登录 因为他需要的不是md5 的密码
        UserService.GetTokenCallback callback = new UserService.GetTokenCallback() {
            @Override
            public void onSuccess() {
                //怎么去获取用户信息呢
                //loginUserPost(username,password);
                Call call = UserServiceKt.Companion.userInit();
                if(call==null){
                    //ToastUtil.show(login.this,"init失败");
                    ToastUtil.showOnUiThread(login.this,"init失败");
                    return;
                }
                call.enqueue(userInitCb);
                //call.enqueue(new Callback() {
                //    @Override
                //    public void onFailure(Call call, IOException e) {
                //
                //    }
                //
                //    @Override
                //    public void onResponse(Call call, Response response) throws IOException {
                //        //String string = response.body().string();
                //        ////JsonJavaUtil.co
                //        //JsonObject jsonObject = JsonUtil.Companion.strToJsonObject(string);
                //        JsonObject jsonObject = JsonJavaUtil.responseToJsonObject(response);
                //    }
                //});
            }

            @Override
            public void onFail() {

                Log.i("onFail", "onFail: ");
            }
        };
        UserService.getToken(username, password, callback);


    }
//    private void initChatUi(){
//        mRvChat.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                if (bottom < oldBottom) {
//                    mRvChat.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (mAdapter.getItemCount() > 0) {
//                                mRvChat.smoothScrollToPosition(mAdapter.getItemCount() - 1);
//                            }
//                        }
//                    });
//                }
//            }
//        });
//        //点击空白区域关闭键盘
//        mRvChat.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                mUiHelper.hideBottomLayout(false);
//                mUiHelper.hideSoftInput();
//                mEtContent.clearFocus();
//                mIvEmo.setImageResource(R.mipmap.ic_emoji);
//                return false;
//            }
//        });

//    }


    public void addLayoutListener(final View main, final View scroll) {
        main.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();  //1、获取main在窗体的可视区域
                main.getWindowVisibleDisplayFrame(rect);  //2、获取main在窗体的不可视区域高度，在键盘没有弹起时，main.getRootView().getHeight()调节度应该和rect.bottom高度一样
                int mainInvisibleHeight = main.getRootView().getHeight() - rect.bottom;
                int screenHeight = main.getRootView().getHeight();//屏幕高度
                //3、不可见区域大于屏幕本身高度的1/4：说明键盘弹起了
                if (mainInvisibleHeight > screenHeight / 4) {
                    int[] location = new int[2];
                    scroll.getLocationInWindow(location);   // 4､获取Scroll的窗体坐标，算出main需要滚动的高度
                    int srollHeight = (location[1] + scroll.getHeight()) - rect.bottom;   //5､让界面整体上移键盘的高度
                    //  int srollHeight = 500;  //5､让界面整体上移键盘的高度
                    // int srollHeight =  screenHeight / 3;
                    main.scrollTo(0, srollHeight);
                } else {  //3、不可见区域小于屏幕高度1/4时,说明键盘隐藏了，把界面下移，移回到原有高度
                    main.scrollTo(0, 0);
                }
            }
        });
    }
}
//    void login() throws ExecutionException, InterruptedException {
//        String  username = tvUsername.getText().toString();
//        //database.userDao().insert(User.builder().username("111").password(Security.md5("111")))
//        if(username.equals("")){
//            ToastUtil.show(this,"请填写用户名");
//            return;
//        }
//        String  password = tvPassword.getText().toString();
//        if(password.equals("")){
//            ToastUtil.show(this,"请填写密码");
//            return;
//        }
//        List<User> users = repository.getUserRepository().
//                selectBy(User.builder().username(username).password(Security.md5(password)).build());
//        if(users.size()==0){
//            ToastUtil.show(this,"登录错误 请检查账号密码");
//            return;
//        }
//
//        if(users.size()!=1){
//            ToastUtil.show(this,"登录错误(有多个这样的账号) 请联系管理员 您的账号 : "+username);
//            return;
//        }
//
//        User user = users.get(0);
//        Bus.curUser=user;
//        Log.d("user", "login: user  "+user);
//
//        if(user.getCityId()==null){
//            ActivityUtil.startActivity(this, RegionSettingActivity.class);
//
//        }else{
//            ActivityUtil.startActivity(this, MainActivity.class);
//        }
//    }

//    public void loginbuttom(View view) {
//        Intent intent = new Intent(login.this, messages.class);
//        startActivity(intent);
//    }


//}
