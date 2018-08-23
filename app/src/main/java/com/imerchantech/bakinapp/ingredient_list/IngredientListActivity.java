package com.imerchantech.bakinapp.ingredient_list;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imerchantech.bakinapp.R;
import com.imerchantech.bakinapp.databinding.ActivityIngredientListBinding;
import com.imerchantech.bakinapp.provider.RecipeContract;
import com.imerchantech.bakinapp.recipe_details.view.DetailsIngredientsAdapter;
import com.imerchantech.bakinapp.recipe_list.entities.IngredientsEntity;

import java.util.ArrayList;
import java.util.List;

import static com.imerchantech.bakinapp.provider.RecipeContract.BASE_CONTENT_URI;
import static com.imerchantech.bakinapp.provider.RecipeContract.PATH_RECIPE;
import static com.imerchantech.bakinapp.recipe_details.view.RecipeDetailsActivity.EXTRA_RECIPE_ID;

public class IngredientListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    List<IngredientsEntity> ingredientsEntityList;
    String recipeName = "";
    long ingreId;
    Context context;

    ActivityIngredientListBinding binding;
    DetailsIngredientsAdapter ingredientsAdapter;
    private static final int SINGLE_LOADER_ID = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ingredient_list);

        context = this;
        ingreId = getIntent().getLongExtra(EXTRA_RECIPE_ID, RecipeContract.INVALID_RECIPE_ID);

        Log.d("listSizeOfIngredients", "is: " + ingreId);
        getSupportLoaderManager().initLoader(SINGLE_LOADER_ID, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri SINGLE_RECIPE_URI = ContentUris.withAppendedId(
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPE).build(), ingreId);
        return new CursorLoader(this, SINGLE_RECIPE_URI, null,
                null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor == null || cursor.getCount() < 1) return;

        cursor.moveToFirst();

        int ingredientListIndex = cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_INGREDIENT_LIST);
        int recipeNameIndex = cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME);

        String ingredientAt = cursor.getString(ingredientListIndex);
        ingredientsEntityList = new Gson().fromJson(ingredientAt, new TypeToken<ArrayList<IngredientsEntity>>() {
        }.getType());

        recipeName = cursor.getString(recipeNameIndex);
        Log.d("sizeOfListIngrelist", "is " + ingredientsEntityList.size());
        Log.d("sizeOfListIngreName", "is " + ingredientsEntityList.size());

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(recipeName);

        ingredientsAdapter = new DetailsIngredientsAdapter(context, ingredientsEntityList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        binding.rvIngredients.setLayoutManager(linearLayoutManager);
        binding.rvIngredients.setAdapter(ingredientsAdapter);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
