package com.example.whatrubbish.activity;

import android.os.Bundle;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whatrubbish.R;
import com.example.whatrubbish.view.Rotate3dView;

public class FlipCardActivity extends AppCompatActivity {
 
    private SeekBar seekBar;
    private Rotate3dView rotate3dView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_flip_card);

        rotate3dView = findViewById(R.id.rotate_view);
 
        seekBar = findViewById(R.id.seek_bar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rotate3dView.setDegrees(progress);
            }
 
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
 
            }
 
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
 
            }
        });
 
    }
 
}