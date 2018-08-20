package com.example.bakinapp.recipe_list;

import com.example.bakinapp.recipe_list.entities.RecipeEntity;

import java.util.List;

public interface RecipeContract {

    interface ModelInteractor {
        void fetchRecipes(CallBack callBack);

        void setRecipesEntityList(List<RecipeEntity> recipesEntityList);

        List<RecipeEntity> getRecipesEntityList();

        List<RecipeEntity> getAdapterEntityList();

    }

    interface View {

        void showProgress(Boolean show);

        void showRecipeList(Boolean show);

        void showRecipeDetails(RecipeEntity recipeEntity);

        void notifyRecipeData();

        void showOfflineMsg(boolean b);

    }

    interface Presenter {

        void getRecipes();

        void recipeClicked(int position);

        RecipeEntity getAdapterEntity(int position);

        int getAdapterEntityCount();

        void create();
    }

    interface CallBack {
        void onSuccess();

        void onFailure();
    }
}
