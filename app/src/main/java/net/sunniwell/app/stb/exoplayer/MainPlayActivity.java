package net.sunniwell.app.stb.exoplayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import static com.google.android.exoplayer2.Player.REPEAT_MODE_ALL;

public class MainPlayActivity extends AppCompatActivity {
    //private PlayerView mPlayerView;
    private SurfaceView mSurfaceView;
    private DataSource.Factory mediaDataSourceFactory;
    private DefaultTrackSelector trackSelector;
    private SimpleExoPlayer mSimpleExoPlayer;
    private String streamUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_player);
        //mPlayerView = findViewById(R.id.video_view);
        mSurfaceView = findViewById(R.id.video_view2);
        Intent mIntent = getIntent();
        streamUrl = mIntent.getStringExtra("url");
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializePlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSimpleExoPlayer.release();
    }

    private void initializePlayer() {
        //https://exoplayer.dev/hello-world.html
        //mPlayerView.setUseController(false);
        mediaDataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"));
        MediaSourceFactory mediaSourceFactory = new DefaultMediaSourceFactory(mediaDataSourceFactory);
        trackSelector = new DefaultTrackSelector(this);
        mSimpleExoPlayer = new SimpleExoPlayer.Builder(this).setTrackSelector(trackSelector).setMediaSourceFactory(mediaSourceFactory).build();
        MediaItem mMediaItem = MediaItem.fromUri(streamUrl);
        //可以使用addMediaItem或者setMediaItem
        mSimpleExoPlayer.setMediaItem(mMediaItem);
        mSimpleExoPlayer.setPlayWhenReady(true);
        //循环模式
        mSimpleExoPlayer.setRepeatMode(REPEAT_MODE_ALL);
        mSimpleExoPlayer.setVideoSurfaceView(mSurfaceView);
        mSurfaceView.requestFocus();
        mSimpleExoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                switch (playbackState) {
                    case Player.STATE_READY:
                        TrackGroupArray trackGroups = mSimpleExoPlayer.getCurrentTrackGroups();
                        TrackSelectionArray trackSelections = mSimpleExoPlayer.getCurrentTrackSelections();
                        trackSelector.buildUponParameters().setMaxVideoSizeSd();
                        break;
                }
            }
        });
        mSimpleExoPlayer.prepare();
        mSimpleExoPlayer.play();
    }

}