package com.imerchantech.bakinapp.recipe_list.entities;

import java.util.List;

public class RecipeEntity {

    private String id;
    private String name;
    private List<IngredientsEntity> ingredients;
    private List<StepsEntity> steps;
    private String servings;
    private String image;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IngredientsEntity> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientsEntity> ingredients) {
        this.ingredients = ingredients;
    }

    public List<StepsEntity> getSteps() {
        return steps;
    }

    public void setSteps(List<StepsEntity> steps) {
        this.steps = steps;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
