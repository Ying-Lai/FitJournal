package com.gmail.plai2.ying.fitjournal.ui.workout.cardio_session;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.gmail.plai2.ying.fitjournal.room.Exercise;
import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.ui.workout.WorkoutViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

public class CardioSessionFragment extends Fragment {

    private static final String EXERCISE_TYPE = "exercise_type_key";
    private static final String EXERCISE_NAME = "exercise_name_key";
    private String exerciseNameInput;
    private String exerciseTypeInput;
    private WorkoutViewModel viewModel;
    private TextInputEditText durationTIET;
    private Button saveButton;

    public CardioSessionFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
             exerciseNameInput = getArguments().getString(EXERCISE_NAME);
             exerciseTypeInput = getArguments().getString(EXERCISE_TYPE);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cardio_session, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(WorkoutViewModel.class);
        MaterialToolbar toolbar = root.findViewById(R.id.cardio_session_tb);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(exerciseNameInput);

        saveButton = root.findViewById(R.id.save_cardio_exercise_btn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.to_workout);
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