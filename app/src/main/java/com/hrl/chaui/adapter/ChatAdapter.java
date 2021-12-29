package com.hrl.chaui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
//import com.example.smlj.R;
import com.example.whatrubbish.R;
import com.hrl.chaui.activity.ChatActivity;
import com.hrl.chaui.bean.Message;
import com.hrl.chaui.bean.MsgType;
import com.hrl.chaui.bean.TextMsgBody;

import java.util.List;

public class ChatAdapter extends BaseQuickAdapter<Message,BaseViewHolder> {


    private static final int TYPE_SEND_TEXT=1;
    private static final int TYPE_RECEIVE_TEXT=2;
    private static final int TYPE_SEND_IMAGE=3;
    private static final int TYPE_RECEIVE_IMAGE=4;
    private static final int TYPE_SEND_VIDEO=5;
    private static final int TYPE_RECEIVE_VIDEO=6;
    private static final int TYPE_SEND_FILE=7;
    private static final int TYPE_RECEIVE_FILE=8;
    private static final int TYPE_SEND_AUDIO=9;
    private static final int TYPE_RECEIVE_AUDIO=10;

    private static final int SEND_TEXT = R.layout.item_text_send;
    private static final int RECEIVE_TEXT = R.layout.item_text_receive;






    public ChatAdapter(Context context, List<Message> data) {
        super(data);
        setMultiTypeDelegate(new MultiTypeDelegate<Message>() {
            @Override
            protected int getItemType(Message entity) {
              boolean isSend = entity.getSenderId().equals(ChatActivity.mSenderId);
               if (MsgType.TEXT==entity.getMsgType()) {
                    return isSend ? TYPE_SEND_TEXT : TYPE_RECEIVE_TEXT;
                }else if(MsgType.IMAGE==entity.getMsgType()){
                     return isSend ? TYPE_SEND_IMAGE : TYPE_RECEIVE_IMAGE;
                }else if(MsgType.VIDEO==entity.getMsgType()){
                     return isSend ? TYPE_SEND_VIDEO : TYPE_RECEIVE_VIDEO;
                 }else if(MsgType.FILE==entity.getMsgType()){
                     return isSend ? TYPE_SEND_FILE : TYPE_RECEIVE_FILE;
                 }else if(MsgType.AUDIO==entity.getMsgType()){
                     return isSend ? TYPE_SEND_AUDIO : TYPE_RECEIVE_AUDIO;
                 }
                return 0;
            }
        });
        getMultiTypeDelegate() .registerItemType(TYPE_SEND_TEXT, SEND_TEXT)
                .registerItemType(TYPE_RECEIVE_TEXT,RECEIVE_TEXT);

    }

    @Override
    protected void convert(BaseViewHolder helper, Message item) {
        setContent(helper, item);


    }



        private void setContent(BaseViewHolder helper, Message item) {
                if (item.getMsgType().equals(MsgType.TEXT)) {
                    TextMsgBody msgBody = (TextMsgBody) item.getBody();
                    helper.setText(R.id.chat_item_content_text, msgBody.getMessage());
                }

    }




}
