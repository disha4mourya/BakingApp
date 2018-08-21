package com.example.bakinapp.RecipeDetails;

import android.app.Activity;
import android.content.Context;

import com.example.bakinapp.recipe_list.entities.IngredientsEntity;
import com.example.bakinapp.recipe_list.entities.RecipeEntity;
import com.example.bakinapp.recipe_list.entities.StepsEntity;

import java.util.List;

public class RecipeDetailsModelInteractor implements RecipeDetailsContract.ModelInteractor {

    private Activity activity;
    private RecipeEntity recipeEntity;
    private List<IngredientsEntity> ingredientsEntityList;
    private List<StepsEntity> stepsEntityList;

    public RecipeDetailsModelInteractor(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void setRecipeEntityList(RecipeEntity recipeEntityList) {
        this.recipeEntity = recipeEntityList;
    }

    @Override
    public void setIngredientsEntityList(List<IngredientsEntity> ingredientsEntityList) {
        this.ingredientsEntityList = ingredientsEntityList;
    }

    @Override
    public void setStepsEntityList(List<StepsEntity> stepsEntityList) {
        this.stepsEntityList = stepsEntityList;
    }

    @Override
    public List<IngredientsEntity> getIngredientsList() {
        return ingredientsEntityList;
    }

    @Override
    public List<StepsEntity> getStepsList() {
        return stepsEntityList;
    }
}
