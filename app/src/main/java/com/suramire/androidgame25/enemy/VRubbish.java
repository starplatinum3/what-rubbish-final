package com.suramire.androidgame25.enemy;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.example.whatrubbish.entity.Rubbish;
import com.suramire.androidgame25.util.BitmapUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 敌人类-板栗
 */
public class VRubbish extends Chestunt {

    //private static final int  = 867;
     public    static  final String harmful="harmful";
    public  static    final String dry="dry";
    public  static final   String recycle="recycle";
    public  static final  String wet="wet";
    public Rubbish getRubbish() {
        return rubbish;
    }

    public void setRubbish(Rubbish rubbish) {
        this.rubbish = rubbish;
    }

    Rubbish rubbish;
    public VRubbish(int width, int height, List<Bitmap> bitmaps) {
        super(width, height, bitmaps);
    }

    public VRubbish( List<Bitmap> bitmaps) {
        super( bitmaps);
    }

    //public VRubbish(List<Bitmap> bitmaps, Rubbish rubbish) {
    //    super(bitmaps);
    //    this.rubbish = rubbish;
    //}


    public VRubbish(List<Bitmap> bitmaps, Rubbish rubbish) {
        super(bitmaps);
        this.rubbish = rubbish;
        setVisiable(true);
        setMirror(true);
    }

    Context context;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    String   typeName="dry";

    Bitmap getTypeBitmap( String   typeName){
        String  fileName= String.format("enemy/rubbish/%s/%s_0.png",typeName,typeName );
        //Bitmap rubbishBitmap = getBitmap("enemy/rubbish/typeName/foam_lunch_box.png");
        Bitmap rubbishBitmap =BitmapUtil. getBitmap(fileName,context);
        return rubbishBitmap;
    }

   public   List<Bitmap> initRubbishBitmaps(){
        //这是一个垃圾的不同的帧数时候的图片
        List<Bitmap>  rubbishBitmaps = new ArrayList<>();
        //餐盒子
        //rubbish.getTypeId()
        //String   typeName="dry";
        //Bitmap rubbishBitmap = getBitmap("enemy/rubbish/foam_lunch_box.png");
        //String  fileName= String.format("enemy/rubbish/%s/%s_0.png",typeName,typeName );
        //Bitmap rubbishBitmap = getBitmap("enemy/rubbish/typeName/foam_lunch_box.png");
        //Bitmap rubbishBitmap = BitmapUtil.getBitmap(fileName,context);
        //rubbishBitmaps.add(rubbishBitmap);
        //rubbishBitmaps.add(getTypeBitmap("harmful"));
        rubbishBitmaps.add(getTypeBitmap(typeName));
        //rubbishBitmaps.add(getTypeBitmap("recycle"));
        //rubbishBitmaps.add(getTypeBitmap("wet"));
        setBitmaps(rubbishBitmaps);
        return rubbishBitmaps;
    }


    public VRubbish(List<Bitmap> bitmaps, Rubbish rubbish,int x,int y) {
        super(bitmaps);
        this.rubbish = rubbish;
        setVisiable(true);
        setMirror(true);
        setPosition(x,y);
    }

    public VRubbish( Rubbish rubbish,int x,int y) {
        //super(bitmaps);
        super();
        this.rubbish = rubbish;
        setVisiable(true);
        setMirror(true);
        setPosition(x,y);
    }

}
