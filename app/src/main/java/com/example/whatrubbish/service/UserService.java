package com.example.whatrubbish.service;

import android.app.Activity;
import android.util.Log;

import com.example.whatrubbish.Bus;
import com.example.whatrubbish.activity.ProblemActivity;
import com.example.whatrubbish.db.AppDatabase;
import com.example.whatrubbish.db.BaseDao;
import com.example.whatrubbish.db.BaseRepository;
import com.example.whatrubbish.db.Repository;
import com.example.whatrubbish.dbo.RubbishInfoDbo;
import com.example.whatrubbish.entity.Article;
import com.example.whatrubbish.entity.CardGame;
import com.example.whatrubbish.entity.RubbishInfo;
import com.example.whatrubbish.util.HttpUtil;
import com.example.whatrubbish.util.JsonUtil;
import com.example.whatrubbish.util.ToastUtil;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserService {

    Activity activity;
    Repository repository;
    void initDatabase() {
        repository=new Repository(activity);
    }

   public   interface  GetTokenCallback{
       void  onSuccess();
       void  onFail();
    }

    public  static  void getToken(String usernameStr,String passwordStr,GetTokenCallback callback){
        //java 怎么传入 回调函数
        OkHttpClient okHttpClient =new OkHttpClient();
        //RequestBody:FormBody，表单键值对
        //okhttp3.RequestBody formBody = new FormBody.Builder()
        //        .add("username", "hfy")
        //        .add("password", "qaz")
        //        .build();

        //RequestBody:FormBody，表单键值对
        //okhttp3.RequestBody formBody = new FormBody.Builder()
        //        .add("username", "hfy")
        //        .add("password", "qaz")
        //        .build();

//            kotlin compose 获取 activity
//            getAc



        //String usernameStr=username.value
        //String passwordStr=password.value
        FormBody formBody = new FormBody.Builder()
                .add("client_id","v-client")
                .add("client_secret","v-client-ppp")
                .add("grant_type","password")
                .add("scope","select")
                .add("username",usernameStr)
                .add("password",passwordStr).build();
        Request getRequest =
                new Request.Builder() //.url("https://api.github.com/markdown/raw")
                        .url(Bus.baseDbUrl+"/oauth/token") //.addHeader()
                        .post(formBody)
                        .build();


        Call newCall = okHttpClient.newCall(getRequest);
//            Callback.
//            kotlin okhttp3 callback
//            newCall.enqueue(object :Callback{
//                override fun onFailure(call: Call, e: IOException) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onResponse(call: Call, response: Response) {
//                    TODO("Not yet implemented")
//                }
//
//            })

//            https://www.jianshu.com/p/9fe3ba7cdf88
        newCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                callback.onFail();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();

                if (responseData != null) {
                    //但是用的他的账号密码 token 获取不到的吧
                    JsonObject token=  JsonUtil.Companion.strToJsonObject(responseData);
                    //val token = JsonUtil.strToJsonObject(responseData)
                    Bus.token = token;
//                        navigation.
//                        navController.navigate("second_screen")
                    Log.i(" Bus.token", "onResponse: "+ Bus.token);
//                        val activity = context.getActivity()
//                    if(activity==null){
//                        Log.i("activity", "onResponse: null");
//                        return;
//                    }

                }
                callback.onSuccess();

            }
        });
    }

    public UserService(Activity activity) {
        this.activity = activity;
    }

    void  registerHttp(){

    }

    //void userAddPoint(){
    //    if(   Bus.curUser==null){
    //        ToastUtil.show(activity,"没有登录 请登录");
    //        return;
    //    }
    //    try {
    //        Bus.curUser.addPoint(1);
    //        JsonObject post = HttpUtil.post(Bus.baseDbUrl + Bus.userSave, Bus.curUser);
    //        Log.d("post", "onAnsRightListener: " + post);
    //        //finishActivity(1);
    //        finish();
    //        //ActivityUtil.startActivity(this, CollectRubFragment);
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //    }
    //}

    void sendMessage(){
//        Message message = new Message();
//        message.setContent();
//        RoomUtil.
        AppDatabase database = AppDatabase.getDatabase(activity);
//        articleRepository = new ArticleRepository(database.articleDao());
        BaseDao<Article> baseDao;
//        BaseRepository<Article> articleBaseRepository=new BaseRepository<>(new BaseDao<Article>);
        BaseRepository<CardGame> cardGameBaseRepository=new BaseRepository<>(database.cardGameDao());
//        cardGameBaseRepository.insert();
//        repository.getArticleRepository().insert(message);

    }
    //api 拿到数据之后要 保存
//   public  JsonObject save(RubbishInfo rubbishInfo) throws IOException {
////        HTTP http = HTTP.builder()
//////                .baseUrl("http://api.tianapi.com/lajifenlei/index?key="+keyRubbish+"&word=")
////                .baseUrl(Bus.baseDbUrl)
////                .addMsgConvertor(new GsonMsgConvertor())
////                .build();
//        //Bus.httpDb.async()
//        //JsonObject post = HttpUtil.post(Bus.baseDbUrl+"/rubbishInfo/save", rubbishInfo);
//        //return post;
//
//       if(   Bus.curUser==null){
//           ToastUtil.show(activity,"没有登录 请登录");
//           return;
//       }
//       try {
//           Bus.curUser.addPoint(1);
//           JsonObject post = HttpUtil.post(Bus.baseDbUrl + Bus.userSave, Bus.curUser);
//           Log.d("post", "onAnsRightListener: " + post);
//           //finishActivity(1);
//          //activity. finish();
//           //ActivityUtil.startActivity(this, CollectRubFragment);
//       } catch (Exception e) {
//           e.printStackTrace();
//       }
//    }

    public static JsonObject save(RubbishInfo rubbishInfo) throws IOException {
//        HTTP http = HTTP.builder()
////                .baseUrl("http://api.tianapi.com/lajifenlei/index?key="+keyRubbish+"&word=")
//                .baseUrl(Bus.baseDbUrl)
//                .addMsgConvertor(new GsonMsgConvertor())
//                .build();
        //Bus.httpDb.async()
        JsonObject post = HttpUtil.post(Bus.baseDbUrl+"/rubbishInfo/save", rubbishInfo);
        return post;
    }


    public static JsonObject save(RubbishInfoDbo rubbishInfoDbo) throws IOException {
//        HTTP http = HTTP.builder()
////                .baseUrl("http://api.tianapi.com/lajifenlei/index?key="+keyRubbish+"&word=")
//                .baseUrl(Bus.baseDbUrl)
//                .addMsgConvertor(new GsonMsgConvertor())
//                .build();
        //Bus.httpDb.async()
        JsonObject post = HttpUtil.post(Bus.baseDbUrl+"/rubbishInfo/save", rubbishInfoDbo);
        return post;
    }
}
