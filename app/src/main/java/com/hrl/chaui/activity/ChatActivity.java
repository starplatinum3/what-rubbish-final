package com.hrl.chaui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.whatrubbish.Bus;
import com.example.whatrubbish.MainActivity;
import com.example.whatrubbish.R;
import com.example.whatrubbish.constant.ChatType;
import com.example.whatrubbish.constant.MessageInfoType;
import com.example.whatrubbish.constant.MessageTargetType;
import com.example.whatrubbish.databinding.ActivityChatBinding;
import com.example.whatrubbish.databinding.ActivityRegionSettingBinding;
import com.example.whatrubbish.db.AppDatabase;
import com.example.whatrubbish.im.SendInfo;
import com.example.whatrubbish.im.SendMsg;
import com.example.whatrubbish.util.HttpUtil;
import com.example.whatrubbish.util.JsonJavaUtil;
import com.example.whatrubbish.util.JsonUtil;
import com.example.whatrubbish.util.ThreadPoolFactory;
import com.example.whatrubbish.util.ToastUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hrl.chaui.adapter.ChatAdapter;
import com.hrl.chaui.bean.MsgBody;
import com.hrl.chaui.bean.MsgType;
import com.hrl.chaui.bean.Message;
//import com.hrl.chaui.R;
//import com.example.smlj.R;
import com.hrl.chaui.bean.MsgSendStatus;
import com.hrl.chaui.bean.TextMsgBody;
import com.hrl.chaui.util.ChatUiHelper;
//import com.hrl.chaui.util.PictureFileUtil;
import com.hrl.chaui.widget.MediaManager;
import com.hrl.chaui.widget.RecordButton;
import com.hrl.chaui.widget.StateButton;
//import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.val;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ChatActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.llContent)
    LinearLayout mLlContent;
    @BindView(R.id.rv_chat_list)
    RecyclerView mRvChat;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.bottom_layout)
    RelativeLayout mRlBottomLayout;//表情,添加底部布局
    @BindView(R.id.ivAdd)
    ImageView mIvAdd;
    @BindView(R.id.ivEmo)
    ImageView mIvEmo;
    @BindView(R.id.btn_send)
    StateButton mBtnSend;//发送按钮
    @BindView(R.id.ivAudio)
    ImageView mIvAudio;//录音图片
    @BindView(R.id.btnAudio)
    RecordButton mBtnAudio;//录音按钮
    @BindView(R.id.rlEmotion)
    LinearLayout mLlEmotion;//表情布局
    @BindView(R.id.llAdd)
    LinearLayout mLlAdd;//添加布局
    @BindView(R.id.swipe_chat)
    SwipeRefreshLayout mSwipeRefresh;//下拉刷新
    private ActivityChatBinding binding;

    private ChatAdapter mAdapter;
    public static final String mSenderId = "right";
    public static final String mTargetId = "left";
    public static final int REQUEST_CODE_IMAGE = 0000;
    public static final int REQUEST_CODE_VEDIO = 1111;
    public static final int REQUEST_CODE_FILE = 2222;
    //@BindView(R.id.common_toolbar_title)
    TextView common_toolbar_title;
    @BindView(R.id.common_titlebar)
    View common_titlebar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //common_titlebar
        //binding = ActivityChatBinding.inflate(getLayoutInflater());
        //LinearLayout root = binding.getRoot();
        //binding.commonTitleBar.commonToolbarTitle.setText("李薇薇");
        //binding.commonTitleBar.commonToolbarTitle.setText(Bus.nowFriendId);
        //binding.commonTitleBar.commonToolbarTitle.setText(Bus.curFriend.getNickname());
        //common_toolbar_title= root.findViewById(R.id.common_toolbar_title);

        initContent();
        //放在后面不行的
        //setContentView(R.layout.activity_chat);
    }


    private ImageView ivAudio;

    protected void initContent() {
        ButterKnife.bind(this);
        //ButterKnife 和 binding 是不是冲突的

        common_toolbar_title = common_titlebar.findViewById(R.id.common_toolbar_title);

        mAdapter = new ChatAdapter(this, new ArrayList<Message>());
        LinearLayoutManager mLinearLayout = new LinearLayoutManager(this);
        mRvChat.setLayoutManager(mLinearLayout);
        mRvChat.setAdapter(mAdapter);
        mSwipeRefresh.setOnRefreshListener(this);
        initChatUi();
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                final boolean isSend = mAdapter.getItem(position).getSenderId().equals(ChatActivity.mSenderId);
                if (ivAudio != null) {
                    if (isSend) {
                        //    ivAudio.setBackgroundResource(R.mipmap.audio_animation_list_right_3);
                    } else {
                        //    ivAudio.setBackgroundResource(R.mipmap.audio_animation_list_left_3);
                    }
                    ivAudio = null;
                    MediaManager.reset();
                } else {
                    ivAudio = view.findViewById(R.id.ivAudio);
                    MediaManager.reset();
                    if (isSend) {
                        ivAudio.setBackgroundResource(R.drawable.audio_animation_right_list);
                    } else {
                        // ivAudio.setBackgroundResource(R.drawable.audio_animation_left_list);
                    }
                    AnimationDrawable drawable = (AnimationDrawable) ivAudio.getBackground();
                    drawable.start();
//                     MediaManager.playSound(ChatActivity.this,((AudioMsgBody)mAdapter.getData().get(position).getBody()).getLocalPath(), new MediaPlayer.OnCompletionListener() {
//                        @Override
//                        public void onCompletion(MediaPlayer mp) {
//                             if (isSend){
//                                ivAudio.setBackgroundResource(R.mipmap.audio_animation_list_right_3);
//                            }else {
//                                ivAudio.setBackgroundResource(R.mipmap.audio_animation_list_left_3);
//                            }
//
//                            MediaManager.release();
//                         }
//                    });
                }
            }
        });

        if(mockDataYes==false){
            //if(mo)
            //common_toolbar_title.
            CharSequence text = common_toolbar_title.getText();
            //CharSequence text = binding.commonTitlebar.commonToolbarTitle.getText();
            Log.i("text", "initChatUi: " + text);
            Log.i("Bus.curFriend", "onCreate: " + Bus.curFriend);
            //binding.commonTitlebar.commonToolbarTitle.setText(Bus.curFriend.getNickname());
            //setText Te
            //TextView  setText 无效
            common_toolbar_title.setText(Bus.curFriend.getNickname());
            CharSequence textSeted = common_toolbar_title.getText();
            //CharSequence textSeted = binding.commonTitlebar.commonToolbarTitle.getText();
            Log.i("textSeted", "initChatUi: " + textSeted);
        }

    }

    boolean mockDataYes=true;
    public void back(View view) {
        Intent intent = new Intent(ChatActivity.this, MainActivity.class);
        startActivity(intent);
    }


    void httpGetMsgList() {

        //Bus
        //ChatTy
        //mes
        //http://localhost:8080/api/message/list
        //HttpUtil.post()

        //不能是null 的吗
        //AppDatabase
        FormBody formBody = new FormBody.Builder()
                .add("chatId", Bus.nowFriendId)
                .add("fromId", Bus.curUser.getId() + "")
                .add("chatType", ChatType.FRIEND)
                .add("pageNo", "0")
//            .add("username",username.value)
//            .add("password",password.value)
                .build();
        Log.i("formBody", "httpGetMsgList: " + formBody);
        //String chatId, String fromId,String chatType, Long pageNo
        //okhttp3.RequestBody formBody=new FormBody.Builder().
        okhttp3.OkHttpClient okHttpClient = new okhttp3.OkHttpClient();
        if (Bus.token == null) {
            ToastUtil.show(this, "没有token");
            return;
        }
        //获取token 顺便把人获取了
        String access_token = Bus.token.get("access_token").getAsString();
        okhttp3.Request getRequest = new
                okhttp3.Request.Builder()
                //.url("https://api.github.com/markdown/raw")
                .url(Bus.baseDbUrl + "/api/message/list")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "bearer " + access_token)
                .post(formBody)
                //.get()
                .build();
        //HttpUtil.post3()
        Call call = okHttpClient.newCall(getRequest);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String string = response.body().string();
                //JsonJavaUtil.

                List<Message> mReceiveMsgList = new ArrayList<Message>();
                JsonObject jsonObject = JsonUtil.Companion.strToJsonObject(string);
                JsonArray messageList = jsonObject.get("messageList").getAsJsonArray();
                com.example.whatrubbish.im.Message[] messages = JsonJavaUtil.jsonArrToObjArr(messageList, com.example.whatrubbish.im.Message[].class);
                for (com.example.whatrubbish.im.Message message : messages) {
                    if (message.getFromId().equals(Bus.curUser.getId() + "")) {
                        //是我发的
                        mReceiveMsgList.add(makeSendMsg(message.content));
                    } else {
                        //我朋友发的
                        mReceiveMsgList.add(makeRecvMsg(message.content));
                    }
                }

                ChatActivity.this.runOnUiThread(() -> {
                    mAdapter.addData(0, mReceiveMsgList);

                    mSwipeRefresh.setRefreshing(false);
                });


            }
        });

    }

    Message makeSendMsg(String content) {
        //Message mMessgaeText = getBaseReceiveMessage(MsgType.TEXT);
        Message mMessgaeText = getBaseSendMessage(MsgType.TEXT);

        TextMsgBody mTextMsgBody = new TextMsgBody();
        mTextMsgBody.setMessage(content);
        mMessgaeText.setBody(mTextMsgBody);
        return mMessgaeText;
    }

    Message makeRecvMsg(String content) {
        Message mMessgaeText = getBaseReceiveMessage(MsgType.TEXT);
        //Message mMessgaeText = getBaseSendMessage(MsgType.TEXT);

        TextMsgBody mTextMsgBody = new TextMsgBody();
        mTextMsgBody.setMessage(content);
        mMessgaeText.setBody(mTextMsgBody);
        return mMessgaeText;
    }

    void mockRefresh() {
        List<Message> mReceiveMsgList = mockData();
        //okHttpClient.newCall(getRequest);
        //HttpUtil.getHttp()
        //这个用户的所有消息 和他聊天的
        //下拉刷新模拟获取历史消息
        //如何分辨自己和别人的消息 只是一条条放下来 ？ 你一条我一条？ 哦哦 有不同的

        mAdapter.addData(0, mReceiveMsgList);

        mSwipeRefresh.setRefreshing(false);
    }


    @Override
    public void onRefresh() {
        //httpGetMsgList();
        mockRefresh();

    }

    List<Message> mockData() {
        List<Message> mReceiveMsgList = new ArrayList<Message>();
        //构建文本消息
        Message mMessgaeText = getBaseReceiveMessage(MsgType.TEXT);

        TextMsgBody mTextMsgBody = new TextMsgBody();
        mTextMsgBody.setMessage("你见过我想要的塑料瓶的吗？我好像没有，我今天才开始打这个游戏。");
        mMessgaeText.setBody(mTextMsgBody);
        mReceiveMsgList.add(mMessgaeText);

        Message mMessgae = getBaseSendMessage(MsgType.TEXT);
        TextMsgBody mTextMsgBody2 = new TextMsgBody();
        mTextMsgBody2.setMessage("没见过");
        mMessgae.setBody(mTextMsgBody2);
        mReceiveMsgList.add(mMessgae);

        Message mMessgaeText1 = getBaseReceiveMessage(MsgType.TEXT);
        TextMsgBody mTextMsgBody1 = new TextMsgBody();
        mTextMsgBody1.setMessage("不过我刚刚在游戏中看见了你想要的。");
        mMessgaeText1.setBody(mTextMsgBody1);
        mReceiveMsgList.add(mMessgaeText1);
        return mReceiveMsgList;
    }


    private void initChatUi() {
        //mBtnAudio
        final ChatUiHelper mUiHelper = ChatUiHelper.with(this);
        mUiHelper.bindContentLayout(mLlContent)
                .bindttToSendButton(mBtnSend)
                .bindEditText(mEtContent)
                .bindBottomLayout(mRlBottomLayout)
                .bindEmojiLayout(mLlEmotion)
                .bindAddLayout(mLlAdd)
                .bindToAddButton(mIvAdd)
                .bindToEmojiButton(mIvEmo)
                .bindAudioBtn(mBtnAudio)
                .bindAudioIv(mIvAudio)
                .bindEmojiData();
        //底部布局弹出,聊天列表上滑
        mRvChat.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    mRvChat.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mAdapter.getItemCount() > 0) {
                                mRvChat.smoothScrollToPosition(mAdapter.getItemCount() - 1);
                            }
                        }
                    });
                }
            }
        });
        //点击空白区域关闭键盘
        mRvChat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mUiHelper.hideBottomLayout(false);
                mUiHelper.hideSoftInput();
                mEtContent.clearFocus();
                mIvEmo.setImageResource(R.mipmap.ic_emoji);
                return false;
            }
        });


    }

    @OnClick({R.id.btn_send, R.id.rlPhoto, R.id.rlVideo, R.id.rlLocation, R.id.rlFile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                sendTextMsg(mEtContent.getText().toString());
                mEtContent.setText("");
                break;

            case R.id.rlLocation:
                break;
        }
    }


    //文本消息
    private void sendTextMsg(String hello) {
        final Message mMessgae = getBaseSendMessage(MsgType.TEXT);
        TextMsgBody mTextMsgBody = new TextMsgBody();
        mTextMsgBody.setMessage(hello);
        //mTextMsgBody.getMessage();
        mMessgae.setBody(mTextMsgBody);
        //开始发送
        mAdapter.addData(mMessgae);
        //模拟两秒后发送成功
        //4.10 要交了
        //TextMsgBody body =(TextMsgBody) mMessgae.getBody();
        //String message = body.getMessage();
        //mMessgae.getBody().ge
        updateMsg(mMessgae);
    }


    private Message getBaseSendMessage(MsgType msgType) {
        Message mMessgae = new Message();
        mMessgae.setUuid(UUID.randomUUID() + "");
        mMessgae.setSenderId(mSenderId);
        mMessgae.setTargetId(mTargetId);
        mMessgae.setSentTime(System.currentTimeMillis());
        mMessgae.setSentStatus(MsgSendStatus.SENDING);
        mMessgae.setMsgType(msgType);
        return mMessgae;
    }


    private Message getBaseReceiveMessage(MsgType msgType) {
        Message mMessgae = new Message();
        mMessgae.setUuid(UUID.randomUUID() + "");
        mMessgae.setSenderId(mTargetId);
        mMessgae.setTargetId(mSenderId);
        mMessgae.setSentTime(System.currentTimeMillis());
        mMessgae.setSentStatus(MsgSendStatus.SENDING);
        mMessgae.setMsgType(msgType);
        return mMessgae;
    }


    private void updateMsg(final Message mMessgae) {
        mRvChat.scrollToPosition(mAdapter.getItemCount() - 1);
        //模拟2秒后发送成功
        //mMessgae.getBody()


        if(mockDataYes){
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    int position = 0;
                    mMessgae.setSentStatus(MsgSendStatus.SENT);
                    //更新单个子条目
                    for (int i = 0; i < mAdapter.getData().size(); i++) {
                        Message mAdapterMessage = mAdapter.getData().get(i);
                        //发送的消息 他的数据要变化 其他的不用变吗
                        //他不是最后吗？不一定的吗 还是一定是最后呢
                        if (mMessgae.getUuid().equals(mAdapterMessage.getUuid())) {
                            position = i;
                        }
                    }
                    mAdapter.notifyItemChanged(position);
                }
            }, 2000);
        }else{
            TextMsgBody body = (TextMsgBody) mMessgae.getBody();
            String message = body.getMessage();
            ThreadPoolFactory.getExecutorService().execute(() -> {
                sendMsgWs(message);
                updateMsgAdapter(mMessgae);
            });
        }




    }

    void updateMsgAdapter(final Message mMessgae) {
        int position = 0;
        mMessgae.setSentStatus(MsgSendStatus.SENT);
        //更新单个子条目
        for (int i = 0; i < mAdapter.getData().size(); i++) {
            Message mAdapterMessage = mAdapter.getData().get(i);
            //发送的消息 他的数据要变化 其他的不用变吗
            //他不是最后吗？不一定的吗 还是一定是最后呢
            if (mMessgae.getUuid().equals(mAdapterMessage.getUuid())) {
                position = i;
            }
        }
        //ActivityU
        int finalPosition = position;
        this.runOnUiThread(()->{
            mAdapter.notifyItemChanged(finalPosition);
        });

    }

    void sendMsgWs(String sendMessage) {
        //HttpU
        SendInfo sendInfo = new SendInfo();
        sendInfo.code = MessageInfoType.Companion.getMSG_MESSAGE();
        //sendInfo.code= MessageInfoType.Companion.MSG_MESSAGE;
        sendInfo.message = new com.example.whatrubbish.im.Message();
        sendInfo.message.username = Bus.curUser.getUsername();
        sendInfo.message.content = sendMessage;
        //朋友应该是imuser 类型吧
        sendInfo.message.chatId = Bus.curFriend.getId() + "";
        sendInfo.message.fromId = Bus.curUser.getId() + "";
        sendInfo.message.type = MessageTargetType.FRIEND;
        //MessageInfoType.Companion.getMSG_MESSAGE()
//            jsonOBject 转化str
//            JSONObject.toJSONString(sendInfo)
//        他这里绑定的接收消息是。。 根据token吗
        Log.i("sendInfo", "ChatScreen: " + sendInfo);
        String sendString = JsonUtil.Companion.objToStr(sendInfo);
//            myWebSocketClient.sendStr(sendInfo.toString())
//        登录的时候 初始化
//        这个挺慢的 会卡死 直到断开？
//        ThreadPoolFactory.getExecutorService().execute(() -> {
//            String res = Bus.myWebSocketClient.sendStr(sendString);
//            Log.i("res", "sendMsgWs: " + res);
//        });
        String res = Bus.myWebSocketClient.sendStr(sendString);
        Log.i("res", "sendMsgWs: " + res);
        //myWebSocketClient.sendStr(sendString);
//            JsonUtil.
//            MessageInfoType.MSG_MESSAGE
//            MessageInfoType.M
//        SendMsg sendMsg=new SendMsg();
//        sendMsg.chatId="1";
    }


}
