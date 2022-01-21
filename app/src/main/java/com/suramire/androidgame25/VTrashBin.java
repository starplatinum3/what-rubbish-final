package com.suramire.androidgame25;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.SystemClock;

import com.example.whatrubbish.entity.Rubbish;
import com.example.whatrubbish.entity.RubbishType;
import com.suramire.androidgame25.item.Bullet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suramire on 2017/11/9.
 * 玛丽类 实现无敌与免伤状态
 */

public class VTrashBin extends Mario {

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
