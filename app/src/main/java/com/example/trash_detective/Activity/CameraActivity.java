package com.example.trash_detective.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.camerakit.CameraKitView;
import com.example.trash_detective.API.AdvancedGeneral;
import com.example.trash_detective.Model.DataFlow;
import com.example.whatrubbish.R;
//import com.example.whatrubbish.R;

import java.io.File;
import java.io.FileOutputStream;

public class CameraActivity extends AppCompatActivity implements View.OnTouchListener{
    private CameraKitView cameraKitView;
    private Button actionButton;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_api);
        this.context = this;
        cameraKitView = findViewById(R.id.camera);
        actionButton = findViewById(R.id.picture);
        actionButton.setOnTouchListener(this);
        Point displaySize = new Point();
        this.getWindowManager().getDefaultDisplay().getSize(displaySize);
        DataFlow.setScreenSize(displaySize.y,displaySize.x);
    }

    @Override
    protected void onStart() {
        super.onStart();
        cameraKitView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.onResume();
    }

    @Override
    protected void onPause() {
        cameraKitView.onPause();
        super.onPause();

    }

    @Override
    protected void onStop() {
        cameraKitView.onStop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.picture: {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    actionButton.setBackgroundResource(R.drawable.button_2);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    actionButton.setBackgroundResource(R.drawable.button_1);
                    LoadingDialog.show(this);
                    showToast("正在保存图像");
                    this.tackPicture();
                    actionButton.performClick();
                }
                break;
            }
        }
        return true;
    }

    private void showToast(final String text) {
        runOnUiThread(() -> Toast.makeText(CameraActivity.this, text, Toast.LENGTH_SHORT).show());
    }

    private void tackPicture(){
        cameraKitView.captureImage((cameraKitView, capturedImage) -> {
            File savedPhoto = new File(Environment.getExternalStorageDirectory(), "cache.jpg");
            try {
                new NetLoadingThread(capturedImage).start();
                FileOutputStream outputStream = new FileOutputStream(savedPhoto.getPath());
                outputStream.write(capturedImage);
                outputStream.close();

            } catch (java.io.IOException e) {
                e.printStackTrace();
                LoadingDialog.dismiss();
            }
        });
    }

    class NetLoadingThread extends Thread{
        byte[] capturedImage;
        NetLoadingThread(byte[] capturedImage){
            this.capturedImage = capturedImage;
        }
        @Override
        public void run() {
                Intent intent = null;
                showToast("正在发送请求");
                try {

                    String json_data = AdvancedGeneral.advancedGeneral(capturedImage);
                    showToast(json_data);

                    intent = new Intent(context, ShowUpActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("blocks", DataFlow.parseBlockData(json_data));
//                    switch (DataFlow.getCode()) {
//                        case 1:
//                            break;
//                        case -1:
//                            break;
//                        case 0:
//                            break;
//                    }
                    LoadingDialog.dismiss();
                    if (intent != null) {
                        context.startActivity(intent);
                        finish();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    showToast("连接服务异常");
                    LoadingDialog.dismiss();
                }
        }
    }
}
