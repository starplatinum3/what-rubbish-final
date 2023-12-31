/**
 * The abstract spriteclass for power-ups
 * 
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package com.quchen.flappycow.sprites;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;

/**
 * 加分的组件吗
 */
public abstract class PowerUp extends Sprite {
    public PowerUp(GameView view, Game game) {
        super(view, game);
        init();
    }
    
    /**
     * Sets this sprite above the visible screen.
     * At x = 4/5 of the screen.
     * Uses the speed of the GameView to let the power-up fall slowly down.
     * *将此精灵设置在可见屏幕上方。
     * *在屏幕的x=4/5处。
     * *使用GameView的速度让通电缓慢下降。
     */
    private void init(){
        this.x = view.getWidth() * 4/5;
        this.y = 0 - this.height;
        this.speedX = - view.getSpeedX();
        this.speedY = (int) (view.getSpeedX() * (Math.random() + 0.5));
    }
}
