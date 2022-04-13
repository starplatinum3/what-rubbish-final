package com.example.trash_detective.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trash_detective.Bean.Block;
import com.example.whatrubbish.R;

import java.util.ArrayList;

public class ShowUpActivity extends AppCompatActivity implements View.OnClickListener {

    private mSurfaceView mSurfaceView;
    private ArrayList<Block> blocks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showup);
        findViewById(R.id.back_to_img).setOnClickListener(this);
        mSurfaceView = findViewById(R.id.surface_view);
        //他是要在图片的位置把他圈出来吗 但是那个标记好像没有了
        ArrayList<Block> blocks = getIntent().getParcelableArrayListExtra("blocks");
        mSurfaceView.drawBlocks(blocks);
        //Probl
        this.blocks=blocks;
        Log.i("blocks", "onCreate: "+blocks);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSurfaceView.drawBlocks(blocks);
    }



    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.back_to_img){
            finish();
        }
    }
}
