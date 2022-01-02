package com.example.trash_detective.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trash_detective.Bean.Block;
import com.example.trash_detective.Bean.Trash;
import com.example.trash_detective.Model.DataFlow;
import com.example.whatrubbish.R;

import java.io.IOException;

public class DescriptionActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView name_view;
    private TextView description_view;
    private TextView classification_view ;
    private ImageView classification_img;
    private Button back_btn;
    private ImageButton refresh_btn;

    private Handler handler ;
    private Block b;
    String[] classification_names={"可回收垃圾","厨余垃圾","有害垃圾","其他垃圾"};
    int[] classification_imgs = {R.drawable.recy,R.drawable.cook,R.drawable.poison,R.drawable.other};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        handler =new Handler();
        b = getIntent().getParcelableExtra("block");
        LoadingDialog.show(this);
        this.InitView();
        this.startAThread();

    }

    private void InitView(){
        back_btn = findViewById(R.id.back);
        refresh_btn = findViewById(R.id.refresh);
        name_view = findViewById(R.id.name);
        description_view = findViewById(R.id.description);
        classification_view = findViewById(R.id.classification);
        classification_img = findViewById(R.id.trash);
        refresh_btn.setOnClickListener(this);
        back_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back: {
                finish();
                break;
            }
            case R.id.refresh:{
                LoadingDialog.show(this);
                this.startAThread();
                break;
            }

        }
    }

    private void  startAThread(){
        new DescriptionThread().start();
    }

    private class updateUI implements Runnable {
        private Trash trash;
        updateUI(Trash trash){
            this.trash = trash;
        }
        @Override
        public void run() {
            try {
                name_view.setText(trash.getName_CN());
                name_view.setTextSize(30);
                int index = trash.getClassification_id();
                classification_img.setImageResource(classification_imgs[index]);
                description_view.setText(trash.getDescription());
                description_view.setTextSize(20);
                classification_view.setText(trash.getClassification());
                classification_view.setTextSize(25);
            }catch (Exception e){
                showToast("出现错误，请重试");
                e.printStackTrace();
            }
            LoadingDialog.dismiss();
        }
    }
    private class DescriptionThread extends Thread{
        @Override
        public void run() {
            try {
                String response_data = DataFlow.sendData(b.getObject_id());
                if (response_data != null || response_data.length() > 30) {
                    Trash trash = DataFlow.parseTrashData(response_data);
                    handler.post(new updateUI(trash));
                }
            }catch (IOException e){
                e.printStackTrace();
                showToast("连接服务异常");
                LoadingDialog.dismiss();
            }

        }
    }

    private void showToast(final String text) {
        runOnUiThread(() -> Toast.makeText(DescriptionActivity.this, text, Toast.LENGTH_SHORT).show());
    }
}
