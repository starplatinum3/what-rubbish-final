package com.example.whatrubbish.util;

import android.app.Activity;
import android.widget.Toast;

public class ToastUtil {
  public  static   void show(Activity activity,String text){
        Toast.makeText(activity,text,Toast.LENGTH_SHORT).show();
    }

    public  static   void showOnUiThread(Activity activity,String text){
      activity.runOnUiThread(()->{
          Toast.makeText(activity,text,Toast.LENGTH_SHORT).show();
      });

    }
}
