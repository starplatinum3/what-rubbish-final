package com.example.whatrubbish.util;

import android.content.Context;

import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.graphics.Paint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuffXfermode;
import android.graphics.PorterDuff;
import android.graphics.Rect;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;

public class CircleImage extends AbsoluteLayout {
    private int mWidth = 100;
    private int mHeight = 100;
    private Paint mPaint;
    private String TAG = "PKG_UICTRL_CircleImage";
    private ImageView mImageView = null;

    public CircleImage(Context context) {
        this(context, null);
    }

    public CircleImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImage(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CircleImage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
        mPaint.setDither(true);
        setWillNotDraw(false);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG, "onFinishInflate: ");
        getView();

    }

    private void getView() {
        Log.d(TAG, " getView iCount="+getChildCount());
        for (int iCount = 0; iCount < getChildCount(); ++iCount) {
            View child = this.getChildAt(iCount);
            if (child instanceof ImageView) {
                Log.d(TAG, " ImageView");
                mImageView=(ImageView)child;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (null == mImageView){
            return;
        }

        Drawable drawable = mImageView.getDrawable();
        if (null != drawable && (drawable instanceof BitmapDrawable)) {
            Bitmap b = ((BitmapDrawable) drawable).getBitmap();
            Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);

            mWidth = getWidth();
            mHeight = getHeight();
             // 计算显示圆形的半径，为保证圆形，取图片的长宽小的一半作为圆形
            int radius = (mWidth < mHeight ? mWidth : mHeight) / 2;
            // 获取我们处理后的圆形图片
            Bitmap roundBitmap = getCroppedCircleBitmap(bitmap, radius);
            // 绘制图片进行显示
            canvas.drawBitmap(roundBitmap, mWidth / 2 - radius, mHeight / 2 - radius, null);

            mImageView.setImageBitmap(roundBitmap);
        }
        else {
            super.onDraw(canvas);
        }
    }

    public Bitmap getCroppedCircleBitmap(Bitmap bmp, int radius) {
        Bitmap scaledSrcBmp;
        int diameter = radius * 2;
        // 对图片进行处理，获取我们需要的中央部分
        Bitmap squareBitmap = getCenterBitmap(bmp);

        // 将图片缩放到需要的圆形比例大小
        if (squareBitmap.getWidth() != diameter || squareBitmap.getHeight() != diameter) {
            scaledSrcBmp = Bitmap.createScaledBitmap(squareBitmap, diameter, diameter, true);
        } else {
            scaledSrcBmp = squareBitmap;
        }
        // 创建一个我们输出的对应
        Bitmap output = Bitmap.createBitmap(scaledSrcBmp.getWidth(),
                scaledSrcBmp.getHeight(),
                Bitmap.Config.ARGB_8888);
        // 在output上进行绘画
        Canvas canvas = new Canvas(output);
        // 创建裁剪的矩形
        Rect rect = new Rect(0, 0, scaledSrcBmp.getWidth(),scaledSrcBmp.getHeight());

        // 绘制dest目标区域
        canvas.drawCircle(scaledSrcBmp.getWidth() / 2,
                scaledSrcBmp.getHeight() / 2,
                scaledSrcBmp.getWidth() / 2,
                mPaint);

        // 设置xfermode模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        // 绘制src源区域
        canvas.drawBitmap(scaledSrcBmp, rect, rect, mPaint);

        bmp.recycle();
        squareBitmap.recycle();
        scaledSrcBmp.recycle();
        return output;
    }

    // 注意这里必须只能用bitmap.getWidth的图片，去算尺寸，不然外面的框，大于bitmap的大小，超出的就变成了黑色的
    private Bitmap getCenterBitmap(Bitmap bitmap){
        // 为了防止图片宽高不相等，造成圆形图片变形，因此截取长方形中处于中间位置最大的正方形图片  
        int bmpWidth = bitmap.getWidth();
        int bmpHeight = bitmap.getHeight();
        Bitmap squareBitmap;
        if (bmpHeight > bmpWidth) {
            // 截取正方形图片  ，从(bmpHeight - bmpWidth) / 2处开始截取
            squareBitmap = Bitmap.createBitmap(bitmap, 0, (bmpHeight - bmpWidth) / 2, bmpWidth, bmpWidth);
        } else if (bmpHeight < bmpWidth) {
            squareBitmap = Bitmap.createBitmap(bitmap, (bmpWidth - bmpHeight) / 2, 0, bmpHeight, bmpHeight);
        } else {
            squareBitmap = bitmap;
        }
        return squareBitmap;
    }
}

