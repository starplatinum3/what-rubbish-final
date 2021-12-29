package com.example.compx202_finalproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.whatrubbish.R;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    int scoreNumber = 0;

    public class GraphicView extends View {
        GestureDetector gestureDetector;
        Bird bird1;
        Bird bird2;
        Bird bird3;
        Kiwi_Fruit fruit1;
        Box_normal box_normal1;
        Box_normal box_normal2;
        Box_normal box_normal3;
        AlertDialog.Builder builder;
        AlertDialog dialog;
        int counter = 0;

        //  Make the class of the kiwi bird, which include all the attributes of the bird
        //创建wiki鸟的类，该类包含该鸟的所有属性
        class Bird {
            boolean hasSpeed;
            private float x;
            private float y;
            private float speedX;
            private float speedY;
            double birdSpeed;
            private final int width;
            private final int height;
            private final Paint paint;
            Bitmap bitmap;
            Matrix birdMatrix;
            private float rotation;

            //  Set the position of the bird, and make the speed equals 0
            //设置鸟的位置，使速度等于0
            public Bird(int width, int height) {
                this.hasSpeed = true;
                this.birdSpeed = 0;
                speedX = 0;
                speedY = 0;
                this.width = width;
                this.height = height;
                paint = new Paint();
                this.birdMatrix = new Matrix();
                paint.setARGB(255, new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kiwi);
            }
        }

        //  Make the class of the kiwi fruit, which include all the attributes of the fruit
        //制作猕猴桃类，包括猕猴桃的所有属性
        class Kiwi_Fruit {
            private float x;
            private float y;
            private final int width;
            private final int height;
            private boolean shouldShow;
            private final Paint paint;
            Bitmap bitmap;

            //  Set the position variable of the kiwi fruit
            public Kiwi_Fruit(int width, int height) {
                this.width = width;
                this.height = height;
                this.shouldShow = true;
                paint = new Paint();
                paint.setARGB(255, new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kiwi_fruit);
            }
        }

        //  Make the class of the box, which include all the attributes of the box, and shouldShow is true to make it visible
        //创建长方体的类，其中包括长方体的所有属性，并且shouldShow为true以使其可见
        class Box_normal {
            private boolean isBroken;
            private boolean shouldShow;
            private float x;
            private float y;
            private final int width;
            private final int height;
            private final Paint paint;
            Bitmap bitmap;
            //  Set the position variable of the kiwi fruit, and shouldShow is true to make it visible
            //设置猕猴桃的位置变量，shouldShow为true以使其可见
            public Box_normal(int width, int height) {
                this.isBroken = false;
                this.shouldShow = true;
                this.width = width;
                this.height = height;
                paint = new Paint();
                paint.setARGB(255, new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
                //bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.box_normal);
                //Resources resources = getResources();
                bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.recycle);
            }

            public Box_normal(int width, int height,int imgRes) {
                this.isBroken = false;
                this.shouldShow = true;
                this.width = width;
                this.height = height;
                paint = new Paint();
                paint.setARGB(255, new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
                //bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.box_normal);
                //bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.recycle);
                bitmap = BitmapFactory.decodeResource(getResources(), imgRes);
            }
        }

        public GraphicView(Context context) {
            super(context);
            Intent intent = new Intent(context, HighScoreActivity.class);

            // Add the bird, boxes and kiwi fruit to the game page, set the size of these elements
            //将鸟、盒子和猕猴桃添加到游戏页面，设置这些元素的大小
            bird1 = new Bird(150, 120);
            bird2 = new Bird(150, 120);
            bird3 = new Bird(150, 120);
            fruit1 = new Kiwi_Fruit(120, 120);
            box_normal1 = new Box_normal(120, 120);
            box_normal2 = new Box_normal(120, 120);
            box_normal3 = new Box_normal(120, 120);
            gestureDetector = new GestureDetector(context, new MyGestureListener());

            // When the game is over, show the pop-ups to remind user enter next page
            //游戏结束后，显示弹出窗口以提醒用户进入下一页
            builder = new AlertDialog.Builder(context);
            builder.setMessage("Please go to see the top 5 rank.");
            builder.setIcon(R.mipmap.ic_launcher_round);
            builder.setCancelable(false);
            builder.setPositiveButton("See Rank", new DialogInterface.OnClickListener() {
                @Override
                /*
                    Add this function to the button, and pass the score obtained by the user to
                    the next page of the leaderboard
                    将此功能添加到按钮，并将用户获得的分数传递给
                    排行榜的下一页
                 */
                public void onClick(DialogInterface dialog, int which) {
                    intent.putExtra("score", scoreNumber);
                    startActivity(intent);
                    dialog.dismiss();
                }
            });
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            adjustSpeed(bird1);
            adjustSpeed(bird2);
            adjustSpeed(bird3);

            /*
                If the box exists, judge the collision between each box and each bird, and then call the function
                to give feedback.
                如果盒子存在，判断每个盒子和每只鸟之间的碰撞，然后调用该函数
                    提供反馈。
             */
            if(box_normal1.shouldShow) {
                collision_bird_box(bird1, box_normal1);
                collision_bird_box(bird2, box_normal1);
                collision_bird_box(bird3, box_normal1);
                canvas.drawBitmap(box_normal1.bitmap, null, new RectF(box_normal1.x, box_normal1.y, box_normal1.width + box_normal1.x, box_normal1.height + box_normal1.y), box_normal1.paint);
            }
            if(box_normal2.shouldShow) {
                collision_bird_box(bird1, box_normal2);
                collision_bird_box(bird2, box_normal2);
                collision_bird_box(bird3, box_normal2);
                canvas.drawBitmap(box_normal2.bitmap, null, new RectF(box_normal2.x, box_normal2.y, box_normal2.width + box_normal2.x, box_normal2.height + box_normal2.y), box_normal2.paint);
            }
            if(box_normal3.shouldShow) {
                collision_bird_box(bird1, box_normal3);
                collision_bird_box(bird2, box_normal3);
                collision_bird_box(bird3, box_normal3);
                canvas.drawBitmap(box_normal3.bitmap, null, new RectF(box_normal3.x, box_normal3.y, box_normal3.width + box_normal3.x, box_normal3.height + box_normal3.y), box_normal3.paint);
            }

            // Call the collision function of each bird and the wall
            //调用每只鸟与墙的碰撞函数
            collision_wall(bird1);
            collision_wall(bird2);
            collision_wall(bird3);

            // Use a matrix to make the bird rotate while flying
            //使用矩阵使鸟在飞行时旋转
            bird1.birdMatrix.setScale(0.1f, 0.1f);
            bird1.birdMatrix.postTranslate(bird1.x, bird1.y);
            if(bird1.speedX != 0 || bird1.speedY != 0) {
                // Set the bird's rotation angle and center of rotation
                //设置鸟的旋转角度和旋转中心
                bird1.rotation += 2f;
                bird1.birdMatrix.postRotate(bird1.rotation, bird1.x + 77, bird1.y + 63);
            }
            canvas.drawBitmap(bird1.bitmap, bird1.birdMatrix, bird1.paint);
            // When the first bird has died, set the second bird's data
            //当第一只鸟死后，设置第二只鸟的数据
            if(!bird1.hasSpeed) {
                bird2.birdMatrix.setScale(0.1f, 0.1f);
                bird2.birdMatrix.postTranslate(bird2.x, bird2.y);
                if(bird2.speedX != 0 || bird2.speedY != 0) {
                    // Set the bird's rotation angle and center of rotation
                    //设置鸟的旋转角度和旋转中心
                    bird2.rotation += 2f;
                    bird2.birdMatrix.postRotate(bird2.rotation, bird2.x + 77, bird2.y + 63);
                }
                canvas.drawBitmap(bird2.bitmap, bird2.birdMatrix, bird2.paint);
            }
            // When the second bird has died, set the second bird's data
            //第二只鸟死后，设置第二只鸟的数据
            if(!bird2.hasSpeed) {
                bird3.birdMatrix.setScale(0.1f, 0.1f);
                bird3.birdMatrix.postTranslate(bird3.x, bird3.y);
                if(bird3.speedX != 0 || bird3.speedY != 0) {
                    // Set the bird's rotation angle and center of rotation
                    //设置鸟的旋转角度和旋转中心
                    bird3.rotation += 2f;
                    bird3.birdMatrix.postRotate(bird3.rotation, bird3.x + 77, bird3.y + 63);
                }
                canvas.drawBitmap(bird3.bitmap, bird3.birdMatrix, bird3.paint);
            }

            // If the fruit exist, call the function of every collision of boxes.
            //如果水果存在，调用每个盒子碰撞的函数。
            if (fruit1.shouldShow){
                collision_bird_fruit(bird1, fruit1);
                collision_bird_fruit(bird2, fruit1);
                collision_bird_fruit(bird3, fruit1);
                canvas.drawBitmap(fruit1.bitmap, null, new RectF(fruit1.x, fruit1.y, fruit1.width + fruit1.x, fruit1.height + fruit1.y), fruit1.paint);
            }

            // If the third bird died, show the pop-ups
            //如果第三只鸟死了，显示弹出窗口
            if(!bird3.hasSpeed) {
                if(counter == 0) {
                    builder.setTitle("Your score is: " + scoreNumber);
                    dialog = builder.create();
                    dialog.show();
                }
                counter++;
            }

            invalidate();
        }

        /**
         * 斜方向速度 算 sqrt(x^2+y^2)
         * @param speedX
         * @param speedY
         * @return
         */
        double obliqueVelocity(float speedX,float speedY){
           double speedObliquePow= Math.pow(speedX, 2) + Math.pow(speedY, 2);
           return  Math.sqrt(speedObliquePow);
        }

        /**
         * The method to adjust the direction adn the image after the bird collision with the wall
         * 鸟撞墙后图像方向调整方法
         * @param bird Bird
         */
        protected void collision_wall(Bird bird) {
            //  When the bird touch the ground, the picture of bird will changed, and the speed change to 0
            //当鸟触地时，鸟的图片会改变，速度变为0
            if(bird.y > getHeight() - 120) {
                //屏幕高度 -120 的位置  大概就是 地面吧
                bird.y = getHeight() - 120;
                bird.bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kiwi_dead);
                bird.hasSpeed = false;
            }

            // When the bird touch the right border of the screen, the bird will rebound.
            //当鸟碰到屏幕的右边框时，鸟会反弹。
            if(bird.x > getWidth() - 150) {
                //getWidth() - 150 是右边墙的 位置
                //bird.speedY = (float) (bird.speedX * Math.tan(Math.acos(bird.speedX / (Math.sqrt(Math.pow(bird.speedY, 2) + Math.pow(bird.speedX, 2))))));
                //Math.pow(bird.speedY, 2) + Math.pow(bird.speedX, 2);
                //bird.speedY = (float) (bird.speedX * Math.tan(Math.acos(bird.speedX / (Math.sqrt()))));
                Log.d(" bird.speedY before ", "collision_wall:  bird.speedY  "+ bird.speedY);
                double angle = Math.acos(bird.speedX / obliqueVelocity(bird.speedX, bird.speedY));
                //bird.speedY = (float) (bird.speedX * Math.tan(Math.acos(bird.speedX / obliqueVelocity(bird.speedX,bird.speedY))));
                bird.speedY = (float) (bird.speedX * Math.tan(angle));
                //y 的方向 可能是正的也可能是反的  但是数字大小都是一样的
                Log.d(" bird.speedY after", "collision_wall:  bird.speedY  "+ bird.speedY);
                //他y 的速度本来没有吗
                bird.speedX = -bird.speedX;
                //x 来个反方向
            }

            // Limit the position of the bird so that he cannot go out of the left edge of the screen
            else if(bird.x < 0) {
                bird.x = 0;
            }
        }

        /**
         * 调整鸟的速度
         * Adjust the speed of bird
         * @param bird Bird
         */
        protected void adjustSpeed(Bird bird) {
            // Calculate the speed of the bird in the forward direction
            //Calculate the speed of the bird in the forward direction
            //调整鸟的速度
            if(bird.hasSpeed) {
                bird.x += bird.speedX;
                bird.y += bird.speedY;
                bird.birdSpeed = Math.sqrt(Math.pow(bird.speedX, 2) + Math.pow(bird.speedY, 2));
            }
            else {
                bird.speedX = 0;
                bird.speedY = 0;
            }
            // If bird have the speed in Y axis, add a acceleration to the bird
            //如果鸟在Y轴上有速度，则为鸟添加加速度
            if(bird.speedY != 0) {
                bird.speedY += 1;
            }
        }

        /**
         * The collision method of bird and fruit
         * @param bird Bird
         * @param fruit Kiwi_Fruit
         */
        protected void collision_bird_fruit(Bird bird, Kiwi_Fruit fruit) {
            /*
                Judge whether the bird and the fruit collide, if they collide, the score is increased by 3,
                and the fruit disappears
                判断鸟和水果是否相撞，如果相撞，分数增加3分，
                    水果就不见了
             */
            boolean collision = (bird.x <= (fruit.x + fruit.width) && (bird.x + bird.width) >= fruit.x
                    && bird.y <= (fruit.y + fruit.height) && (bird.y + bird.height) >= fruit.y);
            if(collision) {
                if (fruit.shouldShow){
                    scoreNumber+=3;
                    updateScore();
                    fruit.shouldShow = false;
                }
            }
        }

        /**
         * The collision method of bird and box
         * 鸟与箱的碰撞方法
         * @param bird Bird
         * @param box_normal Box_normal
         */
        protected void collision_bird_box(Bird bird, Box_normal box_normal) {
            // Judge whether the position of bird and boxes are intersect
            //判断鸟和盒子的位置是否相交
            boolean getCollision = (bird.x <= (box_normal.x + box_normal.width) && (bird.x + bird.width) >= box_normal.x
                    && bird.y <= (box_normal.y + box_normal.height) && (bird.y + bird.height) >= box_normal.y);

            // If bird and boxes have collision, there are some reaction
            //如果鸟和箱子发生碰撞，会有一些反应
            if(getCollision) {
                // If the box is broken, the box disappears after another collision and the score increases by one
                //如果长方体破裂，则长方体在另一次碰撞后消失，分数增加一分
                if(box_normal.isBroken) {
                    box_normal.shouldShow = false;
                    box_normal.isBroken = true;
                    scoreNumber+=1;
                    updateScore();
                }
                // If the box is not broken, it will react differently depending on the speed of the bird
                //如果盒子没有被打破，它会根据鸟的速度做出不同的反应
                else {
                    // If the speed lower than 100, the box will show broken and the bird will rebound
                    //如果速度低于100，箱子将显示破损，鸟将反弹
                    if(bird.birdSpeed < 100) {
                        box_normal.bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.box_broken);
                        box_normal.isBroken = true;
                        bird.speedY = (float) (bird.speedX * Math.tan(Math.acos(bird.speedX / (Math.sqrt(Math.pow(bird.speedX, 2) + Math.pow(bird.speedY, 2))))));
                        bird.speedX = -bird.speedX;
                    }
                    // If the speed higher than 100, the wox will directly broken
                    //如果速度高于100，盒子将直接 破了
                    else {
                        box_normal.shouldShow = false;
                        box_normal.isBroken = true;
                    }
                    // Game score changes after collision
                    //碰撞后游戏分数发生变化
                    scoreNumber+=1;
                    updateScore();
                }
            }
        }

        public void collisionRubBin(Bird bird, Box_normal box_normal) {
            // Judge whether the position of bird and boxes are intersect
            //判断鸟和盒子的位置是否相交
            boolean getCollision = (bird.x <= (box_normal.x + box_normal.width) && (bird.x + bird.width) >= box_normal.x
                    && bird.y <= (box_normal.y + box_normal.height) && (bird.y + bird.height) >= box_normal.y);

            // If bird and boxes have collision, there are some reaction
            //如果鸟和箱子发生碰撞，会有一些反应
            if(getCollision) {
                // If the box is broken, the box disappears after another collision and the score increases by one
                //如果长方体破裂，则长方体在另一次碰撞后消失，分数增加一分
                if(box_normal.isBroken) {
                    box_normal.shouldShow = false;
                    box_normal.isBroken = true;
                    scoreNumber+=1;
                    updateScore();
                }
                // If the box is not broken, it will react differently depending on the speed of the bird
                //如果盒子没有被打破，它会根据鸟的速度做出不同的反应
                else {
                    // If the speed lower than 100, the box will show broken and the bird will rebound
                    //如果速度低于100，箱子将显示破损，鸟将反弹
                    if(bird.birdSpeed < 100) {
                        box_normal.bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.box_broken);
                        box_normal.isBroken = true;
                        bird.speedY = (float) (bird.speedX * Math.tan(Math.acos(bird.speedX / (Math.sqrt(Math.pow(bird.speedX, 2) + Math.pow(bird.speedY, 2))))));
                        bird.speedX = -bird.speedX;
                    }
                    // If the speed higher than 100, the wox will directly broken
                    //如果速度高于100，盒子将直接 破了
                    else {
                        box_normal.shouldShow = false;
                        box_normal.isBroken = true;
                    }
                    // Game score changes after collision
                    //碰撞后游戏分数发生变化
                    scoreNumber+=1;
                    updateScore();
                }
            }
        }


        // Set the position of every elements
        //设置每个元素的位置
        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            bird1.x = (float) (w * 0.13);
            bird1.y = (float) (h * 0.46);
            bird2.x = (float) (w * 0.13);
            bird2.y = (float) (h * 0.46);
            bird3.x = (float) (w * 0.13);
            bird3.y = (float) (h * 0.46);
            fruit1.x = (float) (w * 0.665);
            fruit1.y = (float) (h * 0.53);
            box_normal1.x = (float) (w * 0.6);
            box_normal1.y = (float) (h  * 0.236);
            box_normal2.x = (float) (w * 0.4);
            box_normal2.y = (float) (h * 0.543);
            box_normal3.x = (float) (w * 0.825);
            box_normal3.y = (float) (h * 0.449);
        }


        /**
         * Limit the scope of touch
         * 限制触摸的范围
         *
         * @param event MotionEvent
         * @return
         */
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            final int heightCondition = getHeight() / 4;
            final int heightCondition2 = getHeight() * 7 / 8;
            final int widthCondition = getWidth() * 3 / 8;

            /*
                If birds have speed ,onTouchEvent return true, so user cannot move the position of bird
                when bird flying
                如果鸟有速度，onTouchEvent返回true，因此用户无法移动鸟的位置
                当鸟儿飞翔时
             */
            if(gestureDetector.onTouchEvent(event) || bird1.hasSpeed || bird2.hasSpeed || bird3.hasSpeed) { return true; }
            if(bird1.hasSpeed) {
                if(event.getY() > (heightCondition) && event.getY() < (heightCondition2) && event.getX() < (widthCondition)) {
                    bird1.x = event.getX();
                    bird1.y = event.getY();
                    bird1.speedX = 0;
                    bird1.speedY = 0;
                }
            }
            else {
                if(bird2.hasSpeed) {
                    if(event.getY() > (heightCondition) && event.getY() < (heightCondition2) && event.getX() < (widthCondition)) {
                        bird2.x = event.getX();
                        bird2.y = event.getY();
                        bird2.speedX = 0;
                        bird2.speedY = 0;
                    }
                }
                else {
                    if(bird3.hasSpeed) {
                        if(event.getY() > (heightCondition) && event.getY() < (heightCondition2) && event.getX() < (widthCondition)) {
                            bird3.x = event.getX();
                            bird3.y = event.getY();
                            bird3.speedX = 0;
                            bird3.speedY = 0;
                        }
                    }
                }
            }
            return super.onTouchEvent(event);
        }

        /*
            Limit the scope of fling
            限制放纵的范围
         */
        public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
            @Override
            public boolean onDown(MotionEvent e) { return true; }

            // 6. 用户按下触摸屏、快速移动后松开
            // 参数：
            // e1：第1个ACTION_DOWN MotionEvent
            // e2：最后一个ACTION_MOVE MotionEvent
            // velocityX：X轴上的移动速度，像素/秒
            // velocityY：Y轴上的移动速度，像素/秒
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                final int heightCondition = getHeight() / 4;
                final int heightCondition2 = getHeight() * 7 / 8;
                final int widthCondition = getWidth() * 3 / 8;

                if(bird1.hasSpeed) {
                    //如果他是在这么个范围里面的话
                    if(e2.getY() > (heightCondition) && e2.getY() < (heightCondition2) && e2.getX() < (widthCondition)) {
                        bird1.x = e2.getX();
                        bird1.y = e2.getY();
                        bird1.speedX = velocityX / 50;
                        bird1.speedY = velocityY / 50;
                    }
                }
                else {
                    if(bird2.hasSpeed) {
                        if(e2.getY() > (heightCondition) && e2.getY() < (heightCondition2) && e2.getX() < (widthCondition)) {
                            bird2.x = e2.getX();
                            bird2.y = e2.getY();
                            bird2.speedX = velocityX / 50;
                            bird2.speedY = velocityY / 50;
                        }
                    }
                    else {
                        if(bird3.hasSpeed) {
                            if(e2.getY() > (heightCondition) && e2.getY() < (heightCondition2) && e2.getX() < (widthCondition)) {
                                bird3.x = e2.getX();
                                bird3.y = e2.getY();
                                bird3.speedX = velocityX / 50;
                                bird3.speedY = velocityY / 50;
                            }
                        }
                    }
                }
                return true;
            }
        }
    }

    //  This code is make the TextView update the score
    //此代码用于使TextView更新分数
    private void updateScore(){
        TextView tv = (TextView) findViewById(R.id.scoreNumber);
        tv.setText(String.valueOf(scoreNumber));
    }

    /**
     * This code is used to make the game full screen
     * 此代码用于使游戏全屏显示
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        GraphicView gv = new GraphicView(this);
        ConstraintLayout rootView = (ConstraintLayout) findViewById(R.id.gameContainer);
        rootView.addView(gv);

        int uiOptions = View. SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View. SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);
    }
}
