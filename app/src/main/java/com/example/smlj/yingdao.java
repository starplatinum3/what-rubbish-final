package com.example.smlj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whatrubbish.R;

public class yingdao extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yingdao_1);
    }

    int flag = 1;


    public void tiaoguo(View view) {
        Intent intent = new Intent(yingdao.this, login.class);
        startActivity(intent);
    }

    public void next(View view) {
        TextView t1 = (TextView) findViewById(R.id.textView);
        ImageView jindu = (ImageView) findViewById(R.id.jingdu_1);
        ImageView yingdao = (ImageView) findViewById(R.id.imageView2);
        flag++;

        if (flag == 2) {
            t1.setText("趣味游戏");
            jindu.setImageResource(R.mipmap.jindu_2);
            yingdao.setImageResource(R.mipmap.yingdao_21);
        } else if (flag == 3) {
            t1.setText("知识百科");
            jindu.setImageResource(R.mipmap.jindu_3);
            yingdao.setImageResource(R.mipmap.yingdao_31);
        } else if (flag == 4) {
            t1.setText("什么垃圾");
            jindu.setImageResource(R.mipmap.jindu_4);
            yingdao.setImageResource(R.mipmap.yingdao_41);
        } else {
            Intent intent = new Intent(yingdao.this, login.class);
            startActivity(intent);
        }
    }
}
