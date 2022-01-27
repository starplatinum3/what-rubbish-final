/**
 * An obstacle: spider + logHead
 *
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package com.quchen.flappycow.sprites;

import android.graphics.Canvas;

import com.example.whatrubbish.Bus;
import com.example.whatrubbish.R;
import com.example.whatrubbish.entity.Rubbish;
//import com.facebook.stetho.common.ListUtil;
import com.example.whatrubbish.util.ListUtil;
import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.MainActivity;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

//障碍
public class ObstacleWithRub extends Sprite {
    //private Spider chRubbish;
    private ChRubbish chRubbish;

    public ChRubbish getChRubbish() {
        return chRubbish;
    }

    public void setChRubbish(ChRubbish chRubbish) {
        this.chRubbish = chRubbish;
    }

    //ChRubbish chRubbish;
    private WoodLog log;

    private static int collideSound = -1;
    private static int passSound = -1;

    /** Necessary so the onPass method is just called once */
    public boolean isAlreadyPassed = false;

    public void solution() {
        List<Integer> givenList = Arrays.asList(1, 2, 3);
        Random rand = new Random();
        int randomElement = givenList.get(rand.nextInt(givenList.size()));
    }

    //public static <T> List<T> select(BaseDao<T> dao, T obj)
    public static <T> T randomGetOne(List<T> list) {
        //RoomUtil
        Random rand = new Random();
        int i = rand.nextInt(list.size());
        return list.get(i);
        //int randomElement = givenList.get(rand.nextInt(givenList.size()));
        //java 列表随机选取一个
    }

    public ObstacleWithRub(GameView view, Game game) {
        super(view, game);
        //chRubbish = new Spider(view, game);
        //哪种垃圾呢
        //获取所有的垃圾图片
        //new Rubbish()
        //java 列表随机选取一个
        //获得是引用 之后我会改变吗 应该只会读取吧 所以引用也没事 如果会改变的话 还是要clone
        Rubbish rubbish = ListUtil.randomGetOne(Bus.rubbishes);
        //ListUtil.copyToImmutableList()
        //chRubbish = new ChRubbish(view, game);
        chRubbish = new ChRubbish(view, game, rubbish);
        //chRubbish.setContext(conte);
        //context
        log = new WoodLog(view, game);

        if (collideSound == -1) {
            collideSound = Game.soundPool.load(game, R.raw.crash, 1);
        }
        if (passSound == -1) {
            passSound = Game.soundPool.load(game, R.raw.pass, 1);
        }

        initPos();
    }

    /**
     * Creates a spider and a wooden log at the right of the screen.
     * With a certain gap between them.
     * The vertical position is in a certain area random.
     */
    private void initPos() {
        int height = game.getResources().getDisplayMetrics().heightPixels;
        int gab = height / 4 - view.getSpeedX();
        if (gab < height / 5) {
            gab = height / 5;
        }
        int random = (int) (Math.random() * height * 2 / 5);
        //int y1 = (height / 10) + random - spider.height;
        int y1 = (height / 10) + random - chRubbish.height;
        int y2 = (height / 10) + random + gab;

        //spider.init(game.getResources().getDisplayMetrics().widthPixels, y1);
        chRubbish.init(game.getResources().getDisplayMetrics().widthPixels, y1);
        log.init(game.getResources().getDisplayMetrics().widthPixels, y2);
    }

    /**
     * Draws spider and log.
     */
    @Override
    public void draw(Canvas canvas) {
        //spider.draw(canvas);
        chRubbish.draw(canvas);
        log.draw(canvas);
    }

    /**
     * Checks whether both, spider and log, are out of range.
     */
    @Override
    public boolean isOutOfRange() {
        //return spider.isOutOfRange() && log.isOutOfRange();
        return chRubbish.isOutOfRange() && log.isOutOfRange();
    }

    /**
     * Checks whether the spider or the log is colliding with the sprite.
     */
    @Override
    public boolean isColliding(Sprite sprite) {
        //return spider.isColliding(sprite) || log.isColliding(sprite);
        return chRubbish.isColliding(sprite) || log.isColliding(sprite);
    }

    /**
     * Moves both, spider and log.
     */
    @Override
    public void move() {
        chRubbish.move();
        log.move();
    }

    /**
     * Sets the speed of the spider and the log.
     */
    @Override
    public void setSpeedX(float speedX) {
        chRubbish.setSpeedX(speedX);
        log.setSpeedX(speedX);
    }

    /**
     * Checks whether the spider and the log are passed.
     */
    @Override
    public boolean isPassed() {
        return chRubbish.isPassed() && log.isPassed();
    }

    private static final int SOUND_VOLUME_DIVIDER = 3;

    boolean rightRub=false;

    public boolean isRightRub() {
        return rightRub;
    }

    public void setRightRub(boolean rightRub) {
        this.rightRub = rightRub;
    }

    /**
     * Will call obstaclePassed of the game, if this is the first pass of this obstacle.
     */
    public void onPass() {
        if (!isAlreadyPassed) {
            isAlreadyPassed = true;
            if(rightRub){
                view.getGame().increasePoints(2);
            }else{
                view.getGame().increasePoints(1);
            }

            Game.soundPool.play(passSound, MainActivity.volume / SOUND_VOLUME_DIVIDER, MainActivity.volume / SOUND_VOLUME_DIVIDER, 0, 0, 1);
        }
    }

    @Override
    public void onCollision() {
        super.onCollision();
        Game.soundPool.play(collideSound, MainActivity.volume / SOUND_VOLUME_DIVIDER, MainActivity.volume / SOUND_VOLUME_DIVIDER, 0, 0, 1);
    }

}
