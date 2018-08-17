package com.example.bakinapp.recipe_list;

import com.example.bakinapp.recipe_list.entities.RecipeEntity;

import java.util.List;

public class RecipeModelInteractor implements RecipeContract.ModelInteractor{
    @Override
    public void fetchRecipes(RecipeContract.CallBack callBack) {

    }

    @Override
    public void setRecipesEntityList(List<RecipeEntity> recipesEntityList) {

    }

    @Override
    public List<RecipeEntity> getRecipesEntityList() {
        return null;
    }

    @Override
    public List<RecipeEntity> getAdapterEntityList() {
        return null;
    }
}
