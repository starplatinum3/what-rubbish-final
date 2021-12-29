package com.hurteng.stormplane.object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    Bitmap bitmap;
    float left;
    float top;
    float width;
    float height;
    float targetX;
    float targetY;

    public Item(Bitmap bitmap, float left, float top, float width, float height) {
        this.bitmap = bitmap;
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
    }

    public void draw(Canvas canvas, Paint paint){
        canvas.clipRect(left, top, left + width, top+ height);

//			放在下面之后 把他覆盖了
//			做游戏的时候 坐标怎么知道的 怎么量出来
        canvas.drawBitmap(bitmap, left, top, paint);
//        if (isPlay) {
//            canvas.drawBitmap(bitmap, left, top, paint);
//        } else {
//            canvas.drawBitmap(bitmap, left, top - height, paint);
//        }
        canvas.restore();

    }
}
