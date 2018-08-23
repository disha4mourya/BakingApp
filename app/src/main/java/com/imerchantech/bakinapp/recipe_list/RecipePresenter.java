package com.imerchantech.bakinapp.recipe_list;

import android.app.Activity;
import android.content.Context;

import com.imerchantech.bakinapp.recipe_list.entities.RecipeEntity;

public class RecipePresenter implements RecipeContract.Presenter {

    private RecipeContract.View view = null;
    private RecipeContract.ModelInteractor modelInteractor = null;
    private Context context;

    public RecipePresenter(RecipeContract.View view, RecipeContract.ModelInteractor modelInteractor) {
        this.view = view;
        this.modelInteractor = modelInteractor;
    }

    public RecipePresenter(Activity activity, RecipeContract.View view) {
        this(view, new RecipeModelInteractor(activity));
        this.context = activity;
    }


    @Override
    public void create() {

        getRecipes();
    }

    @Override
    public void getRecipes() {
        modelInteractor.fetchRecipes(new RecipeContract.CallBack() {
            @Override
            public void onSuccess() {
                view.showRecipeList(true);
                view.showProgress(false);
                view.notifyRecipeData();
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void recipeClicked(int position) {
        RecipeEntity recipeEntity = modelInteractor.getRecipesEntityList().get(position);
        if (recipeEntity != null)
            view.showRecipeDetails(recipeEntity);
    }

    @Override
    public RecipeEntity getAdapterEntity(int position) {
        return modelInteractor.getAdapterEntityList().get(position);
    }

    @Override
    public int getAdapterEntityCount() {
        if (modelInteractor.getAdapterEntityList() != null)
            return modelInteractor.getAdapterEntityList().size();
        else
            return 0;
    }
}
