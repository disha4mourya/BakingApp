package com.imerchantech.bakinapp.recipe_list.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imerchantech.bakinapp.recipe_list.RecipeContract;
import com.imerchantech.bakinapp.recipe_list.entities.RecipeEntity;
import com.imerchantech.bakinapp.R;
import com.imerchantech.bakinapp.databinding.RecipeRowBinding;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MatchViewHolder> {

    private Context context;
    private RecipeContract.Presenter presenter;

    RecipeAdapter(Context context, RecipeContract.Presenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public MatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecipeRowBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.recipe_row, parent, false);
        return new MatchViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MatchViewHolder holder, int position) {
        RecipeEntity recipeEntity = presenter.getAdapterEntity(position);

        holder.binding.tvRecipeName.setText(recipeEntity.getName());
    }

    @Override
    public int getItemCount() {
        return presenter.getAdapterEntityCount();
    }

    class MatchViewHolder extends RecyclerView.ViewHolder {

        private RecipeRowBinding binding;

        private MatchViewHolder(RecipeRowBinding matchRowBinding) {
            super(matchRowBinding.getRoot());
            this.binding = matchRowBinding;

            binding.cvRecipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position >= 0)
                        presenter.recipeClicked(position);
                }
            });
        }
    }
}
