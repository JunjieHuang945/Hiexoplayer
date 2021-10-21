package net.sunniwell.app.stb.exoplayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MainIjkPlayer extends AppCompatActivity implements SurfaceHolder.Callback {
    private SurfaceView mSurfaceView;
    private String streamUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_player);
        mSurfaceView = findViewById(R.id.video_view2);
        Intent mIntent = getIntent();
        streamUrl = mIntent.getStringExtra("url");
        mSurfaceView.getHolder().addCallback(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (ijkMediaPlayer != null) {
            ijkMediaPlayer.reset();
            ijkMediaPlayer.stop();
            ijkMediaPlayer.release();
        }
    }

    private IjkMediaPlayer ijkMediaPlayer;

    private void initializePlayer(SurfaceHolder holder) {
        ijkMediaPlayer = new IjkMediaPlayer();
        ijkMediaPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                iMediaPlayer.start();
            }
        });
        ijkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG);

//开启硬解码
ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
        try {
            ijkMediaPlayer.setDataSource(streamUrl);
            ijkMediaPlayer.setDisplay(holder);
            ijkMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        initializePlayer(holder);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
}
