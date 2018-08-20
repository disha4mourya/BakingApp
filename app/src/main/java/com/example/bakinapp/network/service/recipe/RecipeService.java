package com.example.bakinapp.network.service.recipe;

import com.example.bakinapp.recipe_list.entities.RecipeEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {


    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<RecipeEntity>> getRecipesLIst();
}
