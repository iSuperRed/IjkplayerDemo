package com.github.isuperred.ijkplayerdemo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import tv.danmaku.ijk.media.example.application.Settings;
import tv.danmaku.ijk.media.example.widget.media.AndroidMediaController;
import tv.danmaku.ijk.media.example.widget.media.IjkVideoView;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MainActivity extends AppCompatActivity {
    private IjkVideoView mVideoView;
    private AndroidMediaController mMediaController;
    private TextView mTextView;
    private String mVideoPath;
    private TableLayout mHudView;

    private Settings mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startVideo();

    }

    private void startVideo() {
        mSettings = new Settings(this);
        mVideoPath = "http://qthttp.apple.com.edgesuite.net/1010qwoeiuryfg/sl.m3u8";

        mMediaController = new AndroidMediaController(this, false);

        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        mVideoView = (IjkVideoView) findViewById(R.id.videoView);
        mHudView = (TableLayout) findViewById(R.id.hud_view);
        mVideoView.setMediaController(mMediaController);
        mVideoView.setHudView(mHudView);

        if (TextUtils.isEmpty(mVideoPath)) {
            Toast.makeText(this,
                    "No Video Found! Press Back Button To Exit",
                    Toast.LENGTH_LONG).show();
        } else {
            mVideoView.setVideoURI(Uri.parse(mVideoPath));
            mVideoView.start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mVideoView.stopPlayback();
        mVideoView.release(true);
        mVideoView.stopBackgroundPlay();
        IjkMediaPlayer.native_profileEnd();
    }
}
