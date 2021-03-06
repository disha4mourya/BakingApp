package com.imerchantech.bakinapp.recipe_list.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.imerchantech.bakinapp.recipe_details.view.RecipeDetailsActivity;
import com.imerchantech.bakinapp.recipe_list.RecipeContract;
import com.imerchantech.bakinapp.recipe_list.RecipePresenter;
import com.imerchantech.bakinapp.recipe_list.entities.RecipeEntity;
import com.google.gson.Gson;
import com.imerchantech.bakinapp.R;
import com.imerchantech.bakinapp.databinding.ActivityRecipeBinding;

import static com.imerchantech.bakinapp.network.Constants.RECIPEENTITY;

public class RecipeActivity extends AppCompatActivity implements RecipeContract.View {

    ActivityRecipeBinding binding;
    RecipePresenter presenter;
    Context context;
    RecipeAdapter adapter;
    Boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe);
        context = this;

        isTablet = getResources().getBoolean(R.bool.isTablet);
        presenter = new RecipePresenter(this, this);
        adapter = new RecipeAdapter(this, presenter);
        if (isTablet) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
            binding.rvRecipes.setLayoutManager(gridLayoutManager);
        } else {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            binding.rvRecipes.setLayoutManager(linearLayoutManager);
        }
        binding.rvRecipes.setAdapter(adapter);
        presenter.create();
    }

    @Override
    public void showProgress(Boolean show) {
        binding.pbRecipe.setVisibility(show ? View.VISIBLE : View.GONE);

    }

    @Override
    public void showRecipeList(Boolean show) {
        binding.rvRecipes.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showRecipeDetails(RecipeEntity recipeEntity) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(RECIPEENTITY, new Gson().toJson(recipeEntity));
        startActivity(intent);
    }

    @Override
    public void notifyRecipeData() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showOfflineMsg(boolean show) {
        if (show)
            Toast.makeText(context, "No internet Connection", Toast.LENGTH_SHORT).show();
    }
}
