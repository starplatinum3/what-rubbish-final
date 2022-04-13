package com.example.whatrubbish.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.whatrubbish.LoginActivity;
import com.example.whatrubbish.databinding.ActivityLoginBinding;

public class ActivityUtil {
  public  static   void startActivity(Activity activity, Class<?> cls){
        Intent intent = new Intent(activity,cls);
        activity.startActivity(intent);
    }

    public  static   void startActivity(Context context, Class<?> cls){
        Intent intent = new Intent(context,cls);
        context.startActivity(intent);
    }
    public  static   void startActivityOnUiThread(Activity activity, Class<?> cls){
      activity.runOnUiThread(()->{
          startActivity(activity,cls);
      });

    }

}
