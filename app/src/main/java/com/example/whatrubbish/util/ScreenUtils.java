package com.example.whatrubbish.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.whatrubbish.R;

/**
 * Author       wildma
 * Date         2017/8/18
 * Desc	        ${屏幕相关工具类}
 */
public class ScreenUtils {

    /**
     * 获取屏幕相关参数
     */
//    public String getScreenParams(Activity activity, WindowManager windowManager, Context context) {
    public String getScreenParams(Activity activity) {
//        context.gewindo
        WindowManager windowManager = activity.getWindowManager();
        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
        windowManager.getDefaultDisplay().getMetrics(dm);
//        int heightPixels = ScreenUtils.getScreenHeight(this);
//        int widthPixels = ScreenUtils.getScreenWidth(this);
//        int heightPixels = ScreenUtils.getScreenHeight(context);
//        int widthPixels = ScreenUtils.getScreenWidth(context);
        int heightPixels = ScreenUtils.getScreenHeight(activity);
        int widthPixels = ScreenUtils.getScreenWidth(activity);
        float xdpi = dm.xdpi;
        float ydpi = dm.ydpi;
        int densityDpi = dm.densityDpi;
        float density = dm.density;
        float scaledDensity = dm.scaledDensity;
        float heightDP = heightPixels / density;
        float widthDP = widthPixels / density;
        float smallestWidthDP;
        if (widthDP < heightDP) {
            smallestWidthDP = widthDP;
        } else {
            smallestWidthDP = heightDP;
        }
        String str = "=== screen params ===";
        str += "\nheightPixels: " + heightPixels + "px";
        str += "\nwidthPixels: " + widthPixels + "px";
        str += "\nxdpi: " + xdpi + "dpi";
        str += "\nydpi: " + ydpi + "dpi";
        str += "\ndensityDpi: " + densityDpi + "dpi";
        str += "\ndensity: " + density;
        str += "\nscaledDensity: " + scaledDensity;
        str += "\nheightDP: " + heightDP + "dp";
        str += "\nwidthDP: " + widthDP + "dp";
        str += "\nsmallestWidthDP: " + smallestWidthDP + "dp";
        return str;
    }

    private static final String  TAG = "ScreenUtils";
    /**
     * 动态设置dp与sp
     */
//    public void dynamicSet(View view,Activity activity, Resources resources,Context context) {
    public void dynamicSet(View view,Activity activity) {

        /**
         * 注意：
         * getDimension()方法并不是直接拿到dimens.xml文件中的dp或sp值
         * 而是將dimens.xml文件中的dp或sp值乘以屏幕密度（density）来换算成px值
         * 所以拿到该值后还需要换算成对应的dp或sp
         */

        Resources resources = activity.getResources();

        /*获取sp值*/
//        float pxValue = getResources().getDimension(R.dimen.sp_20);//获取对应资源文件下的sp值
        float pxValue =resources.getDimension(R.dimen.sp_20);//获取对应资源文件下的sp值
//        int spValue = ConvertUtils.px2sp(this, pxValue);//将px值转换成sp值
//        int spValue = ConvertUtils.px2sp(context, pxValue);//将px值转换成sp值
        int spValue = ConvertUtils.px2sp(activity, pxValue);//将px值转换成sp值

        /*获取dp值*/
        float pxValue2 = resources.getDimension(R.dimen.dp_180);//获取对应资源文件下的dp值
//        int dpValue = ConvertUtils.px2dp(context, pxValue2);//将px值转换成dp值
        int dpValue = ConvertUtils.px2dp(activity, pxValue2);//将px值转换成dp值

        Log.d(TAG, "pxValue= " + pxValue);
        Log.d(TAG, "spValue= " + spValue);
        Log.d(TAG, "pxValue2= " + pxValue2);
        Log.d(TAG, "dpValue= " + dpValue);

        /*动态设置文字大小与控件大小*/
//        mTvShowParams.setTextSize(spValue);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) pxValue2, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        mBtnTestModule.setLayoutParams(new LinearLayout.LayoutParams((int) pxValue2, ViewGroup.LayoutParams.WRAP_CONTENT));
    }


    /**
     * 获取屏幕宽度
     *
     * @param context Context
     * @return 屏幕宽度（px）
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    /**
     * 获取屏幕高度
     *
     * @param context Context
     * @return 屏幕高度（px）
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }
}
