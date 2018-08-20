package com.example.bakinapp.network.service.recipe;

import com.example.bakinapp.network.RetrofitBuilder;
import com.example.bakinapp.recipe_list.entities.RecipeEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeServiceImpl {

    private RecipeService recipeService;

    public RecipeServiceImpl() {
        recipeService = RetrofitBuilder.buildApiService(RecipeService.class);
    }

    public void fetchRecipes(final FetchRecipeCallBack callBack) {

        final Call<List<RecipeEntity>> call = recipeService.getRecipesLIst();

        call.enqueue(new Callback<List<RecipeEntity>>() {
            @Override
            public void onResponse(Call<List<RecipeEntity>> call, Response<List<RecipeEntity>> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else {
                    callBack.onFailure();
                }
            }

            @Override
            public void onFailure(Call<List<RecipeEntity>> call, Throwable t) {

                callBack.onFailure();
            }
        });

    }

    public interface FetchRecipeCallBack {
        void onSuccess(List<RecipeEntity> recipeEntity);

        void onFailure();
    }


}
