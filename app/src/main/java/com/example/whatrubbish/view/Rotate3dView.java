package com.example.whatrubbish.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.whatrubbish.R;

public class Rotate3dView extends View {
    private Bitmap frontBitmap;
    private Bitmap backBitmap;
    private Paint paint;
    private Matrix matrix;
    private Camera camera;
    private  float[] mValues = new float[9];
    private float scale = 1;
    public Rotate3dView(Context context) {
        super(context);
        init();
    }
 
    public Rotate3dView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init(){
        camera = new Camera();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        //frontBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_jn,options);
        //backBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_m,options);

        frontBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.tuisong_1,options);
        backBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.tuisong_2,options);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        matrix = new Matrix();
        scale = getResources().getDisplayMetrics().density;
    }
    private int degrees = 0;
 
    public void setDegrees(int degrees) {
        this.degrees = degrees;
        postInvalidate();
    }
 
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(getWidth() == 0 || getHeight() == 0){
            return;
        }
        float centerX = getWidth()/2;
        float centerY = getHeight()/2;
        float left = centerX - frontBitmap.getWidth()/2;
        float top = centerY - frontBitmap.getHeight()/2;
        canvas.save();
        camera.save();
        matrix.setTranslate(0,0);
        if(degrees < 90){
            camera.rotateY(degrees);
        }else{
            camera.rotateY(degrees - 180);
        }
 
        camera.getMatrix(matrix);
 
        camera.restore();
 
 
        matrix.getValues(mValues);           //获取数值
        mValues[6] = mValues[6]/scale;       //数值修正
        mValues[7] = mValues[7]/scale;       //数值修正
        matrix.setValues(mValues);           //重新赋值
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
 
//        matrix.postTranslate(100, 100);
        canvas.setMatrix(matrix);
        if(degrees < 90){
            canvas.drawBitmap(frontBitmap,left ,top,null);
        }else{
            canvas.drawBitmap(backBitmap,left ,top,null);
        }
 
        canvas.restore();
 
        canvas.drawRect(left,top,left+frontBitmap.getWidth(),top+frontBitmap.getHeight(),paint);
 
        canvas.drawLine(centerX,0,centerX,getHeight(),paint);
        canvas.drawLine(0,centerY,getWidth(),centerY,paint);
 
 
    }
}