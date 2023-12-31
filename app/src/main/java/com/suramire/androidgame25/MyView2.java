package com.suramire.androidgame25;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.example.whatrubbish.Bus;
import com.example.whatrubbish.R;
import com.example.whatrubbish.entity.Rubbish;
import com.example.whatrubbish.entity.RubbishType;
import com.example.whatrubbish.util.HttpUtil;
import com.suramire.androidgame25.audio.MyMusic;
import com.suramire.androidgame25.audio.MySoundPool;
import com.suramire.androidgame25.enemy.Cannon;
import com.suramire.androidgame25.enemy.Chestunt;
import com.suramire.androidgame25.enemy.Enemy;
import com.suramire.androidgame25.enemy.Turtle;
import com.suramire.androidgame25.enemy.VRubbish;
import com.suramire.androidgame25.enums.GameState;
import com.suramire.androidgame25.enums.ItemType;
import com.suramire.androidgame25.enums.Site;
import com.suramire.androidgame25.item.Bullet;
import com.suramire.androidgame25.item.CButton;
import com.suramire.androidgame25.item.Coin;
import com.suramire.androidgame25.item.EnemyBullet;
import com.suramire.androidgame25.item.Flower;
import com.suramire.androidgame25.item.ItemSprite;
import com.suramire.androidgame25.item.Mushroom;
import com.suramire.androidgame25.item.Star;
import com.suramire.androidgame25.item.brick.Brick;
import com.suramire.androidgame25.item.brick.Broken;
import com.suramire.androidgame25.item.brick.CommonBrick;
import com.suramire.androidgame25.item.brick.Pipe;
import com.suramire.androidgame25.util.BitmapUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.suramire.androidgame25.enums.GameState.FINISH;
import static com.suramire.androidgame25.enums.GameState.GAMEOVER;
import static com.suramire.androidgame25.enums.GameState.GAMING;
import static com.suramire.androidgame25.enums.GameState.LIFTCOUNTER;
import static com.suramire.androidgame25.enums.GameState.LOGO;
import static com.suramire.androidgame25.enums.GameState.TRANSFER;
import static com.suramire.androidgame25.enums.Site.上中;
import static com.suramire.androidgame25.enums.Site.上右;
import static com.suramire.androidgame25.enums.Site.上左;
import static com.suramire.androidgame25.enums.Site.下;
import static com.suramire.androidgame25.enums.Site.下中;
import static com.suramire.androidgame25.enums.Site.下右;
import static com.suramire.androidgame25.enums.Site.下左;
import static com.suramire.androidgame25.enums.Site.右;
import static com.suramire.androidgame25.enums.Site.右上;
import static com.suramire.androidgame25.enums.Site.右下;
import static com.suramire.androidgame25.enums.Site.右中;
import static com.suramire.androidgame25.enums.Site.左;
import static com.suramire.androidgame25.enums.Site.左上;
import static com.suramire.androidgame25.enums.Site.左下;
import static com.suramire.androidgame25.enums.Site.左中;


public class MyView2 extends SurfaceView implements Callback, Runnable {
    public static final String TAG = "aaa";
    private final MyMusic myMusic;
    private final MySoundPool mySoundPool;
    private final SharedPreferences sp;
    //region Field
    private boolean isPause;
    private SurfaceHolder holder;
    private boolean isRunning;
    private Canvas lockCanvas;
    private float scaleX;
    private float scaleY;
    private Context context;
    private Bitmap aBitmap;
    private Bitmap bBitmap;
    private Bitmap leftBitmap;
    private Bitmap rightBitmap;
    int controllerHorizontalOffset=30;

    private RectF rectF;
    //private Mario vTrashBin;
    private VTrashBin vTrashBin;
    private TiledLayer tiledLayer_peng01;
    private Bitmap gameoverbitmap;
    private List<Sprite> chestunts;
    private List<Bitmap> chestuntBitmaps;
    private boolean isEnemyShown1;
    private boolean isEnemyShown2;
    private boolean isEnemyShown3;
    private Integer score;//用于记录分数
    private Paint mPaint;
    private Bitmap marioBitmap;
    private int lifeNumber = 3;
    private boolean isInited;
    private String scoreString;
    private int delay;
    private Bitmap logoBitmap;
    private List<Sprite> bricks;
    private int time;//表示剩余时间
    private Thread thread;
    private Bitmap finishBitmap;
    private boolean threadRunning;
    private Bitmap coinBitmap;
    private int coinNumber;
    private GameState gameState;//表示当前游戏状态
    private int stateDelay_lifecounter;
    private int stateDelay_finish;
    private int stateDelay_logo;
    private int stateDelay_gameover;
    private boolean isFirstRun;
    private boolean isMaioDieSoundPlayed;
    private List<Sprite> commonBricks;
    private TiledLayer tiledLayer_cover;
    private Bitmap enemyBulletBitmap;
    private List<Bitmap> cannonBitmaps;
    private List<Bitmap> turtleBitmaps;
    private List<Sprite> cannons;
    private List<Sprite> turtles;
    private List<Sprite> items;
    private boolean isEnemyShown4;
    private boolean isEnemyShown5;
    private List<Bitmap> fireBitmaps;
    private Bitmap downBitmap;
    private List<Sprite> pipes;
    private boolean isTransferReady;
    private boolean isTransferDone;
    private CButton buttonA;
    //private VTrashBin vTrashBin;
    //    private Bitmap mushroomBitmap;

    public boolean isPause() {
        return isPause;
    }

    public void setPause(boolean pause) {
        isPause = pause;
    }

    //endregion
    //region 通用方法
    public MyView2(Context context) {
        super(context);
        this.context = context;
        sp = context.getSharedPreferences("sp", Context.MODE_PRIVATE);
        holder = getHolder();
        holder.addCallback(this);
        Point outSize = new Point();
        //获取屏幕尺寸以便进行缩放调整
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getRealSize(outSize);
//        安卓 横屏
        //获取对应在缩放比例
        scaleX = (float) outSize.x / 800;
        scaleY = (float) outSize.y / 480;

//        scaleX = (float) outSize.x / 900;
//        scaleY = (float) outSize.y / 500;
        myMusic = new MyMusic(context);
        mySoundPool = new MySoundPool(context);
    }

    int upTop=300;
    int upBottom=360;

    int leftTop=300;
    int leftBottom=360;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isRunning = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
        myMusic.stop();
        threadRunning = false;
        //游戏退出时记录最高分
        Integer hiscore = sp.getInt("hiscore", 0);
        if (hiscore < score) {
            sp.edit().putInt("hiscore", score).apply();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gameState != null) {
            switch (gameState) {
                case LOGO: {
                    if (stateDelay_logo > 40) {
                        init();
                        stateDelay_logo = 0;
                        coinNumber = 0;
                    }
                }
                break;
                case FINISH: {
                    if (stateDelay_finish > 100) {
                        gameState = LOGO;
                        lifeNumber = 3;
                        stateDelay_finish = 0;
                    }
                }
                break;
                case GAMEOVER: {
                    if (stateDelay_gameover > 40) {
                        gameState = LOGO;
                        lifeNumber = 3;
                        stateDelay_gameover = 0;
                    }

                }
                case GAMING: {
                    int pointCount = event.getPointerCount();
                    for (int i = 0; i < pointCount; i++) {
//                        rectF.set(0, 360 * scaleY, 60 * scaleX, 420 * scaleY);
//                        rectF.set(0, upTop * scaleY, 60 * scaleX, upBottom * scaleY);
//                        rectF.set(0, leftTop * scaleY, 60 * scaleX, leftBottom * scaleY);
//                        rectF.set(controllerHorizontalOffset+0, leftTop * scaleY,
//                                controllerHorizontalOffset+60 * scaleX, leftBottom * scaleY);
//                        rectF.set(controllerHorizontalOffset+0, leftTop * scaleY,
//                                controllerHorizontalOffset+30 * scaleX, leftBottom * scaleY);
                        rectF.set(controllerHorizontalOffset+0, leftTop * scaleY,
                                (controllerHorizontalOffset+30) * scaleX, leftBottom * scaleY);
//                        Paint paint = new Paint();
//                        paint.setColor(YELLOW);
//                        lockCanvas.drawRect(controllerHorizontalOffset+0, leftTop * scaleY,
//                                (controllerHorizontalOffset+30) * scaleX, leftBottom * scaleY,paint);
                        if (rectF.contains(event.getX(i), event.getY(i))) {
                            //left running
                            vTrashBin.setMirror(true);
                            vTrashBin.setRunning(true);

                            //vTrashBin.setMirror(true);
                            //vTrashBin.setRunning(true);
                        }
//                        rectF.set(60, 420 * scaleY, 120 * scaleX, 480 * scaleY);
//                        rectF.set(controllerHorizontalOffset+60, 420 * scaleY,
//                                controllerHorizontalOffset+120 * scaleX, 480 * scaleY);
                        rectF.set((controllerHorizontalOffset+60)* scaleX, 420 * scaleY,
                                (controllerHorizontalOffset+120 )* scaleX, 480 * scaleY);
//                        paint.setColor(BLUE);
//                        lockCanvas.drawRect(controllerHorizontalOffset+60, 420 * scaleY,
//                                controllerHorizontalOffset+120 * scaleX, 480 * scaleY,paint);
                        if (rectF.contains(event.getX(i), event.getY(i))) {
                            if (isTransferReady) {
                                gameState = TRANSFER;
                                mySoundPool.play(mySoundPool.getTransferSound());
                            }
                        }

//                        rectF.set(120, 360 * scaleY, 180 * scaleX, 420 * scaleY);
//                        rectF.set(120, leftTop * scaleY, 180 * scaleX, leftBottom * scaleY);
//                        rectF.set(controllerHorizontalOffset+120, leftTop * scaleY,
//                                controllerHorizontalOffset+180 * scaleX, leftBottom * scaleY);
                        rectF.set((controllerHorizontalOffset+120)* scaleX, leftTop * scaleY,
                                (controllerHorizontalOffset+180 )* scaleX, leftBottom * scaleY);
//                        paint.setColor(RED);
////                        lockCanvas.drawRect(controllerHorizontalOffset+120, leftTop * scaleY,
////                                controllerHorizontalOffset+180 * scaleX, leftBottom * scaleY,paint);
//                        lockCanvas.drawRect((controllerHorizontalOffset+120)* scaleX, leftTop * scaleY,
//                                (controllerHorizontalOffset+180 )* scaleX, leftBottom * scaleY,paint);
                        if (rectF.contains(event.getX(i), event.getY(i))) {
                            //right running
                            vTrashBin.setMirror(false);
                            vTrashBin.setRunning(true);
                        }

                        if(buttonB.isHit(event.getX(i), event.getY(i))){
                            //jump
                            if (!vTrashBin.isDead() && !vTrashBin.isJumping()) {
                                vTrashBin.setJumping(true);
                                mySoundPool.play(mySoundPool.getJumpSound());
                                vTrashBin.setSpeedY(-16);
                            }
                        }
//                        只需要 left 和 top吗，width 是 根据bitmap获得？
//                        rectF.set(740, 360 * scaleY, 800 * scaleX, 420 * scaleY);
//                        if (rectF.contains(event.getX(i), event.getY(i))) {
//                            //jump
//                            if (!mario.isDead() && !mario.isJumping()) {
//                                mario.setJumping(true);
//                                mySoundPool.play(mySoundPool.getJumpSound());
//                                mario.setSpeedY(-16);
//                            }
//                        }

                        if(buttonA.isHit(event.getX(i), event.getY(i))){
                            vTrashBin.fire();
                        }
//                        rectF.set(680, 420 * scaleY, 740 * scaleX, 4800 * scaleY);
//                        if (rectF.contains(event.getX(i), event.getY(i))) {
//                            mario.fire();
//                        }
                        //手指移开时停止移动
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            //stop running
                            vTrashBin.setRunning(false);
                            performClick();
                        }
                    }
                }
                break;
            }

        }


        return true;
    }


    @Override
    public void run() {
        isFirstRun = true;
        init();
        while (isRunning) {
            //游戏暂停
            if (!isPause()) {
                myLogic();
            }
            long startTime = System.currentTimeMillis();
            myDraw();
            long endTime = System.currentTimeMillis();
            long time = endTime - startTime;
            if (time < 1000 / 35) {
                SystemClock.sleep(1000 / 35 - time);
            }
        }
    }

    /**
     * 加载图片
     *
     * @param fileName 图片文件名
     * @return Bitmap
     */
    public Bitmap getBitmap(String fileName) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(context.getAssets().open(fileName));
        } catch (IOException e) {
            Log.e(TAG, "getBitmap is null !!!");
        }
        return bitmap;
    }

    private int[][] getMapArray() {
        return new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 27, 27, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 27, 27, 27, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 27, 27, 27, 27, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 27, 27, 27, 27, 27, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 27, 0, 0, 0, 0, 0, 0, 0,
                        0, 27, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 27, 27, 27, 27, 27, 27, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 27, 27, 0, 0, 0, 0, 0, 0, 0,
                        0, 27, 27, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 27, 27, 27, 27, 27, 27, 27, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 27, 27, 27, 0, 0, 0, 0, 0, 0, 0,
                        0, 27, 27, 27, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 27, 27, 27, 27, 27, 27, 27, 27, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26,
                        26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26,
                        26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26,
                        26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 0, 0, 26, 26, 26, 26,
                        26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26,
                        26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26,
                        26, 26, 26, 26},
                {30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30,
                        30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30,
                        30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30,
                        30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 0, 0, 30, 30, 30, 30,
                        30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30,
                        30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30,
                        30, 30, 30, 30},
                {30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30,
                        30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30,
                        30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30,
                        30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 0, 0, 30, 30, 30, 30,
                        30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30,
                        30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30,
                        30, 30, 30, 30}
        };
    }

    private int[][] getMapCoverArray() {
        return new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 45, 50, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 55, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 45, 50, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 45, 50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        55, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 55, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 41,
                        42, 43, 44, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 7, 8, 9, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 46,
                        47, 48, 49, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 17, 18, 19,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 12, 13, 14, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 1, 2, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 51,
                        52, 53, 54, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0}
        };
    }

    /**
     * 通用的精灵移动方法
     *
     * @param sprites   目标精灵
     * @param distenceX x轴移动距离
     */
    private void spritesMove(List<Sprite> sprites, int distenceX) {
        if (sprites != null) {
            for (int i = 0; i < sprites.size(); i++) {
                Sprite sprite = sprites.get(i);
                sprite.move(distenceX, 0);
                if (sprite instanceof CommonBrick) {
                    Broken broken = ((CommonBrick) sprite).getBroken();
                    if (broken != null && broken.isVisiable()) {
                        broken.move(distenceX, 0);
                    }
                }
            }
        }
    }

    /**
     * 通用精灵绘制方法
     *
     * @param sprites 目标精灵
     * @param canvas  画布对象
     */
    private void spritesDraw(List<Sprite> sprites, Canvas canvas) {
        if (sprites != null) {
//            Log.i(TAG, "spritesDraw: sprites.size()  "+sprites.size());
            for (int i = 0; i < sprites.size(); i++) {
//                Log.i(TAG, "spritesDraw: i "+i);
                try {
                    Sprite sprite = sprites.get(i);
                    try{
                        sprite.draw(canvas);
                    }catch (Exception e){
                        //获得一个抽象类实际的类型
                        if(sprite instanceof  VRubbish){
                            //String canonicalName = sprite.getClass().getCanonicalName();
                            //sout
                            Log.i(TAG, "spritesDraw: VRubbish 问题");
                        }
                        String canonicalName = sprite.getClass().getCanonicalName();
                        Log.i(TAG, "spritesDraw: canonicalName "+canonicalName);
                    }

                    //绘制普通砖块破碎效果
                    if (sprite instanceof CommonBrick) {
                        Broken broken = ((CommonBrick) sprite).getBroken();
                        if (broken != null && broken.isVisiable()) {
                            broken.draw(canvas);
                        }
                    }
                }catch (ArrayIndexOutOfBoundsException e){
                    e.printStackTrace();
                }


            }
        }
    }

    private void spritesLogic(List<Sprite> sprites) {
        if (sprites != null) {
            for (int i = 0; i < sprites.size(); i++) {
                Sprite sprite = sprites.get(i);
                sprite.logic();
                //普通砖块破碎效果逻辑
                if (sprite instanceof CommonBrick) {
                    Broken broken = ((CommonBrick) sprite).getBroken();
                    if (broken != null && broken.isVisiable()) {
                        broken.logic();
                    }
                }
            }

        }
    }


    //endregion

    private void myLogic() {
        switch (gameState) {
            case TRANSFER: {
                if (!vTrashBin.isDead()) {
                    vTrashBin.move(0, 3);
                    if (vTrashBin.getY() > 280) {
                        tiledLayer_peng01.move(-980, 0);
                        tiledLayer_cover.move(-980, 0);
                        //到达地图边界 人物移动
                        spritesMove(chestunts, -980);
                        spritesMove(cannons, -980);
                        spritesMove(turtles, -980);
                        spritesMove(bricks, -980);
                        spritesMove(commonBricks, -980);
                        spritesMove(items, -980);
                        spritesMove(pipes, -980);
                        spritesMove(rubbishes, -980);
                        isTransferDone = false;
                        gameState = GAMING;
                    }
                }
            }
            break;
            case GAMING: {
                if (!threadRunning && isInited) {
                    thread.start();
                    threadRunning = true;
                }
                if(!isTransferDone && vTrashBin.getY()>216){
                    vTrashBin.move(0,-3);
                    if(vTrashBin.getY()<=216){
                        isTransferDone = true;
                    }
                }else{
                    vTrashBin.logic();
                    if (!vTrashBin.isDead()) {

                        if (vTrashBin.isInvincible()) {
                            myMusic.play("music/invincible.mp3", true);
                        } else {
                            myMusic.play("music/bgm.mp3", true);
                        }
                        spritesLogic(cannons);
                        spritesLogic(chestunts);
                        spritesLogic(vTrashBin.getBullets());
                        spritesLogic(commonBricks);
                        spritesLogic(items);
                        spritesLogic(turtles);
                        spritesLogic(bricks);
                        spritesLogic(pipes);
                        spritesLogic(rubbishes);
                    } else {
                        if (!isMaioDieSoundPlayed) {
                            myMusic.play("music/over.mp3", false);
                            isMaioDieSoundPlayed = true;
                        }
                    }
                    marioMove();
                    myStep();
                    collisionWithMap();
                    myCollision();
                }

                myTime();
            }
            break;
            case LIFTCOUNTER: {
                myMusic.stop();
                if (lifeNumber < 1) {
                    gameState = GAMEOVER;
                } else {
                    if (stateDelay_lifecounter++ > 50) {
                        gameState = GAMING;
                        init();
                        stateDelay_lifecounter = 0;
                    }
                }
            }
            break;
            case GAMEOVER: {
                stateDelay_gameover++;

            }
            break;
            case FINISH: {
                myMusic.play("music/finish.mp3", false);
                threadRunning = false;
                stateDelay_finish++;
            }
            break;
            case LOGO: {
                stateDelay_logo++;
                myMusic.stop();
            }
            break;
        }
    }

    /**
     * 超时处理
     */
    private void myTime() {
        if (time <= 0) {
            vTrashBin.setDead(true);
        }
    }

    /**
     * 更新分数
     *
     * @param scoreValue 分数值
     */
    private void updateScore(int scoreValue) {
        if (scoreValue > 0) {
            score += scoreValue;
            scoreString = "+" + scoreValue;
        }
        if (scoreValue < 0) {
            score -= scoreValue;
            scoreString = "-" + scoreValue;
        }

    }

   final static int yFloor=328;
    /**
     * 游戏进度控制
     */
    private void myStep() {
        if (tiledLayer_peng01.getX() == 0 && !isEnemyShown1) {
//            展示一些 怪物 第一个场景吗
            for (int i = 0; i < 3; i++) {
                Enemy chestunt = new Chestunt(32, 32, chestuntBitmaps);
                chestunt.setVisiable(true);
                chestunt.setMirror(true);
                chestunt.setPosition(500 + 60 * i, 328);
                chestunts.add(chestunt);

            }

            initRubbish();

//            //Enemy rubbish = new Chestunt(64, 81, rubbishBitmaps);
//            //Enemy rubbish = new VRubbish(64, 81, rubbishBitmaps);
//            //Rubbish rubbishEntity = new Rubbish();
//            //rubbishEntity.
//            //HttpUtil.post(Bus.baseDbUrl+Bus.rub)
//            Rubbish build = Rubbish.builder().typeId(1L).build();
//            Rubbish type2 = Rubbish.builder().typeId(2L).build();
//            //哪里来的数据呢 数据库吗
//            Enemy rubbish = new VRubbish( rubbishBitmaps,build);
//            //rubbish.se
//            //rubbish.setVisiable(true);
//            //rubbish.setMirror(true);
////            rubbish.setPosition(424, yFloor);
////            rubbish.setPosition(424, 312);
////            如果位置 太往下的话 就不会返回
//            rubbish.setPosition(424, 288);
//            rubbishes.add(rubbish);
//            //rubbishes.add(new VRubbish( rubbishBitmaps,type2,450,288));
//            rubbishes.add(new VRubbish( rubbishBitmaps,type2,500,288));

//            for (int i = 0; i < 3; i++) {
//                Enemy chestunt = new Chestunt(32, 32, chestuntBitmaps);
//                chestunt.setVisiable(true);
//                chestunt.setMirror(true);
//                chestunt.setPosition(500 + 60 * i, 328);
//                chestunts.add(chestunt);
//
//            }

            for (int i = 0; i < 2; i++) {
                Turtle turtle = new Turtle(32, 48, turtleBitmaps);
                turtle.setVisiable(true);
                turtle.setMirror(false);
                turtle.setPosition(360 + 40 * i, 312);
                turtles.add(turtle);
            }
            isEnemyShown1 = true;

        }
        //第二个场景
        if (tiledLayer_peng01.getX() == -480 && !isEnemyShown2) {
            //for (int i = 0; i < 3; i++) {
            //    Enemy enemy = new Chestunt(32, 32, chestuntBitmaps);
            //    enemy.setVisiable(true);
            //    enemy.setPosition(720 + 60 * i, 328);
            //    chestunts.add(enemy);
            //}
            initRubbish();
            for (int i = 0; i < 2; i++) {
                Cannon cannon = new Cannon(40, 80, cannonBitmaps, mySoundPool);
                cannon.setVisiable(true);
                cannon.setPosition(800 + 80 * i, 160 - 40 * i);
                EnemyBullet enemyBullet = new EnemyBullet(enemyBulletBitmap);
                enemyBullet.setMirror(false);
                List<Sprite> bullets = new ArrayList<>();
                bullets.add(enemyBullet);
                cannon.setBullets(bullets);
                cannons.add(cannon);
            }

            isEnemyShown2 = true;
        }
        if (tiledLayer_peng01.getX() == -1000 && !isEnemyShown3) {
            for (int i = 0; i < 3; i++) {
                Enemy chestunt = new Chestunt(32, 32, chestuntBitmaps);
                chestunt.setVisiable(true);
                chestunt.setPosition(500 + 60 * i, 0);
                chestunts.add(chestunt);
            }
            isEnemyShown3 = true;
        }
        if (tiledLayer_peng01.getX() == -1400 && !isEnemyShown4) {
            for (int i = 0; i < 2; i++) {
                Turtle turtle = new Turtle(32, 48, turtleBitmaps);
                turtle.setVisiable(true);
                turtle.setMirror(false);
                turtle.setCanFly(true);
                turtle.setPosition(700 + 40 * i, 100);
                turtles.add(turtle);
            }
            Cannon cannon = new Cannon(40, 80, cannonBitmaps, mySoundPool);
            cannon.setVisiable(true);
            cannon.setPosition(640, 160);
            EnemyBullet enemyBullet = new EnemyBullet(enemyBulletBitmap);
            enemyBullet.setMirror(false);
            List<Sprite> bullets = new ArrayList<>();
            bullets.add(enemyBullet);
            cannon.setBullets(bullets);
            cannons.add(cannon);
            isEnemyShown4 = true;
        }
        if (tiledLayer_peng01.getX() == -2200 && !isEnemyShown5) {
            for (int i = 0; i < 7; i++) {
                Enemy chestunt = new Chestunt(32, 32, chestuntBitmaps);
                chestunt.setVisiable(true);
                chestunt.setPosition(360 + 40 * i, 240 - 40 * i);
                chestunts.add(chestunt);
            }
            isEnemyShown5 = true;
        }


        if (tiledLayer_peng01.getX() == -2800) {
            gameState = FINISH;
        }
    }

    int groudY=288;
    void initRubbish(){
        //Enemy rubbish = new Chestunt(64, 81, rubbishBitmaps);
        //Enemy rubbish = new VRubbish(64, 81, rubbishBitmaps);
        //Rubbish rubbishEntity = new Rubbish();
        //rubbishEntity.
        //HttpUtil.post(Bus.baseDbUrl+Bus.rub)
        //List<Bitmap> rubbishBitmaps = initRubbishBitmaps();


        Rubbish build = new Rubbish();
        build.setTypeId(1L);
        //Rubbish build = Rubbish.builder().typeId(1L).build();
        Rubbish type2 = new Rubbish();
        type2.setTypeId(2L);
        //Rubbish type2 = Rubbish.builder().typeId(2L).build();
        //哪里来的数据呢 数据库吗
//        Enemy rubbish = new VRubbish( rubbishBitmaps,build);
//        //rubbish.se
//        //rubbish.setVisiable(true);
//        //rubbish.setMirror(true);
////            rubbish.setPosition(424, yFloor);
////            rubbish.setPosition(424, 312);
////            如果位置 太往下的话 就不会返回
//        rubbish.setPosition(424, 288);
//        //rubbish.setPosition(424, 288);
//        rubbishes.add(rubbish);

        VRubbish vRubbish = new VRubbish( type2, 424, groudY);
        vRubbish.setRubbish(build);
        vRubbish.setContext(context);
        vRubbish.setTypeName(VRubbish.recycle);
        //vRubbish.initWithBitmaps();
        vRubbish.initRubbishBitmaps();
        vRubbish.initByMyBitmaps();
        //rubbishes.add(new VRubbish( rubbishBitmapsType2,type2,500,288));
        rubbishes.add(vRubbish);


        //rubbishes.add(new VRubbish( rubbishBitmaps,type2,450,288));

        //List<Bitmap>  rubbishBitmapsType2 = new ArrayList<>();
        //Bitmap rubbishBitmap = getBitmap("enemy/rubbish/foam_lunch_box.png");
        //Bitmap rubbishBitmap = getBitmap("enemy/rubbish/harmful/harmful_2.png");
        //rubbishBitmapsType2.add(rubbishBitmap);
        //rubbishBitmapsType2.add(getBitmap("enemy/rubbish/harmful/harmful_2.png"));

        //VRubbish vRubbish = new VRubbish(rubbishBitmapsType2, type2, 500, 288);
        VRubbish vRubbish2 = new VRubbish( type2, 500, 288);
        vRubbish2.setRubbish(type2);
        vRubbish2.setContext(context);
        vRubbish2.setTypeName("harmful");
        //vRubbish.initWithBitmaps();
        vRubbish2.initRubbishBitmaps();
        vRubbish2.initByMyBitmaps();
        //rubbishes.add(new VRubbish( rubbishBitmapsType2,type2,500,288));
        rubbishes.add(vRubbish2);

    }


//    void initRubbish(){
//        //Enemy rubbish = new Chestunt(64, 81, rubbishBitmaps);
//        //Enemy rubbish = new VRubbish(64, 81, rubbishBitmaps);
//        //Rubbish rubbishEntity = new Rubbish();
//        //rubbishEntity.
//        //HttpUtil.post(Bus.baseDbUrl+Bus.rub)
//        List<Bitmap> rubbishBitmaps = initRubbishBitmaps();
//
//
//        Rubbish build = Rubbish.builder().typeId(1L).build();
//        Rubbish type2 = Rubbish.builder().typeId(2L).build();
//        //哪里来的数据呢 数据库吗
//        Enemy rubbish = new VRubbish( rubbishBitmaps,build);
//        //rubbish.se
//        //rubbish.setVisiable(true);
//        //rubbish.setMirror(true);
////            rubbish.setPosition(424, yFloor);
////            rubbish.setPosition(424, 312);
////            如果位置 太往下的话 就不会返回
//        rubbish.setPosition(424, 288);
//        rubbishes.add(rubbish);
//        //rubbishes.add(new VRubbish( rubbishBitmaps,type2,450,288));
//
//        List<Bitmap>  rubbishBitmapsType2 = new ArrayList<>();
//        //Bitmap rubbishBitmap = getBitmap("enemy/rubbish/foam_lunch_box.png");
//        //Bitmap rubbishBitmap = getBitmap("enemy/rubbish/harmful/harmful_2.png");
//        //rubbishBitmapsType2.add(rubbishBitmap);
//        rubbishBitmapsType2.add(getBitmap("enemy/rubbish/harmful/harmful_2.png"));
//
//        VRubbish vRubbish = new VRubbish(rubbishBitmapsType2, type2, 500, 288);
//        vRubbish.setContext(context);
//        vRubbish.setTypeName("harmful");
//        //rubbishes.add(new VRubbish( rubbishBitmapsType2,type2,500,288));
//        rubbishes.add(vRubbish);
//
//    }


    /**
     * 处理移动与跳跃的逻辑
     */
    private void marioMove() {

        //1 死亡前先起跳
        if (vTrashBin.isDead()) {
            vTrashBin.move(0, vTrashBin.mSpeedY++);

        } else {
            if (vTrashBin.isRunning()) {
                //左移
                if (vTrashBin.isMirror()) {
                    vTrashBin.move(-4, 0);
                    //未到达屏幕中点
                } else if (vTrashBin.getX() < 400) {
                    vTrashBin.move(4, 0);
                    //越过屏幕中点 地图移动
                } else if (tiledLayer_peng01.getX() > 800 - tiledLayer_peng01.getCols()
                        * tiledLayer_peng01.getWidth()) {
                    tiledLayer_peng01.move(-4, 0);
                    tiledLayer_cover.move(-4, 0);
                    //到达地图边界 人物移动
                    spritesMove(chestunts, -4);
                    spritesMove(cannons, -4);
                    spritesMove(turtles, -4);
                    spritesMove(bricks, -4);
                    spritesMove(commonBricks, -4);
                    spritesMove(items, -4);
                    spritesMove(pipes, -4);
                    spritesMove(rubbishes, -4);


                } else {
                    vTrashBin.move(4, 0);
                }
            }
            if (vTrashBin.isJumping()) {
                vTrashBin.move(0, vTrashBin.mSpeedY++);
            }
        }
        if (vTrashBin.getY() > 400) {
            //2 再次落下才表示游戏结束
            if (!vTrashBin.isDead()) {
                vTrashBin.setDead(true);
                vTrashBin.setSpeedY(-16);
            } else {
                lifeNumber--;
                gameState = LIFTCOUNTER;
            }
        }


    }

    //controller button
    CButton buttonB;

    //List<Bitmap> rubbishBitmaps;
    List<Sprite>  rubbishes;

    List<Bitmap> initRubbishBitmaps(){
        //这是一个垃圾的不同的帧数时候的图片
        List<Bitmap>  rubbishBitmaps = new ArrayList<>();
        //餐盒子
     String   typeName="dry";
        //Bitmap rubbishBitmap = getBitmap("enemy/rubbish/foam_lunch_box.png");
        String  fileName= String.format("enemy/rubbish/%s/%s_0.png",typeName,typeName );
        //Bitmap rubbishBitmap = getBitmap("enemy/rubbish/typeName/foam_lunch_box.png");
        Bitmap rubbishBitmap = getBitmap(fileName);
        rubbishBitmaps.add(rubbishBitmap);
        rubbishBitmaps.add(getTypeBitmap("harmful"));
        rubbishBitmaps.add(getTypeBitmap("recycle"));
        rubbishBitmaps.add(getTypeBitmap("wet"));
        return rubbishBitmaps;
    }

    Bitmap getTypeBitmap( String   typeName){
        String  fileName= String.format("enemy/rubbish/%s/%s_0.png",typeName,typeName );
        //Bitmap rubbishBitmap = getBitmap("enemy/rubbish/typeName/foam_lunch_box.png");
        Bitmap rubbishBitmap = getBitmap(fileName);
        return rubbishBitmap;
    }
    private void init() {
        isTransferReady = false;
        isTransferDone = true;
        isInited = false;
        threadRunning = false;
        isMaioDieSoundPlayed = false;
        time = 300;
        isEnemyShown1 = false;
        isEnemyShown2 = false;
        isEnemyShown3 = false;
        isEnemyShown4 = false;
        isEnemyShown5 = false;

        mPaint = new Paint();
        mPaint.setTextSize(20);//设置字号
        mPaint.setColor(Color.WHITE);//画笔颜色
        mPaint.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/Bit8.ttf"));//加载字体文件
        score = sp.getInt("hiscore", 0);//设置画笔字体
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //当显示过开场图片并且时间不为负数且线程停止标志位不为真时
                while (threadRunning && time > 0) {
                    SystemClock.sleep(1000);
                    isTransferReady = false;
                    time--;
                    if (time == 100) {
                        mySoundPool.play(mySoundPool.getHurryUpSound());

                    }
                }
            }
        });

//        aBitmap 的面积变大
        //加载图片资源
//        aBitmap = getBitmap("button/a.png");
//        bBitmap = getBitmap("button/b.png");
//        CButton buttonB=new CButton(new Rect(700,300,));
        buttonA = new CButton(650,350,"button/a.png",context,scaleX,scaleY);
         buttonB=new CButton(700,300,"button/b.png",context,scaleX,scaleY);
//        lockCanvas.drawBitmap(bBitmap, 740, 360, null);

        leftBitmap = getBitmap("button/left.png");
        rightBitmap = getBitmap("button/right.png");
        downBitmap = getBitmap("button/down.png");
        gameoverbitmap = getBitmap("logo/gameover.png");
        marioBitmap = getBitmap("mario/small/mario_01.png");
        logoBitmap = getBitmap("logo/logo.jpg");
        finishBitmap = getBitmap("logo/finish.jpg");
        Bitmap mushroomBitmap = getBitmap("item/mushroom.png");
        coinBitmap = getBitmap("item/coin/coin_00.png");
        Bitmap cannonBitmap = getBitmap("enemy/cannon.png");
        enemyBulletBitmap = getBitmap("enemy/enemy_bullet.png");
        Bitmap pipeBitmap = getBitmap("brick/pipe.png");

        cannons = new ArrayList<>();
        turtles = new ArrayList<>();
        List<Bitmap> marioSmallBitmaps = new ArrayList<>();
        ArrayList<Bitmap> marioFireInvBitmaps = new ArrayList<>();
        ArrayList<Bitmap> marioSmallInvBitmaps = new ArrayList<>();
        ArrayList<Bitmap> marioBigInvincibleBitmaps = new ArrayList<>();
        commonBricks = new ArrayList<>();
        List<Bitmap> commonbrickbitmaps = new ArrayList<>();
        bricks = new ArrayList<>();
        items = new ArrayList<>();
        List<Bitmap> marioBigBitmaps = new ArrayList<>();
        List<Bitmap> marioFireBitmaps = new ArrayList<>();
        List<Bitmap> flowerBitmaps = new ArrayList<>();
        List<Bitmap> brokenBitmaps = new ArrayList<>();
        List<Bitmap> pipeBitmaps = new ArrayList<>();


        rubbishes=new ArrayList<>();


        fireBitmaps = new ArrayList<>();
        cannonBitmaps = new ArrayList<>();
        pipes = new ArrayList<>();
        //设置数组型图片资源
        for (int i = 0; i < 7; i++) {
            marioBigBitmaps.add(getBitmap("mario/big/mario_0" + i + ".png"));
            marioSmallBitmaps.add(getBitmap("mario/small/mario_0" + i + ".png"));
            marioFireBitmaps.add(getBitmap("mario/fire/mario_0" + i + ".png"));
            marioFireInvBitmaps.add(getBitmap("mario/fire/mario_inv_0" + i + ".png"));
            marioSmallInvBitmaps.add(getBitmap("mario/small/mario_inv_0" + i + ".png"));
            marioBigInvincibleBitmaps.add(getBitmap("mario/big/mario_inv_0" + i + ".png"));
        }

        pipeBitmaps.add(pipeBitmap);

        for (int i = 0; i < 10; i++) {
            brokenBitmaps.add(getBitmap(String.format(Locale.CHINA,
                    "brick/broken/broken_%02d.png", i)));
        }

        chestuntBitmaps = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            chestuntBitmaps.add(getBitmap("enemy/chestunt/chestunt_0" + i + ".png"));
        }
        chestunts = new ArrayList<>();

        List<Bitmap> coinBitmaps = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            coinBitmaps.add(getBitmap(String.format(Locale.CHINA,
                    "item/coin/coin_%02d.png", i)));
        }
        for (int i = 0; i < 4; i++) {
            flowerBitmaps.add(getBitmap("item/flower/flower_0" + i + ".png"));
            fireBitmaps.add(getBitmap("item/fire/fire_0" + i + ".png"));
        }
        cannonBitmaps.add(cannonBitmap);
        List<Bitmap> brickBitmaps = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            brickBitmaps.add(getBitmap(String.format(Locale.CHINA, "brick/brick_%02d.png", i)));
        }
        turtleBitmaps = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            turtleBitmaps.add(getBitmap(String.format(Locale.CHINA, "enemy/turtle/turtle_%02d" +
                    ".png", i)));
        }

        List<Bitmap> starBitmaps = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            starBitmaps.add(getBitmap(String.format(Locale.CHINA, "item/star/item_star_%02d.png",
                    i)));
        }

        int[][] pipePositions = {
                {37, 7}, {62, 7}
        };

        for (int i = 0; i < 2; i++) {
            Pipe pipe = new Pipe(80, 80, pipeBitmaps);
            pipe.setVisiable(true);
            if (i == 0) {
                pipe.setTransfer(true);
            }
            pipe.setPosition(pipePositions[i][0] * 40, pipePositions[i][1] * 40);
            pipes.add(pipe);
        }

        //存放问号砖块行列坐标
        int[][] itemBrickPositions = {
                {3, 3}, {3, 6}, {56, 6}, {12, 5}
        };
        //存放金币坐标
        int[][] coinsPostions = {
                {10, 0}, {10, 1}, {10, 2}, {10, 3}, {10, 4}, {11, 2}, {12, 0}, {12, 1}, {12, 2}, {12, 3}, {12, 4},
                {14, 0}, {14, 1}, {14, 2}, {14, 3}, {14, 4}, {15, 0}, {16, 0}, {15, 2}, {16, 2}, {15, 4}, {16, 4},
                {18, 0}, {18, 1}, {18, 2}, {18, 3}, {18, 4}, {19, 4}, {20, 4},
                {22, 0}, {22, 1}, {22, 2}, {22, 3}, {22, 4}, {23, 4}, {24, 4},
                {26, 1}, {26, 2}, {26, 3}, {28, 1}, {28, 2}, {28, 3}, {27, 0}, {27, 4},
                {36, 5}, {37, 5}, {38, 5}, {39, 5}, {40, 5}, {41, 5}, {42, 5}, {43, 5}

        };
        //创建金币
        for (int[] coinposition : coinsPostions) {
            Coin coin = new Coin(40, 40, coinBitmaps);
            coin.setVisiable(true);
            coin.setPosition(coinposition[0] * 40, coinposition[1] * 40);
            items.add(coin);
        }
        //存放普通砖块坐标
        int[][] commonBrickPostions = {
                {2, 6}, {4, 6}, {29, 7}, {30, 7}, {32, 6}, {34, 5}, {36, 3}, {43, 3}, {36, 4},
                {37, 4}, {38, 4}, {39, 4}, {40, 4}, {41, 4}, {42, 4}, {43, 4}, {11, 5}, {13, 5},
                {14, 5}, {21, 6}, {22, 6}, {50, 6}, {51, 6}, {52, 6}, {53, 6}, {54, 6}

        };


        //创建砖块
        Brick brick = new Brick(40, 40, brickBitmaps);
        brick.setPosition(40 * itemBrickPositions[0][0], 40 * itemBrickPositions[0][1]);
        brick.createItem(true, flowerBitmaps, ItemType.Flower);
        items.add(brick.getItemSprite());
        brick.setVisiable(true);
        bricks.add(brick);
        Brick brick2 = new Brick(40, 40, brickBitmaps);
        brick2.setPosition(40 * itemBrickPositions[1][0], 40 * itemBrickPositions[1][1]);

        brick2.createItem(true, mushroomBitmap, ItemType.Mushroom);
        items.add(brick2.getItemSprite());
        brick2.setVisiable(true);
        bricks.add(brick2);

        Brick brick3 = new Brick(40, 40, brickBitmaps);
        brick3.setPosition(40 * itemBrickPositions[2][0], 40 * itemBrickPositions[2][1]);
        brick3.createItem(true, starBitmaps, ItemType.Star);
        items.add(brick3.getItemSprite());
        brick3.setVisiable(true);
        bricks.add(brick3);

        Brick brick4 = new Brick(40, 40, brickBitmaps);
        brick4.setPosition(40 * itemBrickPositions[3][0], 40 * itemBrickPositions[3][1]);
        brick4.createItem(true, flowerBitmaps, ItemType.Flower);
        items.add(brick4.getItemSprite());
        brick4.setVisiable(true);
        bricks.add(brick4);


        commonbrickbitmaps.add(getBitmap("brick/commonBrick.png"));

        for (int[] position : commonBrickPostions) {
            CommonBrick commonBrick = new CommonBrick(40, 40, commonbrickbitmaps);
            commonBrick.setVisiable(true);
            Broken broken = new Broken(187, 187, brokenBitmaps);
            commonBrick.setBroken(broken);
            commonBrick.setCanBroken(false);
            commonBrick.setPosition(40 * position[0], 40 * position[1]);
            commonBricks.add(commonBrick);
        }

        rectF = new RectF();
        //初始化玛丽
        //给的图片就是 32 那么 有必要传入吗，虽然说他有大小的
        //但是一开始的图片就是 32的，但是怎么知道要哪张

        List<Bitmap>bitmapsVTrashBin=new ArrayList<>();
        bitmapsVTrashBin.add(BitmapUtil.getBitmap(context,R.mipmap.recycle));
        //vTrashBin = new VTrashBin(bitmapsVTrashBin);

        RubbishType rubbishType = new RubbishType();
        rubbishType.setId(1L);
        //RubbishType build = RubbishType.builder().id(1L).build();
        vTrashBin = new VTrashBin(bitmapsVTrashBin,rubbishType);
        //this.vTrashBin = new Mario(32, 32, marioSmallBitmaps);
        //this.vTrashBin.setVisiable(true);
        //this.vTrashBin.setPosition(0, 330);
        List<List<Bitmap>> bitmapsList = new ArrayList<>();
        bitmapsList.add(marioSmallBitmaps);
        bitmapsList.add(marioBigBitmaps);
        bitmapsList.add(marioFireBitmaps);
        bitmapsList.add(marioSmallInvBitmaps);
        bitmapsList.add(marioBigInvincibleBitmaps);
        bitmapsList.add(marioFireInvBitmaps);
        //this.vTrashBin.setBitmapsList(bitmapsList);
        vTrashBin.initTrashBinBitmapList(context);


        //this.vTrashBin = new VTrashBin(32, 32, marioSmallBitmaps);
        this.vTrashBin.setVisiable(true);
        this.vTrashBin.setPosition(0, 330);

        //vTrashBin.is
        //初始化地图
        int map_peng01[][] = getMapArray();
        int map_cover[][] = getMapCoverArray();
        Bitmap mapBitmap = getBitmap("map/map1.png");
//        分块图层  TiledLayer
        tiledLayer_peng01 = new TiledLayer(mapBitmap, 100, 12, 40, 40);
        tiledLayer_cover = new TiledLayer(mapBitmap, 100, 12, 40, 40);
        tiledLayer_peng01.setTiledCell(map_peng01);
        tiledLayer_cover.setTiledCell(map_cover);
        if (isFirstRun) {
            gameState = LOGO;
            isFirstRun = false;
        } else {
            gameState = GAMING;
        }
        isInited = true;


    }

    private void myDraw() {
        try {
            lockCanvas = holder.lockCanvas();
            lockCanvas.save();
            lockCanvas.scale(scaleX, scaleY);
            lockCanvas.drawColor(Color.parseColor("#3786ef"));
            switch (gameState) {
                case LOGO: {
                    lockCanvas.drawBitmap(logoBitmap, 0, 0, null);
                }
                break;
                case GAMEOVER: {
                    lockCanvas.drawColor(Color.BLACK);
                    lockCanvas.drawBitmap(gameoverbitmap,
                            400 - gameoverbitmap.getWidth() / 2,
                            250 - gameoverbitmap.getHeight() / 2, null);
                }
                break;
                case FINISH: {
                    lockCanvas.drawBitmap(finishBitmap, 0, 0, null);
                }
                break;
                case LIFTCOUNTER: {
                    lockCanvas.drawColor(Color.BLACK);
                    lockCanvas.drawBitmap(marioBitmap, 400 - marioBitmap.getWidth(),
                            250 - marioBitmap.getHeight(), null);
                    lockCanvas.drawText(String.format(Locale.CHINA, "X %02d", lifeNumber),
                            440 - marioBitmap.getWidth(),
                            285 - marioBitmap.getHeight(), mPaint);
                }
                break;
                case TRANSFER:
                case GAMING: {
                    tiledLayer_cover.draw(lockCanvas);
                    tiledLayer_peng01.draw(lockCanvas);
                    spritesDraw(bricks, lockCanvas);
                    spritesDraw(commonBricks, lockCanvas);
                    spritesDraw(chestunts, lockCanvas);
                    spritesDraw(cannons, lockCanvas);
                    spritesDraw(turtles, lockCanvas);
                    spritesDraw(items, lockCanvas);
//                    Log.i(TAG, "myDraw:  rubbishes.size()  "+rubbishes.size());
                    spritesDraw(rubbishes, lockCanvas);
                    vTrashBin.draw(lockCanvas);
                    spritesDraw(pipes, lockCanvas);

//                    lockCanvas.drawBitmap(aBitmap, 680, 420, null);
//                    lockCanvas.drawBitmap(bBitmap, 740, 360, null);
                    buttonB.drawBitmap(lockCanvas);
                    buttonA.drawBitmap(lockCanvas);

//                    lockCanvas.drawBitmap(leftBitmap, 0, 360, null);
//                    lockCanvas.drawBitmap(rightBitmap, 120, 360, null);
//                    lockCanvas.drawBitmap(downBitmap, 60, 420, null);

//                    lockCanvas.drawBitmap(leftBitmap, 0, 300, null);
//                    lockCanvas.drawBitmap(rightBitmap, 120, 300, null);
//                    lockCanvas.drawBitmap(downBitmap, 60, 360, null);


//                    lockCanvas.drawBitmap(leftBitmap, 0, leftTop, null);
//                    lockCanvas.drawBitmap(rightBitmap, 120, leftTop, null);
//                    lockCanvas.drawBitmap(downBitmap, 60, 360, null);

                    lockCanvas.drawBitmap(leftBitmap, 0+controllerHorizontalOffset, leftTop, null);
                    lockCanvas.drawBitmap(rightBitmap, 120+controllerHorizontalOffset, leftTop, null);
                    lockCanvas.drawBitmap(downBitmap, 60+controllerHorizontalOffset, 360, null);
                    //绘制单次得分
                    if (scoreString != null) {
                        lockCanvas.drawText(scoreString, vTrashBin.getX() + 25, vTrashBin.getY() - 20 - 3 * delay, mPaint);
                        if (delay++ == 6) {
                            scoreString = null;
                            delay = 0;
                        }
                    }
                    if (vTrashBin.getBullets() != null) {
                        int useableBulletCount = vTrashBin.getUseableBulletCount();
                        lockCanvas.drawText("Bullets Ready:" + useableBulletCount, 500, 30, mPaint);
                        if (vTrashBin.isInvincible()) {
                            lockCanvas.drawText("Invincible Time:" + vTrashBin.getInvincibleTime(), 500,
                                    60, mPaint);
                            if (isTransferReady) {
                                lockCanvas.drawText("Transfer Ready", 500, 90, mPaint);
                            }

                        } else {
                            if (vTrashBin.isZeroDamage()) {
                                lockCanvas.drawText("ZeroDamage Time:" + vTrashBin.getZeroDamageTime(), 500,
                                        60, mPaint);
                                if (isTransferReady) {
                                    lockCanvas.drawText("Transfer Ready", 500, 90, mPaint);
                                }
                            } else {
                                if (isTransferReady) {
                                    lockCanvas.drawText("Transfer Ready", 500, 60, mPaint);
                                }
                            }

                        }
                    } else {
                        if (vTrashBin.isInvincible()) {
                            lockCanvas.drawText("Invincible Time:" + vTrashBin.getInvincibleTime(), 500,
                                    30, mPaint);
                            if (isTransferReady) {
                                lockCanvas.drawText("Transfer Ready", 500, 60, mPaint);
                            }

                        } else {
                            if (vTrashBin.isZeroDamage()) {
                                lockCanvas.drawText("ZeroDamage Time:" + vTrashBin.getZeroDamageTime(), 500,
                                        30, mPaint);
                                if (isTransferReady) {
                                    lockCanvas.drawText("Transfer Ready", 500, 60, mPaint);
                                }
                            } else {
                                if (isTransferReady) {
                                    lockCanvas.drawText("Transfer Ready", 500, 30, mPaint);
                                }
                            }

                        }
                    }
                    //绘制总分数
                    lockCanvas.drawText(String.format(Locale.CHINA, "SCORE:%06d", score), 30, 30, mPaint);
                    if (vTrashBin.getBullets() != null) {
                        for (int i = 0; i < vTrashBin.getBullets().size(); i++) {
                            vTrashBin.getBullets().get(i).draw(lockCanvas);
                        }
                    }
                    //绘制金币数
                    lockCanvas.drawBitmap(coinBitmap, 200, 0, null);
                    lockCanvas.drawText(String.format(Locale.CHINA, "x %02d", coinNumber), 240,
                            30, mPaint);
                    //绘制剩余时间
                    lockCanvas.drawText(String.format(Locale.CHINA, "TIME:%03d", time), 700, 30, mPaint);
                }
                break;
            }
            lockCanvas.restore();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (lockCanvas != null)
                holder.unlockCanvasAndPost(lockCanvas);
        }
    }

    /**
     * 玛丽奥与砖块碰撞
     */
    private void myCollision() {
        //玛丽与普通砖块
        sptiteCollisionSprite(vTrashBin, commonBricks);
        //玛丽与问号砖块
        sptiteCollisionSprite(vTrashBin, bricks);

        sptiteCollisionSprite(vTrashBin, pipes);

        //玛丽与大炮
        sptiteCollisionSprite(vTrashBin, cannons);
        //道具与砖块碰撞
        sptitesCollisionSprites(items, bricks);
        //道具与普通砖块碰撞
        sptitesCollisionSprites(items, commonBricks);
        sptitesCollisionSprites(items, pipes);

        //大炮与砖块碰撞
        sptitesCollisionSprites(cannons, bricks);
        sptitesCollisionSprites(cannons, commonBricks);
        sptitesCollisionSprites(cannons, pipes);
        //乌龟与砖块碰撞
        sptitesCollisionSprites(turtles, bricks);
        //乌龟与问号砖块
        sptitesCollisionSprites(turtles, commonBricks);
        sptitesCollisionSprites(turtles, pipes);
        //敌人与砖块
        sptitesCollisionSprites(chestunts, bricks);
        sptitesCollisionSprites(chestunts, commonBricks);
        sptitesCollisionSprites(chestunts, pipes);

        //敌人与砖块 垃圾
        sptitesCollisionSprites(rubbishes, bricks);
        sptitesCollisionSprites(rubbishes, commonBricks);
        sptitesCollisionSprites(rubbishes, pipes);

        //玛丽子弹与砖块
        sptitesCollisionSprites(vTrashBin.getBullets(), bricks);
        //龟壳与敌人碰撞
        sptitesCollisionSprites(vTrashBin.getBullets(), commonBricks);
        sptitesCollisionSprites(vTrashBin.getBullets(), pipes);
        turtleShellCollisionWith(turtles);
        turtleShellCollisionWith(chestunts);
        turtleShellCollisionWith(rubbishes);

        if (!vTrashBin.isDead()) {
            //玛丽与板栗碰撞
            marioCollisionWith(chestunts);
            marioCollisionWith(rubbishes);
            marioCollisionWith(turtles);
            marioCollisionWith(items);
            marioCollisionWith(pipes);
            //玛丽与大炮子弹碰撞
            if (cannons != null) {
                for (int w = 0; w < cannons.size(); w++) {
                    List<Sprite> bullets = ((Cannon) cannons.get(w)).getBullets();
                    marioCollisionWith(bullets);
                }
            }
        }
    }


    /**
     * 精灵与地图碰撞
     * 玛丽
     * 敌人
     * 砖块道具
     */
    private void collisionWithMap() {
        //玛丽碰撞
        if (!vTrashBin.isDead()) {
            spriteCollisionMap(vTrashBin);
            spritesCollisionMap(vTrashBin.getBullets());
        }
        //敌人碰撞 和地图
        spritesCollisionMap(chestunts);
        spritesCollisionMap(rubbishes);
        spritesCollisionMap(turtles);
        spritesCollisionMap(cannons);
        //道具与地图碰撞
        spritesCollisionMap(items);
    }

    //碰撞
    //精灵与精灵
    //玛丽与敌人
    //精灵与地图


    private void turtleShellCollisionWith(List<Sprite> sprites) {
        if (sprites != null && turtles != null) {
            for (int i = 0; i < turtles.size(); i++) {
                Sprite shell = turtles.get(i);
                if (shell.isVisiable() && shell.isDead()) {
                    turtleShellCollisionWith(shell, sprites);
                }
            }
        }
    }

    private void turtleShellCollisionWith(Sprite shell, List<Sprite> sprites) {
        if (sprites != null) {
            for (int i = 0; i < sprites.size(); i++) {
                turtleShellCollisionWith(shell, sprites.get(i));
            }

        }
    }

    /**
     * 龟壳碰撞
     *
     * @param shell  龟壳
     * @param sprite 目标精灵
     */
    private void turtleShellCollisionWith(Sprite shell, Sprite sprite) {
        if (shell.isVisiable() && shell.isRunning() && !sprite.isDead()) {
            if (shell.collisionWith(sprite)) {
                if (sprite instanceof Turtle) {
                    if (!((Turtle) sprite).isZeroDamage()) {
                        if (((Turtle) sprite).isCanFly()) {
                            ((Turtle) sprite).setCanFly(false);
                            ((Turtle) sprite).setZeroDamage(true);
                            updateScore(100);
                        } else {
                            mySoundPool.play(mySoundPool.getHitEnemySound());
                            ((Turtle) sprite).setOverturn(true);
                            if (shell.getX() > sprite.getX()) {
                                sprite.setMirror(false);
                            } else {
                                sprite.setMirror(true);
                            }
                            sprite.setSpeedY(-10);
                            sprite.setDead(true);
                            updateScore(100);
                        }
                    }
                } else {
                    mySoundPool.play(mySoundPool.getHitEnemySound());
                    ((Enemy) sprite).setOverturn(true);
                    if (shell.getX() > sprite.getX()) {
                        sprite.setMirror(false);
                    } else {
                        sprite.setMirror(true);
                    }
                    sprite.setSpeedY(-10);
                    sprite.setDead(true);
                    updateScore(100);
                }


            }
        }
    }

    /**
     * 玛丽与一组精灵
     *
     * @param sprites 精灵数组
     */
    private void marioCollisionWith(List<Sprite> sprites) {
        if (sprites != null) {
            for (int i = 0; i < sprites.size(); i++) {
                Sprite sprite = sprites.get(i);
                marioCollisionWith(sprite);
                if (!(sprite instanceof ItemSprite)) {
                    marioBulletCollisionWithEnemy(sprite);
                }
            }
        }
    }


    /**
     * 玛丽与精灵碰撞
     *
     * @param sprite 单个精灵
     */
    private void marioCollisionWith(Sprite sprite) {
        if (vTrashBin.collisionWith(sprite)) {
            if (sprite instanceof Pipe) {
                if (((Pipe) sprite).isTransfer()) {
                    isTransferReady = true;
                }

            } else
                //与道具碰撞
                if (sprite instanceof ItemSprite) {
                    //与大炮子弹
                    if (sprite instanceof EnemyBullet) {
                        if (vTrashBin.isInvincible()) {
                            updateScore(100);
                            sprite.setVisiable(false);
                        } else if (vTrashBin.isJumping() && vTrashBin.siteCollisionWith(sprite, 下) &&
                                !sprite.isDead()) {
                            mySoundPool.play(mySoundPool.getHitEnemySound());
                            //杀死敌人时获得100积分
                            updateScore(100);
                            vTrashBin.setSpeedY(-10);
                            sprite.setVisiable(false);
                        } else {
                            if (!vTrashBin.isZeroDamage() && sprite.isVisiable()) {
                                int status = vTrashBin.getStatus();
                                if (status != 0) {
                                    vTrashBin.shapeShift(status - 1);
                                    mySoundPool.play(mySoundPool.getHurtSound());
                                    vTrashBin.setZeroDamage(true);
                                } else {
                                    vTrashBin.setDead(true);
                                    vTrashBin.setSpeedY(-16);
                                }
                            }
                        }
                    } else {
                        int sound = mySoundPool.getItemSound();
                        int status = vTrashBin.getStatus();
                        if (sprite instanceof Mushroom) {
                            mySoundPool.play(mySoundPool.getItemSound());
                            if (status == 0) {
                                vTrashBin.shapeShift(1);
                            }
                        }
                        if (sprite instanceof Flower) {
                            //花
                            if (status != 2) {
                                //非第三状态都执行
                                vTrashBin.shapeShift(2);
                                //设置弹夹
                                List<Sprite> bullets = new ArrayList<>();
                                for (int j = 0; j < 5; j++) {
                                    Bullet bullet = new Bullet(16, 16, fireBitmaps);
                                    if (vTrashBin.isMirror()) {
                                        bullet.setMirror(false);
                                    } else {
                                        bullet.setMirror(true);
                                    }
                                    bullets.add(bullet);
                                }
                                vTrashBin.setBullets(bullets);
                            }
                        }
                        if (sprite instanceof Star) {
                            //玛丽吃到无敌星后变成无敌状态
                            vTrashBin.setInvincible(true);
                        }
                        if (sprite instanceof Coin) {
                            coinNumber++;
                            sound = mySoundPool.getCoinSound();
                        }
                        mySoundPool.play(sound);
                        sprite.setVisiable(false);
                        updateScore(100);
                    }


                } else {
                    //与敌人碰撞
                    if (!sprite.isDead()) {
                        if (vTrashBin.isInvincible()) {
//                            是不可战胜的
                            //如果乌龟带翅膀则切换状态
                            if (sprite instanceof Turtle) {
                                //乌龟在带翅膀状态被击中有免伤时间
                                if (!((Turtle) sprite).isZeroDamage()) {
                                    if (((Turtle) sprite).isCanFly()) {
                                        ((Turtle) sprite).setCanFly(false);
                                        sprite.setSpeedY(0);
                                        ((Turtle) sprite).setZeroDamage(true);

                                        updateScore(100);
                                        vTrashBin.setSpeedY(-10);
                                        vTrashBin.setJumping(true);
                                    } else {
                                        ((Enemy) sprite).setOverturn(true);
                                        sprite.setDead(true);
                                        mySoundPool.play(mySoundPool.getHitEnemySound());
                                        updateScore(100);
                                        vTrashBin.setSpeedY(-10);
                                        vTrashBin.setJumping(true);
                                    }
                                }
                            } else {
                                ((Enemy) sprite).setOverturn(true);
                                sprite.setSpeedY(-10);
                                sprite.setDead(true);
                                mySoundPool.play(mySoundPool.getHitEnemySound());
                                //杀死敌人时获得100积分
                                updateScore(100);
                            }
                        } else if (vTrashBin.isJumping()) {
                            //不是不死的
                            if (sprite instanceof Turtle) {
                                //乌龟在带翅膀状态被击中有免伤时间
                                if (!((Turtle) sprite).isZeroDamage()) {
                                    if (((Turtle) sprite).isCanFly()) {
                                        ((Turtle) sprite).setCanFly(false);
                                        sprite.setSpeedY(-3);
                                        ((Turtle) sprite).setZeroDamage(true);
                                        updateScore(100);
                                        vTrashBin.setSpeedY(-10);
                                    } else {
                                        //不能飞
                                        sprite.setDead(true);
                                        mySoundPool.play(mySoundPool.getHitEnemySound());
                                        updateScore(100);
                                    }
                                }
                            } else {
                                checkRubType(sprite);
                                //if(sprite instanceof VRubbish){
                                //    checkRubType(sprite);
                                //}

                                //他不是一个垃圾桶的类
                                //不是乌龟 敌人直接死
                                //垃圾需要查看是不是 适合的类型
                                //sprite.setDead(true);
                                //mySoundPool.play(mySoundPool.getHitEnemySound());
                                ////杀死敌人时获得100积分
                                //updateScore(100);
                                //vTrashBin.setSpeedY(-10);
                            }
                        } else {
                            checkGetHit();
                            //他没有在跳 mario
                            //那就是撞到了 被人撞了 我自己受伤
                            //if (!vTrashBin.isZeroDamage()) {
                            //    //标志是否处于免伤状态
                            //    //不是免伤
                            //    int status = vTrashBin.getStatus();
                            //    if (status != 0) {
                            //        //退化
                            //        vTrashBin.shapeShift(status - 1);
                            //        mySoundPool.play(mySoundPool.getHurtSound());
                            //        vTrashBin.setZeroDamage(true);
                            //        //一段时间的免伤
                            //    } else {
                            //        //没有退化的可能 死掉了
                            //        vTrashBin.setDead(true);
                            //        vTrashBin.setSpeedY(-16);
                            //    }
                            //}
                        }
                        //乌龟壳
                    } else if (sprite instanceof Turtle) {
                        if (!sprite.isRunning()) {
                            if (vTrashBin.getX() < sprite.getX()) {
                                sprite.setMirror(true);
                                sprite.setRunning(true);
                            } else {
                                sprite.setMirror(false);
                                sprite.setRunning(true);
                            }
                            vTrashBin.setJumping(true);
                            vTrashBin.setSpeedY(-16);
                        } else {
                            if (!vTrashBin.isZeroDamage() && !vTrashBin.isJumping()) {
                                sprite.setVisiable(false);
                                int status = vTrashBin.getStatus();
                                if (status != 0) {
                                    vTrashBin.shapeShift(status - 1);
                                    mySoundPool.play(mySoundPool.getHurtSound());
                                    vTrashBin.setZeroDamage(true);
                                } else {
                                    vTrashBin.setDead(true);
                                    vTrashBin.setSpeedY(-16);
                                }
                            }
                        }
                    }
                }
        }
    }

    void checkGetHit(){
        //他没有在跳 mario
        //那就是撞到了 被人撞了 我自己受伤
        if (!vTrashBin.isZeroDamage()) {
            //标志是否处于免伤状态
            //不是免伤
            int status = vTrashBin.getStatus();
            if (status != 0) {
                //退化
                vTrashBin.shapeShift(status - 1);
                mySoundPool.play(mySoundPool.getHurtSound());
                vTrashBin.setZeroDamage(true);
                //一段时间的免伤
            } else {
                //没有退化的可能 死掉了
                vTrashBin.setDead(true);
                vTrashBin.setSpeedY(-16);
            }
        }
    }

    void deadTheEnemy(Sprite sprite){
        sprite.setDead(true);
        mySoundPool.play(mySoundPool.getHitEnemySound());
        //杀死敌人时获得100积分
        updateScore(100);
        vTrashBin.setSpeedY(-10);
    }

    void checkRubType(Sprite sprite){
        if(sprite instanceof VRubbish){
            VRubbish vRubbish = (VRubbish) sprite;
            Rubbish rubbish = vRubbish.getRubbish();
            RubbishType rubbishType = vTrashBin.getRubbishType();

            if (rubbish.getTypeId().equals( rubbishType.getId())) {
                //可以回收
                //不是乌龟 敌人直接死
                //垃圾需要查看是不是 适合的类型
                //sprite.setDead(true);
                //mySoundPool.play(mySoundPool.getHitEnemySound());
                ////杀死敌人时获得100积分
                //updateScore(100);
                //vTrashBin.setSpeedY(-10);
                deadTheEnemy(sprite);
                return;
            }
            //类型不同  主角死亡
            checkGetHit();
            return;
        }

        deadTheEnemy(sprite);

    }
    private void marioBulletCollisionWithEnemy(Sprite sprite) {
        List<Sprite> bullets = vTrashBin.getBullets();
        if (bullets != null) {
            for (int i = 0; i < bullets.size(); i++) {
                Sprite sprite1 = bullets.get(i);
                //玛丽子弹不能消灭敌人子弹
                if (sprite1.isVisiable() && sprite1.collisionWith(sprite) && !(sprite instanceof
                        EnemyBullet) && !sprite.isDead()) {
                    //乌龟在带翅膀状态被击中有免伤时间
                    if (sprite instanceof Turtle) {
                        if (!((Turtle) sprite).isZeroDamage()) {
                            if (((Turtle) sprite).isCanFly()) {
                                ((Turtle) sprite).setCanFly(false);
                                ((Turtle) sprite).setZeroDamage(true);
                                updateScore(100);
                            } else {
                                sprite.setDead(true);
                                ((Turtle) sprite).setOverturn(true);
                                if (sprite1.getX() > sprite.getX()) {
                                    sprite.setMirror(false);
                                } else {
                                    sprite.setMirror(true);
                                }
                                sprite.setSpeedY(-10);
                                mySoundPool.play(mySoundPool.getHitEnemySound());
                                updateScore(100);
                            }
                        }
                        sprite1.setVisiable(false);
                    } else {
                        mySoundPool.play(mySoundPool.getHitEnemySound());
                        sprite1.setVisiable(false);
//                        sprite.setVisiable(false);
//                        sprite.setDead(true);
                        ((Enemy) sprite).setOverturn(true);
                        if (sprite1.getX() > sprite.getX()) {
                            sprite.setMirror(false);
                        } else {
                            sprite.setMirror(true);
                        }
                        sprite.setSpeedY(-10);
                        sprite.setDead(true);
                        updateScore(100);
                    }
                }
            }
        }
    }

    /**
     * 精灵与地图（碰撞层）碰撞
     *
     * @param sprites 精灵数组
     */

    private void spritesCollisionMap(List<Sprite> sprites) {
        if (sprites != null) {
            for (int i = 0; i < sprites.size(); i++) {
                spriteCollisionMap(sprites.get(i));
            }
        }
    }

    /**
     * 精灵与地图（碰撞层）碰撞
     *
     * @param sprite 单个精灵
     */
    private void spriteCollisionMap(Sprite sprite) {
        if (sprite.isVisiable() && !sprite.isDead()) {
            //头顶碰撞
            if (sprite.siteCollisionWith(tiledLayer_peng01, 上左) ||
                    sprite.siteCollisionWith(tiledLayer_peng01, 上中) ||
                    sprite.siteCollisionWith(tiledLayer_peng01, 上右)) {
                if (sprite instanceof Bullet) {
                    sprite.setVisiable(false);
                } else {
//                    反方向，顶着头了 要掉下来
                    sprite.setSpeedY(Math.abs(sprite.getSpeedY()));
                }
            }
            //脚部碰撞
            if (sprite.siteCollisionWith(tiledLayer_peng01, 下左) ||
                    sprite.siteCollisionWith(tiledLayer_peng01, 下中) ||
                    sprite.siteCollisionWith(tiledLayer_peng01, 下右)) {
                //乌龟特殊处理
                if (sprite instanceof Turtle) {
                    if (((Turtle) sprite).isCanFly()) {
//                        乌龟飞起来
                        sprite.setJumping(true);
                        sprite.setSpeedY(-10);
                    } else {
                        sprite.setJumping(false);
                        int footY = sprite.getY() + sprite.getHeight();
                        int newY = footY / tiledLayer_peng01.getHeight()
                                * tiledLayer_peng01.getHeight()
                                - sprite.getHeight();
                        sprite.setPosition(sprite.getX(), newY);
                    }
                    //子弹特殊处理
                } else if (sprite instanceof Bullet || sprite instanceof Star) {
                    sprite.setSpeedY(-10);
                    sprite.setJumping(true);
                } else {
                    sprite.setJumping(false);
                    //坐标修正
                    //取得脚部坐标
                    int footY = sprite.getY() + sprite.getHeight();
                    //取整
                    int newY = footY / tiledLayer_peng01.getHeight()
                            * tiledLayer_peng01.getHeight()
                            - sprite.getHeight();
                    sprite.setPosition(sprite.getX(), newY);
                }
            }
            //落地判断
            else if (!sprite.isJumping()) {
                sprite.setJumping(true);
                sprite.setSpeedY(0);
            }
            //左边碰撞
            if (sprite.siteCollisionWith(tiledLayer_peng01, 左上) ||
                    sprite.siteCollisionWith(tiledLayer_peng01, 左中) ||
                    sprite.siteCollisionWith(tiledLayer_peng01, 左下)) {
//                Log.i(TAG, "spriteCollisionMap: 左边 碰撞");
//                怎么一直是左边有碰撞呢 不一定是他呀
//                if(sprite instanceof  Chestunt){
//
//                }
                if (sprite instanceof Bullet) {
                    sprite.setVisiable(false);
                } else {
//                    返回去
                    sprite.setMirror(true);
                    sprite.move(4, 0);
                }
            }
            //右边碰撞
            if (sprite.siteCollisionWith(tiledLayer_peng01, 右上) ||
                    sprite.siteCollisionWith(tiledLayer_peng01, 右中) ||
                    sprite.siteCollisionWith(tiledLayer_peng01, 右下)) {
                if (sprite instanceof Bullet) {
                    sprite.setVisiable(false);
                } else {
                    sprite.setMirror(false);
                    sprite.move(-4, 0);
                }
            }
            //乌龟壳
        } else if (sprite.isVisiable() && sprite.isDead() && sprite instanceof Turtle) {
            if (!((Turtle) sprite).isOverturn()) {
                //脚部碰撞
                if (sprite.siteCollisionWith(tiledLayer_peng01, 下左) ||
                        sprite.siteCollisionWith(tiledLayer_peng01, 下中) ||
                        sprite.siteCollisionWith(tiledLayer_peng01, 下右)) {
                    //乌龟特殊处理
                    sprite.setJumping(false);
                    //坐标修正
                    //取得脚部坐标
                    int footY = sprite.getY() + sprite.getHeight();
                    //取整
                    int newY = footY / tiledLayer_peng01.getHeight()
                            * tiledLayer_peng01.getHeight()
                            - sprite.getHeight();
                    sprite.setPosition(sprite.getX(), newY);
                }
            }
            //落地判断
            else if (!sprite.isJumping()) {
                sprite.setJumping(true);
                sprite.setSpeedY(0);
            }
            //左边碰撞
            if (sprite.siteCollisionWith(tiledLayer_peng01, 左上) ||
                    sprite.siteCollisionWith(tiledLayer_peng01, 左中) ||
                    sprite.siteCollisionWith(tiledLayer_peng01, 左下)) {
                sprite.setMirror(true);
                sprite.move(10, 0);

            }
            //右边碰撞
            if (sprite.siteCollisionWith(tiledLayer_peng01, 右上) ||
                    sprite.siteCollisionWith(tiledLayer_peng01, 右中) ||
                    sprite.siteCollisionWith(tiledLayer_peng01, 右下)) {
                sprite.setMirror(false);
                sprite.move(-10, 0);

            }

        }
    }


    /**
     * 精灵碰撞
     *
     * @param sprites0 主动碰撞的精灵数组
     * @param sprites1 被碰精灵数组
     */


    private void sptitesCollisionSprites(List<Sprite> sprites0, List<Sprite> sprites1) {
        if (sprites0 != null) {
            for (int i = 0; i < sprites0.size(); i++) {
                sptiteCollisionSprite(sprites0.get(i), sprites1);
            }
        }
    }


    /**
     * 精灵碰撞
     *
     * @param sprite0 主动碰撞的精灵
     * @param sprites 被碰精灵数组
     */

    private void sptiteCollisionSprite(Sprite sprite0, List<Sprite> sprites) {
        if (sprites != null) {
            for (int i = 0; i < sprites.size(); i++) {
                Sprite sprite1 = sprites.get(i);
                sptiteCollisionSprite(sprite0, sprite1);
            }
        }
    }

    /**
     * 精灵碰撞
     *
     * @param spriteActive 主动碰撞的精灵
     * @param spritePassive 被碰精灵
     */
    private void sptiteCollisionSprite(Sprite spriteActive, Sprite spritePassive) {
        //精灵可见且非死亡状态
        if (spriteActive.isVisiable() && !spriteActive.isDead() && spritePassive.isVisiable() && !spritePassive.isDead()) {
            //0底部碰撞1
            if (spriteActive.siteCollisionWith(spritePassive, 下)) {
                //乌龟特殊处理
                if (spriteActive instanceof Turtle) {
                    spriteActive.setJumping(true);
                    spriteActive.setSpeedY(-10);
                } else
                    //砖块上方有敌人 砖块被顶起
                    if (spriteActive instanceof Enemy && !(spriteActive instanceof Cannon)
                            && spritePassive instanceof
                            Brick && spritePassive.isJumping()) {
                        ((Enemy) spriteActive).setOverturn(true);
                        spriteActive.setSpeedY(-10);
                        spriteActive.setDead(true);
                        updateScore(100);
                    } else
                        //砖块上方有道具 砖块被顶起
                        if (spriteActive instanceof ItemSprite && spritePassive instanceof CommonBrick && spritePassive.isJumping()) {
                            spriteActive.setSpeedY(-10);
                            spriteActive.setJumping(true);

                        } else
                            //子弹与无敌星弹跳处理
                            if (spriteActive instanceof Bullet || spriteActive instanceof Star) {
                                spriteActive.setSpeedY(-10);
                                spriteActive.setJumping(true);
                            } else {
                                spriteActive.setJumping(false);
                                //坐标修正
                                //取得脚部坐标
                                int footY = spriteActive.getY() + spriteActive.getHeight();
                                //取整
                                int newY = footY / tiledLayer_peng01.getHeight()
                                        * tiledLayer_peng01.getHeight()
                                        - spriteActive.getHeight();
                                spriteActive.setPosition(spriteActive.getX(), newY);
                            }
            }
            if (spriteActive.siteCollisionWith(spritePassive, Site.上)) {
                //玛丽顶砖块
                if (spriteActive instanceof Mario && spriteActive.isJumping()) {
                    if (spritePassive instanceof CommonBrick) {
                        spriteActive.setSpeedY(Math.abs(spriteActive.getSpeedY()));
                        if (!spritePassive.isJumping()) {
                            if (vTrashBin.getStatus() != 0) {
                                ((CommonBrick) spritePassive).setCanBroken(true);
                                spritePassive.setSpeedY(-4);
                                spritePassive.setJumping(true);
                                mySoundPool.play(mySoundPool.getBrokenSound());
                            } else {
                                spritePassive.setSpeedY(-4);
                                spritePassive.setJumping(true);
                                mySoundPool.play(mySoundPool.getCannotbreakSound());
                            }
                        }
                    } else if (spritePassive instanceof Brick) {
                        spriteActive.setSpeedY(Math.abs(spriteActive.getSpeedY()));
                        if (((Brick) spritePassive).hasItem()) {
                            mySoundPool.play(mySoundPool.getHitbrickSound());
                            spritePassive.setSpeedY(-4);
                            spritePassive.setJumping(true);
                        }
                    }
                } else {
                    if (spriteActive instanceof Bullet) {
                        spriteActive.setVisiable(false);
                    } else {
                        spriteActive.setSpeedY(Math.abs(spriteActive.getSpeedY()));
                    }
                }

            }
            if (spriteActive.siteCollisionWith(spritePassive, 左)) {
                if (spriteActive instanceof Bullet) {
                    spriteActive.setVisiable(false);
                } else {
                    spriteActive.setMirror(true);
                    spriteActive.move(4, 0);
                }
            }
            if (spriteActive.siteCollisionWith(spritePassive, 右)) {
                if (spriteActive instanceof Bullet) {
                    spriteActive.setVisiable(false);
                } else {
                    spriteActive.setMirror(false);
                    spriteActive.move(-4, 0);
                }
            }
            //乌龟壳
        } else if (spriteActive.isVisiable() && spriteActive.isDead() && spritePassive.isVisiable() &&
                !spritePassive.isDead() && spriteActive instanceof Turtle && !(spritePassive instanceof Turtle)) {
            Log.e(TAG, "sptiteCollisionSprite: 龟壳碰撞");
            //0底部碰撞1
            if (spriteActive.siteCollisionWith(spritePassive, 下)) {
                spriteActive.setJumping(false);
                //坐标修正
                //取得脚部坐标
                int footY = spriteActive.getY() + spriteActive.getHeight();
                //取整
                int newY = footY / tiledLayer_peng01.getHeight()
                        * tiledLayer_peng01.getHeight()
                        - spriteActive.getHeight();
                spriteActive.setPosition(spriteActive.getX(), newY);
            }
            if (spriteActive.siteCollisionWith(spritePassive, 左)) {
                spriteActive.setMirror(true);
                spriteActive.move(10, 0);
            }
            if (spriteActive.siteCollisionWith(spritePassive, 右)) {

                spriteActive.setMirror(false);
                spriteActive.move(-10, 0);

            }

        }
    }
}
