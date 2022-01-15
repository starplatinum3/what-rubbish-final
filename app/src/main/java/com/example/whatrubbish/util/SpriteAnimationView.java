package com.example.whatrubbish.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.whatrubbish.R;

public class SpriteAnimationView extends SurfaceView implements Runnable {
    private static final String TAG = "AnimationView";

    private static final String LEFT_TO_RIGHT = "left-to-right";
    private static final String RIGHT_TO_LEFT = "right-to-left";

    private Thread mRenderThread;

    private SurfaceHolder mSurfaceHolder;

    private volatile boolean mRunning;

    private boolean mIsPlaying;
    private boolean mIsPause;

    private Canvas mCanvas;

    private Paint mPaint;

    private Bitmap mBitmap;

    // Used to record last frame change time
    private long mLastFrameChangeTime;

    // Used to record current frame rate
    private long mFps;

    // Source frame rect (on the sprite sheet)
    private Rect mSrcFrameRect;

    // Destination frame rect (to be drawn)
    private RectF mDstFrameRect;

    // ---------------- user configurable parameters ----------------
    // Sprite sheet resource ID (e.g. R.drawable.rabbit)
    private int mSpriteSheet;

    // Each frame's width
    private int mFrameWidth = 300;

    // Each frame's height
    private int mFrameHeight = 150;

    // Frame count
    private int mFrameCount = 3;

    // Sprite sheet's row count
    private int mRowCount = 1;

    // Indicate which row of the sprite sheet should be used
    private int mRowIndex = 0;

    // Current frame index
    private int mCurrentFrame = 0;

    // Display duration of each frame (ms)
    private int mFrameDuration = 100;

    // Current offset on the sprite sheet
    private float mOffset = 0;

    // Moved offset per second
    private float mOffsetPerSecond = 250;

    // Play animation in place or not
    private boolean mInPlace = true;

    // Trigger the animation via touch event or not
    private boolean mTouchToMove = false;

    // Play animation in loop or not
    private boolean mPlayOnce = false;

    // Move direction (left-to-right or right-to-left)
    private String mDirection = LEFT_TO_RIGHT;

    public SpriteAnimationView(Context context) {
        this(context, null);
    }

    public SpriteAnimationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpriteAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        parseAttributes(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mSurfaceHolder = getHolder();

        // Set transparent background
        mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        setZOrderOnTop(true);

        // Load the Sprite bitmap
        mBitmap = BitmapFactory.decodeResource(this.getResources(), mSpriteSheet);

        // Scale the bitmap
        mBitmap = Bitmap.createScaledBitmap(mBitmap,
                mFrameWidth * mFrameCount,
                mFrameHeight * mRowCount,
                false);

        // Initialize frame rectangles
        mSrcFrameRect = new Rect(
                0,
                mFrameHeight * mRowIndex,
                mFrameWidth,
                mFrameHeight * (mRowIndex + 1));

        mDstFrameRect = new RectF();
    }

    private void parseAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SpriteAnimationView, defStyleAttr, 0);
        //TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
        //        R.styleable.Spri, defStyleAttr, 0);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.SpriteAnimationView_sprite_sheet:
                    mSpriteSheet = a.getResourceId(attr, mSpriteSheet);
                    break;
                case R.styleable.SpriteAnimationView_frame_width:
                    mFrameWidth = a.getDimensionPixelSize(attr, mFrameWidth);
                    break;
                case R.styleable.SpriteAnimationView_frame_height:
                    mFrameHeight = a.getDimensionPixelSize(attr, mFrameHeight);
                    break;
                case R.styleable.SpriteAnimationView_frame_count:
                    mFrameCount = a.getInteger(attr, mFrameCount);
                    break;
                case R.styleable.SpriteAnimationView_row_count:
                    mRowCount = a.getInteger(attr, mRowCount);
                    break;
                case R.styleable.SpriteAnimationView_row_index:
                    mRowIndex = a.getInteger(attr, mRowIndex);
                    break;
                case R.styleable.SpriteAnimationView_start_frame:
                    mCurrentFrame = a.getInteger(attr, mCurrentFrame);
                    break;
                case R.styleable.SpriteAnimationView_frame_duration:
                    mFrameDuration = a.getInteger(attr, mFrameDuration);
                    break;
                case R.styleable.SpriteAnimationView_offset_per_second:
                    mOffsetPerSecond = a.getDimensionPixelSize(attr, (int) mOffsetPerSecond);
                    break;
                case R.styleable.SpriteAnimationView_position:
                    mOffset = a.getDimensionPixelSize(attr, (int) mOffset);
                    break;
                case R.styleable.SpriteAnimationView_in_place:
                    mInPlace = a.getBoolean(attr, mInPlace);
                    break;
                case R.styleable.SpriteAnimationView_touch_to_move:
                    mTouchToMove = a.getBoolean(attr, mTouchToMove);
                    break;
                case R.styleable.SpriteAnimationView_play_once:
                    mPlayOnce = a.getBoolean(attr, mPlayOnce);
                    break;
                case R.styleable.SpriteAnimationView_direction:
                    mDirection = (a.getString(attr) != null ? a.getString(attr) : mDirection);
                    break;
            }
        }
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mOffset = (mDirection.equals(LEFT_TO_RIGHT) ? 0 : getMeasuredWidth() - mFrameWidth);
    }

    @Override
    public void run() {
        while (mRunning) {

            // Capture the current time in milliseconds in startFrameTime
            long frameStartTime = System.currentTimeMillis();

            // Update the frame
            updateFrame();

            // Draw the frame
            drawFrame();

            // Calculate fps based on frame drawing time
            long frameDrawingTime = System.currentTimeMillis() - frameStartTime;
            if (frameDrawingTime >= 1) {
                mFps = 1000 / frameDrawingTime;
                if (!mTouchToMove && !mIsPlaying) {
                    mIsPlaying = true;
                }
            }
        }

    }

    public void getCurrentFrame(){
        long time = System.currentTimeMillis();
        if (mIsPlaying) {
            if (time > mLastFrameChangeTime + mFrameDuration) {
                mLastFrameChangeTime = time;
                mCurrentFrame = (mCurrentFrame + 1) % mFrameCount;
            }
        }

        if(!mIsPause){
            // Update source frame rect
            mSrcFrameRect.left = mCurrentFrame * mFrameWidth;
            mSrcFrameRect.right = mSrcFrameRect.left + mFrameWidth;
        }

    }

    /**
     * 只能画出两种类型 左到右 或者右到左 ，如果精灵图不是 平着的一系列 就不行
     */
    public void updateFrame() {
        if (mIsPlaying && !mInPlace) {
            if (getMeasuredWidth() > 0) {
                switch (mDirection) {
                    case LEFT_TO_RIGHT:
                        // Update the offset based on fps
                        mOffset += (mOffsetPerSecond / mFps);
                        if (!mPlayOnce) {
                            // Wrap the offset to play animation repeatedly
                            mOffset %= getMeasuredWidth();
                        }
                        break;
                    case RIGHT_TO_LEFT:
                        // Update the offset based on fps
                        mOffset -= (mOffsetPerSecond / mFps);
                        if (!mPlayOnce) {
                            // Wrap the offset to play animation repeatedly
                            mOffset = mOffset > -mFrameWidth ? mOffset : mOffset + getMeasuredWidth();
                        }
                        break;
                }
            }
        }
    }

    public  void doDraw(){
        mCanvas = mSurfaceHolder.lockCanvas();

        // Clear background color
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        // Get source frame rect
        getCurrentFrame();

        // Set destination frame rect
        mDstFrameRect.set(mOffset,
                0,
                mOffset + mFrameWidth,
                mFrameHeight);

        // Draw bitmap from source to destination
        mCanvas.drawBitmap(mBitmap,
                mSrcFrameRect,
                mDstFrameRect, mPaint);

        mSurfaceHolder.unlockCanvasAndPost(mCanvas);
    }

    public void drawFrame() {
        if (mSurfaceHolder.getSurface().isValid()) {
            doDraw();
            //Log.d(TAG, "drawFrame: isValid");
            //mCanvas = mSurfaceHolder.lockCanvas();
            //
            //// Clear background color
            //mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            //
            //// Get source frame rect
            //getCurrentFrame();
            //
            //// Set destination frame rect
            //mDstFrameRect.set(mOffset,
            //        0,
            //        mOffset + mFrameWidth,
            //        mFrameHeight);
            //
            //// Draw bitmap from source to destination
            //mCanvas.drawBitmap(mBitmap,
            //        mSrcFrameRect,
            //        mDstFrameRect, mPaint);
            //
            //mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    public void stop() {
        mIsPlaying = false;
        mRunning = false;
        try {
            mRenderThread.join();
        } catch (InterruptedException e) {
            Log.e(TAG, "joining thread");
        }

    }

    public void pause() {
        mIsPause=true;
        //start();
        mRunning = true;
        mRenderThread = new Thread(this);
        mRenderThread.start();
    }
    public void start() {
        mIsPause=false;
        mRunning = true;
        mRenderThread = new Thread(this);
        mRenderThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (mTouchToMove) {
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    mIsPlaying = true;
                    break;
                case MotionEvent.ACTION_UP:
                    mIsPlaying = false;
                    break;
            }

            return true;
        } else {
            return super.onTouchEvent(motionEvent);
        }
    }

}
