//package com.example.whatrubbish.circleProgressBar;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.drawable.Drawable;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.view.animation.LinearInterpolator;
//import android.widget.RelativeLayout;
//
//import com.example.whatrubbish.R;
//
///**
// * 圆形进度条中间含旋转的图片
// */
//public class CircleProgressBarAndImageView extends RelativeLayout {
//    private Context context;
//    private CircleProgressBar progressBar;
//    private CircleImageView imageView;
//    private View view;
//    private int imageWidth,imageHeight;
//    public CircleProgressBarAndImageView(Context context) {
//        super(context);
//    }
//    public CircleProgressBarAndImageView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        this.context=context;
//        init(context, attrs);
//    }
//
//
//    private void init(Context context, AttributeSet attrs) {
//        TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleProgressbar, 0 , 0);
//        imageWidth = typeArray.getColor(R.styleable.CircleProgressbar_imageWidth, 58);//获取自定义图片宽度
//        imageHeight = typeArray.getColor(R.styleable.CircleProgressbar_imageHeight, 58);//获取自定义图片高度
//
////        CircleProgressBar.
////        https://zhuanlan.zhihu.com/p/113370351
//        view= LayoutInflater.from(context).inflate(R.layout.audioplayer_progress_image,this,true);
//        progressBar=view.findViewById(R.id.cicle_progressBar);
//        imageView=view.findViewById(R.id.cicle_image);
//    }
//
//    /**
//     * 设置进度
//     * @param i
//     */
//    public void setProgress(int i){
//        progressBar.setProgress(i);
//    }
//
//    /**
//     * 圆形图片开始旋转
//     */
//    public void imageStartAnimation(){
//        //圆形图片加载一个旋转的动画
//        Animation operatingAnim = AnimationUtils.loadAnimation(getContext(), R.anim.image_transtle);
//        LinearInterpolator lin = new LinearInterpolator();
//        operatingAnim.setInterpolator(lin);
//        imageView.startAnimation(operatingAnim);
//    }
//    /**
//     * 圆形图片停止旋转
//     */
//    public void imageStopAnimation(){
//        imageView.clearAnimation();
//    }
//    /**
//     * 给圆形图片控件设置图片
//     * @param drawable
//     */
//    public void setDrawable(Drawable drawable){
//        imageView.setImageDrawable(drawable);
//    }
//}