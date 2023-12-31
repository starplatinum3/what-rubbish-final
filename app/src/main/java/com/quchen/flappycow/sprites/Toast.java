/**
 * A yummy toast
 * 
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package com.quchen.flappycow.sprites;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.example.whatrubbish.R;
import com.quchen.flappycow.Util;

import android.graphics.Bitmap;

public class Toast extends PowerUp {
    
    /**
     * Static bitmap to reduce memory usage.
     * 静态位图以减少内存使用。
     */
    public static Bitmap globalBitmap;
    
    public static final int POINTS_TO_TOAST = 42;

    public Toast(GameView view, Game game) {
        super(view, game);
        if(globalBitmap == null){
            globalBitmap = Util.getScaledBitmapAlpha8(game, R.drawable.toast);
        }
        this.bitmap = globalBitmap;
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight();
    }

    /**
     * When eaten the player will turn into nyan cat.
     * 当玩家被吃掉时，他会变成年猫。
     */
    @Override
    public void onCollision() {
        super.onCollision();
        view.changeToNyanCat();
    }
    
    
}
