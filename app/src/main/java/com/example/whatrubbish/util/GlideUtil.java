package com.example.whatrubbish.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.whatrubbish.MainActivity;
import com.example.whatrubbish.R;

public class GlideUtil {
    public static RequestBuilder<Drawable> glideWithPlaceHolder(Context context, Object object) {
       /*默认的策略是DiskCacheStrategy.AUTOMATIC
//DiskCacheStrategy有五个常量：
//DiskCacheStrategy.ALL 使用DATA和RESOURCE缓存远程数据，仅使用RESOURCE来缓存本地数据。
//DiskCacheStrategy.NONE 不使用磁盘缓存
//DiskCacheStrategy.DATA 在资源解码前就将原始数据写入磁盘缓存
//DiskCacheStrategy.RESOURCE 在资源解码后将数据写入磁盘缓存，即经过缩放等转换后的图片资源。
//DiskCacheStrategy.AUTOMATIC 根据原始图片数据和资源编码策略来自动选择磁盘缓存策略。*/

//每次渲染都需要1S
//        return Glide.with(context).load(object).diskCacheStrategy(DiskCacheStrategy.ALL).apply(new RequestOptions().placeholder(R.drawable.camera).dontAnimate());
//        三次有一次渲染需要1S,默认是Auto
//        return Glide.with(context).load(object).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(new RequestOptions().placeholder(R.drawable.camera).dontAnimate());
//        RequestOptions options = new RequestOptions().error(R.drawable.img_load_failure).bitmapTransform(new RoundedCorners(30));//图片圆角为30
//        return Glide.with(context).load(object).diskCacheStrategy(DiskCacheStrategy.NONE).apply(new RequestOptions().placeholder(R.drawable.camera).dontAnimate().bitmapTransform(new RoundedCorners(30)));

        RequestOptions transform = new RequestOptions().placeholder(R.drawable.miku_fang).
                dontAnimate().transform(new GlideRoundTransform(15));
        return Glide.with(context).load(object).diskCacheStrategy(DiskCacheStrategy.NONE).
                apply(transform);
        //加载时显示该图 placeholder

    }

    public static RequestBuilder<Drawable> drawTopTwoRound(Context context, Object object) {
       /*默认的策略是DiskCacheStrategy.AUTOMATIC
//DiskCacheStrategy有五个常量：
//DiskCacheStrategy.ALL 使用DATA和RESOURCE缓存远程数据，仅使用RESOURCE来缓存本地数据。
//DiskCacheStrategy.NONE 不使用磁盘缓存
//DiskCacheStrategy.DATA 在资源解码前就将原始数据写入磁盘缓存
//DiskCacheStrategy.RESOURCE 在资源解码后将数据写入磁盘缓存，即经过缩放等转换后的图片资源。
//DiskCacheStrategy.AUTOMATIC 根据原始图片数据和资源编码策略来自动选择磁盘缓存策略。*/

//每次渲染都需要1S
//        return Glide.with(context).load(object).diskCacheStrategy(DiskCacheStrategy.ALL).apply(new RequestOptions().placeholder(R.drawable.camera).dontAnimate());
//        三次有一次渲染需要1S,默认是Auto
//        return Glide.with(context).load(object).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(new RequestOptions().placeholder(R.drawable.camera).dontAnimate());
//        RequestOptions options = new RequestOptions().error(R.drawable.img_load_failure).bitmapTransform(new RoundedCorners(30));//图片圆角为30
//        return Glide.with(context).load(object).diskCacheStrategy(DiskCacheStrategy.NONE).apply(new RequestOptions().placeholder(R.drawable.camera).dontAnimate().bitmapTransform(new RoundedCorners(30)));

        RequestOptions transform = new RequestOptions().placeholder(R.drawable.miku_fang).
                dontAnimate().transform(new GlideRoundTransform(15));
        return Glide.with(context).load(object).diskCacheStrategy(DiskCacheStrategy.NONE).
                apply(transform);
        //加载时显示该图 placeholder

    }

    //https://blog.csdn.net/xiyangyang8110/article/details/106193197/
    public static void main(String[] args) {
        //GlideUtil.GlideWithPlaceHolder(MainActivity.this, data.toString()).into(iv);

    }
}

