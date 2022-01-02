package com.example.trash_detective.Activity;

import android.app.ProgressDialog;
import android.content.Context;

public abstract class LoadingDialog {
    private static ProgressDialog mDialog;

    public static void show(Context context) {
        try{
            mDialog = new ProgressDialog(context);
            mDialog.setMessage("正在加载...");
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
        }finally {

        }
    }

    public static void dismiss(){
        mDialog.dismiss();
    }

    public static ProgressDialog getmDialog() {
        return mDialog;
    }
}
