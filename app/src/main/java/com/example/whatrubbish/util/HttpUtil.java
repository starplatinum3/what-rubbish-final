package com.example.whatrubbish.util;

import android.util.Log;

import com.ejlchina.okhttps.GsonMsgConvertor;
import com.ejlchina.okhttps.HTTP;
import com.example.whatrubbish.Bus;
import com.example.whatrubbish.entity.RubbishInfo;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HttpUtil {


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    //static OkHttpClient client = new OkHttpClient();
    static OkHttpClient client;

    public static OkHttpClient getClient() {
        if (client == null) {
            return new OkHttpClient();
        }
        return client;
    }

    public static JsonObject strToJson(String string) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(string, JsonObject.class);
        return jsonObject;
    }


    public static HTTP getHttp(String url) {
        HTTP http = HTTP.builder()
                .baseUrl(url)
                .addMsgConvertor(new GsonMsgConvertor())
                .build();
        return http;
    }

//    void d(String  baseUrl){
//        HTTP http = HTTP.builder()
////                .baseUrl("http://api.tianapi.com/lajifenlei/index?key="+keyRubbish+"&word=")
////                .baseUrl(Bus.tianapiBaseUrl)
//                .baseUrl(baseUrl)
//                .addMsgConvertor(new GsonMsgConvertor())
//                .build();
//
//
////
////        HTTP http = HTTP.builder()
//////                .baseUrl("http://api.tianapi.com/lajifenlei/index?key="+keyRubbish+"&word=")
////                .baseUrl("http://api.tianapi.com/lajifenlei/index")
////                .addMsgConvertor(new GsonMsgConvertor())
////                .build();
//
////        http://localhost:8889/tbAdmin/find?id=1
//
////        List<User> users = http.sync("/users") // http://api.example.com/users
////                .addPathPara("key",keyRubbish)
////                .addPathPara("word",searchWord)
////                .post()                         // GET请求
////                .getBody()                     // 获取响应报文体
////                .toList(User.class);           // 得到目标数据
//
////        Mapper mapper = http.sync("/users") // http://api.example.com/users
////                .addPathPara("key", keyRubbish)
////                .addPathPara("word", searchWord)
////                .post()                         // GET请求
////                .getBody().toMapper();
////        Mapper 可以直接映射为实体吗 okhttps
////        Mapper mapper = http.sync("/find") // http://api.example.com/users
////                .addPathPara("id", 1)
////                .post()                         // GET请求
////                .getBody().toMapper();
//
////        Mapper mapper = http.sync("") // http://api.example.com/users
//////                .addPathPara("id", 1)
////                .post()                         // GET请求
////                .getBody().toMapper();
//
////        Mapper mapper = http.sync("") // http://api.example.com/users
//////                .addPathPara("id", 1)
////                .get()                        // GET请求
////                .getBody().toMapper();
////        okhhtps 没有反应
//
////        String s = http.sync("") // http://api.example.com/users
//////                .addPathPara("id", 1)
////                .get()                        // GET请求
////                .getBody().toString();
//
//        //Object keyRubbish;
//        String res = http.sync("") // http://api.example.com/users
//                .addPathPara(Bus.APIKEY_TOKEN, Bus.keyRubbish)
//                .addPathPara(Bus.WORD_TOKEN, searchWord)
//                .get()                        // GET请求
//                .getBody().toString();
////        HttpUtil
//
//        Log.i("res",res);
//        Gson gson = new Gson();
//        JsonObject jsonObject = gson.fromJson(res, JsonObject.class);
//    }

    //public void setClient(OkHttpClient client) {
    //    this.client = client;
    //}
//    可以用

    /**
     * @param url
     * @param jsonBody 是放在 body 里面的
     * @return
     * @throws IOException
     */
    public static String post(String url, String jsonBody) throws IOException {
//        OkHttpClient client = new OkHttpClient();
//        RequestBody body = RequestBody.create(JSON, jsonBody);
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
////同步
//        Response response = client.newCall(request).execute();
////        ResponseBody body1 = response.body();
////        body1.
////        ResponseBody 是json
//        if (response.isSuccessful()) {
//            return response.body().string();
//        } else {
//            throw new IOException("Unexpected code " + response);
//        }

        OkHttpClient client = getClient();
        return post(url, jsonBody, client);
    }

    public static String post(String url, String jsonBody, OkHttpClient client) throws IOException {
        //OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, jsonBody);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
//同步
        Response response = client.newCall(request).execute();
//        ResponseBody body1 = response.body();
//        body1.
//        ResponseBody 是json
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public static <T> JsonObject post(String url, T t) throws IOException {
        Gson gson = new Gson();

//        RubbishInfo rubbishInfo=new RubbishInfo();
//        rubbishInfo.setQueryRub("string");

        String jsonString = gson.toJson(t);
        String res = post(url, jsonString);
//        System.out.println(jsonString);
//        String post = httpUtil.post(listUrl, jsonString);
//        content
        Log.d("res", "post: res  " + res);
        JsonObject jsonObject = gson.fromJson(res, JsonObject.class);
        return jsonObject;

    }

}
