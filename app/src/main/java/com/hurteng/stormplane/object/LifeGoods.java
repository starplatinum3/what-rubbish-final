package com.hurteng.stormplane.object;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import com.example.whatrubbish.R;

////import com.hurteng.stormplane.myplane.R; import com.example.whatrubbish.R;

/**
 * 生命物品
 */
public class LifeGoods extends GameGoods {

	public LifeGoods(Resources resources) {
		super(resources);
	}
	
	@Override
	protected void initBitmap() {
		bmp = BitmapFactory.decodeResource(resources, R.drawable.life);
		object_width = bmp.getWidth();			
		object_height = bmp.getHeight();	
	}

}
