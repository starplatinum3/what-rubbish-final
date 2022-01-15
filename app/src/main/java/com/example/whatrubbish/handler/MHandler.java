//package com.example.whatrubbish.handler;
//
//import android.os.Handler;
//import android.os.Looper;
//import android.os.Message;
//
//import androidx.annotation.NonNull;
//
//public class MHandler extends Handler {
//    public MHandler(@NonNull Looper looper) {
//        super(looper);
//    }
//
//    // 通过复写handlerMessage() 从而确定更新UI的操作
//    @Override
//    public void handleMessage(Message msg) {
//        // 根据不同线程发送过来的消息，执行不同的UI操作
//        // 根据 Message对象的what属性 标识不同的消息
//        switch (msg.what) {
//            case 1:
//                mTextView.setText("执行了线程1的UI操作");
//                break;
//            case 2:
//                mTextView.setText("执行了线程2的UI操作");
//                break;
//        }
//    }
//}