package com.example.whatrubbish.util;

import android.content.Context;
import android.util.TypedValue;

//import net.lucode.hackware.magicindicator.buildins.UIUtil;
//导入依赖的package包/类
public class DpPxSpTool {


    public static int Dp2Px(Context con, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, dp,
                con.getResources().getDisplayMetrics());
    }

    public static int Px2Dp(Context con, int px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px,
                con.getResources().getDisplayMetrics());
    }

    public static int getPxFromDpRes(Context con, int resid) {
        return (int) con.getResources().getDimension(resid);
    }
//
//————————————————
//    版权声明：本文为CSDN博主「generallizhong」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//    原文链接：https://blog.csdn.net/generallizhong/article/details/102852142
//

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 二维码的 大小
     *
     * @param
     * @return void
     * @author Steven
     */
    public static int getMaxCodeSize(float density) {
        int textSize = 0;
        if (density >= 3.0) {
            textSize = 220;
        } else if (density > 2.0 && density < 3.0) {
            textSize = 180;
        } else if (density >= 1.5 && density < 2.0) {
            textSize = 140;
        } else if (density >= 1.0 && density < 1.5) {
            textSize = 100;
        } else {
            textSize = 100;
        }
        return textSize;
    }

    /**
     * 密度计算 字体大小 文本框
     *
     * @param
     * @return void
     * @author Steven
     */
    public static float getTextSize(float density) {
        float textSize = 0.0f;
        if (density >= 3.0) {
            textSize = 24;
        } else if (density >= 2.0 && density < 3.0) {
            textSize = 16;
        } else if (density >= 1.5 && density < 2.0) {
            textSize = 14;
        } else if (density >= 1.0 && density < 1.5) {
            textSize = 10;
        } else {
            textSize = 12;
        }
        return textSize;
    }

    /**
     * 密度计算 发送短信 字体 字体大小
     *
     * @param
     * @return void
     * @author Steven
     */
    public static float getSendCodeBtnTextSize(float density) {
        float textSize = 0.0f;
        if (density >= 3.0) {
            textSize = 15;
        } else if (density >= 2.0 && density < 3.0) {
            textSize = 14;
        } else if (density >= 1.5 && density < 2.0) {
            textSize = 12;
        } else if (density >= 1.0 && density < 1.5) {
            textSize = 10;
        } else {
            textSize = 10;
        }
        return textSize;
    }

//————————————————
//    版权声明：本文为CSDN博主「generallizhong」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//    原文链接：https://blog.csdn.net/generallizhong/article/details/102852142
}
