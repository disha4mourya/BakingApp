package com.imerchantech.bakinapp.recipe_list;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.imerchantech.bakinapp.RecipeService;
import com.imerchantech.bakinapp.network.service.recipe.RecipeServiceImpl;
import com.imerchantech.bakinapp.provider.RecipeContract;
import com.imerchantech.bakinapp.provider.RecipeDbHelper;
import com.imerchantech.bakinapp.recipe_list.entities.AllRecipeEntity;
import com.imerchantech.bakinapp.recipe_list.entities.IngredientsEntity;
import com.imerchantech.bakinapp.recipe_list.entities.RecipeEntity;

import java.util.List;

import static com.imerchantech.bakinapp.app.BakingApp.context;
import static com.imerchantech.bakinapp.app.BakingApp.editor;
import static com.imerchantech.bakinapp.network.Constants.ALLRECIPEENTITY;

public class RecipeModelInteractor implements com.imerchantech.bakinapp.recipe_list.RecipeContract.ModelInteractor {

    private List<RecipeEntity> recipeEntityList;
    private Activity activity;
    private RecipeServiceImpl service;
    RecipeDbHelper dbHelper;
    SQLiteDatabase database;

    public RecipeModelInteractor(Activity activity) {
        this.activity = activity;
        service = new RecipeServiceImpl();
    }

    @Override
    public void fetchRecipes(final com.imerchantech.bakinapp.recipe_list.RecipeContract.CallBack callBack) {

        service.fetchRecipes(new RecipeServiceImpl.FetchRecipeCallBack() {
            @Override
            public void onSuccess(List<RecipeEntity> recipeEntity) {

                recipeEntityList = recipeEntity;

                AllRecipeEntity allRecipeEntity = new AllRecipeEntity();
                allRecipeEntity.setRecipeEntityList(recipeEntityList);

                String allRecipes = new Gson().toJson(allRecipeEntity);
                editor.putString(ALLRECIPEENTITY, allRecipes);
                editor.commit();

                dbHelper = new RecipeDbHelper(context);
                database = dbHelper.getWritableDatabase();

                database.delete(RecipeContract.RecipeEntry.TABLE_NAME, null, null);
                //database.execSQL("DROP TABLE IF EXISTS " + RecipeContract.RecipeEntry.TABLE_NAME);
                //dbHelper.onCreate(database);
                //database.execSQL("delete from " + RecipeContract.RecipeEntry.TABLE_NAME);

                for (int i = 0; i < recipeEntityList.size(); i++) {
                    ContentValues contentValues = new ContentValues();
                    long timeNow = System.currentTimeMillis();

                    List<IngredientsEntity> ingredientsEntityList = recipeEntityList.get(i).getIngredients();
                    contentValues.put(RecipeContract.RecipeEntry._ID, recipeEntityList.get(i).getId());
                    contentValues.put(RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME, recipeEntityList.get(i).getName());
                    contentValues.put(RecipeContract.RecipeEntry.COLUMN_STEPS_LIST, timeNow);
                    contentValues.put(RecipeContract.RecipeEntry.COLUMN_INGREDIENT_LIST, new Gson().toJson(ingredientsEntityList));
                    context.getContentResolver().insert(RecipeContract.RecipeEntry.CONTENT_URI, contentValues);
                    RecipeService.startActionUpdateRecipeWidgets(context);
                }


                callBack.onSuccess();
            }

            @Override
            public void onFailure() {

                callBack.onFailure();
            }
        });
    }

    @Override
    public void setRecipesEntityList(List<RecipeEntity> recipesEntityList) {
        this.recipeEntityList = recipesEntityList;
    }

    @Override
    public List<RecipeEntity> getRecipesEntityList() {
        return recipeEntityList;
    }

    @Override
    public List<RecipeEntity> getAdapterEntityList() {
        return recipeEntityList;
    }
}
