package com.gmail.plai2.ying.fitjournal.ui.workout.search_exercise_tabs;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.AvailableExerciseItem;
import com.gmail.plai2.ying.fitjournal.ui.workout.WorkoutViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Collections;
import java.util.List;

public class AddCustomFragment extends Fragment {


    private static final String EXERCISE_TYPE = "exercise_type_key";
    private String mExerciseTypeInput;
    private WorkoutViewModel mViewModel;
    private TextInputEditText mExerciseNameTIET;
    private Button mSaveButton;


    public AddCustomFragment() {
        setHasOptionsMenu(true);
    }

    public static AddCustomFragment newInstance(String exerciseTypeInput) {
        AddCustomFragment fragment = new AddCustomFragment();
        Bundle args = new Bundle();
        args.putString(EXERCISE_TYPE, exerciseTypeInput);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            this.mExerciseTypeInput = getArguments().getString(EXERCISE_TYPE);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(WorkoutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_custom, container, false);
        mExerciseNameTIET = root.findViewById(R.id.add_custom_exercise_name_tiet);
        mSaveButton = root.findViewById(R.id.save_custom_btn);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mExerciseNameTIET.getText() != null) {
                    AvailableExerciseItem.ExerciseType exerciseType = (mExerciseTypeInput.equals("Cardio")? AvailableExerciseItem.ExerciseType.CARDIO : AvailableExerciseItem.ExerciseType.STRENGTH);
                    mViewModel.insert(new AvailableExerciseItem(exerciseType, mExerciseNameTIET.getText().toString(), false, true));
                    getFragmentManager().popBackStackImmediate();
                }
            }
        });
        return root;
    }
}