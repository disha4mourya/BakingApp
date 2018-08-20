package com.example.bakinapp.RecipeDetails.view.fragment;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imerchantech.bakinapp.R;
import com.imerchantech.bakinapp.databinding.FragmentMasterListBinding;

public class MasterListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        FragmentMasterListBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_master_list, container, false);
        View view = binding.getRoot();
        //here data must be an instance of the class MarsDataProvider

        return view;
    }
}
