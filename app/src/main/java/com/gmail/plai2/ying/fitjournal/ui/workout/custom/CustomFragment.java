package com.gmail.plai2.ying.fitjournal.ui.workout.custom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.ui.workout.WorkoutViewModel;

public class CustomFragment extends Fragment {

    private static final String EXERCISE_TYPE = "exercise_type_key";
    private WorkoutViewModel viewModel;
    private String exerciseTypeInput;

    public CustomFragment() {
        // Required empty public constructor
    }

    public static CustomFragment newInstance(String exerciseTypeInput) {
        CustomFragment fragment = new CustomFragment();
        Bundle args = new Bundle();
        args.putString(EXERCISE_TYPE, exerciseTypeInput);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.exerciseTypeInput = getArguments().getString(EXERCISE_TYPE);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(WorkoutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_custom, container, false);
        return root;
    }

}
