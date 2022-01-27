package com.snatik.matches.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.LinearLayout;

import com.example.whatrubbish.R;
import com.snatik.matches.common.Shared;
import com.snatik.matches.events.ui.FlipCardEvent;
import com.snatik.matches.model.BoardArrangment;
import com.snatik.matches.model.BoardConfiguration;
import com.snatik.matches.model.Game;
import com.snatik.matches.utils.Utils;

public class BoardView extends LinearLayout {

	private LinearLayout.LayoutParams mRowLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	private LinearLayout.LayoutParams mTileLayoutParams;
	private int mScreenWidth;
	private int mScreenHeight;
	private BoardConfiguration mBoardConfiguration;
	private BoardArrangment mBoardArrangment;
	private Map<Integer, TileView> mViewReference;
	private List<Integer> flippedUp = new ArrayList<Integer>();
	private boolean mLocked = false;
	private int mSize;

	public BoardView(Context context) {
		this(context, null);
	}

	public BoardView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		setOrientation(LinearLayout.VERTICAL);
		setGravity(Gravity.CENTER);
		int margin = getResources().getDimensionPixelSize(R.dimen.margine_top);
		int padding = getResources().getDimensionPixelSize(R.dimen.board_padding);
		mScreenHeight = getResources().getDisplayMetrics().heightPixels - margin - padding*2;
		mScreenWidth = getResources().getDisplayMetrics().widthPixels - padding*2 - Utils.px(20);
		mViewReference = new HashMap<Integer, TileView>();
		setClipToPadding(false);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
	}

	public static BoardView fromXml(Context context, ViewGroup parent) {
		//空的
		return (BoardView) LayoutInflater.from(context).inflate(R.layout.board_view, parent, false);
	}

	public void setBoard(Game game) {
		mBoardConfiguration = game.boardConfiguration;
		mBoardArrangment = game.boardArrangment;
		// calc prefered tiles in width and height
		int singleMargin = getResources().getDimensionPixelSize(R.dimen.card_margin);
		float density = getResources().getDisplayMetrics().density;
		singleMargin = Math.max((int) (1 * density), (int) (singleMargin - mBoardConfiguration.difficulty * 2 * density));
		int sumMargin = 0;
		for (int row = 0; row < mBoardConfiguration.numRows; row++) {
			sumMargin += singleMargin * 2;
		}
		int tilesHeight = (mScreenHeight - sumMargin) / mBoardConfiguration.numRows;
		int tilesWidth = (mScreenWidth - sumMargin) / mBoardConfiguration.numTilesInRow;
		mSize = Math.min(tilesHeight, tilesWidth);

		mTileLayoutParams = new LinearLayout.LayoutParams(mSize, mSize);
		mTileLayoutParams.setMargins(singleMargin, singleMargin, singleMargin, singleMargin);

		// build the ui
		buildBoard();
	}

	//G:\project\Android\memory-game\app\src\main\res\drawable-nodpi\animals_1.png
	/**
	 * Build the board
	 */
	private void buildBoard() {
		//这个是那几块东西吗 好多板子

		for (int row = 0; row < mBoardConfiguration.numRows; row++) {
			// add row
			addBoardRow(row);
		}

		setClipChildren(false);
	}

	private void addBoardRow(int rowNum) {

		LinearLayout linearLayout = new LinearLayout(getContext());
		linearLayout.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout.setGravity(Gravity.CENTER);
		//tile 砖块 就是那几个小牌子吧
		//这个垃圾桶对应了一个 pos 的 列表
		//pair 要找这个列表
		//我们没办法知道哪个是垃圾吧 但是一开始可以设置好吧
		//pos 对应了一个实体 拿出 rubbish 列表里面的实体，如果两个实体的rubId和 typeId 对应了
		//但是pos 可以是对调
		//map <pos ,Rub> 如果拿出来是null 说明不对 然后反着来一下 还是不对的话 就是没有配对了
		//Rub.typeID
		//pos 的位置 放着一个垃圾 还是id 是放实体还是 id？ id怎么分辨是什么
		//实体需要  多态，多态之后 分辨两个id 是不是 1===1000+1
		//那也不用多态 只要 获得两个id 是 相差10000
		for (int tile = 0; tile < mBoardConfiguration.numTilesInRow; tile++) {
			//就是一个pos 传递的就是pos 作为id
			addTile(rowNum * mBoardConfiguration.numTilesInRow + tile, linearLayout);
		}

		// add to this view
		addView(linearLayout, mRowLayoutParams);
		linearLayout.setClipChildren(false);
	}

	/**
	 *
	 * @param id  tile的位置
	 * @param parent
	 */
	private void addTile(final int id, ViewGroup parent) {
		final TileView tileView = TileView.fromXml(getContext(), parent);
		tileView.setLayoutParams(mTileLayoutParams);
		parent.addView(tileView);
		parent.setClipChildren(false);
		mViewReference.put(id, tileView);

		new AsyncTask<Void, Void, Bitmap>() {

			@Override
			protected Bitmap doInBackground(Void... params) {
				try{
					return mBoardArrangment.getTileBitmap(id, mSize);
				}catch (Exception e){
					Log.i("id", "doInBackground: "+id);
					return null;
				}

			}
			
			@Override
			protected void onPostExecute(Bitmap result) {
				tileView.setTileImage(result);
			}
		}.execute();
		
		tileView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//它翻了个底朝天 isFlippedDown
				if (!mLocked && tileView.isFlippedDown()) {
					tileView.flipUp();
					flippedUp.add(id);
					//传递的id 是什么
					if (flippedUp.size() == 2) {
						mLocked = true;
						//锁住了 其他的牌不能翻动了
					}
					Shared.eventBus.notify(new FlipCardEvent(id));
				}
			}
		});

		//翻转动画
		ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(tileView, "scaleX", 0.8f, 1f);
		scaleXAnimator.setInterpolator(new BounceInterpolator());
		ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(tileView, "scaleY", 0.8f, 1f);
		scaleYAnimator.setInterpolator(new BounceInterpolator());
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
		animatorSet.setDuration(500);
		tileView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		animatorSet.start();
	}

	public void flipDownAll() {
		for (Integer id : flippedUp) {
			mViewReference.get(id).flipDown();
		}
		flippedUp.clear();
		mLocked = false;
		//可以重新翻开了
	}

	public void hideCards(int id1, int id2) {
		animateHide(mViewReference.get(id1));
		animateHide(mViewReference.get(id2));
		flippedUp.clear();
		mLocked = false;
	}

	protected void animateHide(final TileView v) {
		ObjectAnimator animator = ObjectAnimator.ofFloat(v, "alpha", 0f);
		animator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				v.setLayerType(View.LAYER_TYPE_NONE, null);
				v.setVisibility(View.INVISIBLE);
			}
		});
		animator.setDuration(100);
		v.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		animator.start();
	}

}
