package com.example.trash_detective.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trash_detective.Bean.Block;
import com.example.whatrubbish.Bus;
import com.example.whatrubbish.R;
import com.example.whatrubbish.activity.ProblemActivity;
import com.example.whatrubbish.databinding.ActivityAbsBinding;
import com.example.whatrubbish.databinding.ActivityShowNameBinding;
import com.example.whatrubbish.util.ActivityUtil;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class ShowNameActivity extends AppCompatActivity  {
    //
    //private mSurfaceView mSurfaceView;
    //private ArrayList<Block> blocks;

    ActivityShowNameBinding binding;
    //private ActivityAbsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShowNameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //{"keyword":"二维码","score":0.276231,"root":"二维码-二维码"}
        String keyword = Bus.detectedTrash.get("keyword").getAsString();
        JsonObject detectedTrash = Bus.detectedTrash;
        binding.tvKeyword.setText(keyword);
        binding.tvName.setText( Bus.detectedTrash.get("root").getAsString());
        binding.tvScore.setText(detectedTrash.get("score").getAsString());
        //binding.tvKeyword.setText(keyword);
        //setContentView(R.layout.activity_showup);
        //findViewById(R.id.back_to_img).setOnClickListener(this);
        //mSurfaceView = findViewById(R.id.surface_view);
        ////他是要在图片的位置把他圈出来吗 但是那个标记好像没有了
        //ArrayList<Block> blocks = getIntent().getParcelableArrayListExtra("blocks");
        //mSurfaceView.drawBlocks(blocks);
        //this.blocks=blocks;
        //Bus.
        //Log.i("blocks", "onCreate: "+blocks);
        binding.backToImg.setOnClickListener(v->{
            finish();
        });
        binding.btnToProblem.setOnClickListener(v->{
            ActivityUtil.startActivity(this, ProblemActivity.class);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //mSurfaceView.drawBlocks(blocks);
    }



    //@Override
    //public void onClick(View v) {
    //    if(v.getId()==R.id.back_to_img){
    //        finish();
    //    }
    //}
}
