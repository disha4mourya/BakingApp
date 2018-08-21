package com.example.bakinapp.recipe_details.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakinapp.recipe_list.entities.StepsEntity;
import com.example.bakinapp.utils.onStepClickListener;
import com.imerchantech.bakinapp.R;
import com.imerchantech.bakinapp.databinding.StepRowBinding;

import java.util.List;

public class DetailsStepsAdapter extends RecyclerView.Adapter<DetailsStepsAdapter.MatchViewHolder> {

    private Context context;
    List<StepsEntity> stepsEntities;
    onStepClickListener onStepClickListener;
    //private RecipeDetailsContract.Presenter presenter;

    public DetailsStepsAdapter(Context context, List<StepsEntity> stepsEntityList) {
        this.context = context;
        this.stepsEntities = stepsEntityList;
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
        StepsEntity recipeEntity = stepsEntities.get(position);

        holder.binding.tvStepName.setText(recipeEntity.getShortDescription());
    }

    @Override
    public int getItemCount() {
        //  return presenter.getStepsEntityCount();
        return stepsEntities.size();
    }
    public void setClickListener(onStepClickListener itemClickListener) {
        this.onStepClickListener = itemClickListener;
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
                    onStepClickListener.stepClicked(position);
                    // if (position >= 0)
                    // presenter.stepsClicked(position);
                }
            });
        }
    }
}