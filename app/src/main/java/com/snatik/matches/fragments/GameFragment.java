package com.snatik.matches.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//imporcom.example.whatrubbish.R;R;
import com.example.whatrubbish.R;
import com.example.whatrubbish.activity.AbsActivity;
import com.example.whatrubbish.activity.Rotate3dActivity;
import com.example.whatrubbish.util.ActivityUtil;
import com.snatik.matches.common.Shared;
import com.snatik.matches.events.engine.FlipDownCardsEvent;
import com.snatik.matches.events.engine.GameWonEvent;
import com.snatik.matches.events.engine.HidePairCardsEvent;
import com.snatik.matches.model.Game;
import com.snatik.matches.ui.BoardView;
import com.snatik.matches.ui.PopupManager;
import com.snatik.matches.utils.Clock;
import com.snatik.matches.utils.Clock.OnTimerCount;
import com.snatik.matches.utils.FontLoader;
import com.snatik.matches.utils.FontLoader.Font;

public class GameFragment extends BaseFragment {

	private BoardView mBoardView;
	private TextView mTime;
	private ImageView mTimeImage;
	private LinearLayout ads;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup view = (ViewGroup) inflater.inflate(R.layout.game_fragment, container, false);
		view.setClipChildren(false);
		((ViewGroup)view.findViewById(R.id.game_board)).setClipChildren(false);
		mTime = (TextView) view.findViewById(R.id.time_bar_text);
		mTimeImage = (ImageView) view.findViewById(R.id.time_bar_image);
		FontLoader.setTypeface(Shared.context, new TextView[] {mTime}, Font.GROBOLD);
		mBoardView = BoardView.fromXml(getActivity().getApplicationContext(), view);
		FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.game_container);
		frameLayout.addView(mBoardView);
		frameLayout.setClipChildren(false);

		// build board
		buildBoard();
		Shared.eventBus.listen(FlipDownCardsEvent.TYPE, this);
		Shared.eventBus.listen(HidePairCardsEvent.TYPE, this);
		Shared.eventBus.listen(GameWonEvent.TYPE, this);
		
		return view;
	}
	
	@Override
	public void onDestroy() {
		Shared.eventBus.unlisten(FlipDownCardsEvent.TYPE, this);
		Shared.eventBus.unlisten(HidePairCardsEvent.TYPE, this);
		Shared.eventBus.unlisten(GameWonEvent.TYPE, this);
		super.onDestroy();
	}

	private void buildBoard() {
		Game game = Shared.engine.getActiveGame();
		int time = game.boardConfiguration.time;
		setTime(time);
		mBoardView.setBoard(game);
		
		startClock(time);
	}
	
	private void setTime(int time) {
		int min = time / 60;
		int sec = time - min*60;
		mTime.setText(" " + String.format("%02d", min) + ":" + String.format("%02d", sec));
	}

	private void startClock(int sec) {
		Clock clock = Clock.getInstance();
		clock.startTimer(sec*1000, 1000, new OnTimerCount() {
			
			@Override
			public void onTick(long millisUntilFinished) {
				setTime((int) (millisUntilFinished/1000));
			}
			
			@Override
			public void onFinish() {
				setTime(0);
			}
		});
	}

	@Override
	public void onEvent(GameWonEvent event) {
		mTime.setVisibility(View.GONE);
		mTimeImage.setVisibility(View.GONE);
		//加一张卡片如何
		//Activity.
		PopupManager.showPopupWon(event.gameState);


		int waitMs=3000;
		//int waitMs=1000;
		new Handler(message -> {
			//ActivityUtil.startActivity(getActivity(), AbsActivity.class);
			ActivityUtil.startActivity(getActivity(), Rotate3dActivity.class);
			return false;
		}).sendEmptyMessageDelayed(0, waitMs); // 延迟3秒

	}

	@Override
	public void onEvent(FlipDownCardsEvent event) {
		mBoardView.flipDownAll();
		//一共就两张 算是全部翻转回去吗 还是说后面难度增加了 是会有多张可以翻开的
	}

	@Override
	public void onEvent(HidePairCardsEvent event) {
		mBoardView.hideCards(event.id1, event.id2);
	}

}
