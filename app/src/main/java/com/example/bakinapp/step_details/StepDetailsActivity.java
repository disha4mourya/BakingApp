package com.example.bakinapp.step_details;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.bakinapp.recipe_list.entities.StepsEntity;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.imerchantech.bakinapp.R;

import java.util.List;

import static android.view.View.VISIBLE;

public class StepDetailsActivity extends AppCompatActivity {

    StepsEntity stepsEntity;
    int selectedPosition = 0;
    PlayerView playerView;
    TextView tvDescription;

    private SimpleExoPlayer player;

    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;

    List<StepsEntity> stepsEntityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        playerView = findViewById(R.id.video_view);
        tvDescription = findViewById(R.id.tvDescription);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {

            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void initializePlayer() {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(this),
                    new DefaultTrackSelector(), new DefaultLoadControl());

            playerView.setPlayer(player);

            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);

        }

      /*  if (stepsEntityList.get(0).getVideoURL() != null && !stepsEntityList.get(0).getVideoURL().equals("")) {
            setUrlToPlay(stepsEntityList.get(0).getVideoURL());
        } else
            showVideoView(false);*/


    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {

        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("bakingApp")).
                createMediaSource(uri);
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    public void setUrlToPlay(String urlToPlay) {
        showVideoView(true);
        MediaSource mediaSource = buildMediaSource(Uri.parse(urlToPlay));
        player.prepare(mediaSource, true, false);
    }

    public void showVideoView(Boolean show) {
        playerView.setVisibility(show ? VISIBLE : View.GONE);
    }

    public void showDescription(Boolean show) {
        tvDescription.setVisibility(show ? VISIBLE : View.GONE);

       /* if (!show) {
            binding.videoView.setLayoutParams(
                    new ViewGroup.LayoutParams(
                            // or ViewGroup.LayoutParams.WRAP_CONTENT
                            0,
                            // or ViewGroup.LayoutParams.WRAP_CONTENT,
                            0));
        } else {
            binding.videoView.setLayoutParams(
                    new ViewGroup.LayoutParams(
                            // or ViewGroup.LayoutParams.WRAP_CONTENT
                            0,
                            // or ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
        }*/
    }

    public void setStepData(int selectedPosition) {
        String videoUrl = stepsEntityList.get(selectedPosition).getVideoURL();
        String description = stepsEntityList.get(selectedPosition).getDescription();
        if (videoUrl != null && !videoUrl.equals("")) {
            setUrlToPlay(videoUrl);
        } else {
            showVideoView(false);
        }
        if (description != null && !description.equals("")) {
            showDescription(true);
            tvDescription.setText(description);
        } else
            showDescription(false);
    }
}
