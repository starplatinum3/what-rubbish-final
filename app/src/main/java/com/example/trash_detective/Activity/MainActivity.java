package com.example.trash_detective.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.whatrubbish.R;

//import com.example.whatrubbish.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main_trash_detect);
        InitCamera();
    }
    private boolean PermissionCheck(){
        String camera = Manifest.permission.CAMERA;
        String read = Manifest.permission.READ_EXTERNAL_STORAGE;
        String write = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        String[] permissions = new String[]{camera,read,write};
        int granted = this.getPackageManager().PERMISSION_GRANTED;
        if(ContextCompat.checkSelfPermission(this,camera)!=granted
        ||ContextCompat.checkSelfPermission(this,read)!=granted
        ||ContextCompat.checkSelfPermission(this,write)!=granted){
            ActivityCompat.requestPermissions(this,permissions,200);
            return false;
        }else{
            return true;
        }
    }
    private void InitCamera(){
        Button start_btn=findViewById(R.id.start);
        start_btn.setOnClickListener(view -> {
            if(PermissionCheck()) {
                if (checkCameraHardware()) {
                    ForwardCameraActivity();
                }
            }
        });
    }
    private boolean checkCameraHardware() {
        if (MainActivity.this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            return true;
        } else {
            showToast("您的设备中没有相机");
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                showToast("没有授权继续操作");
            }
        }
    }

    private void ForwardCameraActivity(){
        Intent intent = new Intent(MainActivity.this, CameraActivity.class);
        startActivity(intent);
    }

    private void showToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
