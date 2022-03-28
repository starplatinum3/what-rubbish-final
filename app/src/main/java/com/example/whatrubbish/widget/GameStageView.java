package com.example.whatrubbish.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
//import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by Administrator on 2018/5/4.
 */

public class GameStageView extends View {

    private int item_bg_color = Color.parseColor("#FF1BBC9B");


    private int marginLeft = 20;
    private int marginTop = 100;
    private int marginRight = 20;

    private int item_heith = 50;
    private int item_width = 120;

    private int text_size = 25;
    private int text_normal_color = Color.parseColor("#FFFFFF");

    private int dividerWidth = 70;
    private int dividerHeith = 10;

    private int count = 0;
    private int item_horizontal_move = 0;


    public GameStageView(Context context) {
        super(context);
    }

    public GameStageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GameStageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GameStageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //写着四个 为啥画出了3个
        int drawCnt=8;
        //确实是个数 但是好像少了一个
        //安卓 绝对布局
        //for (int i = 0; i < 4; i++) {
        for (int i = 0; i < drawCnt; i++) {
            count = i;
            drawRoundRect(canvas);
            item_horizontal_move = count * (item_width + dividerWidth);
        }
    }

    /**
     * 写着第一关字的那个 方框
     * @param canvas
     */
    private void drawRoundRect(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(item_bg_color);
        RectF rectF = new RectF();
        rectF.left = marginLeft + item_horizontal_move;
        rectF.right = rectF.left + item_width;
        rectF.top = marginTop;
        rectF.bottom = rectF.top + item_heith;
        canvas.drawRoundRect(rectF, 16, 16, paint);

        drawRoundRectText(canvas);
    }

    private void drawRoundRectText(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(text_normal_color);
        paint.setTextSize(text_size);
        canvas.drawText("第一关", marginLeft + item_width / 5 + item_horizontal_move, marginTop + item_heith / 2 + 10, paint);
        drawHorizontalDivider(canvas);
        drawVerticalDivider(canvas);
    }

    private void drawHorizontalDivider(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(item_bg_color);
        Rect rect = new Rect();
        rect.left = marginLeft + item_width + item_horizontal_move;
        rect.right = rect.left + dividerWidth;
        rect.top = marginTop + item_heith / 2 - dividerHeith / 2;
        rect.bottom = rect.top + dividerHeith;
        canvas.drawRect(rect, paint);
    }

    private void drawVerticalDivider(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(item_bg_color);
        Rect rect = new Rect();
        rect.left = marginLeft + item_width / 2 - dividerHeith / 2 + item_horizontal_move;
        rect.right = rect.left + dividerHeith;
        rect.top = marginTop + item_heith;
        rect.bottom = rect.top + dividerWidth;
        canvas.drawRect(rect, paint);
    }

}
