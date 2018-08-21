package com.example.bakinapp.RecipeDetails;

import android.app.Activity;
import android.content.Context;

import com.example.bakinapp.RecipeDetails.view.fragment.MasterListFragment;
import com.example.bakinapp.recipe_list.entities.IngredientsEntity;
import com.example.bakinapp.recipe_list.entities.RecipeEntity;
import com.example.bakinapp.recipe_list.entities.StepsEntity;

import java.util.List;

public class RecipeDetailsPresenter implements RecipeDetailsContract.Presenter {

    private Context context;
    private RecipeEntity recipeEntity;
    private List<IngredientsEntity> ingredientsEntityList;
    private List<StepsEntity> stepsEntityList;
    private RecipeDetailsContract.View view = null;
    private RecipeDetailsContract.ModelInteractor modelInteractor = null;
    private MasterListFragment fragment;

    public RecipeDetailsPresenter() {
    }

    public RecipeDetailsPresenter(RecipeDetailsContract.View view, RecipeDetailsContract.ModelInteractor modelInteractor) {
        this.view = view;
        this.modelInteractor = modelInteractor;
    }

    public RecipeDetailsPresenter(Activity activity, RecipeDetailsContract.View view) {
        this(view, new RecipeDetailsModelInteractor(activity));
        this.context = activity;
        fragment = new MasterListFragment();
    }

    @Override
    public void createCalled(RecipeEntity recipeEntityList) {
        recipeEntity = recipeEntityList;
        ingredientsEntityList = recipeEntity.getIngredients();
        stepsEntityList = recipeEntity.getSteps();

        modelInteractor.setIngredientsEntityList(ingredientsEntityList);
        modelInteractor.setStepsEntityList(stepsEntityList);
        modelInteractor.setRecipeEntityList(recipeEntityList);

        fragment.showIngredientsList(ingredientsEntityList);
        fragment.showStepsList(stepsEntityList);
    }

    @Override
    public void stepsClicked(int position) {

    }

    @Override
    public IngredientsEntity getIngredientEntity(int position) {
        return ingredientsEntityList.get(position);
    }

    @Override
    public StepsEntity getStepAdapterEntity(int position) {
        return stepsEntityList.get(position);
    }

    @Override
    public int getIngredientsEntityCount() {
        if (ingredientsEntityList != null)
            return ingredientsEntityList.size();
        else
            return 0;
    }

    @Override
    public int getStepsEntityCount() {
        if (stepsEntityList != null)
            return stepsEntityList.size();
        else
            return 0;
    }
}
