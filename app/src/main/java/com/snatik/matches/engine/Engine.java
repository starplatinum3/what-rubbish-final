package com.snatik.matches.engine;

import static com.snatik.matches.themes.Themes.URI_DRAWABLE;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.example.whatrubbish.R;
import com.example.whatrubbish.entity.Rubbish;
import com.example.whatrubbish.entity.RubbishType;
import com.snatik.matches.common.Memory;
import com.snatik.matches.common.Music;
import com.snatik.matches.common.Shared;
import com.snatik.matches.engine.ScreenController.Screen;
import com.snatik.matches.events.EventObserverAdapter;
import com.snatik.matches.events.engine.FlipDownCardsEvent;
import com.snatik.matches.events.engine.GameWonEvent;
import com.snatik.matches.events.engine.HidePairCardsEvent;
import com.snatik.matches.events.ui.BackGameEvent;
import com.snatik.matches.events.ui.DifficultySelectedEvent;
import com.snatik.matches.events.ui.FlipCardEvent;
import com.snatik.matches.events.ui.NextGameEvent;
import com.snatik.matches.events.ui.ResetBackgroundEvent;
import com.snatik.matches.events.ui.StartEvent;
import com.snatik.matches.events.ui.ThemeSelectedEvent;
import com.snatik.matches.model.BoardArrangment;
import com.snatik.matches.model.BoardConfiguration;
import com.snatik.matches.model.Game;
import com.snatik.matches.model.GameState;
import com.snatik.matches.model.MRubbish;
import com.snatik.matches.model.MTrashBin;
import com.snatik.matches.model.Tile;
import com.snatik.matches.themes.Theme;
import com.snatik.matches.themes.Themes;
import com.snatik.matches.ui.PopupManager;
import com.snatik.matches.utils.Clock;
import com.snatik.matches.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Engine extends EventObserverAdapter {

    private static Engine mInstance = null;
    private Game mPlayingGame = null;
    private int mFlippedId = -1;
    private int mToFlip = -1;
    private ScreenController mScreenController;
    private Theme mSelectedTheme;
    private ImageView mBackgroundImage;
    private Handler mHandler;

    private Engine() {
        mScreenController = ScreenController.getInstance();
        mHandler = new Handler();
    }

    public static Engine getInstance() {
        if (mInstance == null) {
            mInstance = new Engine();
        }
        return mInstance;
    }

    public void start() {
        Shared.eventBus.listen(DifficultySelectedEvent.TYPE, this);
        Shared.eventBus.listen(FlipCardEvent.TYPE, this);
        Shared.eventBus.listen(StartEvent.TYPE, this);
        Shared.eventBus.listen(ThemeSelectedEvent.TYPE, this);
        Shared.eventBus.listen(BackGameEvent.TYPE, this);
        Shared.eventBus.listen(NextGameEvent.TYPE, this);
        Shared.eventBus.listen(ResetBackgroundEvent.TYPE, this);
    }

    public void stop() {
        mPlayingGame = null;
        mBackgroundImage.setImageDrawable(null);
        mBackgroundImage = null;
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;

        Shared.eventBus.unlisten(DifficultySelectedEvent.TYPE, this);
        Shared.eventBus.unlisten(FlipCardEvent.TYPE, this);
        Shared.eventBus.unlisten(StartEvent.TYPE, this);
        Shared.eventBus.unlisten(ThemeSelectedEvent.TYPE, this);
        Shared.eventBus.unlisten(BackGameEvent.TYPE, this);
        Shared.eventBus.unlisten(NextGameEvent.TYPE, this);
        Shared.eventBus.unlisten(ResetBackgroundEvent.TYPE, this);

        mInstance = null;
    }

    @Override
    public void onEvent(ResetBackgroundEvent event) {
        Drawable drawable = mBackgroundImage.getDrawable();
        if (drawable != null) {
            ((TransitionDrawable) drawable).reverseTransition(2000);
        } else {
            new AsyncTask<Void, Void, Bitmap>() {

                @Override
                protected Bitmap doInBackground(Void... params) {
                    //缩小
                    //缩小到屏幕的大小吗
                    Bitmap bitmap = Utils.scaleDown(R.drawable.background, Utils.screenWidth(), Utils.screenHeight());
                    return bitmap;
                }

                protected void onPostExecute(Bitmap bitmap) {
                    mBackgroundImage.setImageBitmap(bitmap);
                }

                ;

            }.execute();
        }
    }

    @Override
    public void onEvent(StartEvent event) {
        mScreenController.openScreen(Screen.THEME_SELECT);
    }

    @Override
    public void onEvent(NextGameEvent event) {
        PopupManager.closePopup();
        int difficulty = mPlayingGame.boardConfiguration.difficulty;
        if (mPlayingGame.gameState.achievedStars == 3 && difficulty < 6) {
            difficulty++;
        }
        Shared.eventBus.notify(new DifficultySelectedEvent(difficulty));
    }

    @Override
    public void onEvent(BackGameEvent event) {
        PopupManager.closePopup();
        mScreenController.openScreen(Screen.DIFFICULTY);
    }

    @Override
    public void onEvent(ThemeSelectedEvent event) {
        mSelectedTheme = event.theme;
        mScreenController.openScreen(Screen.DIFFICULTY);
        AsyncTask<Void, Void, TransitionDrawable> task = new AsyncTask<Void, Void, TransitionDrawable>() {

            @Override
            protected TransitionDrawable doInBackground(Void... params) {
                Bitmap bitmap = Utils.scaleDown(R.drawable.background, Utils.screenWidth(), Utils.screenHeight());
                Bitmap backgroundImage = Themes.getBackgroundImage(mSelectedTheme);
                backgroundImage = Utils.crop(backgroundImage, Utils.screenHeight(), Utils.screenWidth());
                Drawable backgrounds[] = new Drawable[2];
                backgrounds[0] = new BitmapDrawable(Shared.context.getResources(), bitmap);
                backgrounds[1] = new BitmapDrawable(Shared.context.getResources(), backgroundImage);
                TransitionDrawable crossfader = new TransitionDrawable(backgrounds);
                return crossfader;
            }

            @Override
            protected void onPostExecute(TransitionDrawable result) {
                super.onPostExecute(result);
                mBackgroundImage.setImageDrawable(result);
                result.startTransition(2000);
            }
        };
        task.execute();
    }

    @Override
    public void onEvent(DifficultySelectedEvent event) {
        mFlippedId = -1;
        mPlayingGame = new Game();
        mPlayingGame.boardConfiguration = new BoardConfiguration(event.difficulty);
        mPlayingGame.theme = mSelectedTheme;
        mToFlip = mPlayingGame.boardConfiguration.numTiles;

        // arrange board
        //arrangeBoard();

        arrangeRubbishBoard();
        // start the screen
        mScreenController.openScreen(Screen.GAME);
    }

    private void arrangeBoard() {
        BoardConfiguration boardConfiguration = mPlayingGame.boardConfiguration;
        BoardArrangment boardArrangment = new BoardArrangment();

        // build pairs
        // result {0,1,2,...n} // n-number of tiles
        List<Integer> ids = new ArrayList<Integer>();
        for (int i = 0; i < boardConfiguration.numTiles; i++) {
            ids.add(i);
        }
        // shuffle
        // result {4,10,2,39,...}
        Collections.shuffle(ids);
        //现在他的顺序是显示的顺序吗,不是吧 他的数字是显示的位置
        //但是顺序应该就是相邻的长得一样

        // place the board
        List<String> tileImageUrls = mPlayingGame.theme.tileImageUrls;
        Collections.shuffle(tileImageUrls);
        boardArrangment.pairs = new HashMap<Integer, Integer>();
        boardArrangment.tileUrls = new HashMap<Integer, String>();
        int j = 0;
        for (int i = 0; i < ids.size(); i++) {
            if (i + 1 < ids.size()) {
                // {4,10}, {2,39}, ...
                //前一个和后一个配对 他们是长得一样的，但是数字有差别
                //所以数字表示的是他们放的位置吧
                boardArrangment.pairs.put(ids.get(i), ids.get(i + 1));
                // {10,4}, {39,2}, ...
                boardArrangment.pairs.put(ids.get(i + 1), ids.get(i));
                // {4,
                boardArrangment.tileUrls.put(ids.get(i), tileImageUrls.get(j));
                //我们只要在放图片的时候  改变一下 让他放的是两个配对的 垃圾和垃圾桶就好了
                boardArrangment.tileUrls.put(ids.get(i + 1), tileImageUrls.get(j));
                i++;
                j++;
            }
        }

        mPlayingGame.boardArrangment = boardArrangment;
    }

    void initImgsToBean(int cnt, String typeStr, int typeId,
                        List<Rubbish >rubbishes,List<RubbishType>rubbishTypes) {
        for (int i = 0; i <= cnt; i++) {
            //String  imgUrl=URI_DRAWABLE + String.format("dry_%d", i);
            //根据url 获得 bitmap
            //有垃圾桶的 有垃圾的
            String imgUrlRubbish = URI_DRAWABLE + String.format("%s_%d", typeStr, i);
            String imgUrlTrashBin = URI_DRAWABLE + String.format("bin_%s", typeStr);
            Rubbish build = Rubbish.builder().typeId((long) typeId).imgUrl(imgUrlRubbish).build();
            rubbishes.add(build);
            RubbishType build1 = RubbishType.builder().id((long) typeId).imgUrl(imgUrlTrashBin).build();
            rubbishTypes.add(build1);
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
            rubId++;
        }
    }

    void initImgs(int cnt, String typeStr, int typeId) {
        for (int i = 0; i <= cnt; i++) {
            //String  imgUrl=URI_DRAWABLE + String.format("dry_%d", i);
            //根据url 获得 bitmap
            //有垃圾桶的 有垃圾的
            String imgUrl = URI_DRAWABLE + String.format("%s_%d", typeStr, i);
            String imgUrlTrashBin = URI_DRAWABLE + String.format("bin_%s", typeStr);
            //rubbishes.add(new MRubbish(rubId,imgUrl,10000+rubId));
            //rubbishes.add(new MRubbish(rubId,imgUrl,offset+rubId));
            ////trashBins.add(new MTrashBin(rubId+offset,imgUrl,offset+rubId));
            //trashBins.add(new MTrashBin(rubId+offset,imgUrlTrashBin,offset+rubId));

            //MRubbish mRubbish = new MRubbish(rubId, imgUrl, typeId);
            //MRubbish mRubbish = new MRubbish(rubId, imgUrl, typeId+offset);
            MRubbish mRubbish = new MRubbish(typeId, imgUrl, typeId+offset);
            mRubbish.setImgUrlTrashBin(imgUrlTrashBin);
            //mRubbish.se
            //rubbishes.add(new MRubbish(rubId, imgUrl, typeId));
            rubbishes.add(mRubbish);
            //trashBins.add(new MTrashBin(typeId,imgUrlTrashBin,offset+rubId));
            trashBins.add(new MTrashBin(typeId + offset, imgUrlTrashBin, offset + rubId));
            //MTrashBin 的 ttpe id 是不需要的吧
            rubId++;
        }
    }

    List<MRubbish> rubbishes = new ArrayList<>();
    List<MTrashBin> trashBins = new ArrayList<>();


    int offset = 10000;
    int rubId = 0;

    private void arrangeRubbishBoard() {
        BoardConfiguration boardConfiguration = mPlayingGame.boardConfiguration;
        BoardArrangment boardArrangment = new BoardArrangment();

        // build pairs
        // result {0,1,2,...n} // n-number of tiles
        List<Integer> posLst = new ArrayList<Integer>();
        List<Tile> tiles = new ArrayList<>();
        //List<MRubbish>rubbishes=new ArrayList<>();
        //List<MTrashBin>trashBins=new ArrayList<>();
        //theme.tileImageUrls.add(URI_DRAWABLE + String.format("animals_%d", i));
        List<String> tileImageUrls = mPlayingGame.theme.tileImageUrls;
        String rubTypeStr = "dry";
        String URI_DRAWABLE = "drawable://";
        //全部加载 然后打乱一个 列表
        //0--9 dry
        //Trash
        int offset = 10000;
        int rubId = 0;
        initImgs(9, "dry", 1);
        initImgs(7, "harmful", 2);
        initImgs(15, "recycle", 3);
        initImgs(14, "wet", 4);
        //for (int i = 0; i <=9 ; i++) {
        //	String  imgUrl=URI_DRAWABLE + String.format("dry_%d", i);
        //	rubbishes.add(new MRubbish(rubId,imgUrl,10000+rubId));
        //	trashBins.add(new MTrashBin(rubId+offset,imgUrl,offset+rubId));
        //	//MTrashBin 的 ttpe id 是不需要的吧
        //	rubId++;
        //}


        //for (int i = 0; i <=7 ; i++) {
        //
        //	String  imgUrl=URI_DRAWABLE + String.format("harmful_%d", i);
        //	rubbishes.add(new MRubbish(rubId,imgUrl,10000+rubId));
        //	trashBins.add(new MTrashBin(rubId+offset,imgUrl,offset+rubId));
        //	//MTrashBin 的 ttpe id 是不需要的吧
        //	//rubbishes.add(new MRubbish(i,imgUrl,10000+i));
        //	//trashBins.add(new MTrashBin(i+offset,imgUrl,offset+i));
        //}

        //type 一共四种
        //但是这里的id 不是类型 而是他的位置吗
        //他们要一样长度的
        //for (int i = 0; i < boardConfiguration.numTiles/2; i++) {
        for (int i = 0; i < boardConfiguration.numTiles; i++) {
            //ids.add(i);
            //e(int id, String tileImageUrl) {
            //tiles.add(new Tile(i,))
            //从 rubbish 取出那么多
            posLst.add(i);
        }
        // shuffle
        // result {4,10,2,39,...}
        Collections.shuffle(posLst);
        Collections.shuffle(rubbishes);
        //现在他的顺序是显示的顺序吗,不是吧 他的数字是显示的位置
        //但是顺序应该就是相邻的长得一样

        // place the board
        //List<String> tileImageUrls = mPlayingGame.theme.tileImageUrls;
        //Collections.shuffle(tileImageUrls);
        boardArrangment.pairs = new HashMap<Integer, Integer>();
        boardArrangment.tileUrls = new HashMap<Integer, String>();
        int j = 0;
        int[] mapPosId = new int[222];
        for (int i = 0; i < posLst.size(); i++) {
            if (i + 1 < posLst.size()) {
                // {4,10}, {2,39}, ...
                //前一个和后一个配对 他们是长得一样的，但是数字有差别
                //所以数字表示的是他们放的位置吧
                //去获得他们摆出来的pos
                //这个是根据 pos 配对的 但是我们要根据垃圾的分类配对 可能有很多的牌子是一样的垃圾桶
                boardArrangment.pairs.put(posLst.get(i), posLst.get(i + 1));
                //可以放 Rubbish typeId
                // {10,4}, {39,2}, ...
                boardArrangment.pairs.put(posLst.get(i + 1), posLst.get(i));
                //应该不是只是和一个配对的
                // {4,
                //boardArrangment.tileUrls.put(posLst.get(i), tileImageUrls.get(j));
                //图片配置在这里  但是逻辑在上面 pairs
                //会获得这个 pos 的值吗
                //pos 和 id 的map
                Integer pos0 = posLst.get(i);
                Integer pos1 = posLst.get(i + 1);
                MRubbish mRubbishPos0 = rubbishes.get(j);
                //他的id 放在这个类里面吧
                //MRubbish mRubbishPos0 = rubbishes.get(j);
                MTrashBin mTrashBinPos1 = trashBins.get(j);
                //mapPosId[pos0]=mRubbishPos0.getId();
                //mapPosId[pos1]= mTrashBinPos1.getId();

                int typeId = mRubbishPos0.getTypeId();
                int trashBinPos1Id = mTrashBinPos1.getId();
                //mapPosId[pos0] = mRubbishPos0.getTypeId();
                //mapPosId[pos1] = mTrashBinPos1.getId();

                mapPosId[pos0] = mRubbishPos0.getId();
                mapPosId[pos1] = mRubbishPos0.getTypeId();
                //Log.i("map two ", "arrangeRubbishBoard: mRubbishPos0.getId( "
                //		+mRubbishPos0.getId()+" mTrashBinPos1.getId() "+mTrashBinPos1.getId());
                Log.i("map two ", "arrangeRubbishBoard: typeId  "
                        + typeId + " trashBinPos1Id " + trashBinPos1Id);

                //boardArrangment.tileUrls.put(posLst.get(i), rubbishes.get(j).getImgUrl());
                ////怎么让这两个位置配置一样呢 有对应的
                ////只要是 j来个 随机 对应就行吧
                ////或者先写个map  内部变量
                ////我们只要在放图片的时候  改变一下 让他放的是两个配对的 垃圾和垃圾桶就好了
                ////下面这个比如是垃圾的 10000+1·
                ////boardArrangment.tileUrls.put(posLst.get(i + 1), tileImageUrls.get(j));
                //boardArrangment.tileUrls.put(posLst.get(i + 1), trashBins.get(j).getImgUrl());

                boardArrangment.tileUrls.put(posLst.get(i), mRubbishPos0.getImgUrl());
                boardArrangment.tileUrls.put(posLst.get(i + 1), mRubbishPos0.getImgUrlTrashBin());
                i++;
                j++;
            }
        }

        boardArrangment.mapPosId = mapPosId;
        boardArrangment.gameMode = BoardArrangment.rubbishGame;
        mPlayingGame.boardArrangment = boardArrangment;
    }

    @Override
    public void onEvent(FlipCardEvent event) {
        // Log.i("my_tag", "Flip: " + event.id);
        int id = event.id;
        //传过来的 动物的id
        if (mFlippedId == -1) {
            mFlippedId = id;
            //刚才已经翻过来的
            //这个是记录刚才翻过的 。因为一共翻两张 记录的翻过的第一张
            // Log.i("my_tag", "Flip: mFlippedId: " + event.id);
        } else {
            if (mPlayingGame.boardArrangment.isPair(mFlippedId, id)) {
                // Log.i("my_tag", "Flip: is pair: " + mFlippedId + ", " + id);
                // send event - hide id1, id2
                //配对的话 两个就消失了
                Shared.eventBus.notify(new HidePairCardsEvent(mFlippedId, id), 1000);
                // play music
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Music.playCorrent();
                    }
                }, 1000);
                mToFlip -= 2;
                //需要配对的减少2
                if (mToFlip == 0) {
                    //结束了
                    int passedSeconds = (int) (Clock.getInstance().getPassedTime() / 1000);
                    Clock.getInstance().pause();
                    int totalTime = mPlayingGame.boardConfiguration.time;
                    GameState gameState = new GameState();
                    mPlayingGame.gameState = gameState;
                    // remained seconds
                    gameState.remainedSeconds = totalTime - passedSeconds;
                    gameState.passedSeconds = passedSeconds;

                    // calc stars
                    if (passedSeconds <= totalTime / 2) {
                        gameState.achievedStars = 3;
                    } else if (passedSeconds <= totalTime - totalTime / 5) {
                        gameState.achievedStars = 2;
                    } else if (passedSeconds < totalTime) {
                        gameState.achievedStars = 1;
                    } else {
                        gameState.achievedStars = 0;
                    }

                    // calc score
                    gameState.achievedScore = mPlayingGame.boardConfiguration.difficulty * gameState.remainedSeconds * mPlayingGame.theme.id;

                    // save to memory
                    Memory.save(mPlayingGame.theme.id, mPlayingGame.boardConfiguration.difficulty, gameState.achievedStars);
                    Memory.saveTime(mPlayingGame.theme.id, mPlayingGame.boardConfiguration.difficulty, gameState.passedSeconds);


                    Shared.eventBus.notify(new GameWonEvent(gameState), 1200);
                }
            } else {
                //没有配对
                // Log.i("my_tag", "Flip: all down");
                // send event - flip all down
                //全部向下翻转 没有配对所以要全部翻转回去
                Shared.eventBus.notify(new FlipDownCardsEvent(), 1000);
            }
            //两张牌翻完，我们要设置回去原来的 两张都没有翻开的状态
            mFlippedId = -1;
            // Log.i("my_tag", "Flip: mFlippedId: " + mFlippedId);
        }
    }

    public Game getActiveGame() {
        return mPlayingGame;
    }

    public Theme getSelectedTheme() {
        return mSelectedTheme;
    }

    public void setBackgroundImageView(ImageView backgroundImage) {
        mBackgroundImage = backgroundImage;
    }
}
