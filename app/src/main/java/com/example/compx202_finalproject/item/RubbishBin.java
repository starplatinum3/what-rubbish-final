package com.example.compx202_finalproject.item;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;

//import com.example.compx202_finalproject.R;

import com.example.whatrubbish.R;

import java.util.Random;

//  Make the class of the box, which include all the attributes of the box, and shouldShow is true to make it visible
//创建长方体的类，其中包括长方体的所有属性，并且shouldShow为true以使其可见
public class RubbishBin {
    private boolean isBroken;
    private boolean shouldShow;
    private float x;
    private float y;
    private final int width;
    private final int height;
    private final Paint paint;
    Bitmap bitmap;
    Resources resources;

    //  Set the position variable of the kiwi fruit, and shouldShow is true to make it visible
    //设置猕猴桃的位置变量，shouldShow为true以使其可见
    public RubbishBin(int width, int height) {
        this.isBroken = false;
        this.shouldShow = true;
        this.width = width;
        this.height = height;
        paint = new Paint();
        paint.setARGB(255, new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
        //bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.box_normal);
        bitmap = BitmapFactory.decodeResource(resources, R.mipmap.recycle);
        //this
    }

    public RubbishBin(int width, int height, int imgRes) {
        this.isBroken = false;
        this.shouldShow = true;
        this.width = width;
        this.height = height;
        paint = new Paint();
        paint.setARGB(255, new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
        //bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.box_normal);
        //bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.recycle);
        bitmap = BitmapFactory.decodeResource(resources, imgRes);
    }
}
