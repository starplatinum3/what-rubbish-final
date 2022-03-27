package com.example.whatrubbish.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Matrix;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
//import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
//import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
//import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.snatik.matches.themes.Themes;

import java.io.IOException;
import java.io.InputStream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

//import com.example.myapplication.R;


//https://blog.csdn.net/xiaohanluo/article/details/52945791
@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class DrawUtil {

    public static  int getDrawableResourceId(String  stringUrl,Context context){
        if (stringUrl.contains(Themes.URI_DRAWABLE)) {
            String drawableResourceName = stringUrl.substring(Themes.URI_DRAWABLE.length());
            //Bus.context
            //int drawableResourceId = Shared.context.getResources().
            //        getIdentifier(drawableResourceName, "drawable", Shared.context.getPackageName());

            //cont
            //int drawableResourceId = Bus.context.getResources().
            //        getIdentifier(drawableResourceName, "drawable", Bus.context.getPackageName());
            int drawableResourceId = context.getResources().
                    getIdentifier(drawableResourceName, "drawable",context.getPackageName());

            return drawableResourceId;
            //Bitmap bitmap = Utils.scaleDown(drawableResourceId, size, size);
            //return Utils.crop(bitmap, size, size);
        }
        return  -1;
    }

    //https://blog.csdn.net/ybf326/article/details/83186206
    public static @Nullable
    Bitmap loadBitmapFromRawResource(@NonNull Context context, @RawRes int id) {
        InputStream inputStream = null;
        try {
            inputStream = context.getResources().openRawResource(id);
            Bitmap rawBitmap = BitmapFactory.decodeStream(inputStream);
            return rawBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
            }
        }
        return null;
    }

//    void clipPic(){
//        //实现对图片的剪切的类，是一个匿名内部类
//        BitmapFactory.Options factory = new BitmapFactory.Options();
//        factory.inJustDecodeBounds  = true; //如果设置为true,允许查询图片不是按照像素分配的内存
//        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, factory);
//        //对图片的宽度和高度对应手机的屏幕进行匹配
//
//        int hRatio = (int)Math.ceil(factory.outHeight/(float)dh);
//        //如果大于1，表示图片的高度大于手机屏幕的高度
//
//        int wRatio = (int)Math.ceil(factory.outWidth/(float)dw);
//        //如果大于1，表示图片的宽度大于手机屏幕的高度
//
//        //缩放到1/radio的尺寸和1/radio^2的像素
//        if(hRatio > 1 || wRatio > 1){
//            if(hRatio > wRatio){
//                factory.inSampleSize = hRatio;
//            }else {
//                factory.inSampleSize = wRatio;
//            }
//        }
//        factory.inJustDecodeBounds = false;
//        //使用BitmapFactory对图片进行适合屏幕的操作
//        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri),null,factory);
//        //imageView.setImageBitmap(bitmap);
//
////————————————————
////        版权声明：本文为CSDN博主「途途途」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
////        原文链接：https://blog.csdn.net/u013556056/article/details/21649029
//    }
   //static Canvas mCanvas;
   // static Paint mPaint;
    private  static PaintFlagsDrawFilter pfd = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
    public synchronized static Bitmap scaleImageCanvas(boolean isApplyRadio,Bitmap bm, int newWidth, int newHeight,int iconScaleRadio,Canvas mCanvas,Paint mPaint) {
        //应用图标缩放
        if(isApplyRadio&&iconScaleRadio!=1f){
            newWidth= (int) (newWidth*iconScaleRadio);
            newHeight= (int) (newHeight*iconScaleRadio);
        }
        if (bm == null) {
            return null;
        }
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Bitmap newbm = Bitmap.createBitmap(newWidth, newWidth,
                Bitmap.Config.ARGB_8888);
        mCanvas.setBitmap(newbm);
        mPaint.setXfermode(null);
        mPaint.setAntiAlias(true);
        mCanvas.save();
        mCanvas.scale(scaleWidth, scaleHeight);
        //保证图标不失真
        mCanvas.setDrawFilter(pfd);
        mCanvas.drawBitmap(bm,0,0,null);
        mCanvas.restore();
        if (bm != null & !bm.isRecycled()) {
            bm.recycle();
            bm = null;
        }
        return newbm;
    }
    //————————————————
//        版权声明：本文为CSDN博主「重播」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/xufeifandj/article/details/52171389
//

    //    加载网络图片圆角
    public static  void loadImageRoundedCorners(Context context, String path, ImageView imageView) {
        RoundedCorners roundedCorners = new RoundedCorners(20);//数字为圆角度数
//        RequestOptions coverRequestOptions = new RequestOptions()
//                .transforms(new CenterCrop(), roundedCorners)//, roundedCorners
////                .error(R.mipmap.default_img)//加载错误显示
////                .placeholder(R.mipmap.default_img)//加载中显示
//                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全部
////                .skipMemoryCache(true)
//                ;//不做内存缓存


        RequestOptions coverRequestOptions = new RequestOptions().
                transform(new CenterCrop(), roundedCorners)//, roundedCorners
//                .error(R.mipmap.default_img)//加载错误显示
//                .placeholder(R.mipmap.default_img)//加载中显示
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全部
//                .skipMemoryCache(true)
                ;//不做内存缓存

        //Glide 加载图片简单用法
        Glide.with(context)
                .load(path)
                .apply(coverRequestOptions)
                .into(imageView);
    }


    //不带圆角，目前视频使用
    public static void loadImage(Context context, String path, ImageView imageView) {
//        RoundedCorners roundedCorners = new RoundedCorners(20);//数字为圆角度数
        RequestOptions coverRequestOptions = new RequestOptions()
                .transforms(new CenterCrop())//, roundedCorners
//                .error(R.mipmap.default_img)//加载错误显示
//                .placeholder(R.mipmap.default_img)//加载中显示
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全部
//                .skipMemoryCache(true)//多种原因造成闪烁，这里是之一，如果坚持跳过内存缓存，需要tag,增加noty的各种判断，
                //但是不跳过，可能因为数据过大会boom,
                ;//不做内存缓存

        //Glide 上面圆角
        //Glide 加载图片简单用法
        Glide.with(context)
                .load(path)
                .apply(coverRequestOptions)
                .into(imageView);


    }

    public static void loadImageChoose(Context context, String path, ImageView imageView) {
        try{
            int intRes = Integer.parseInt(path);
            imageView.setImageResource(intRes);
        }catch (Exception e){
            loadImage(context,path,imageView);
        }


    }


    //    exportBtn = (Button)findViewById(R.id.setting_export_btn);
//    addButtonIcon(exportBtn, R.drawable.icon_export, R.string.setting_export);
//    https://www.cnblogs.com/iceface/archive/2013/09/29/3346193.html
    public static void addButtonIcon(Button btn, int icon_drawable, int text_id, Context context)
    {

        String text = "  " + context.getString(text_id); //add two space to look better
        addButtonIcon( btn,  icon_drawable,   text,  context);
    }

    public static void addButtonIcon(Button btn, int icon_drawable, String  text,final Context context)
    {
        Html.ImageGetter imgGetter = new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                Drawable drawable = null;
                drawable = context.getResources().getDrawable(
                        Integer.parseInt(source));
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());
                return drawable;
            }
        };

        StringBuffer sb = new StringBuffer();
//        String text = "  " + context.getString(text_id); //add two space to look better
        sb.append("<img src=\"").append(icon_drawable).append("\"/>").append(text);
        Spanned span = Html.fromHtml(sb.toString(), imgGetter, null);
        btn.setText(span);
        sb = null;
    }


    /**
     * 他会把 长方形的图片也拉成正方形的
     * @param imageView
     * @param drawableId
     * @param resources
     */
    public  static   void setRoundImageDrawable(ImageView imageView,int drawableId,
                                                Resources resources ,int radius){
//        Resources resources = context.getResources();
//            Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.makima);
        Bitmap bitmap = BitmapFactory.decodeResource(resources, drawableId);
        Drawable drawable = DrawUtil.roundBitmapByBitmapDrawable(bitmap,
                100,
                100,
                radius, resources);
        imageView.setImageDrawable(drawable);
    }

    /**
     * 他会把 长方形的图片也拉成正方形的
     * @param imageView
     * @param drawableId
     * @param resources
     */
   public  static   void setRoundImageDrawable(ImageView imageView,int drawableId,Resources resources ){
//        Resources resources = context.getResources();
//            Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.makima);
        Bitmap bitmap = BitmapFactory.decodeResource(resources, drawableId);
        Drawable drawable = DrawUtil.roundBitmapByBitmapDrawable(bitmap,
                100,
                100,
                5, resources);
        imageView.setImageDrawable(drawable);
    }
//    https://blog.csdn.net/xiaohanluo/article/details/52945791
    /**
     * 利用BitmapShader绘制圆角图片
     *
     * @param bitmap
     *              待处理图片
     * @param outWidth
     *              结果图片宽度，一般为控件的宽度
     * @param outHeight
     *              结果图片高度，一般为控件的高度
     * @param radius
     *              圆角半径大小
     * @return
     *              结果图片
     */
    public static Bitmap roundBitmapByShader(Bitmap bitmap, int outWidth, int outHeight, int radius) {
        if(bitmap == null) {
            throw new NullPointerException("Bitmap can't be null");
        }
        // 初始化缩放比
        float widthScale = outWidth * 1.0f / bitmap.getWidth();
        float heightScale = outHeight * 1.0f / bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(widthScale, heightScale);

        // 初始化绘制纹理图
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        // 根据控件大小对纹理图进行拉伸缩放处理
        bitmapShader.setLocalMatrix(matrix);

        // 初始化目标bitmap
        Bitmap targetBitmap = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);

        // 初始化目标画布
        Canvas targetCanvas = new Canvas(targetBitmap);

        // 初始化画笔
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);

        // 利用画笔将纹理图绘制到画布上面
        targetCanvas.drawRoundRect(new RectF(0, 0, outWidth, outWidth), radius, radius, paint);

        return targetBitmap;
    }



    /**
     * 利用BitmapShader绘制底部圆角图片
     *
     * @param bitmap
     *              待处理图片
     * @param outWidth
     *              结果图片宽度，一般为控件的宽度
     * @param outHeight
     *              结果图片高度，一般为控件的高度
     * @param radius
     *              圆角半径大小
     * @return
     *              结果图片
     */
    public static  Bitmap roundBottomBitmapByShader(Bitmap bitmap, int outWidth, int outHeight, int radius) {
        if(bitmap == null) {
            throw new NullPointerException("Bitmap can't be null");
        }
        // 初始化缩放比
        float widthScale = outWidth * 1.0f / bitmap.getWidth();
        float heightScale = outHeight * 1.0f / bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(widthScale, heightScale);

        // 初始化绘制纹理图
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        // 根据控件大小对纹理图进行拉伸缩放处理
        bitmapShader.setLocalMatrix(matrix);

        // 初始化目标bitmap
        Bitmap targetBitmap = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);

        // 初始化目标画布
        Canvas targetCanvas = new Canvas(targetBitmap);

        // 初始化画笔
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);

        // 利用画笔绘制底部圆角
        targetCanvas.drawRoundRect(new RectF(0, outHeight - 2 * radius, outWidth, outWidth), radius, radius, paint);

        // 利用画笔绘制顶部上面直角部分
        targetCanvas.drawRect(new RectF(0, 0, outWidth, outHeight - radius), paint);

        return targetBitmap;
    }

    /**
     * 利用Xfermode绘制圆角图片
     *
     * @param bitmap
     *              待处理图片
     * @param outWidth
     *              结果图片宽度，一般为控件的宽度
     * @param outHeight
     *              结果图片高度，一般为控件的高度
     * @param radius
     *              圆角半径大小
     * @return
     *              结果图片
     */
    public static  Bitmap roundBitmapByXfermode(Bitmap bitmap, int outWidth, int outHeight, int radius) {
        if(bitmap == null) {
            throw new NullPointerException("Bitmap can't be null");
        }

        // 等比例缩放拉伸
        float widthScale = outWidth * 1.0f / bitmap.getWidth();
        float heightScale = outHeight * 1.0f / bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(widthScale, heightScale);
        Bitmap newBt = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        // 初始化目标bitmap
        Bitmap targetBitmap = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        canvas.drawARGB(0, 0, 0, 0);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        RectF rectF = new RectF(0f, 0f, (float) outWidth, (float) outHeight);

        // 在画布上绘制圆角图
        canvas.drawRoundRect(rectF, radius, radius, paint);

        // 设置叠加模式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        // 在画布上绘制原图片
        Rect ret = new Rect(0, 0, outWidth, outHeight);
        canvas.drawBitmap(newBt, ret, ret, paint);

        return targetBitmap;
    }

    /**
     * 利用RoundedBitmapDrawable绘制圆角图片
     *
     * @param bitmap
     *              待处理图片
     * @param outWidth
     *              结果图片宽度，一般为控件的宽度
     * @param outHeight
     *              结果图片高度，一般为控件的高度
     * @param radius
     *              圆角半径大小
     * @return
     *              结果图片
     */
    public static Drawable roundBitmapByBitmapDrawable(Bitmap bitmap, int outWidth,
                                                       int outHeight, int radius, Resources resources) {
        if(bitmap == null) {
            throw new NullPointerException("Bitmap can't be null");
        }

        // 等比例缩放拉伸
        float widthScale = outWidth * 1.0f / bitmap.getWidth();
        float heightScale = outHeight * 1.0f / bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(widthScale, heightScale);
        Bitmap newBt = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        // 绘制圆角
//        Resources resources = getResources();
        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(resources, newBt);
        dr.setCornerRadius(radius);
        dr.setAntiAlias(true);

        return dr;
    }

    /**
     * 利用BitmapShader绘制底部圆角图片
     *
     * @param bitmap
     *              待处理图片
     * @param edgeWidth
     *              正方形控件大小
     * @param radius
     *              圆角半径大小
     * @return
     *              结果图片
     */
    public static  Bitmap circleBitmapByShader(Bitmap bitmap, int edgeWidth, int radius) {
        if(bitmap == null) {
            throw new NullPointerException("Bitmap can't be null");
        }
        float btWidth = bitmap.getWidth();
        float btHeight = bitmap.getHeight();
        // 水平方向开始裁剪的位置
        float btWidthCutSite = 0;
        // 竖直方向开始裁剪的位置
        float btHeightCutSite = 0;
        // 裁剪成正方形图片的边长，未拉伸缩放
        float squareWidth = 0f;
        if(btWidth > btHeight) { // 如果矩形宽度大于高度
            btWidthCutSite = (btWidth - btHeight) / 2f;
            squareWidth = btHeight;
        } else { // 如果矩形宽度不大于高度
            btHeightCutSite = (btHeight - btWidth) / 2f;
            squareWidth = btWidth;
        }

        // 设置拉伸缩放比
        float scale = edgeWidth * 1f / squareWidth;
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);

        // 将矩形图片裁剪成正方形并拉伸缩放到控件大小
        Bitmap squareBt = Bitmap.createBitmap(bitmap, (int)btWidthCutSite, (int)btHeightCutSite, (int)squareWidth, (int)squareWidth, matrix, true);

        // 初始化绘制纹理图
        BitmapShader bitmapShader = new BitmapShader(squareBt, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        // 初始化目标bitmap
        Bitmap targetBitmap = Bitmap.createBitmap(edgeWidth, edgeWidth, Bitmap.Config.ARGB_8888);

        // 初始化目标画布
        Canvas targetCanvas = new Canvas(targetBitmap);

        // 初始化画笔
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);

        // 利用画笔绘制圆形图
        targetCanvas.drawRoundRect(new RectF(0, 0, edgeWidth, edgeWidth), radius, radius, paint);

        return targetBitmap;
    }
}
