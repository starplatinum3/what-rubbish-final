package com.example.whatrubbish.util;

import android.util.Log;

import com.ejlchina.okhttps.GsonMsgConvertor;
import com.ejlchina.okhttps.HTTP;
import com.example.whatrubbish.Bus;
import com.example.whatrubbish.entity.RubbishInfo;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Route;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;

//import okhttp3.FormBody;

public class HttpUtil {


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    //public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    //static OkHttpClient client = new OkHttpClient();
    static OkHttpClient client;

    private static String doPost(String strUrl, String content) {
        String result = "";

        try {
            URL url = new URL(strUrl);
            //通过调用url.openConnection()来获得一个新的URLConnection对象，并且将其结果强制转换为HttpURLConnection.
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            //设置连接的超时值为30000毫秒，超时将抛出SocketTimeoutException异常
            urlConnection.setConnectTimeout(30000);
            //设置读取的超时值为30000毫秒，超时将抛出SocketTimeoutException异常
            urlConnection.setReadTimeout(30000);
            //将url连接用于输出，这样才能使用getOutputStream()。getOutputStream()返回的输出流用于传输数据
            urlConnection.setDoOutput(true);
            //设置通用请求属性为默认浏览器编码类型
            urlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            //getOutputStream()返回的输出流，用于写入参数数据。
            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
            //此时将调用接口方法。getInputStream()返回的输入流可以读取返回的数据。
            InputStream inputStream = urlConnection.getInputStream();
            byte[] data = new byte[1024];
            StringBuilder sb = new StringBuilder();
            //inputStream每次就会将读取1024个byte到data中，当inputSteam中没有数据时，inputStream.read(data)值为-1
            while (inputStream.read(data) != -1) {
                String s = new String(data, Charset.forName("utf-8"));
                sb.append(s);
            }
            result = sb.toString();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void main(String[] args) {
        String str = doPost("http://localhost:8888/task/", "firstName=Mickey%26&lastName=Mouse ");
        System.out.println(str);
    }

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


    public static String post3(String url,   okhttp3.RequestBody formBody) throws IOException {
        okhttp3.OkHttpClient okHttpClient=new okhttp3.OkHttpClient();
        //RequestBody:FormBody，表单键值对
        //okhttp3.RequestBody formBody = new FormBody.Builder()
        //        .add("username", "hfy")
        //        .add("password", "qaz")
        //        .build();

        okhttp3.Request getRequest = new
                okhttp3.Request.Builder()
                //.url("https://api.github.com/markdown/raw")
                .url(url)
                //.addHeader()
                .post(formBody)
                .build();



        //Call call = okHttpClient.newCall(getRequest);
        okhttp3.Response response = okHttpClient.newCall(getRequest).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
        //response.body().

        //call.enqueue(new Callback() {
        //    @Override
        //    public void onFailure(Call call, IOException e) {
        //    }
        //
        //    @Override
        //    public void onResponse(Call call, okhttp3.Response response) throws IOException {
        //        Log.i("onResponse", "okHttpPost enqueue: \n onResponse:"+ response.toString() +"\n body:" +response.body().string());
        //    }
        //
        //
        //});
    }
    public static String post(String url, String jsonBody, OkHttpClient client) throws IOException {
        //OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, jsonBody);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
//同步




        //Response response = client.newCall(formBody).execute();
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
//        Log.d("res", "post: res  " + res);
        JsonObject jsonObject = gson.fromJson(res, JsonObject.class);
        return jsonObject;

    }


    //public static Oauth accessToken() throws IOException {
    //
    //
    //    OkHttpClient client = new OkHttpClient.Builder().authenticator(
    //            new Authenticator() {
    //                public Request authenticate(Route route, Response response) {
    //                    String credential = Credentials.basic("api", "secret");
    //                    return response.request().newBuilder().header("Authorization", credential).build();
    //                }
    //            }
    //    ).build();
    //
    //    String url = "https://api.netkiller.cn/oauth/token";
    //
    //    RequestBody formBody = new FormBody.Builder()
    //            .add("grant_type", "password")
    //            .add("username", "blockchain")
    //            .add("password", "123456")
    //            .build();
    //
    //    Request request = new Request.Builder()
    //            .url(url)
    //            .post(formBody)
    //            .build();
    //
    //    Response response = client.newCall(request).execute();
    //    if (!response.isSuccessful()) {
    //        throw new IOException("服务器端错误: " + response);
    //    }
    //
    //    Gson gson = new Gson();
    //    Oauth oauth = gson.fromJson(response.body().string(), Oauth.class);
    //    Log.i("oauth", oauth.toString());
    //    return oauth;
    //}
}
