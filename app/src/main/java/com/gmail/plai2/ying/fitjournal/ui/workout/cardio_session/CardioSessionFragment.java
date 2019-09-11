package com.gmail.plai2.ying.fitjournal.ui.workout.cardio_session;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.gmail.plai2.ying.fitjournal.room.CompletedExerciseItem;
import com.gmail.plai2.ying.fitjournal.room.ExerciseIntensity;
import com.gmail.plai2.ying.fitjournal.room.ExerciseType;
import com.gmail.plai2.ying.fitjournal.room.TypeConverters;
import com.gmail.plai2.ying.fitjournal.ui.workout.WorkoutViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;
import java.util.List;

public class CardioSessionFragment extends Fragment {

    // Input fields
    private ExerciseType mExerciseTypeInput;
    private String mExerciseNameInput;
    private int mExerciseIdInput;
    private int mExerciseDurationInput;
    private ExerciseIntensity mExerciseIntensityInput;
    private boolean mShouldUpdate = false;

    // UI fields
    private WorkoutViewModel mViewModel;
    private TextInputEditText mDurationTIET;
    private RadioGroup mIntensityRadioGroup;
    private Button mSaveButton;
    private MaterialToolbar mtoolbar;

    // Empty constructor
    public CardioSessionFragment() {
        // To enable menu for this fragment
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // To enable menu for this fragment
        setHasOptionsMenu(true);
        // Parse through bundle
        if (getArguments() != null) {
             List<String> exerciseInfo = getArguments().getStringArrayList(MainActivity.EXERCISE_INFO);
             mExerciseTypeInput = TypeConverters.intToExerciseType(Integer.parseInt(exerciseInfo.get(0)));
             mExerciseNameInput = exerciseInfo.get(1);
             if (exerciseInfo.size() > 2) {
                 mExerciseIdInput = Integer.parseInt(exerciseInfo.get(2));
                 mExerciseDurationInput = Integer.parseInt(exerciseInfo.get(3));
                 mExerciseIntensityInput = TypeConverters.intToIntensity(Integer.parseInt(exerciseInfo.get(4)));
                 mShouldUpdate = true;
             }
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Initialize fields and variables
        View root = inflater.inflate(R.layout.fragment_cardio_session, container, false);
        mViewModel = ViewModelProviders.of(getActivity()).get(WorkoutViewModel.class);
        mDurationTIET = root.findViewById(R.id.duration_tiet);
        mIntensityRadioGroup = root.findViewById(R.id.intensity_rg);
        mtoolbar = root.findViewById(R.id.cardio_session_tb);
        mSaveButton = root.findViewById(R.id.save_cardio_exercise_btn);

        // Setup app tool bar
        ((AppCompatActivity)getActivity()).setSupportActionBar(mtoolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(mExerciseNameInput);

        // Update elements if passed from workout fragment
        if (mShouldUpdate) {
            mDurationTIET.setText(Integer.toString(mExerciseDurationInput));
            switch(mExerciseIntensityInput) {
                case LOW:
                    mIntensityRadioGroup.check(R.id.low_rb);
                    break;
                case MEDIUM:
                    mIntensityRadioGroup.check(R.id.medium_rb);
                    break;
                case HIGH:
                    mIntensityRadioGroup.check(R.id.high_rb);
                    break;
            }
        }

        // On click listeners
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = mIntensityRadioGroup.getCheckedRadioButtonId();
                RadioButton selectedIntensity = mIntensityRadioGroup.findViewById(selectedId);
                ExerciseIntensity newIntensity = null;
                if (selectedIntensity.getText().toString().equals("Low")) {
                    newIntensity = ExerciseIntensity.LOW;
                } else if (selectedIntensity.getText().toString().equals("Medium")) {
                    newIntensity = ExerciseIntensity.MEDIUM;
                } else if (selectedIntensity.getText().toString().equals("High")) {
                    newIntensity = ExerciseIntensity.HIGH;
                }
                int newDuration = Integer.parseInt(mDurationTIET.getText().toString());
                Date today = new Date();
                today.setTime(0);
                if (mShouldUpdate) {
                    CompletedExerciseItem updatedItem = new CompletedExerciseItem(mExerciseTypeInput, mExerciseNameInput, today, newDuration, newIntensity);
                    updatedItem.setMId(mExerciseIdInput);
                    mViewModel.update(updatedItem);
                } else {
                    CompletedExerciseItem newItem = new CompletedExerciseItem(mExerciseTypeInput, mExerciseNameInput, today, newDuration, newIntensity);
                    mViewModel.insert(newItem);
                }
                Navigation.findNavController(view).popBackStack(R.id.navigation_to_workout, false);
            }
        });
        return root;
    }

    // Setup menu options
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.exercise_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Navigation.findNavController(getView()).popBackStack();
        }
        return true;
    }
}