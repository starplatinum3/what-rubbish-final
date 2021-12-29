package com.hurteng.stormplane.widget;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.constraintlayout.solver.widgets.Rectangle;

import com.hurteng.stormplane.myplane.R;

public class Button {
    Bitmap bmp;
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

    void initTextRect() {
//        paint.setTextSize(40);
        paint.getTextBounds(text, 0, text.length(), textRect);
    }

    void putBmp() {
        bmp = BitmapFactory.decodeResource(resources, res);
    }

    public void release() {
        if (!bmp.isRecycled()) {
            bmp.recycle();
        }
    }
    public Button(int res, float screenWidth, float screenHeight, Resources resources) {

        this.res = res;

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.resources = resources;
        putBmp();
        left = (float) (screenWidth / 2 - bmp.getWidth() * 1.0 / 2);
        top = screenHeight / 2 + bmp.getHeight();
    }

    public Button(float width, float height, int res, float screenWidth, float screenHeight, Resources resources) {
        this.width = width;
        this.height = height;
        this.res = res;

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.resources = resources;
        putBmp();
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

    public void draw(Canvas canvas, Paint paint) {
        canvas.clipRect(left, top, left + width, top + height);
//        canvas.drawText(text, screenWidth / 2 - strwid / 2, button_y + button.getHeight() / 2 + strhei / 2, paint);
        float x = (float) (screenWidth / 2 - textRect.width() * 1.0 / 2);
        float y = (float) (top + bmp.getHeight() * 1.0 / 2 + textRect.height() * 1.0 / 2);
        canvas.drawText(text, x, y, paint);

//			放在下面之后 把他覆盖了
//			做游戏的时候 坐标怎么知道的 怎么量出来
        canvas.drawBitmap(bmp, left, top, paint);
//        if (isPlay) {
//            canvas.drawBitmap(bitmap, left, top, paint);
//        } else {
//            canvas.drawBitmap(bitmap, left, top - height, paint);
//        }
        canvas.restore();

    }
}
