package com.example.trash_detective.Model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.trash_detective.Bean.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockDrawer{
    /**
     * 画笔
     */

    private ArrayList<Block> block_list = new ArrayList<>();
    private static int color_count = 0;
    private static final List ColorList = Arrays.asList(Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW);
    private Paint block_paint;
    private Paint text_paint;
    public BlockDrawer(){
        block_paint = new Paint();
        block_paint.setStyle(Paint.Style.STROKE);
        block_paint.setStrokeWidth(10f);

        text_paint = new Paint();
        text_paint.setTextAlign(Paint.Align.LEFT);
        text_paint.setTextSize(40);
        text_paint.setStrokeWidth(3f);
    }

    public void drawBlock(Canvas canvas,Block block){
        int color = getColor();
        block_paint.setColor(color);
        text_paint.setColor(color);
        double[] location= block.getLocation();
        String text=block.getName_CN()+"("+block.getScore()+")";
        canvas.drawText(text, (float) location[0],  (float) location[1]-10, text_paint);
        canvas.drawRect(block.getRect(), block_paint);
        block_list.add(block);
    }

    private int getColor(){
        color_count+=1;
        return (int)ColorList.get(color_count%ColorList.size());
    }

    public ArrayList<Block> getBlockList(){
        return  block_list;
    }
}

