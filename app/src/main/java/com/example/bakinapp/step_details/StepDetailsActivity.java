package com.example.bakinapp.step_details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.bakinapp.recipe_list.entities.StepsEntity;
import com.imerchantech.bakinapp.R;

public class StepDetailsActivity extends AppCompatActivity {

    StepsEntity stepsEntity;
    int selectedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

    }
}
