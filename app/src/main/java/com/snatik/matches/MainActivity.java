package com.snatik.matches;


import static com.snatik.matches.themes.Themes.URI_DRAWABLE;

import android.graphics.Bitmap;
import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;

import com.example.whatrubbish.R;
import com.example.whatrubbish.entity.Rubbish;
import com.example.whatrubbish.entity.RubbishType;
import com.snatik.matches.common.Shared;
import com.snatik.matches.engine.Engine;
import com.snatik.matches.engine.ScreenController;
import com.snatik.matches.engine.ScreenController.Screen;
import com.snatik.matches.events.EventBus;
import com.snatik.matches.events.ui.BackGameEvent;
import com.snatik.matches.ui.PopupManager;
import com.snatik.matches.utils.Utils;

import java.util.ArrayList;
import java.util.List;

//记忆游戏
public class MainActivity extends FragmentActivity {

	private ImageView mBackgroundImage;

	void initImgsToBean(int cnt, String typeStr, int typeId,
						List<Rubbish> rubbishes, List<RubbishType>rubbishTypes) {
		for (int i = 0; i <= cnt; i++) {
			//String  imgUrl=URI_DRAWABLE + String.format("dry_%d", i);
			//根据url 获得 bitmap
			//有垃圾桶的 有垃圾的
			String imgUrlRubbish = URI_DRAWABLE + String.format("%s_%d", typeStr, i);
			String imgUrlTrashBin = URI_DRAWABLE + String.format("bin_%s", typeStr);
			Rubbish rubbish = new Rubbish();
			rubbish.setTypeId((long) typeId);
			rubbish.setImgUrl(imgUrlRubbish);
			//Rubbish build = Rubbish.builder().typeId((long) typeId).imgUrl(imgUrlRubbish).build();
			rubbishes.add(rubbish);
			RubbishType rubbishType = new RubbishType();
			rubbishType.setId((long) typeId);
			rubbishType.setImgUrl(imgUrlTrashBin);
			//RubbishType build1 = RubbishType.builder().id((long) typeId).imgUrl(imgUrlTrashBin).build();
			rubbishTypes.add(rubbishType);
			//rubbishes.add(new MRubbish(rubId,imgUrl,10000+rubId));
			//rubbishes.add(new MRubbish(rubId,imgUrl,offset+rubId));
			////trashBins.add(new MTrashBin(rubId+offset,imgUrl,offset+rubId));
			//trashBins.add(new MTrashBin(rubId+offset,imgUrlTrashBin,offset+rubId));

			//MRubbish mRubbish = new MRubbish(rubId, imgUrl, typeId);
			//MRubbish mRubbish = new MRubbish(rubId, imgUrl, typeId+offset);
			//MRubbish mRubbish = new MRubbish(typeId, imgUrlRubbish, typeId+offset);
			//mRubbish.setImgUrlTrashBin(imgUrlTrashBin);
			////mRubbish.se
			////rubbishes.add(new MRubbish(rubId, imgUrl, typeId));
			//rubbishes.add(mRubbish);
			////trashBins.add(new MTrashBin(typeId,imgUrlTrashBin,offset+rubId));
			//trashBins.add(new MTrashBin(typeId + offset, imgUrlTrashBin, offset + rubId));
			//MTrashBin 的 ttpe id 是不需要的吧
			//rubId++;
		}
	}

	void initRubbishes(){
		List<Rubbish> rubbishes=new ArrayList<>();
		List<RubbishType>rubbishTypes=new ArrayList<>();
		initImgsToBean(9, "dry", 1,rubbishes,rubbishTypes);
		initImgsToBean(7, "harmful", 2,rubbishes,rubbishTypes);
		initImgsToBean(15, "recycle", 3,rubbishes,rubbishTypes);
		initImgsToBean(14, "wet", 4,rubbishes,rubbishTypes);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Shared.context = getApplicationContext();
		Shared.engine = Engine.getInstance();
		Shared.eventBus = EventBus.getInstance();

		//setContentView(R.layout.activity_main);
		setContentView(R.layout.activity_main_memo);
		mBackgroundImage = (ImageView) findViewById(R.id.background_image);

		Shared.activity = this;
		Shared.engine.start();
		Shared.engine.setBackgroundImageView(mBackgroundImage);

		// set background
		setBackgroundImage();

		// set menu
		ScreenController.getInstance().openScreen(Screen.MENU);


	}

	@Override
	protected void onDestroy() {
		Shared.engine.stop();
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		if (PopupManager.isShown()) {
			PopupManager.closePopup();
			if (ScreenController.getLastScreen() == Screen.GAME) {
				Shared.eventBus.notify(new BackGameEvent());
			}
		} else if (ScreenController.getInstance().onBack()) {
			super.onBackPressed();
		}
	}

	private void setBackgroundImage() {
		Bitmap bitmap = Utils.scaleDown(R.drawable.background, Utils.screenWidth(), Utils.screenHeight());
		bitmap = Utils.crop(bitmap, Utils.screenHeight(), Utils.screenWidth());
		bitmap = Utils.downscaleBitmap(bitmap, 2);
		mBackgroundImage.setImageBitmap(bitmap);
	}

}
