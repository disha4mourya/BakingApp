package com.example.bakinapp.recipe_list.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.bakinapp.recipe_list.RecipeContract;
import com.example.bakinapp.recipe_list.entities.RecipeEntity;
import com.imerchantech.bakinapp.R;

public class RecipeActivity extends AppCompatActivity implements RecipeContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
    }

    @Override
    public void showProgress(Boolean show) {

    }

    @Override
    public void showRecipeList(Boolean show) {

    }

    @Override
    public void showRecipeDetails(RecipeEntity recipeEntity) {

    }

    @Override
    public void notifyRecipeData() {

    }

    @Override
    public void showOfflineMsg(boolean b) {

    }
}
