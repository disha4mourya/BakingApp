package com.example.bakinapp.step_details;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
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
import static com.example.bakinapp.network.Constants.SELECTEDENTITY;
import static com.example.bakinapp.network.Constants.STEPSENTITY;

public class StepDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    StepsEntity stepsEntity;
    int selectedPosition = 0;
    PlayerView playerView;
    TextView tvDescription;

    private SimpleExoPlayer player;

    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;

    List<StepsEntity> stepsEntityList;

    ImageButton ibPrev, ibNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        playerView = findViewById(R.id.video_view);
        tvDescription = findViewById(R.id.tvDescription);

        ibPrev = findViewById(R.id.ibPrev);
        ibNext = findViewById(R.id.ibNext);

        ibPrev.setOnClickListener(this);
        ibNext.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        stepsEntityList = bundle.getParcelableArrayList(STEPSENTITY);
        selectedPosition = bundle.getInt(SELECTEDENTITY);
        Log.d("stepsListSize", "" + stepsEntityList.size());
        Log.d("selectedPosition", "" + selectedPosition);

        setVisibilityOfImageButton();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checking the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //First Hide other objects (listview or recyclerview), better hide them using Gone.
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) playerView.getLayoutParams();
            params.width=params.MATCH_PARENT;
            params.height=params.MATCH_PARENT;
            playerView.setLayoutParams(params);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            //unhide your objects here.
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) playerView.getLayoutParams();
            params.width=params.MATCH_PARENT;
            params.height=600;
            playerView.setLayoutParams(params);
        }
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

        setStepData(selectedPosition);
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

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
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
            if(getSupportActionBar()!=null) {
                getSupportActionBar().hide();
            }
            showVideoView(false);
        }
        if (description != null && !description.equals("")) {
            showDescription(true);
            tvDescription.setText(description);
        } else
            showDescription(false);
    }

    public void setVisibilityOfImageButton() {
        if (selectedPosition == 0)
            ibPrev.setVisibility(View.GONE);
        else
            ibPrev.setVisibility(VISIBLE);

        if (selectedPosition == stepsEntityList.size() - 1)
            ibNext.setVisibility(View.GONE);
        else
            ibNext.setVisibility(VISIBLE);

        Log.d("selectedPositionIn",""+selectedPosition);
        Log.d("sizeList",""+stepsEntityList.size());
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ibPrev:
                selectedPosition = selectedPosition - 1;
                setStepData(selectedPosition);
                setVisibilityOfImageButton();
                break;
            case R.id.ibNext:
                selectedPosition = selectedPosition + 1;
                setStepData(selectedPosition);
                setVisibilityOfImageButton();

                break;
        }
    }
}
