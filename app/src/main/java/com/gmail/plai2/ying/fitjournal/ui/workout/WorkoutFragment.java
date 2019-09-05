package com.gmail.plai2.ying.fitjournal.ui.workout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.plai2.ying.fitjournal.room.Exercise;
import com.gmail.plai2.ying.fitjournal.R;

import java.util.List;

public class WorkoutFragment extends Fragment {

    private WorkoutViewModel viewModel;
    private RecyclerView exerciseRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workout, container, false);
        exerciseRecyclerView = root.findViewById(R.id.exercise_rv);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        exerciseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        exerciseRecyclerView.setHasFixedSize(true);
        final ExerciseAdapter adapter = new ExerciseAdapter();
        exerciseRecyclerView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(getActivity()).get(WorkoutViewModel.class);
        viewModel.getAllExercises().observe(getViewLifecycleOwner(), new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                adapter.setExercises(exercises);
                Toast.makeText(getActivity(), "Work in progress.", Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getExerciseInput().observe(getViewLifecycleOwner(), new Observer<Exercise>() {
            @Override
            public void onChanged(Exercise exercise) {
                viewModel.deleteAll();
            }
        });
    }
}