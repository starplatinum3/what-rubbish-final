package com.suramire.androidgame25.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import com.example.whatrubbish.R;
import com.luck.picture.lib.tools.ScreenUtils;
import com.suramire.androidgame25.MainActivity;

import java.io.IOException;

public class BitmapUtil {

    public static Bitmap bitMapScale(Bitmap bitmap,float scaleX,float scaleY) {
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX,scaleY); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return resizeBmp;
    }

    public static Bitmap bitMapScale(Bitmap bitmap,float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale,scale); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return resizeBmp;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }

            return inSampleSize;
        }
        return inSampleSize;
    }

    //https://blog.csdn.net/lvbo23/article/details/52597386
//    void bitMapScale(){
//        BitmapFactory.Options opts = new BitmapFactory.Options();
//        opts.inJustDecodeBounds = true;//只是获取属性，不返回bitmap对象
//        BitmapFactory.decodeResource(context.getResources(), R.drawable.home_tree_full, opts);
//        opts.inSampleSize= calculateInSampleSize(opts, ScreenUtils.SCREEN_WIDTH, ScreenUtils.dip2px(context,250));
////        opts.inSampleSize=3;//缩小倍数，只能缩小
//        opts.inJustDecodeBounds = false;//返回bitmap对象
//        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.home_tree_full, opts);
//
//    }

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
