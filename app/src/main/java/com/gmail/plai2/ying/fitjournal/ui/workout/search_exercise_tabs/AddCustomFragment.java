package com.gmail.plai2.ying.fitjournal.ui.workout.search_exercise_tabs;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gmail.plai2.ying.fitjournal.MainActivity;
import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.AvailableExerciseItem;
import com.gmail.plai2.ying.fitjournal.room.ExerciseType;
import com.gmail.plai2.ying.fitjournal.room.TypeConverters;
import com.gmail.plai2.ying.fitjournal.ui.workout.WorkoutViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class AddCustomFragment extends Fragment {

    // Input fields
    private ArrayList<String> mExerciseInfo = new ArrayList<>();
    private ExerciseType mExerciseTypeInput;

    // UI fields
    private WorkoutViewModel mViewModel;
    private TextInputEditText mExerciseNameTIET;
    private Button mSaveButton;
    private MaterialToolbar mToolbar;

    // Empty constructor
    public AddCustomFragment() {
        // To enable menu for this fragment
        setHasOptionsMenu(true);
    }

    // New instance constructor
    public static AddCustomFragment newInstance(ExerciseType exerciseTypeInput) {
        AddCustomFragment fragment = new AddCustomFragment();
        Bundle bundle = new Bundle();
        ArrayList<String> exerciseInfo = new ArrayList<>();
        exerciseInfo.add(Integer.toString(TypeConverters.exerciseTypetoInt(exerciseTypeInput)));
        bundle.putStringArrayList(MainActivity.EXERCISE_INFO, exerciseInfo);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // To enable menu for this fragment
        setHasOptionsMenu(true);
        // Parse through bundle
        if (getArguments() != null) {
            ArrayList<String> exerciseInfo = getArguments().getStringArrayList(MainActivity.EXERCISE_INFO);
            mExerciseInfo = exerciseInfo;
            mExerciseTypeInput = TypeConverters.intToExerciseType(Integer.parseInt(exerciseInfo.get(0)));
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Initialize fields and variables
        mViewModel = ViewModelProviders.of(this).get(WorkoutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_custom, container, false);
        mToolbar = root.findViewById(R.id.add_custom_tb);
        mExerciseNameTIET = root.findViewById(R.id.add_custom_exercise_name_tiet);
        mSaveButton = root.findViewById(R.id.save_custom_btn);

        // Setup app tool bar
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Add Custom Exercise");

        // On click listeners
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.insert(new AvailableExerciseItem(mExerciseTypeInput, mExerciseNameTIET.getText().toString(), false, true));
                Navigation.findNavController(view).popBackStack();
            }
        });
        return root;
    }

    // Setup menu options
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Navigation.findNavController(getView()).popBackStack();
        }
        return true;
    }
}