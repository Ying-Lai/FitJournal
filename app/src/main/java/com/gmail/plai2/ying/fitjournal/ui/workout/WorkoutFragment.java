package com.gmail.plai2.ying.fitjournal.ui.workout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.plai2.ying.fitjournal.MainActivity;
import com.gmail.plai2.ying.fitjournal.room.CompletedExerciseItem;
import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.ExerciseType;
import com.gmail.plai2.ying.fitjournal.room.TypeConverters;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class WorkoutFragment extends Fragment {

    // UI fields
    private WorkoutViewModel mViewModel;
    private RecyclerView mCompletedExerciseRV;
    private MaterialToolbar mToolbar;
    private CompletedExerciseAdapter mAdapter;
    private MaterialTextView mWorkoutInstructionTV;

    public WorkoutFragment() {
        // To enable menu for this fragment
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // To enable menu for this fragment
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Initialize fields and variables
        View root = inflater.inflate(R.layout.fragment_workout, container, false);
        mCompletedExerciseRV = root.findViewById(R.id.completed_exercise_rv);
        mToolbar = root.findViewById(R.id.workout_tb);
        mWorkoutInstructionTV = root.findViewById(R.id.workout_instruction_tv);
        // Setup app tool bar
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Workout");
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Setup adaptor
        mCompletedExerciseRV.setLayoutManager(new LinearLayoutManager(getContext()));
        mCompletedExerciseRV.setHasFixedSize(true);
        mAdapter = new CompletedExerciseAdapter(Collections.emptyList(), new CompletedExerciseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                CompletedExerciseItem currentCompletedExercise = mAdapter.getExerciseItem(position);
                ArrayList<String> exerciseInfo = new ArrayList<>();
                if (currentCompletedExercise.getExerciseType() == ExerciseType.CARDIO) {
                    Bundle bundle = new Bundle();
                    exerciseInfo.add(Integer.toString(TypeConverters.exerciseTypetoInt(ExerciseType.CARDIO)));
                    exerciseInfo.add(currentCompletedExercise.getExerciseName());
                    exerciseInfo.add(Integer.toString(currentCompletedExercise.getMId()));
                    exerciseInfo.add(TypeConverters.sessionListToString(currentCompletedExercise.getListOfCardioSessions()));
                    bundle.putStringArrayList(MainActivity.EXERCISE_INFO, exerciseInfo);
                    if (Navigation.findNavController(view).getCurrentDestination().getId() == R.id.navigation_to_workout) {
                        Navigation.findNavController(view).navigate(R.id.to_cardio_session, bundle);
                    }
                } else if (currentCompletedExercise.getExerciseType() == ExerciseType.STRENGTH){
                    Bundle bundle = new Bundle();
                    exerciseInfo.add(Integer.toString(TypeConverters.exerciseTypetoInt(ExerciseType.STRENGTH)));
                    exerciseInfo.add(currentCompletedExercise.getExerciseName());
                    exerciseInfo.add(Integer.toString(currentCompletedExercise.getMId()));
                    exerciseInfo.add(TypeConverters.setListToString(currentCompletedExercise.getListOfSets()));
                    bundle.putStringArrayList(MainActivity.EXERCISE_INFO, exerciseInfo);
                    if (Navigation.findNavController(view).getCurrentDestination().getId() == R.id.navigation_to_workout) {
                        Navigation.findNavController(view).navigate(R.id.to_strength_session, bundle);
                    }
                } else if (currentCompletedExercise.getExerciseType() == ExerciseType.CALISTHENICS) {
                    Bundle bundle = new Bundle();
                    exerciseInfo.add(Integer.toString(TypeConverters.exerciseTypetoInt(ExerciseType.CALISTHENICS)));
                    exerciseInfo.add(currentCompletedExercise.getExerciseName());
                    exerciseInfo.add(Integer.toString(currentCompletedExercise.getMId()));
                    exerciseInfo.add(TypeConverters.setListToString(currentCompletedExercise.getListOfSets()));
                    bundle.putStringArrayList(MainActivity.EXERCISE_INFO, exerciseInfo);
                    if (Navigation.findNavController(view).getCurrentDestination().getId() == R.id.navigation_to_workout) {
                        Navigation.findNavController(view).navigate(R.id.to_calisthenics_session, bundle);
                    }
                }
            }
        });
        mCompletedExerciseRV.setAdapter(mAdapter);

        // Observe live data
        mViewModel = ViewModelProviders.of(getActivity()).get(WorkoutViewModel.class);
        Date today = new Date();
        today.setTime(0);
        mViewModel.getAllCompletedExercisesByDate(today).observe(getViewLifecycleOwner(), new Observer<List<CompletedExerciseItem>>() {
            @Override
            public void onChanged(List<CompletedExerciseItem> completedExerciseItems) {
                if (completedExerciseItems.size() != 0) {
                    mWorkoutInstructionTV.setVisibility(View.INVISIBLE);
                } else {
                    mWorkoutInstructionTV.setVisibility(View.VISIBLE);
                }
                mAdapter.setCompletedExerciseItems(completedExerciseItems);
            }
        });
    }

    // Setup menu options
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.exercise_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}