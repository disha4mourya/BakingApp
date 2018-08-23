package com.imerchantech.bakinapp.recipe_details.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.imerchantech.bakinapp.recipe_details.view.fragment.MasterListFragment;
import com.imerchantech.bakinapp.recipe_list.entities.IngredientsEntity;
import com.imerchantech.bakinapp.recipe_list.entities.RecipeEntity;
import com.imerchantech.bakinapp.recipe_list.entities.StepsEntity;
import com.imerchantech.bakinapp.step_details.StepDetailsActivity;
import com.imerchantech.bakinapp.utils.onStepClickListener;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;
import com.imerchantech.bakinapp.R;
import com.imerchantech.bakinapp.databinding.ActivityRecipeDetailsBinding;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;
import static com.imerchantech.bakinapp.network.Constants.RECIPEENTITY;
import static com.imerchantech.bakinapp.network.Constants.SELECTEDENTITY;
import static com.imerchantech.bakinapp.network.Constants.STEPSENTITY;

public class RecipeDetailsActivity extends AppCompatActivity implements onStepClickListener {

    public static final String EXTRA_RECIPE_ID = "RECIPE_ID";
    private SimpleExoPlayer player;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;
    RecipeEntity recipeEntity;
    List<IngredientsEntity> ingredientsEntityList;
    List<StepsEntity> stepsEntityList;
    Boolean isTwoPane = false;
    Context context;
    ActivityRecipeDetailsBinding binding;
    FragmentManager fragmentManager;
    int selectedPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_details);

        context = this;
        if (binding.videoView != null) {
            Toast.makeText(context, "is TwoPane", Toast.LENGTH_SHORT).show();
            isTwoPane = true;
        } else {
            isTwoPane = false;
        }

        fragmentManager = getSupportFragmentManager();
        fetchIntentData();
    }


    private void fetchIntentData() {

        recipeEntity = new Gson().fromJson(getIntent().getStringExtra(RECIPEENTITY), RecipeEntity.class);
        ingredientsEntityList = recipeEntity.getIngredients();
        stepsEntityList = recipeEntity.getSteps();

        Bundle bundle = new Bundle();
        bundle.putString(RECIPEENTITY, new Gson().toJson(recipeEntity));
        Fragment masterListFragment = new MasterListFragment(this);
        masterListFragment.setArguments(bundle);

        fragmentManager.beginTransaction().replace(R.id.flRecipeDetails, masterListFragment).commit();
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
        if (isTwoPane) {
            hideSystemUi();
            if ((Util.SDK_INT <= 23 || player == null)) {
                initializePlayer();
            }
            setStepData(selectedPosition);
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

            binding.videoView.setPlayer(player);

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
        binding.videoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void stepClicked(int position) {
        if (!isTwoPane) {
            Intent intent = new Intent(this, StepDetailsActivity.class);
            Bundle bundle=new Bundle();
            bundle.putParcelableArrayList(STEPSENTITY, (ArrayList<? extends Parcelable>) stepsEntityList);
           // intent.putExtra(STEPSENTITY, new Gson().toJson(stepsEntityList.get(position)));
            bundle.putInt(SELECTEDENTITY, position);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            //selectedPosition = position;
            setStepData(position);
        }
    }

    public void setUrlToPlay(String urlToPlay) {
        showVideoView(true);
        MediaSource mediaSource = buildMediaSource(Uri.parse(urlToPlay));
        player.prepare(mediaSource, true, false);
    }

    public void showVideoView(Boolean show) {
        binding.videoView.setVisibility(show ? VISIBLE : View.GONE);
    }

    public void showDescription(Boolean show) {
        binding.tvDescription.setVisibility(show ? VISIBLE : View.GONE);

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
            binding.tvDescription.setText(description);
        } else
            showDescription(false);
    }
}
