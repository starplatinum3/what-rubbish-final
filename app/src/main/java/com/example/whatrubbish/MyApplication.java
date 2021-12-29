package com.example.whatrubbish;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {
    public static Application		mApplication;

    @Override
    public void onCreate() {
        //Application onCreate 没有调用
        super.onCreate();
        Log.d("MyApplication", "onCreate:MyApplication ");
        mApplication=this;
    }
}
