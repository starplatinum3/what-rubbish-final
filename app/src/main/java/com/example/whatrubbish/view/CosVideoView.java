package com.example.whatrubbish.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.VideoView;

public class CosVideoView extends VideoView {

    public CosVideoView(Context context) {
        super(context);

    }

    public CosVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CosVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //主要方法在这里
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        Log.i("height", "onMeasure: " + height);
        Log.i("width", "onMeasure: " + width);
        setMeasuredDimension(width, height);
    }

    @Override
    public void setOnPreparedListener(MediaPlayer.OnPreparedListener l) {
        super.setOnPreparedListener(l);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

}


//————————————————
//        版权声明：本文为CSDN博主「godliness_sjz」的原创文章，遵循CC4.0BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/keep_driving_xinyang/article/details/50607601