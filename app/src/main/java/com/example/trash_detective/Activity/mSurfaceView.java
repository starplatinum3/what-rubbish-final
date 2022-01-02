package com.example.trash_detective.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

import com.example.trash_detective.Bean.Block;
import com.example.trash_detective.Model.BlockDrawer;

import java.util.ArrayList;

public class mSurfaceView extends SurfaceView implements OnTouchListener, SurfaceHolder.Callback{
    /**
     * 画框模块
     */
    private Context context;
    private BlockDrawer blockDrawer;
    private Canvas canvas;
    private Thread thread;


    public mSurfaceView(Context context) {
        super(context);
        this.context = context;
        blockDrawer = new BlockDrawer();
        SurfaceHolder surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
    }

    public mSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public mSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }


    public void drawBlocks(ArrayList<Block> blocks){
        thread = new DrawThread(getHolder(), blocks);
        thread.start();
        setOnTouchListener(this);
    }

    private void showDescription(Block b) {
        Intent intent =new Intent(context, DescriptionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("block", b);
        context.startActivity(intent);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

        }
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            for (Block b : blockDrawer.getBlockList())
                if (b.getRect().contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                    showDescription(b);
                }
        }
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class DrawThread extends Thread{
        private final SurfaceHolder surfaceHolder;
        private ArrayList<Block> blocks;

        DrawThread(SurfaceHolder surfaceHolder,ArrayList<Block> blocks){
            this.surfaceHolder = surfaceHolder;
            this.blocks = blocks;
        }

        @Override
        public void run() {
            boolean done =false;
            synchronized (surfaceHolder) {
                while(!done) {
                    try {
                        Thread.sleep(500);
                        canvas = surfaceHolder.lockCanvas(null);
                        if(canvas!=null) {
                            Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath() + "/cache.jpg");
                            canvas.drawColor(Color.BLACK);
                            canvas.drawBitmap(bitmap, 0f, 0f, null);
                            blockDrawer = new BlockDrawer();
                            for (Block block : blocks)
                                blockDrawer.drawBlock(canvas, block);
                            done = true;
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        if (canvas != null) {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        }
                    }
                }
            }
        }
    }

}