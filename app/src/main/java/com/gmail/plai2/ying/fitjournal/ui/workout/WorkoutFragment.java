package com.gmail.plai2.ying.fitjournal.ui.workout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.plai2.ying.fitjournal.room.CompletedExerciseItem;
import com.gmail.plai2.ying.fitjournal.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class WorkoutFragment extends Fragment {

    private WorkoutViewModel mViewModel;
    private RecyclerView mCompletedExerciseRV;

    public WorkoutFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workout, container, false);
        mCompletedExerciseRV = root.findViewById(R.id.completed_exercise_rv);
        MaterialToolbar toolbar = root.findViewById(R.id.workout_tb);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Workout");
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCompletedExerciseRV.setLayoutManager(new LinearLayoutManager(getContext()));
        mCompletedExerciseRV.setHasFixedSize(true);
        final CompletedExerciseAdapter adapter = new CompletedExerciseAdapter();
        mCompletedExerciseRV.setAdapter(adapter);
        mViewModel = ViewModelProviders.of(getActivity()).get(WorkoutViewModel.class);
        // will need to change all completed exercise to all completed exercise by date
        mViewModel.getAllCompletedExercises().observe(getViewLifecycleOwner(), new Observer<List<CompletedExerciseItem>>() {
            @Override
            public void onChanged(List<CompletedExerciseItem> completedExerciseItems) {
                adapter.setCompletedExerciseItems(completedExerciseItems);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.exercise_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}