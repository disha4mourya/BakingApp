package com.imerchantech.bakinapp.recipe_list.entities;

import java.util.List;

public class AllRecipeEntity {

    public  List<RecipeEntity> recipeEntityList;

    public  List<RecipeEntity> getRecipeEntityList() {
        return recipeEntityList;
    }

    public void setRecipeEntityList(List<RecipeEntity> recipeEntityList) {
        this.recipeEntityList = recipeEntityList;
    }
}
