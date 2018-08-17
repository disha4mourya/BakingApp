package com.example.bakinapp.network.service.recipe;

import com.example.bakinapp.network.RetrofitBuilder;
import com.example.bakinapp.recipe_list.entities.RecipeEntity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeServiceImpl {

    private RecipeService recipeService;

    public RecipeServiceImpl() {
        recipeService = RetrofitBuilder.buildApiService(RecipeService.class);
    }

    public void fetchRecipes(final FetchRecipeCallBack callBack) {

        final Call<RecipeEntity> call = recipeService.getRecipesLIst();

        call.enqueue(new Callback<RecipeEntity>() {
            @Override
            public void onResponse(Call<RecipeEntity> call, Response<RecipeEntity> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else {
                    callBack.onFailure();
                }
            }

            @Override
            public void onFailure(Call<RecipeEntity> call, Throwable t) {

                callBack.onFailure();
            }
        });

    }

    public interface FetchRecipeCallBack {
        void onSuccess(RecipeEntity recipeEntity);

        void onFailure();
    }


}
