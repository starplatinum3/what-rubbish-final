package com.hrl.chaui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//import com.hrl.chaui.R;
//import com.example.smlj.R;
import com.example.whatrubbish.R;
import com.hrl.chaui.util.LogUtil;
import com.hrl.chaui.widget.SetPermissionDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public class SplashActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try{
                    requestPermisson();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, 1);
        LogUtil.d(new String(Character.toChars(0x1F60E)));
    }
    SetPermissionDialog mSetPermissionDialog;

    private void requestPermisson(){
        RxPermissions rxPermission = new RxPermissions(this);
        rxPermission
                .request(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,//存储权限
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO
                )
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Log.d("ChatActivity", "accept: startActivity ChatActivity");
                            startActivity(new Intent(SplashActivity.this,ChatActivity.class));
                            finish();
                        } else {
                            Log.d("SetPermissionDialog", "accept: SetPermissionDialog ChatActivity");
                            mSetPermissionDialog = new SetPermissionDialog(SplashActivity.this);
                            mSetPermissionDialog.show();
                            mSetPermissionDialog.setConfirmCancelListener(new SetPermissionDialog.OnConfirmCancelClickListener() {
                                @Override
                                public void onLeftClick() {

                                    finish();
                                }

                                @Override
                                public void onRightClick() {

                                    finish();
                                }
                            });
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mSetPermissionDialog!=null){
            mSetPermissionDialog.dismiss();

        }
    }
}
