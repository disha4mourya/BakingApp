package com.example.bakinapp.RecipeDetails.view;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.bakinapp.recipe_list.entities.RecipeEntity;
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
import com.google.gson.Gson;
import com.imerchantech.bakinapp.R;

import java.util.List;

import static com.example.bakinapp.network.Constants.RECIPEENTITY;

public class RecipeDetailsActivity extends AppCompatActivity {

    private SimpleExoPlayer player;
    private PlayerView playerView;

    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;
    RecipeEntity recipeEntity;
    Boolean isTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        fetchIntentData();
        if (findViewById(R.id.video_view) != null) {
            isTwoPane = true;
            playerView = findViewById(R.id.video_view);
        } else {
            isTwoPane = false;
        }
    }

    private void fetchIntentData() {

        recipeEntity = new Gson().fromJson(getIntent().getStringExtra(RECIPEENTITY), RecipeEntity.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            if (isTwoPane)
                initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isTwoPane)
            hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            if (isTwoPane)
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

        List<StepsEntity> stepsEntityList = recipeEntity.getSteps();
        Log.d("videoToBePlayed", "is:=" + stepsEntityList.get(0).getVideoURL());
        MediaSource mediaSource = buildMediaSource(Uri.parse(stepsEntityList.get(0).getVideoURL()));
        player.prepare(mediaSource, true, false);
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

}
