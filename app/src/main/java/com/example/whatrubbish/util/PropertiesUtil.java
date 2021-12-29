package com.example.whatrubbish.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

//    private static final int  = 393;
//  public   static  final String  keyRubbish="keyRubbish";
    public static Properties getProperties(Activity activity) {
        // 读取参数配置

        Properties props = new Properties();

        try {

            props.load(activity.getApplicationContext().getAssets().open("config.properties"));

        } catch (IOException e) {

//e.printStackTrace();

            AlertDialog.Builder dialog = new AlertDialog.Builder(activity);

            dialog.setTitle("错误");

            dialog.setMessage("读取配置文件失败");

            dialog.setCancelable(false);

            dialog.setPositiveButton("OK", (dialogInterface, i) -> {

//                    LoginActivity.super.sysExit();
//                Log.i("获取失败", "获取失败");
//                Log.i("获取成功", "获取成功");
                Log.i("确认", "确认");

            });

            dialog.show();

        }
        return props;

//        ws_namespace = props.getProperty("ws_namespace");
//
//        ws_url = props.getProperty("ws_url");
//
//        ws_method = props.getProperty("ws_method_login");
    }

    public static String getProperty(Activity activity, String key) {
        Properties properties = getProperties(activity);
        return properties.getProperty(key);
    }

}
