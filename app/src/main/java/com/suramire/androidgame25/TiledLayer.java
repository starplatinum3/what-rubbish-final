package com.suramire.androidgame25;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Suramire on 2017/10/18.
 */

public class TiledLayer {
    //region 字段
    private int mX;
    private int mY;
    private int mWidth;
    private int mHeight;
    private Bitmap mBitmap;
    private Rect mDest;
    private Rect mSrc;
    private int mRows;
    private int mCols;
    private int[][] mTiledCell;
    private int[] mTiledX;
    private int[] mTiledY;
    //endregion

    //region Getter and Setter

    public int getRows() {
        return mRows;
    }

    public void setRows(int rows) {
        this.mRows = rows;
    }

    public int getCols() {
        return mCols;
    }

    public void setCols(int cols) {
        this.mCols = cols;
    }

    public int getTiledCell(int cols,int row) {
        return mTiledCell[row][cols];
    }

    public void setTiledCell(int[][] tiledCell) {
        this.mTiledCell = tiledCell;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public int getX() {
        return mX;
    }

    public void setX(int x) {
        mX = x;
    }

    public int getY() {
        return mY;
    }

    public void setY(int y) {
        mY = y;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        mHeight = height;
    }



    //endregion

    public TiledLayer(Bitmap bitmap, int cols ,  int rows , int width, int height) {
        super();
        setBitmap(bitmap);
        setHeight(height);
        setWidth(width);
        setRows(rows);
        setCols(cols);
        mTiledCell = new int[rows][cols];
//        是有多少个方块的意思吗
        int wNum = bitmap.getWidth() / width;
//        整个的宽度 除以 每一个方块的宽度 就是有多少个
        int hNum = bitmap.getHeight() / height;
        mTiledX = new int[wNum*hNum+1];
        mTiledY = new int[wNum*hNum+1];
        for (int i = 0; i < hNum; i++) {
            for (int j = 0; j < wNum; j++) {
//                宽度的不断往右扩展  j * width; 是他的一个瓷砖的x轴的坐标
//               i * wNum + j + 1  是瓷砖的 idx
//                二维转化为一维
                mTiledX[i * wNum + j + 1] = j * width;
                mTiledY[i * wNum + j + 1] = i * height;
            }
        }
        mSrc = new Rect();
        mDest = new Rect();

    }

    public void move(float x, float y) {
        mY += y;
        mX += x;
        outOfBounds();
    }

    public void draw(Canvas canvas) {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
//                为啥要反过来
//                根据局部性的话 这样也不好呀
                int tiledIndex = getTiledCell(j,i);
                if (tiledIndex == 0) {
                    continue;
                }
//                根据 idx 获取他的 x 轴
                int x = mTiledX[tiledIndex];
                int y = mTiledY[tiledIndex];
                int ix = getX() + j * getWidth();
                int iy = getY() + i * getHeight();
                mSrc.set(x, y, x + getWidth(), y + getHeight());
                mDest.set(ix,iy ,
                        ix+getWidth(), iy + getHeight());
                canvas.drawBitmap(mBitmap, mSrc, mDest, null);
            }
        }
    }

    /**
     * 地图的边界处理
     */
    private void outOfBounds() {
        if(getX()>0){
            setX(0);
        }else if(getX()<800-getCols()*getWidth()){
            setX(800-getCols()*getWidth());
        }
        if(getY()>0){
            setY(0);
        }else if(getY()<480-getRows()*getWidth()){
            setY(480-getRows()*getWidth());
        }
    }

}
