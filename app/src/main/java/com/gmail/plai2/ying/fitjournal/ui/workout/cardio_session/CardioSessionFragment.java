package com.gmail.plai2.ying.fitjournal.ui.workout.cardio_session;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.gmail.plai2.ying.fitjournal.MainActivity;
import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.AvailableExerciseItem;
import com.gmail.plai2.ying.fitjournal.room.CompletedExerciseItem;
import com.gmail.plai2.ying.fitjournal.ui.workout.WorkoutViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

public class CardioSessionFragment extends Fragment {

    private static final String EXERCISE_TYPE = "exercise_type_key";
    private static final String EXERCISE_NAME = "exercise_name_key";
    private String mExerciseNameInput;
    private String mExerciseTypeInput;
    private WorkoutViewModel mViewModel;
    private TextInputEditText mDurationTIET;
    private Button mSaveButton;
    private RadioGroup mIntensityRadioGroup;


    public CardioSessionFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
             mExerciseNameInput = getArguments().getString(EXERCISE_NAME);
             mExerciseTypeInput = getArguments().getString(EXERCISE_TYPE);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cardio_session, container, false);
        mViewModel = ViewModelProviders.of(getActivity()).get(WorkoutViewModel.class);
        mDurationTIET = root.findViewById(R.id.duration_tiet);
        mIntensityRadioGroup = root.findViewById(R.id.intensity_rg);
        MaterialToolbar toolbar = root.findViewById(R.id.cardio_session_tb);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(mExerciseNameInput);

        mSaveButton = root.findViewById(R.id.save_cardio_exercise_btn);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = mIntensityRadioGroup.getCheckedRadioButtonId();
                RadioButton selectedIntensity = mIntensityRadioGroup.findViewById(selectedId);
                CompletedExerciseItem.Intensity intensity = CompletedExerciseItem.Intensity.LOW;
                if (selectedIntensity.getText().toString().equals("Low")) {
                    intensity = CompletedExerciseItem.Intensity.LOW;
                } else if (selectedIntensity.getText().toString().equals("Medium")) {
                    intensity = CompletedExerciseItem.Intensity.MEDIUM;
                } else if (selectedIntensity.getText().toString().equals("High")) {
                    intensity = CompletedExerciseItem.Intensity.HIGH;
                }
                if (mDurationTIET != null) {
                    CompletedExerciseItem.ExerciseType exerciseType = (mExerciseTypeInput.equals("Cardio")? CompletedExerciseItem.ExerciseType.CARDIO : CompletedExerciseItem.ExerciseType.STRENGTH);
                    mViewModel.insert(new CompletedExerciseItem(exerciseType, mExerciseNameInput, new Date(), Integer.parseInt(mDurationTIET.getText().toString()), intensity));
                }
                Navigation.findNavController(view).popBackStack(R.id.navigation_to_workout, false);
            }
        });
        return root;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.exercise_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}