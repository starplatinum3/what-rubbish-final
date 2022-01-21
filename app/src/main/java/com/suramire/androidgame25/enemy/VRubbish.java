package com.suramire.androidgame25.enemy;

import android.graphics.Bitmap;
import android.view.View;

import com.example.whatrubbish.entity.Rubbish;

import java.util.List;


/**
 * 敌人类-板栗
 */
public class VRubbish extends Chestunt {

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

    public VRubbish(List<Bitmap> bitmaps, Rubbish rubbish,int x,int y) {
        super(bitmaps);
        this.rubbish = rubbish;
        setVisiable(true);
        setMirror(true);
        setPosition(x,y);
    }

}
