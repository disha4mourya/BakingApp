package com.example.bakinapp.RecipeDetails.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakinapp.RecipeDetails.RecipeDetailsContract;
import com.example.bakinapp.recipe_list.RecipeContract;
import com.example.bakinapp.recipe_list.entities.RecipeEntity;
import com.example.bakinapp.recipe_list.entities.StepsEntity;
import com.imerchantech.bakinapp.R;
import com.imerchantech.bakinapp.databinding.RecipeRowBinding;
import com.imerchantech.bakinapp.databinding.StepRowBinding;

public class DetailsStepsAdapter extends RecyclerView.Adapter<DetailsStepsAdapter.MatchViewHolder> {

    private Context context;
    private RecipeDetailsContract.Presenter presenter;

    public DetailsStepsAdapter(Context context, RecipeDetailsContract.Presenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public DetailsStepsAdapter.MatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        StepRowBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.step_row, parent, false);
        return new DetailsStepsAdapter.MatchViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final DetailsStepsAdapter.MatchViewHolder holder, int position) {
        StepsEntity recipeEntity = presenter.getStepAdapterEntity(position);

        holder.binding.tvStepName.setText(recipeEntity.getDescription());
    }

    @Override
    public int getItemCount() {
        return presenter.getStepsEntityCount();
    }

    class MatchViewHolder extends RecyclerView.ViewHolder {

        private StepRowBinding binding;

        private MatchViewHolder(StepRowBinding matchRowBinding) {
            super(matchRowBinding.getRoot());
            this.binding = matchRowBinding;

            binding.cvStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position >= 0)
                        presenter.stepsClicked(position);
                }
            });
        }
    }
}