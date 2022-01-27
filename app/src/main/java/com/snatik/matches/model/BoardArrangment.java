package com.snatik.matches.model;

import java.util.Map;

import android.graphics.Bitmap;
import android.util.Log;

import com.snatik.matches.common.Shared;
import com.snatik.matches.themes.Themes;
import com.snatik.matches.utils.Utils;

/**
 * Before game starts, engine build new board
 * 在游戏开始之前，引擎构建新的棋盘
 * 
 * @author sromku
 */
public class BoardArrangment {

	// like {0-2, 4-3, 1-5}
	public Map<Integer, Integer> pairs;
	// like {0-mosters_20, 1-mosters_12, 2-mosters_20, ...}
	public Map<Integer, String> tileUrls;

	public int [] mapPosId;
	public   static  int rubbishGame=0;
	public static	int originGame=1;
	public	int gameMode=originGame;
	/**
	 * 
	 * @param id
	 *            The id is the number between 0 and number of possible tiles of
	 *            this theme
	 *            id是介于0和的可能的砖块数量之间的数字
	 * *这个主题 的砖块们
	 * @return The Bitmap of the tile
	 */
	public Bitmap getTileBitmap(int id, int size) {
		String string = tileUrls.get(id);
		//第几个砖块
		//id 就是第几个位置的砖块
		if (string.contains(Themes.URI_DRAWABLE)) {
			String drawableResourceName = string.substring(Themes.URI_DRAWABLE.length());
			int drawableResourceId = Shared.context.getResources().getIdentifier(drawableResourceName, "drawable", Shared.context.getPackageName());
			Bitmap bitmap = Utils.scaleDown(drawableResourceId, size, size);
			return Utils.crop(bitmap, size, size);
		}
		return null;
	}

	int getDrawableResourceId(String  stringUrl){
		if (stringUrl.contains(Themes.URI_DRAWABLE)) {
			String drawableResourceName = stringUrl.substring(Themes.URI_DRAWABLE.length());
			int drawableResourceId = Shared.context.getResources().getIdentifier(drawableResourceName, "drawable", Shared.context.getPackageName());
			return drawableResourceId;
			//Bitmap bitmap = Utils.scaleDown(drawableResourceId, size, size);
			//return Utils.crop(bitmap, size, size);
		}
		return  -1;
	}

//要做成垃圾的游戏的话 因为两个id 不一样 要不一个 1 一个 10001  就可以配对
	public boolean isPair(int id1, int id2) {
		if(gameMode==rubbishGame){
			return isRubbishPair(id1, id2) ;
		}
		return isOriginPair(id1, id2) ;
		//Integer integer = pairs.get(id1);
		//if (integer == null) {
		//	// TODO Report this bug!!!
		//	return false;
		//}
		//return integer.equals(id2);
	}

	public boolean isOriginPair(int pos1, int pos2) {
		Integer integer = pairs.get(pos1);
		if (integer == null) {
			// TODO Report this bug!!!
			return false;
		}
		return integer.equals(pos2);
	}

	public boolean isRubbishPair(int pos1, int pos2) {
		int id1 = mapPosId[pos1];
		int id2 = mapPosId[pos2];
		Log.i("pos1", "isRubbishPair: "+pos1);
		Log.i("pos2", "isRubbishPair: "+pos2);
		Log.i("id1", "isRubbishPair: "+id1);
		Log.i("id2", "isRubbishPair: "+id2);
		return  Math.abs(id1-id2)==10000;
		//return  id1==id2;
		//Integer integer = pairs.get(id1);
		//if (integer == null) {
		//	// TODO Report this bug!!!
		//	return false;
		//}
		//return integer.equals(id2);
	}

}
