package com.example.bakinapp.RecipeDetails.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakinapp.RecipeDetails.RecipeDetailsContract;
import com.example.bakinapp.RecipeDetails.RecipeDetailsPresenter;
import com.example.bakinapp.RecipeDetails.view.DetailsIngredientsAdapter;
import com.example.bakinapp.RecipeDetails.view.DetailsStepsAdapter;
import com.example.bakinapp.RecipeDetails.view.RecipeDetailsActivity;
import com.example.bakinapp.recipe_list.entities.IngredientsEntity;
import com.example.bakinapp.recipe_list.entities.RecipeEntity;
import com.example.bakinapp.recipe_list.entities.StepsEntity;
import com.google.gson.Gson;
import com.imerchantech.bakinapp.R;
import com.imerchantech.bakinapp.databinding.FragmentMasterListBinding;

import java.util.List;

import static com.example.bakinapp.network.Constants.RECIPEENTITY;

public class MasterListFragment extends Fragment implements RecipeDetailsContract.FragmentView {

    FragmentMasterListBinding binding;
    DetailsIngredientsAdapter ingredientsAdapter;
    DetailsStepsAdapter stepsAdapter;
    Context context;
    RecipeDetailsPresenter presenter;
    RecipeEntity recipeEntity=new RecipeEntity();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_master_list, container, false);
        View view = binding.getRoot();
        context = getActivity();


        recipeEntity=RecipeDetailsActivity.getRecipeEntity();
       /* Log.d("ingreFrag", "size" + recipeEntity.getIngredients().size());


        ingredientsAdapter = new DetailsIngredientsAdapter(context, recipeEntity.getIngredients());
        stepsAdapter = new DetailsStepsAdapter(context, presenter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context);
        binding.rvIngredients.setLayoutManager(linearLayoutManager);
        binding.rvSteps.setLayoutManager(linearLayoutManager2);

        binding.rvIngredients.setAdapter(ingredientsAdapter);
        binding.rvSteps.setAdapter(stepsAdapter);

        stepsAdapter.notifyDataSetChanged();
        ingredientsAdapter.notifyDataSetChanged();*/
       // recipeEntity = new Gson().fromJson(activity.getRecipeEntityDetail(), RecipeEntity.class);
       // recipeEntity = new Gson().fromJson(activity.getRecipeEntityDetail(), RecipeEntity.class);
        return view;
    }

    @Override
    public void showIngredientsList(List<IngredientsEntity> ingredientsEntity) {
        if (ingredientsAdapter != null)
            ingredientsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showStepsList(List<StepsEntity> stepsEntity) {
        if (stepsAdapter != null)
            stepsAdapter.notifyDataSetChanged();
    }


}
