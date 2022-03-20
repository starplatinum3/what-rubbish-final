package com.example.whatrubbish.activity;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.whatrubbish.Bus;
import com.example.whatrubbish.R;
import com.example.whatrubbish.util.ToastUtil;
import com.google.android.material.snackbar.Snackbar;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Date;

public class WsTestActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView showMessage;
    private EditText editText;
    private EditText edit_addr;
    private WebSocketClient webSocketClient;
    private StringBuilder sb = new StringBuilder();

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            sb.append("服务器返回数据：");
            sb.append(msg.obj.toString());
            sb.append("\n");
            showMessage.setText(sb.toString());
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_ws_test);
        showMessage = findViewById(R.id.show_message);
        editText = findViewById(R.id.edit_text);
        findViewById(R.id.send).setOnClickListener(this);

        edit_addr = findViewById(R.id.edit_addr);
        //String host="localhost";
        //String host="starplatinumora.top";
        String host=  Bus.dbIp;

        String port="9326";
        //URI serverURI = URI.create("ws://192.168.1.199:8887");
        String uriStr = String.format("ws://%s:%s", host, port);
        edit_addr.setText(uriStr);
        Log.i("uriStr", "onCreate: "+uriStr);
        URI serverURI = URI.create(uriStr);

        webSocketClient = new WebSocketClient(serverURI) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.i("webSocketClient", "onOpen: ");
                sb.append("onOpen at time：");
                sb.append(new Date());
                sb.append("服务器状态：");
                sb.append(handshakedata.getHttpStatusMessage());
                sb.append("\n");
                showMessage.setText(sb.toString());
            }

            @Override
            public void onMessage(String message) {
                Log.i("webSocketClient", "onMessage: ");
                Message handlerMessage = Message.obtain();
                handlerMessage.obj = message;
                handler.sendMessage(handlerMessage);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.i("webSocketClient", "onClose: ");
                sb.append("onClose at time：");
                sb.append(new Date());
                sb.append("\n");
                sb.append("onClose info:");
                sb.append(code);
                sb.append(reason);
                sb.append(remote);
                sb.append("\n");
                showMessage.setText(sb.toString());
            }

            @Override
            public void onError(Exception ex) {
                Log.i("webSocketClient", "onError: ");
                sb.append("onError at time：");
                sb.append(new Date());
                sb.append("\n");
                sb.append(ex);
                sb.append("\n");
                showMessage.setText(sb.toString());
            }
        };
        ToastUtil.show(this,"开始连接");
        Log.i("start connect", "onCreate: start ");
        webSocketClient.connect();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:
                if (webSocketClient.isClosed() || webSocketClient.isClosing()) {
                    Log.i("closed", "onClick: webSocketClient");
                    Snackbar.make(v, "Client正在关闭", Snackbar.LENGTH_SHORT).show();
                    webSocketClient.connect();
                    break;
                }
                if (!webSocketClient.isOpen()) {
                    ToastUtil.show(this,"没有打开链接");
                    break;
                }
                webSocketClient.send(editText.getText().toString().trim());
                sb.append("客户端发送消息：");
                sb.append(new Date());
                sb.append("\n");
                sb.append(editText.getText().toString().trim());
                sb.append("\n");
                showMessage.setText(sb.toString());
                editText.setText("");
                break;
            default:
                break;
        }
    }
}

