package com.example.bakinapp.recipe_list;

import android.app.Activity;

import com.example.bakinapp.network.service.recipe.RecipeServiceImpl;
import com.example.bakinapp.recipe_list.entities.RecipeEntity;

import java.util.List;

public class RecipeModelInteractor implements RecipeContract.ModelInteractor {

    private List<RecipeEntity> recipeEntityList;
    private Activity activity;
    private RecipeServiceImpl service;


    public RecipeModelInteractor(Activity activity) {
        this.activity = activity;
        service = new RecipeServiceImpl();
    }

    @Override
    public void fetchRecipes(final RecipeContract.CallBack callBack) {

        service.fetchRecipes(new RecipeServiceImpl.FetchRecipeCallBack() {
            @Override
            public void onSuccess(List<RecipeEntity> recipeEntity) {

                recipeEntityList = recipeEntity;
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
