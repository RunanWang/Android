package com.bytedance.videoplayer;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.SeekBar;


import com.bytedance.videoplayer.player.VideoPlayerIJK;
import com.bytedance.videoplayer.player.VideoPlayerListener;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class IJKPlayerActivity extends AppCompatActivity {

    private static final String TAG = "debugR";

    private Uri mSelectedVideo;

    private VideoPlayerIJK ijkPlayer;
    private MediaPlayer player;
    private SurfaceHolder holder;
    private SeekBar seekBar;
    private Thread seekBarThr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        int mCurrentOrientation = getResources().getConfiguration().orientation;

        if (mCurrentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            // If current screen is portrait
            Log.i("info", "portrait"); // 竖屏
            setContentView(R.layout.layout_ijkplayer);
        } else if (mCurrentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            //If current screen is landscape
            Log.i("info", "landscape"); // 横屏
            setContentView(R.layout.layout_ijkplayer_land);
        }
        //setContentView(R.layout.layout_ijkplayer);
        setTitle("ijkPlayer");
        mSelectedVideo = getIntent().getData();
        ijkPlayer = findViewById(R.id.ijkPlayer);

        //加载native库
        try {
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        } catch (Exception e) {
            this.finish();
        }
        ijkPlayer.setListener(new VideoPlayerListener());
        if (mSelectedVideo == null) {
            ijkPlayer.setVideoResource(R.raw.yuminhong);
        } else {
            //ijkPlayer.setVideoPath(mSelectedVideo.getPath());
            ijkPlayer.setVideoPath(ResourceUtils.getRealPath(IJKPlayerActivity.this, mSelectedVideo));
        }
//        ijkPlayer.setVideoPath(getVideoPath());

        findViewById(R.id.buttonPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ijkPlayer.start();
            }
        });

        findViewById(R.id.buttonPause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ijkPlayer.pause();
            }
        });

        seekBar = findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    seekBar.setMax((int) ijkPlayer.getDuration());
                    Log.d(TAG, "onCreate() duration = [" + ijkPlayer.getDuration() + "]");
                    ijkPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarThr = new SeekBarThread(handler, ijkPlayer);

        seekBarThr.start();
    }


//    private String getVideoPath() {
//        return "http://qthttp.apple.com.edgesuite.net/1010qwoeiuryfg/sl.m3u8";
////        return "android.resource://" + this.getPackageName() + "/" + resId;
//    }

    @Override
    protected void onStop() {
        super.onStop();
        if (ijkPlayer.isPlaying()) {
            ijkPlayer.stop();
        }
        Log.d(TAG, "onStop() called");
        if (seekBarThr != null) {
            Log.d(TAG, "interrupt!");
            seekBarThr.interrupt();
        }
        IjkMediaPlayer.native_profileEnd();
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //float progress = msg.what/ijkPlayer.get
            seekBar.setMax((int) ijkPlayer.getDuration());
            //Log.d(TAG, "onCreate() duration = [" +  ijkPlayer.getDuration() + "]");
            seekBar.setProgress(msg.what);
            //currentTv.setText(formatTime(msg.what));
        }
    };
}
