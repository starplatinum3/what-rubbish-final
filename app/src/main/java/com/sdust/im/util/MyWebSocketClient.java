package com.sdust.im.util;

import android.content.Context;
import android.util.Log;

import com.example.whatrubbish.Bus;
import com.example.whatrubbish.dao.RecMsgDao;
import com.example.whatrubbish.db.AppDatabase;
import com.example.whatrubbish.entity.RecMsg;

import org.java_websocket.enums.ReadyState;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

//import cn.chenjianlink.android.alarmclock.model.LogInfo;

/**
 * websocket client 客户端端控制
 */
public class MyWebSocketClient extends org.java_websocket.client.WebSocketClient {
 
    private static final Logger logger = LoggerFactory.getLogger(MyWebSocketClient.class);
    
    // 复制请留意，该位置url需要进行更改
    //private static String wsUrl = "ws://127.0.0.1:10080//";
    private static String host = "ws://127.0.0.1:10080//";

    //private static String wsUrl =  String.format("ws://%s:%s?token=%s", Bus.baseWsUrl, port, Bus.token["access_token"].asString)
    private static String wsUrl =  String.format("%s?token=%s", Bus.baseWsUrl, Bus.token.get("access_token").getAsString());

    public static void main(String[] args) {
        MyWebSocketClient myWebSocketClient=MyWebSocketClient.getInstance();
    }
    private static MyWebSocketClient instance;
 
    private int sendFlag = 0;
    private String result = null;

    Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    static {
        try {
            instance = new MyWebSocketClient(wsUrl);
            instance.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
 
    // 获取到当前实例
    public static MyWebSocketClient getInstance(){
       try{
           if(instance != null){
               if(instance.getReadyState() == ReadyState.NOT_YET_CONNECTED
                       || instance.getReadyState() == ReadyState.CLOSED){
                   if(instance.isClosed()){
                       instance.reconnect();
                   }
               }
           }else{
               instance = new MyWebSocketClient(wsUrl);
               instance.connect();
           }
       }catch (Exception ex){
           instance = null;
           logger.error(" websocket 构建实例出现问题！！" + ex);
       }
       return instance;
    }
 
    // 发送字符串消息
    public String sendStr(String text){
        //this.sen
        synchronized(this){
            sendFlag = 1;
            this.send(text);
            while(sendFlag != 0){
                logger.debug(" 等待返回值中 =============== " + sendFlag);
            }
            return result;
        }
    }
 
 
    private MyWebSocketClient(String url) throws URISyntaxException {
        super(new URI(url));
    }
 
    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        logger.debug(" ws 服务正常打开！！");
        Log.i("服务正常打开", "onOpen: 服务正常打开");
        Log.i("serverHandshake", "onOpen: "+serverHandshake);
    }
 
    @Override
    public void onMessage(String received) {
        result = received;
        sendFlag = 0;
        logger.debug(" ws 接收服务器推送的消息！！" + received);
        //后台监听的消息 怎么发送 就是存下来吧
        //PoolT
        //new RecMsgDao();
        //ws 安卓 想要储存数据

        AppDatabase database = AppDatabase.getDatabase(context);
        RecMsg recMsg = new RecMsg();
        recMsg.setMsg(received);
        recMsg.setDate(new Date());
        database.recMsgDao().insert(recMsg);


        //LogInfo logInfo = new LogInfo(null);
        //logInfo.setContent(received);
        //logInfo.setDate(new Date());
        //database.logInfoDao().insert(logInfo);
    }
 
    @Override
    public void onClose(int i, String s, boolean b) {
        result = null;
        sendFlag = 0;
        logger.debug(" ws 客户端正常关闭！！");
    }
 
    @Override
    public void onError(Exception e) {
        result = null;
        sendFlag = 0;
        logger.debug(" ws 客户端连接出现错误！！");
    }
}