package com.gmail.plai2.ying.fitjournal.ui.workout.strength_session;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.Exercise;
import com.gmail.plai2.ying.fitjournal.room.Set;
import com.gmail.plai2.ying.fitjournal.ui.workout.ExerciseAdapter;
import com.gmail.plai2.ying.fitjournal.ui.workout.WorkoutViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class StrengthSessionFragment extends Fragment {

    private static final String EXERCISE_TYPE = "exercise_type_key";
    private static final String EXERCISE_NAME = "exercise_name_key";
    private RecyclerView setRV;
    private String exerciseNameInput;
    private String exerciseTypeInput;
    private WorkoutViewModel viewModel;
    private Button saveButton;

    public StrengthSessionFragment() {
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
        viewModel = ViewModelProviders.of(this).get(WorkoutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_strength_session, container, false);
        MaterialToolbar toolbar = root.findViewById(R.id.strength_session_tb);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(exerciseNameInput);
        saveButton = root.findViewById(R.id.save_strength_exercise_btn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.to_workout);
            }
        });
        setRV = root.findViewById(R.id.sets_rv);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRV.setLayoutManager(new LinearLayoutManager(getContext()));
        setRV.setHasFixedSize(true);
        final SetAdapter adapter = new SetAdapter();
        setRV.setAdapter(adapter);
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