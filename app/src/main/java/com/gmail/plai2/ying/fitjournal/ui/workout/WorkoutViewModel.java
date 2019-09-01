package com.gmail.plai2.ying.fitjournal.ui.workout;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gmail.plai2.ying.fitjournal.Exercise;
import com.gmail.plai2.ying.fitjournal.ExerciseRepository;

import java.util.List;

public class WorkoutViewModel extends AndroidViewModel {

    private ExerciseRepository repository;
    private LiveData<List<Exercise>> allExercises;

    public WorkoutViewModel(@NonNull Application application) {
        super(application);
        repository = new ExerciseRepository(application);
        allExercises = repository.getAllExercises();
    }

    public void insert(Exercise exercise) {
        repository.insert(exercise);
    }

    public void update(Exercise exercise) {
        repository.update(exercise);
    }

    public void delete(Exercise exercise) {
        repository.delete(exercise);
    }

    public LiveData<List<Exercise>> getAllExercises() {
        return allExercises;
    }
}