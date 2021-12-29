package com.hurteng.stormplane.object;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import lombok.Data;

//@Data
public class MoveTargetObj extends GameObject {
    public MoveTargetObj(Resources resources) {
        super(resources);
        //object_height=object_width
    }

    //Bitmap bitmap;
    Bitmap bmp;
    float targetX;
    float targetY;
    float stepX;
    float stepY;

    public MoveTargetObj(float object_x, float object_y, float object_width, float object_height) {
        super(object_x, object_y, object_width, object_height);
    }

    public MoveTargetObj(float object_x, float object_y, float object_width, float object_height, Bitmap bmp) {
        super(object_x, object_y, object_width, object_height);
        //this.bitmap = bitmap;
        this.bmp = bmp;
        //Log.d("bmp", "MoveTargetObj: bmp"+bmp);
        targetX = object_x;
        targetY = object_y;
        stepX=20;
        stepY=20;
    }

    public float getTargetX() {
        return targetX;
    }

    public void setTargetX(float targetX) {
        this.targetX = targetX;
    }

    public float getTargetY() {
        return targetY;
    }

    public void setTargetY(float targetY) {
        this.targetY = targetY;
    }

    @Override
    protected void initBitmap() {

    }

    @Override
    public void drawSelf(Canvas canvas) {
        if (isAlive) {
            canvas.save();
            //Log.d("bmp", "drawSelf: "+bmp);
            canvas.clipRect(object_x, object_y, object_x + object_width, object_y + object_height);
            canvas.drawBitmap(bmp, object_x, object_y, paint);
            canvas.restore();
            logic();
        }
    }

    int dir(float moveDiff) {
        if (moveDiff < 0) return -1;
        return 1;
    }

    @Override
    public void logic() {
        float disX = targetX - object_x;
        float disY =targetY - object_y;
        //if(Math.abs(disX)<=2||Math.abs(disY)<=2){
        if(Math.abs(disX)<=stepX/2||Math.abs(disY)<=stepY/2){
            Log.d("好的成功", "logic: ");
            return;
        }
        object_x += dir(disX) * stepX;
        object_y += dir(disY) * stepY;
        //Log.d("object_x", "logic: object_x"+object_x);
        //Log.d("object_y", "logic: object_y"+object_y);
    }

    @Override
    public void release() {

    }
}
