package com.suramire.androidgame25.item;

import static android.graphics.Color.BLUE;
import static com.suramire.androidgame25.enums.GameState.TRANSFER;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.metrics.Event;
import android.view.MotionEvent;

import com.suramire.androidgame25.util.BitmapUtil;

import lombok.Data;

@Data
public class CButton {
//    Rect rect;
    RectF rect=new RectF();
//    Rect rectTouch;
    RectF rectTouch;
    Bitmap bitmap;
    String  fileName;
    Context context;
    float scaleX;
    float scaleY;
//    float controllerHorizontalOffset;
    int controllerHorizontalOffset;
    public  void drawBitmap(Canvas canvas){
//        canvas.drawBitmap(bitmap, 740, 360, null);
        canvas.drawBitmap(bitmap, rect.left, rect.top, null);
    }
//    void d(){
//        bitmap = BitmapUtil. getBitmap(fileName,context);
//    }

//    public CButton(Rect rect, Rect rectTouch, Bitmap bitmap, String fileName, Context context) {
//        this.rect = rect;
//        this.rectTouch = rectTouch;
//        this.bitmap = bitmap;
//        this.fileName = fileName;
//        this.context = context;
//    }

//    public CButton(Rect rect, String fileName, Context context) {
//        this.rect = rect;
//        this.fileName = fileName;
//        this.context = context;
//        bitmap = BitmapUtil. getBitmap(fileName,context);
////        rectTouch.left=rect.left;
////        rectTouch.right=rect.right;
////        rectTouch.top=rect.top;
////        rectTouch.bottom=rect.bottom;
//        rectTouch.set((controllerHorizontalOffset+rect.left)* scaleX, rect.top * scaleY,
//                (controllerHorizontalOffset+rect.right )* scaleX, rect.bottom * scaleY);
//    }

    public CButton(RectF rect, String fileName, Context context,
                   float scaleX, float scaleY, int controllerHorizontalOffset) {
        this.rect = rect;
        this.fileName = fileName;
        this.context = context;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.controllerHorizontalOffset = controllerHorizontalOffset;

        bitmap = BitmapUtil. getBitmap(fileName,context);

        rectTouch.set((rect.left)* scaleX, rect.top * scaleY,
                (rect.right )* scaleX, rect.bottom * scaleY);
//        rectTouch.set((controllerHorizontalOffset+rect.left)* scaleX, rect.top * scaleY,
//                (controllerHorizontalOffset+rect.right )* scaleX, rect.bottom * scaleY);
    }

    public CButton(RectF rect, String fileName, Context context,
                   float scaleX, float scaleY) {
        this.rect = rect;
        this.fileName = fileName;
        this.context = context;
        this.scaleX = scaleX;
        this.scaleY = scaleY;

        bitmap = BitmapUtil. getBitmap(fileName,context);

        rectTouch.set((rect.left)* scaleX, rect.top * scaleY,
                (rect.right )* scaleX, rect.bottom * scaleY);
//        rectTouch.set((controllerHorizontalOffset+rect.left)* scaleX, rect.top * scaleY,
//                (controllerHorizontalOffset+rect.right )* scaleX, rect.bottom * scaleY);
    }

    public CButton(int left,int top, String fileName, Context context,
                   float scaleX, float scaleY) {


        this.fileName = fileName;
        this.context = context;
        this.scaleX = scaleX;
        this.scaleY = scaleY;


        bitmap = BitmapUtil. getBitmap(fileName,context);
        this.rect =new RectF(left,top,left+bitmap.getWidth(),top+bitmap.getHeight());
//        new Rect(left,top,left+bitmap.getWidth(),top+bitmap.getHeight());
        rectTouch=new RectF();
        rectTouch.set((rect.left)* scaleX, rect.top * scaleY,
                (rect.right )* scaleX, rect.bottom * scaleY);
//        rectTouch.set((controllerHorizontalOffset+rect.left)* scaleX, rect.top * scaleY,
//                (controllerHorizontalOffset+rect.right )* scaleX, rect.bottom * scaleY);
    }


    public  boolean isHit(float x, float y){
//        rectF.set((controllerHorizontalOffset+60)* scaleX, 420 * scaleY,
//                (controllerHorizontalOffset+120 )* scaleX, 480 * scaleY);
//        paint.setColor(BLUE);
//        lockCanvas.drawRect(controllerHorizontalOffset+60, 420 * scaleY,
//                controllerHorizontalOffset+120 * scaleX, 480 * scaleY,paint);
        return rectTouch.contains(x, y);
//        if (rectTouch.contains(x, y)) {
//            if (isTransferReady) {
//                gameState = TRANSFER;
//                mySoundPool.play(mySoundPool.getTransferSound());
//            }
//        }

    }

//    public  boolean isHit(MotionEvent event){
////        rectF.set((controllerHorizontalOffset+60)* scaleX, 420 * scaleY,
////                (controllerHorizontalOffset+120 )* scaleX, 480 * scaleY);
////        paint.setColor(BLUE);
////        lockCanvas.drawRect(controllerHorizontalOffset+60, 420 * scaleY,
////                controllerHorizontalOffset+120 * scaleX, 480 * scaleY,paint);
////        event.getX();
//        return rectTouch.contains(x, y);
////        if (rectTouch.contains(x, y)) {
////            if (isTransferReady) {
////                gameState = TRANSFER;
////                mySoundPool.play(mySoundPool.getTransferSound());
////            }
////        }
//
//    }
}
