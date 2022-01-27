/**
 * The cow that is controlled by the player
 * 
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package com.quchen.flappycow.sprites;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.MainActivity;
import com.example.whatrubbish.R;
import com.quchen.flappycow.Util;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Cow extends PlayableCharacter {
    
    private static final int POINTS_TO_SIR = 23;
    private static final int POINTS_TO_COOL = 35;

    /** Static bitmap to reduce memory usage. */
    public static Bitmap globalBitmap;

    /** The moo sound */
    //private static int sound = -1;
    public static int sound = -1;

    /** sunglasses, hats and stuff */
    //private Accessory accessory;
    public Accessory accessory;

    public Cow(GameView view, Game game) {
        super(view, game);
        init();
        //if(globalBitmap == null){
        //    globalBitmap = Util.getScaledBitmapAlpha8(game, R.drawable.cow);
        //}
        //this.bitmap = globalBitmap;
        //this.width = this.bitmap.getWidth()/(colNr = 8);    // The image has 8 frames in a row
        //this.height = this.bitmap.getHeight()/4;            // and 4 in a column
        //this.frameTime = 3;        // the frame will change every 3 runs
        //this.y = game.getResources().getDisplayMetrics().heightPixels / 2;    // Startposition in in the middle of the screen
        //
        //if(sound == -1){
        //    sound = Game.soundPool.load(game, R.raw.cow, 1);
        //}
        //
        //this.accessory = new Accessory(view, game);
    }

    public Cow(GameView view, Game game,int imgRes) {
        super(view, game);
        init(imgRes);
    }

    public void init(int imgRes){
        if(globalBitmap == null){
            //globalBitmap = Util.getScaledBitmapAlpha8(game, R.drawable.cow);
            globalBitmap = Util.getScaledBitmapAlpha8(game,imgRes);
        }
        this.bitmap = globalBitmap;
        this.width = this.bitmap.getWidth()/(colNr = 8);    // The image has 8 frames in a row
        this.height = this.bitmap.getHeight()/4;            // and 4 in a column
        this.frameTime = 3;        // the frame will change every 3 runs
        //colNr = 8;
        //他只有一列 一行
        //colNr=1;
        //this.width = this.bitmap.getWidth();    // The image has 8 frames in a row
        ////this.height = this.bitmap.getHeight()/4;            // and 4 in a column
        //this.height = this.bitmap.getHeight();          // and 4 in a column
        //this.frameTime = 3;        // the frame will change every 3 runs

        this.y = game.getResources().getDisplayMetrics().heightPixels / 2;    // Startposition in in the middle of the screen

        if(sound == -1){
            sound = Game.soundPool.load(game, R.raw.cow, 1);
        }

        this.accessory = new Accessory(view, game);
    }


    void init(){
        init( R.drawable.cow);
        //if(globalBitmap == null){
        //    globalBitmap = Util.getScaledBitmapAlpha8(game, R.drawable.cow);
        //}
        //this.bitmap = globalBitmap;
        //this.width = this.bitmap.getWidth()/(colNr = 8);    // The image has 8 frames in a row
        //this.height = this.bitmap.getHeight()/4;            // and 4 in a column
        //this.frameTime = 3;        // the frame will change every 3 runs
        //this.y = game.getResources().getDisplayMetrics().heightPixels / 2;    // Startposition in in the middle of the screen
        //
        //if(sound == -1){
        //    sound = Game.soundPool.load(game, R.raw.cow, 1);
        //}
        //
        //this.accessory = new Accessory(view, game);
    }
    
    private void playSound(){
        Game.soundPool.play(sound, MainActivity.volume, MainActivity.volume, 0, 0, 1);
    }

    @Override
    public void onTap(){
        super.onTap();
        playSound();
    }
    
    /**
     * Calls super.move
     * and manages the frames. (flattering cape)
     */
    @Override
    public void move(){
        changeToNextFrame();
        super.move();
        
        // manage frames
        //3 是死掉的那一行
        if(row != 3){
            //其他行是 上飞 向下飞
            // not dead
            if(speedY > getTabSpeed() / 3 && speedY < getMaxSpeed() * 1/3){
                row = 0;
            }else if(speedY > 0){
                row = 1;
            }else{
                row = 2;
            }
        }
        
        if(this.accessory != null){
            this.accessory.moveTo(this.x, this.y);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(this.accessory != null && !isDead){
            this.accessory.draw(canvas);
        }
    }

    /**
     * Calls super.dead
     * And changes the frame to a dead cow -.-
     */
    @Override
    public void dead() {
        this.row = 3;
        this.frameTime = 3;
        super.dead();
    }
    
    @Override
    public void revive() {
        super.revive();
        this.accessory.setBitmap(Util.getScaledBitmapAlpha8(game, R.drawable.accessory_scumbag));
    }

    @Override
    public void upgradeBitmap(int points) {
        super.upgradeBitmap(points);
        if(points == POINTS_TO_SIR){
            this.accessory.setBitmap(Util.getScaledBitmapAlpha8(game, R.drawable.accessory_sir));
        }else if(points == POINTS_TO_COOL){
            this.accessory.setBitmap(Util.getScaledBitmapAlpha8(game, R.drawable.accessory_sunglasses));
        }
    }
    
}
