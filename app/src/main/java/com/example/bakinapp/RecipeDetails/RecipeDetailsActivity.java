package com.example.bakinapp.RecipeDetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.bakinapp.recipe_list.entities.RecipeEntity;
import com.google.gson.Gson;
import com.imerchantech.bakinapp.R;

import static com.example.bakinapp.network.Constants.RECIPEENTITY;

public class RecipeDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        fetchIntentData();
    }

    private void fetchIntentData() {

        RecipeEntity recipeEntity = new Gson().fromJson(getIntent().getStringExtra(RECIPEENTITY), RecipeEntity.class);
    }
}
