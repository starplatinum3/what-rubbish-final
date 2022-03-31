package com.example.whatrubbish.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whatrubbish.R;
import com.example.whatrubbish.databinding.ActivityAbsBinding;
import com.example.whatrubbish.databinding.ActivityVideoBinding;
import com.example.whatrubbish.util.ActivityUtil;

public class VideoActivity extends AppCompatActivity {

    //private ActivityGameStageBinding binding;
    //private ActivityAbsBinding binding;
    private ActivityVideoBinding binding;



    //@SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("AbsActivity", "onCreate: ");
        binding = ActivityVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        VideoView videoView =  binding.videoView;
        //VideoView 放完了
        //VideoView 去除 黑边
        //VideoView videoView = findViewById(R.id.video_view);
        //videoView.setVideoPath("http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8");  // 香港卫视地址
        //videoView.setVideoURI(Uri.parse("android.resource://com.leapfrog.mergelayoutswithevents/raw/videoplayback");
        //VideoView 播放 raw

        //1280
        //720
        //360
        //640

        //240
        //426
        String uri = "android.resource://" + getPackageName() + "/" + R.raw.open_card;
        Uri parse = Uri.parse(uri);
        videoView.setVideoURI(parse);
        //videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/raw/open_card.mp4"));
        videoView.start();
        //videoView.   setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
        //    @Override
        //    public void onCompletion(MediaPlayer mp) {
        //        finish();
        //        //回去的时候 两个已经拼好了吗
        //    }
        //});

        //https://www.cnblogs.com/renhui/p/9267778.html
    //：监听播放完成的事件

//使视频能够暂停、播放、进度条显示等控制
//        MediaController mediaController = new MediaController(this);
//        videoView.setMediaController(mediaController);
//        mediaController.setMediaPlayer(videoView);

    }


}