package com.example.bakinapp.RecipeDetails.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.bakinapp.recipe_list.entities.IngredientsEntity;
import com.imerchantech.bakinapp.R;
import com.imerchantech.bakinapp.databinding.IngredientRowBinding;

import java.util.List;

public class DetailsIngredientsAdapter extends RecyclerView.Adapter<DetailsIngredientsAdapter.MatchViewHolder> {

    private Context context;
    //private RecipeDetailsContract.Presenter presenter;
    List<IngredientsEntity> ingredientsEntity;

   /* public DetailsIngredientsAdapter(Context context, RecipeDetailsContract.Presenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }*/

    public DetailsIngredientsAdapter(Context context, List<IngredientsEntity> ingredientsEntity) {
        this.context = context;
        this.ingredientsEntity = ingredientsEntity;
    }

    @Override
    public DetailsIngredientsAdapter.MatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        IngredientRowBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.ingredient_row, parent, false);
        return new DetailsIngredientsAdapter.MatchViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final DetailsIngredientsAdapter.MatchViewHolder holder, int position) {
        IngredientsEntity ingredientEntity = ingredientsEntity.get(position);

        holder.binding.tvIngredientName.setText(ingredientEntity.getIngredient());
    }

    @Override
    public int getItemCount() {
        return ingredientsEntity.size();
        // return presenter.getIngredientsEntityCount();
    }

    class MatchViewHolder extends RecyclerView.ViewHolder {

        private IngredientRowBinding binding;

        private MatchViewHolder(IngredientRowBinding matchRowBinding) {
            super(matchRowBinding.getRoot());
            this.binding = matchRowBinding;

        }
    }
}