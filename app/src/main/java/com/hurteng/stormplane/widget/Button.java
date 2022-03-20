package com.hurteng.stormplane.widget;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import lombok.Data;

@Data
public class Button {
    Bitmap bmp;
    Bitmap bmpChangedBtn;
    float left;
    float top;
    float width;
    float height;
    float targetX;
    float targetY;
    int res;
    float screenWidth;
    float screenHeight;
    String text;
    Rect textRect;

    Resources resources;
    Paint paint;
    boolean isBtChange;

    public Bitmap getBmp() {
        return bmp;
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }

    public Bitmap getBmpChangedBtn() {
        return bmpChangedBtn;
    }

    public void setBmpChangedBtn(Bitmap bmpChangedBtn) {
        this.bmpChangedBtn = bmpChangedBtn;
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
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

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public float getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(float screenWidth) {
        this.screenWidth = screenWidth;
    }

    public float getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(float screenHeight) {
        this.screenHeight = screenHeight;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Rect getTextRect() {
        return textRect;
    }

    public void setTextRect(Rect textRect) {
        this.textRect = textRect;
    }

    public Resources getResources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public boolean isBtChange() {
        return isBtChange;
    }

    public void setBtChange(boolean btChange) {
        isBtChange = btChange;
    }

    void initTextRect() {
//        paint.setTextSize(40);
        paint.getTextBounds(text, 0, text.length(), textRect);
    }

    void initBmp() {
        bmp = BitmapFactory.decodeResource(resources, res);
    }

    public void release() {
        if (!bmp.isRecycled()) {
            bmp.recycle();
        }
    }

    void setAtCenter(float screenWidth, float screenHeight) {
        left = (float) (screenWidth / 2 - bmp.getWidth() * 1.0 / 2);
        top = screenHeight / 2 + bmp.getHeight();
    }


    public Button(int res, String text, Resources resources, Paint paint) {

        this.res = res;
        this.text = text;
        textRect = new Rect();
        this.paint = paint;
        initTextRect();
        //this.width = width;
        //this.height = height;
        //this.screenWidth = screenWidth;
        //this.screenHeight = screenHeight;
        this.resources = resources;
        initBmp();
        //放在屏幕中间吗
        //left = (float) (screenWidth / 2 - bmp.getWidth() * 1.0 / 2);
        //top = screenHeight / 2 + bmp.getHeight();
    }

    public Button(int res, String text, float left, float top, Resources resources, Paint paint) {

        this.res = res;
        this.text = text;
        textRect = new Rect();
        this.paint = paint;
        initTextRect();
        this.left = left;
        this.top = top;
        //this.width = width;
        //this.height = height;
        //this.screenWidth = screenWidth;
        //this.screenHeight = screenHeight;
        this.resources = resources;
        initBmp();
        //放在屏幕中间吗
        //left = (float) (screenWidth / 2 - bmp.getWidth() * 1.0 / 2);
        //top = screenHeight / 2 + bmp.getHeight();
    }

    public Button(int res, Resources resources) {

        this.res = res;
        //this.width = width;
        //this.height = height;
        //this.screenWidth = screenWidth;
        //this.screenHeight = screenHeight;
        this.resources = resources;
        initBmp();
        //放在屏幕中间吗
        //left = (float) (screenWidth / 2 - bmp.getWidth() * 1.0 / 2);
        //top = screenHeight / 2 + bmp.getHeight();
    }


    public Button(int res, float width, float height, Resources resources) {

        this.res = res;
        this.width = width;
        this.height = height;
        //this.screenWidth = screenWidth;
        //this.screenHeight = screenHeight;
        this.resources = resources;
        initBmp();
        //放在屏幕中间吗
        //left = (float) (screenWidth / 2 - bmp.getWidth() * 1.0 / 2);
        //top = screenHeight / 2 + bmp.getHeight();
    }


    //public Button(int res, float screenWidth, float screenHeight, Resources resources) {
    //
    //    this.res = res;
    //
    //    this.screenWidth = screenWidth;
    //    this.screenHeight = screenHeight;
    //    this.resources = resources;
    //    initBmp();
    //    //放在屏幕中间吗
    //    left = (float) (screenWidth / 2 - bmp.getWidth() * 1.0 / 2);
    //    top = screenHeight / 2 + bmp.getHeight();
    //}

    public Button(float width, float height, int res, float screenWidth, float screenHeight, Resources resources) {
        this.width = width;
        this.height = height;
        this.res = res;

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.resources = resources;
        initBmp();
        left = (float) (screenWidth / 2 - bmp.getWidth() * 1.0 / 2);
        top = screenHeight / 2 + bmp.getHeight();
    }

    public Button(float left, float top, float width, float height, int res, Resources resources) {
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
        this.res = res;
        this.resources = resources;
        initBmp();
//        left = screenWidth / 2 - bmp.getWidth() / 2;
//        top = screenHeight / 2 + bmp.getHeight();
    }

    public Button(float left, float top, int res, Resources resources) {
        this.left = left;
        this.top = top;
        //this.width = width;
        //this.height = height;
        this.res = res;
        this.resources = resources;
        initBmp();
//        left = screenWidth / 2 - bmp.getWidth() / 2;
//        top = screenHeight / 2 + bmp.getHeight();
    }

    public Button(Bitmap bitmap, float left, float top, float width, float height) {
        this.bmp = bitmap;
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
    }

    void drawText(String text, Canvas canvas, float screen_width, float screen_height, Paint paint) {
        paint.setTextSize(60);
//        float textlong = paint.measureText("总分:" + String.valueOf(score));
        float textLen = paint.measureText(text);
        canvas.drawText(text, screen_width / 2 - textLen / 2, screen_height / 2 - 100, paint);
    }

    float getCenterX(){
        float x = (float) (screenWidth / 2 - textRect.width() * 1.0 / 2);
        return  x;
    }

    float getCenterX(float screenWidth){
        float x = (float) (screenWidth / 2 - textRect.width() * 1.0 / 2);
        return  x;
    }

    float getCenterTextX(float screenWidth){
        float x = (float) (screenWidth / 2 - textRect.width() * 1.0 / 2);
        return  x;
    }

    float getCenterTextXOfMe(){
        float x = (float)   (  left+ bmp.getWidth()*1.0/2- textRect.width() * 1.0 / 2);
        return  x;
       //return left+ bmp.getWidth()*1.0/2- textRect.width() * 1.0 / 2;
        //float x = (float) (screenWidth / 2 - textRect.width() * 1.0 / 2);
        //return  x;
    }


    float getCenterTextY(){
        float y = (float) (top + bmp.getHeight() * 1.0 / 2 + textRect.height() * 1.0 / 2);
        return  y;
    }


    public void draw(Canvas canvas, Paint paint) {

        if (isBtChange) {
            canvas.drawBitmap(bmpChangedBtn, left, top, paint);
        } else {
            canvas.drawBitmap(bmp, left, top, paint);
        }

        //canvas.clipRect(left, top, left + width, top + height);
//        canvas.drawText(text, screenWidth / 2 - strwid / 2, button_y + button.getHeight() / 2 + strhei / 2, paint);
//        float x = (float) (screenWidth / 2 - textRect.width() * 1.0 / 2);
        float x = getCenterTextXOfMe();
        float y = (float) (top + bmp.getHeight() * 1.0 / 2 + textRect.height() * 1.0 / 2);
        //Log.d("x y", "draw: x "+x +" y"+y);
        canvas.drawText(text, x, y, paint);

//			放在下面之后 把他覆盖了
//			做游戏的时候 坐标怎么知道的 怎么量出来
//        canvas.drawBitmap(bmp, left, top, paint);
//        if (isPlay) {
//            canvas.drawBitmap(bitmap, left, top, paint);
//        } else {
//            canvas.drawBitmap(bitmap, left, top - height, paint);
//        }
//        canvas.restore();

    }
}
