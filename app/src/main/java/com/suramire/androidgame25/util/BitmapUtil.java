package com.suramire.androidgame25.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.whatrubbish.R;
import com.suramire.androidgame25.MainActivity;

import java.io.IOException;

public class BitmapUtil {

    public static  Bitmap   getBitmap(Context context,int resId){
        Resources res = context.getResources();
        //Resources resources = context.getResources();
        Bitmap bmp= BitmapFactory.decodeResource(res,resId);
        return bmp;
    }
    /**
     * 加载图片
     *
     * @param fileName 图片文件名
     * @return Bitmap
     */
    public static  Bitmap getBitmap(String fileName, Context context) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(context.getAssets().open(fileName));
        } catch (IOException e) {
            Log.e("getBitmap", "getBitmap is null !!!");
        }
        return bitmap;
    }
}
