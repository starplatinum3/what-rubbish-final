package com.example.whatrubbish.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

//import com.alibaba.fastjson.JSONException;
//import com.alibaba.fastjson.JSONObject;

public class CocosUtil {

   final static int getUserCmd=1;

    /**
     * 发送消息给JS
     * 这个函数 js 那边没有调用 所以参数应该可以改掉吧
     * @param data JSONObject格式消息
     */
    public static void postMessageToJs(final JSONObject data){
        //这里不能 有别的参数吧
        org.cocos2dx.lib.Cocos2dxHelper.runOnGLThread(new Runnable() {
            @Override
            public void run() {
                String str = data.toString();
                str = str.replace("\"", "\\\"");
                Log.v("v","[Android] postMessageToJs:" + str);
                //发过去一个str str好的呀 可以转化任何东西 转化 json就好了
                org.cocos2dx.lib.Cocos2dxJavascriptJavaBridge.evalString("cc[\"MainScene\"].revAndroidMsg(\"" + str + "\")");
            }
        });
    }

    /**
     * 接收来自JS的消息
     * js 调用了这个函数 所以感觉应该不好改参数
     * @param data json格式字符串
     * @return 返回数据
     */
    public static String revJsMessage(String data) {
        Log.v("v","[Android] revJsMessage:" + data);
        try{
            JSONObject revObj = new JSONObject(data);
            int cmd = revObj.getInt("cmd");
            switch (cmd){
                //case 1001:
                case getUserCmd:
                    //打印收到的JS消息
                    String msg = revObj.getString("msg");
                    Log.v("v","[Android] revJsMessage:" + msg);
                    //给JS发送一个消息
                    JSONObject object = new JSONObject();
                    //object.put("cmd",1001);
                    object.put("cmd",getUserCmd);
                    object.put("msg","I'm Anroid");
                    //AppActivity.postMessageToJs(object);
                    postMessageToJs(object);
                    break;
            }
        }catch (  JSONException e){
            e.printStackTrace();
        }
        return "";
    }
}
