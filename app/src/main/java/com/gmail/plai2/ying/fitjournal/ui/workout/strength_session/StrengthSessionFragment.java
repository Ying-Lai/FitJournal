package com.gmail.plai2.ying.fitjournal.ui.workout.strength_session;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.Set;
import com.gmail.plai2.ying.fitjournal.ui.workout.WorkoutViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class StrengthSessionFragment extends Fragment {

    private static final String EXERCISE_TYPE = "exercise_type_key";
    private static final String EXERCISE_NAME = "exercise_name_key";
    private RecyclerView mSetRV;
    private String mExerciseNameInput;
    private String mExerciseTypeInput;
    private WorkoutViewModel mViewModel;
    private Button mSaveButton;

    public StrengthSessionFragment() {
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
        mViewModel = ViewModelProviders.of(this).get(WorkoutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_strength_session, container, false);
        MaterialToolbar toolbar = root.findViewById(R.id.strength_session_tb);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(mExerciseNameInput);
        mSaveButton = root.findViewById(R.id.save_strength_exercise_btn);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.to_workout);
            }
        });
        mSetRV = root.findViewById(R.id.sets_rv);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSetRV.setLayoutManager(new LinearLayoutManager(getContext()));
        mSetRV.setHasFixedSize(true);
        final SetAdapter adapter = new SetAdapter();
        mSetRV.setAdapter(adapter);
        List<Set> test = new ArrayList<>();
        test.add(new Set(10,50));
        test.add(new Set(8,40));
        adapter.addSet(test);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.exercise_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}