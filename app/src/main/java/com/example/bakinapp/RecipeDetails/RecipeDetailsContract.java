package com.example.bakinapp.RecipeDetails;

import com.example.bakinapp.recipe_list.entities.IngredientsEntity;
import com.example.bakinapp.recipe_list.entities.RecipeEntity;
import com.example.bakinapp.recipe_list.entities.StepsEntity;

import java.util.List;

public interface RecipeDetailsContract {

    interface View {


        void showStepsDetails(StepsEntity stepsEntity);

        void showStepsDetailsOnNextScreen(List<StepsEntity> stepsEntities);
    }

    interface  FragmentView{
        void showIngredientsList(List<IngredientsEntity> ingredientsEntity);

        void showStepsList(List<StepsEntity> stepsEntity);

    }
    interface ModelInteractor {

        void setRecipeEntityList(RecipeEntity recipeEntityList);

        void setIngredientsEntityList(List<IngredientsEntity> ingredientsEntityList);

        void setStepsEntityList(List<StepsEntity> stepsEntityList);

        List<IngredientsEntity> getIngredientsList();

        List<StepsEntity> getStepsList();
    }

    interface Presenter {

        void createCalled(RecipeEntity recipeEntityList);

        void stepsClicked(int position);

        IngredientsEntity getIngredientEntity(int position);

        StepsEntity getStepAdapterEntity(int position);

        int getIngredientsEntityCount();

        int getStepsEntityCount();
    }
}
