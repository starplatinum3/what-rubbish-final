package com.suramire.androidgame25;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.SystemClock;

import com.example.whatrubbish.entity.Rubbish;
import com.example.whatrubbish.entity.RubbishType;
import com.suramire.androidgame25.item.Bullet;
import com.suramire.androidgame25.util.BitmapUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suramire on 2017/11/9.
 * 玛丽类 实现无敌与免伤状态
 */

public class VTrashBin extends Mario {

	Context context;
	RubbishType rubbishType;

	public RubbishType getRubbishType() {
		return rubbishType;
	}

	public void setRubbishType(RubbishType rubbishType) {
		this.rubbishType = rubbishType;
	}

	public VTrashBin(List<Bitmap> bitmaps) {
		super(bitmaps);
	}

	//String  assetsRootDir="mario";
	//这怎么给他找到的
	String  assetsRootDir="trashBin";

	//void Update () {
	//
	//	if (isInvincible)
	//	{
	//		//2
	//		timeSpentInvincible += Time.deltaTime;
	//
	//		//3
	//		if (timeSpentInvincible < 3f) {
	//			float remainder = timeSpentInvincible % 0.3f;
	//			renderer.enabled = remainder > 0.15f;
	//		}
	//		//4
	//		else {
	//			renderer.enabled = true;
	//			isInvincible = false;
	//		}
	//	}
	//}
	//double timeSpentInvincible;
	//float timeSpentInvincible=0;

	int smallTrashBinWidth=46;
	/**
	 * 状态变化(变身)
	 * @param targetStatus 目标状态
	 */
	public void shapeShift(int targetStatus){
		if(targetStatus!=2){
			//不是有弹药的
			bullets = null;
		}
		int value =0;
		if(isInvincible()){
			//无敌 从三开始
			value=3;
		}
		//BitmapUtil.bitMapScale()
		List<Bitmap> bitmaps = getBitmapsList().get(targetStatus+value);
		Bitmap bitmap = bitmaps.get(0);
		//bitmap.getScaledHeight()
		int width = bitmap.getWidth();
		//因为和原来的 素材大小不一样了吗
		int height = bitmap.getHeight();
		//状态变化才修正坐标
		if(targetStatus!=getStatus()){
			//坐标修正
			int y;
			//变成中间了吗 需要高度的一半吗
			//y=getY();
			//人变宽了 往下面去
			if(getWidth()>width){
				y =getY()+smallTrashBinWidth;
				//y =getY()+32;
			}else if(getWidth()<width){
				y =getY()-smallTrashBinWidth;
			}else{
				y = getY();
			}
			setPosition(getX(),y);
			setStatus(targetStatus);
		}
		setWidth(width);
		setHeight(height);
		setBitmaps(bitmaps);

		int[] temp = new int[bitmaps.size()];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = i;
		}
		setFrameSequence(temp);

	}

	int timeSpentInvincible=0;
	//https://blog.csdn.net/roney_win/article/details/41050483
	@Override
	public void draw(Canvas canvas) {

		if (isZeroDamage()) {
			//还是无敌状态
			//float remainder = timeSpentInvincible % 0.3f;
			////renderer.enabled = remainder > 0.15f;
			//if( remainder > 0.15f){
			//	super.draw(canvas);
			//}

			timeSpentInvincible++;
			int remainder = timeSpentInvincible %2;
			//renderer.enabled = remainder > 0.15f;
			if( remainder ==0){
				super.draw(canvas);
			}

		}
		//4
		else {
			timeSpentInvincible=0;
			super.draw(canvas);
			//setInvincible(false);
			//zero
			//invincible=false;
			//setInvincible(false);
			//is
			//renderer.enabled = true;
			//isInvincible = false;
		}

		//isInvincible()
		//被人碰到的无敌 应该是 zeroDamagge
//		if (isZeroDamage()) {
////2
////			timeSpentInvincible += Time.deltaTime;
//			timeSpentInvincible ++;
//
//			//3
//			//if (timeSpentInvincible < 3f) {
//			if (isZeroDamage()) {
//				//还是无敌状态
//				float remainder = timeSpentInvincible % 0.3f;
//				//renderer.enabled = remainder > 0.15f;
//				if( remainder > 0.15f){
//					super.draw(canvas);
//				}
//
//			}
//			//4
//			else {
//				super.draw(canvas);
//				setInvincible(false);
//				//zero
//				//invincible=false;
//				//setInvincible(false);
//				//is
//				//renderer.enabled = true;
//				//isInvincible = false;
//			}
//			return;
//		}
//		super.draw(canvas);


		//
		//if(isMirror()){
		//	canvas.save();
		//	//翻转画布 相当于翻转人物
		//	canvas.scale(-1,1,getX()+getWidth()/2,getY()+getHeight()/2);
		//	if(isZeroDamage()){
		//		super.draw(canvas,paint);
		//	}else{
		//		super.draw(canvas);
		//	}
		//	canvas.restore();
		//}else{
		//	if(isZeroDamage()){
		//		super.draw(canvas,paint);
		//	}else{
		//		super.draw(canvas);
		//	}
		//}
	}

	public void initTrashBinBitmapList(Context context){

		//draw();
		List<Bitmap> marioSmallBitmaps = new ArrayList<>();
		ArrayList<Bitmap> marioFireInvBitmaps = new ArrayList<>();
		ArrayList<Bitmap> marioSmallInvBitmaps = new ArrayList<>();
		ArrayList<Bitmap> marioBigInvincibleBitmaps = new ArrayList<>();
		List<Bitmap> marioBigBitmaps = new ArrayList<>();
		List<Bitmap> marioFireBitmaps = new ArrayList<>();
		//List<Bitmap> flowerBitmaps = new ArrayList<>();
		//List<Bitmap> brokenBitmaps = new ArrayList<>();
		//List<Bitmap> pipeBitmaps = new ArrayList<>();
		//getco
		//设置数组型图片资源

		Bitmap bitmapSmall = BitmapUtil.getBitmap(assetsRootDir + "/small/mario_00" + ".png", context);
		Bitmap bitmapBig = BitmapUtil.bitMapScale(bitmapSmall, 1, 2.0f);
		int i=0;
		smallTrashBinWidth=bitmapSmall.getWidth();
		marioSmallBitmaps.add(bitmapSmall);
		marioBigBitmaps.add(bitmapBig);
		marioFireBitmaps.add(bitmapBig);
		marioFireInvBitmaps.add(bitmapSmall);
		marioSmallInvBitmaps.add(bitmapBig);
		marioBigInvincibleBitmaps.add(bitmapBig);

		//for (int i = 0; i < 7; i++) {
		//	//marioBigBitmaps.add(BitmapUtil.getBitmap("mario/big/mario_0" + i + ".png",context));
		//	//marioSmallBitmaps.add(BitmapUtil.getBitmap("mario/small/mario_0" + i + ".png",context));
		//	//marioFireBitmaps.add(BitmapUtil.getBitmap("mario/fire/mario_0" + i + ".png",context));
		//	//marioFireInvBitmaps.add(BitmapUtil.getBitmap("mario/fire/mario_inv_0" + i + ".png",context));
		//	//marioSmallInvBitmaps.add(BitmapUtil.getBitmap("mario/small/mario_inv_0" + i + ".png",context));
		//	//marioBigInvincibleBitmaps.add(BitmapUtil.getBitmap("mario/big/mario_inv_0" + i + ".png",context));
		//
		//	//marioSmallBitmaps.add(BitmapUtil.getBitmap(assetsRootDir+"/small/mario_0" + i + ".png",context));
		//	marioBigBitmaps.add(BitmapUtil.getBitmap(assetsRootDir+"/big/mario_0" + i + ".png",context));
		//	marioFireBitmaps.add(BitmapUtil.getBitmap(assetsRootDir+"/fire/mario_0" + i + ".png",context));
		//	marioFireInvBitmaps.add(BitmapUtil.getBitmap(assetsRootDir+"/fire/mario_inv_0" + i + ".png",context));
		//	marioSmallInvBitmaps.add(BitmapUtil.getBitmap(assetsRootDir+"/small/mario_inv_0" + i + ".png",context));
		//	marioBigInvincibleBitmaps.add(BitmapUtil.getBitmap(assetsRootDir+"/big/mario_inv_0" + i + ".png",context));
		//
		//	//marioBigBitmaps.add(BitmapUtil.getBitmap(context,"mario/big/mario_0" + i + ".png"));
		//	//marioSmallBitmaps.add(getBitmap("mario/small/mario_0" + i + ".png"));
		//	//marioFireBitmaps.add(getBitmap("mario/fire/mario_0" + i + ".png"));
		//	//marioFireInvBitmaps.add(getBitmap("mario/fire/mario_inv_0" + i + ".png"));
		//	//marioSmallInvBitmaps.add(getBitmap("mario/small/mario_inv_0" + i + ".png"));
		//	//marioBigInvincibleBitmaps.add(getBitmap("mario/big/mario_inv_0" + i + ".png"));
		//
		//	//rubbishSmallInvBitmaps.add()
		//}

		List<List<Bitmap>> bitmapsList = new ArrayList<>();
		bitmapsList.add(marioSmallBitmaps);
		bitmapsList.add(marioBigBitmaps);
		bitmapsList.add(marioFireBitmaps);
		//无敌的状态 前面有三个 所以 要+3
		bitmapsList.add(marioSmallInvBitmaps);
		bitmapsList.add(marioBigInvincibleBitmaps);
		bitmapsList.add(marioFireInvBitmaps);
		setBitmapsList(bitmapsList);
	}
	//Rubbish rubbish;
	public VTrashBin(int width, int height, List<Bitmap> bitmaps) {
		super(width, height, bitmaps);
	}

	public static void main(String[] args) {
		VTrashBin vTrashBin=new VTrashBin(1, 1, new ArrayList<>());
		//vTrashBin.isZeroDamage()
	}

	public VTrashBin(List<Bitmap> bitmaps, RubbishType rubbishType) {
		super(bitmaps);
		this.rubbishType = rubbishType;
	}
}
