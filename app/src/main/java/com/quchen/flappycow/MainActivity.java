/**
 * Main Activity / Splashscreen with buttons.
 * 
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package com.quchen.flappycow;

import static com.snatik.matches.themes.Themes.URI_DRAWABLE;

import com.example.whatrubbish.Bus;
import com.example.whatrubbish.entity.Rubbish;
import com.example.whatrubbish.entity.RubbishType;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.example.games.basegameutils.BaseGameActivity;

import android.os.Bundle;
import android.content.SharedPreferences;
import android.widget.Toast;
import com.example.whatrubbish.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseGameActivity {
    
    /** Name of the SharedPreference that saves the medals */
    public static final String medaille_save = "medaille_save";
    
    /** Key that saves the medal */
    public static final String medaille_key = "medaille_key";
    
    public static final float DEFAULT_VOLUME = 0.3f;
    
    /** Volume for sound and music */
    public static float volume = DEFAULT_VOLUME;
    
    private StartscreenView view;

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
        Bus.rubbishes=rubbishes;
        Bus.rubbishTypes=rubbishTypes;
    }

  //public     GameViewRub gameViewRub;
  //
  //  public void startGame(){
  //      setContentView(gameViewRub);
  //  }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new StartscreenView(this);
        //gameViewRub=new GameViewRub(this);
        setContentView(view);
        Bus.context=getApplicationContext();
        setSocket();
        initRubbishes();
    }

    public GoogleApiClient getApiClient(){
        return mHelper.getApiClient();
    }
    
    public void login() {
        beginUserInitiatedSignIn();
    }
    
    public void logout() {
        signOut();
        view.setOnline(false);
        view.invalidate();
    }
    
    public void muteToggle() {
        if(volume != 0){
            volume = 0;
            view.setSpeaker(false);
        }else{
            volume = DEFAULT_VOLUME;
            view.setSpeaker(true);
        }
        view.invalidate();
    }
    
    /**
     * Fills the socket with the medals that have already been collected.
     */
    private void setSocket(){
        SharedPreferences saves = this.getSharedPreferences(medaille_save, 0);
        view.setSocket(saves.getInt(medaille_key, 0));
        view.invalidate();
    }

    /**
     * Updates the socket for the medals.
     */
    @Override
    protected void onResume() {
        super.onResume();
        setSocket();
    }

    @Override
    public void onSignInFailed() {
        Toast.makeText(this, "You're not logged in", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSignInSucceeded() {
        Toast.makeText(this, "You're logged in", Toast.LENGTH_SHORT).show();
        view.setOnline(true);
        view.invalidate();
        if(AccomplishmentBox.isOnline(this)){
            AccomplishmentBox.getLocal(this).submitScore(this, getApiClient());
        }
    }
    
}
