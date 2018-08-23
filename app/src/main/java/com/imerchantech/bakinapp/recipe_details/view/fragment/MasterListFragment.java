package com.imerchantech.bakinapp.recipe_details.view.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imerchantech.bakinapp.recipe_details.view.DetailsIngredientsAdapter;
import com.imerchantech.bakinapp.recipe_details.view.DetailsStepsAdapter;
import com.imerchantech.bakinapp.recipe_list.entities.RecipeEntity;
import com.imerchantech.bakinapp.utils.onStepClickListener;
import com.google.gson.Gson;
import com.imerchantech.bakinapp.R;
import com.imerchantech.bakinapp.databinding.FragmentMasterListBinding;

import static com.imerchantech.bakinapp.network.Constants.RECIPEENTITY;

public class MasterListFragment extends Fragment {

    FragmentMasterListBinding binding;
    DetailsIngredientsAdapter ingredientsAdapter;
    DetailsStepsAdapter stepsAdapter;
    Context context;
    RecipeEntity recipeEntity = new RecipeEntity();
    onStepClickListener onStepClickListener;

    public MasterListFragment(){

    }
    @SuppressLint("ValidFragment")
    public MasterListFragment(onStepClickListener onStepClickListener1) {
        this.onStepClickListener=onStepClickListener1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_master_list, container, false);
        View view = binding.getRoot();
        context = getActivity();

        assert getArguments() != null;
        String getArguments = getArguments().getString(RECIPEENTITY);
        recipeEntity = new Gson().fromJson(getArguments, RecipeEntity.class);
        Log.d("ingreFrag", "size" + recipeEntity.getIngredients().size());

        ingredientsAdapter = new DetailsIngredientsAdapter(context, recipeEntity.getIngredients());
        stepsAdapter = new DetailsStepsAdapter(context, recipeEntity.getSteps());

        stepsAdapter.setClickListener(onStepClickListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        binding.rvIngredients.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context);
        binding.rvSteps.setLayoutManager(linearLayoutManager2);

        binding.rvIngredients.setAdapter(ingredientsAdapter);
        binding.rvSteps.setAdapter(stepsAdapter);

        return view;
    }

}
