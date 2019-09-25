package com.gmail.plai2.ying.fitjournal.ui.workout.search_exercise_tabs;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.gmail.plai2.ying.fitjournal.MainActivity;
import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.AvailableExerciseItem;
import com.gmail.plai2.ying.fitjournal.room.ExerciseType;
import com.gmail.plai2.ying.fitjournal.room.TypeConverters;
import com.gmail.plai2.ying.fitjournal.ui.workout.WorkoutViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class AddCustomDialogFragment extends AppCompatDialogFragment {

    // Input fields
    private ExerciseType mExerciseTypeInput;

    // UI fields
    private WorkoutViewModel mViewModel;
    private TextInputEditText mExerciseNameTIET;
    private Button mSaveButton;
    private Button mCancelButton;

    // Empty constructor
    public AddCustomDialogFragment() {
    }

    // New instance constructor
    public static AddCustomDialogFragment newInstance(ExerciseType exerciseTypeInput) {
        AddCustomDialogFragment fragment = new AddCustomDialogFragment();
        Bundle bundle = new Bundle();
        ArrayList<String> exerciseInfo = new ArrayList<>();
        exerciseInfo.add(Integer.toString(TypeConverters.exerciseTypeToInt(exerciseTypeInput)));
        bundle.putStringArrayList(MainActivity.EXERCISE_INFO, exerciseInfo);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Initialize fields and variables
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_custom_dialog,null);
        if (getArguments() != null) {
            ArrayList<String> exerciseInfo = getArguments().getStringArrayList(MainActivity.EXERCISE_INFO);
            mExerciseTypeInput = TypeConverters.intToExerciseType(Integer.parseInt(exerciseInfo.get(0)));
        }
        mViewModel = ViewModelProviders.of(this).get(WorkoutViewModel.class);
        mExerciseNameTIET = view.findViewById(R.id.add_custom_exercise_name_tiet);
        mSaveButton = view.findViewById(R.id.save_custom_btn);
        mCancelButton = view.findViewById(R.id.cancel_custom_btn);

        // On click listeners
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mExerciseNameTIET.getText().toString().equals("")) {
                    mExerciseNameTIET.setError(getString(R.string.toast_error_message));
                } else {
                    mViewModel.insert(new AvailableExerciseItem(mExerciseTypeInput, mExerciseNameTIET.getText().toString(), false, true));
                    dismiss();
                }
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        builder.setView(view)
                .setTitle(getResources().getString(R.string.add_exercise));
        return builder.create();
    }
}