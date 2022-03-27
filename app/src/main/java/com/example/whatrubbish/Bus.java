package com.example.whatrubbish;

import android.content.Context;

import com.ejlchina.okhttps.GsonMsgConvertor;
import com.ejlchina.okhttps.HTTP;
import com.example.whatrubbish.entity.ImUser;
import com.example.whatrubbish.entity.Rubbish;
import com.example.whatrubbish.entity.RubbishType;
import com.example.whatrubbish.entity.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sdust.im.util.MyWebSocketClient;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.val;

@Data
public class Bus {

    //MyWebSocketClient myWebSocketClient=    MyWebSocketClient.getInstance();
    public static MyWebSocketClient myWebSocketClient;





    //public static class ChatType{
    //
    //}
    //public static String packageName =  "com.example.smlj";
    //public static  int maxPoint=5;
    public static List<Rubbish> rubbishes;
    public static   List<RubbishType>rubbishTypes;
    public static String   chat_users_url= "/api/user/chatUserList";
    public static Context context;
    public static JsonObject token;
    public static  int maxPoint=50;
    //public static  int maxPoint=30;
    public static String packageName =  "com.example.whatrubbish";
    public static User curUser;
    public static User curFriend;
    public static User loginForm;
    public static ImUser curImUser;
    public static JsonObject curImUserObj;
    //public static JsonObject friends;
    public static JsonArray friends;
    //public static JsonObject groups;
    public static JsonArray groups;
    public static int stagesLen = 4;
    public static String nowFriendId =null;

    //   public  static  long[] stages=new  long[stagesLen];
//   public  static  long[] stages={1,2,3,4};
    public static int[] stages = {1, 2, 3, 4};
    //public static final String rubClsLocApi ="https://lajifenleiapp.com/sk/防晒喷雾瓶?l=石家庄";

    //public static final String whatRubMark ="what";
    //public static final String locMark ="loc";
    public static final String whatRubMark ="{what}";
    public static final String locMark ="{loc}";
    public static final String rubClsLocApi =String.format("https://lajifenleiapp.com/sk/%s?l=%s",whatRubMark,locMark);
    //public static final String rubClsLocApi ="https://lajifenleiapp.com/sk/{what}?l={loc}";
    public static final String keyRubbish = "keyRubbish";
    public static final String dbIpLocal = "10.0.2.2";
    public static final String dbIp = "10.0.2.2";
    //public static final String dbIp = "localhost";
    //public static final String dbIp = "139.196.8.79";
    //        String url="http://"+ip+":8889/tbAdmin/find?id=1";
//        String url = "http://" + ip + ":8889/rubbishInfo/save";
//    String baseDbUrl = "http://" + dbIp + ":8889/rubbishInfo/list";
    public static final String baseDbUrl = "http://" + dbIp + ":8889";
    public static final String baseWsUrl = "ws://" + dbIp + ":9326";
//    public static final String tianapiBaseUrl = "http://api.tianapi.com/lajifenlei/index";
//    public static final String tianapiBaseUrl = "http://api.tianapi.com/lajifenleinews/index?key={APIKEY}&num={num}";
    public static final String tianapiBaseUrl = "http://api.tianapi.com/lajifenlei/index?key={APIKEY}&word={word}";
    public static final String tianApiNewsUrl = "http://api.tianapi.com/lajifenleinews/index?key={APIKEY}&num={num}";
    public static final String APIKEY_TOKEN = "APIKEY";
    public static final String WORD_TOKEN = "word";
    public static final String NEWS_NUM_TOKEN = "num";
    public static final  HTTP httpDb = HTTP.builder()
//                .baseUrl("http://api.tianapi.com/lajifenlei/index?key="+keyRubbish+"&word=")
            .baseUrl(Bus.baseDbUrl)
            .addMsgConvertor(new GsonMsgConvertor())
            .build();

    public static final   int SUCCESS=1000;
    public static final  int ERROR=400;

    public static final int codeSuccess = 200;
    public static final int codeError = 400;

    public static final  String  contentMark="content";
    public static final  String  dataMark="data";

    public static  final Gson gson = new Gson();
    public static  final String  userSave = "/user/save";
    public static  final String  userList= "/user/list";
    public static  final String  rubbishSave = "/rubbish/save";
    public static  final String  citySave = "/city/save";
    public static  final String  cityList = "/city/list";
    public static  final String  cityFindByPicResNotNull = "/city/findByPicResNotNull";


}
